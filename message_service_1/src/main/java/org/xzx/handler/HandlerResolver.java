package org.xzx.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.bean.messageBean.Message;
import org.xzx.configs.Constants;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Consumer;


public class HandlerResolver {
    private final Object bean;
    private final BeanFactory factory;
    private final Method[] declaredMethods;
    private static final Map<Class<? extends Message>, PriorityQueue<EventHandler>> handlers = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(HandlerResolver.class);

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor = null;

    private static final Map<Class<? extends Message>, Map<String, PriorityQueue<EventHandler>>> fullMessageHandlers = new HashMap<>();

    private static final Map<Class<? extends Message>, Map<String, PriorityQueue<EventHandler>>> regexMessageHandlers = new HashMap<>();


    public HandlerResolver(Object bean, BeanFactory factory, Method... declaredMethods) {
        this.bean = bean;
        this.factory = factory;
        this.declaredMethods = declaredMethods;
        threadPoolTaskExecutor = (ThreadPoolTaskExecutor) factory.getBean("taskExecutor");
        this.resolve();
    }

    private void resolve() {
        Class<?> superclass = bean.getClass().getSuperclass();
        Map<String, Method> superclassDeclaredMethods = new HashMap<>();
        if (superclass != null) {
            Method[] superclassMethods = superclass.getDeclaredMethods();
            for (Method method : superclassMethods) {
                superclassDeclaredMethods.put(method.getName(), method);
            }
        }

        for (Method method : declaredMethods) {
            RobotListenerHandler annotation = method.getAnnotation(RobotListenerHandler.class);
            if (annotation == null) {
                String methodName = method.getName();
                if (superclassDeclaredMethods.containsKey(methodName)) {
                    annotation = superclassDeclaredMethods.get(methodName).getAnnotation(RobotListenerHandler.class);
                } else {
                    continue;
                }
            }
            Class<? extends Message> message = this.methodEvent(method);
            this.addHandlerMethod(annotation, message, method);
        }
    }

    private Class<? extends Message> methodEvent(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length < 1 || parameters[0].getType().isAssignableFrom(Message.class))
            throw new IllegalArgumentException("监听器Handler方法的第一个参数必须是Message及其子类型!");
        return parameters[0].getType().asSubclass(Message.class);
    }

    private void addHandlerMethod(RobotListenerHandler annotation, Class<? extends Message> messageClazz, Method method) {
        Consumer<Message> invoke = message -> this.invokeMethod(method, message);
        if (!fullMessageHandlers.containsKey(messageClazz)) {
            fullMessageHandlers.put(messageClazz, new HashMap<>());
        }

        if (!regexMessageHandlers.containsKey(messageClazz)) {
            regexMessageHandlers.put(messageClazz, new HashMap<>());
        }

        if (annotation.isFullMatch() && !fullMessageHandlers.get(messageClazz).containsKey(annotation.regex())) {
            fullMessageHandlers.get(messageClazz).put(annotation.regex(), new PriorityQueue<>(EventHandler::compareOrder));
        } else if (!annotation.isFullMatch() && !regexMessageHandlers.get(messageClazz).containsKey(annotation.regex())) {
            regexMessageHandlers.get(messageClazz).put(annotation.regex(), new PriorityQueue<>(EventHandler::compareOrder));
        }

        //为了支持全匹配的正则表达式
        if (!regexMessageHandlers.get(messageClazz).containsKey(Constants.ALL_REGEX)) {
            regexMessageHandlers.get(messageClazz).put(Constants.ALL_REGEX, new PriorityQueue<>(EventHandler::compareOrder));
        }


        if (!annotation.isAllRegex()) {
            if (annotation.isFullMatch()) {
                fullMessageHandlers.get(messageClazz).get(annotation.regex()).add(new EventHandler(annotation, invoke));
            } else {
                regexMessageHandlers.get(messageClazz).get(annotation.regex()).add(new EventHandler(annotation, invoke));
            }
        } else {
            regexMessageHandlers.get(messageClazz).get(Constants.ALL_REGEX).add(new EventHandler(annotation, invoke));

        }

    }

    private void invokeMethod(Method method, Message message) {
        Object[] paramObjs = new Object[method.getParameterCount()];
        paramObjs[0] = message;
        Parameter[] parameters = method.getParameters();
        for (int i = 1; i < parameters.length; i++) {
            Object object = factory.getBean(parameters[i].getType());
            paramObjs[i - 1] = object;
        }
        try {
            method.invoke(bean, paramObjs);
        } catch (ReflectiveOperationException exception) {
            logger.error("执行机器人事件监听器Handler时出错，当前监听器: " + bean, exception);
        }
    }


    public static void handleEvent(Message message) {
        PriorityQueue<EventHandler> fullMatchQueue = fullMessageHandlers.getOrDefault(message.getClass(), new HashMap<>()).getOrDefault(message.getRaw_message(), null);
        if (fullMatchQueue != null) {
            for (EventHandler handler : fullMatchQueue) {

                if (handler.annotation().concurrency()) {
                    threadPoolTaskExecutor.execute(() -> handler.accept(message));
                } else {
                    handler.accept(message);
                }
                if (handler.annotation().shutdown()) {
                    return;
                }
            }
        }

        Map<String, PriorityQueue<EventHandler>> regexMatchMap = regexMessageHandlers.getOrDefault(message.getClass(), null);
        if (regexMatchMap == null) {
            return;
        }
        for (String regex : regexMatchMap.keySet()) {
            if (message.getRaw_message().matches(regex)) {
                PriorityQueue<EventHandler> regexMatchQueue = regexMatchMap.get(regex);
                for (EventHandler handler : regexMatchQueue) {
                    System.out.println("命中正则表达式: " + regex);
                    if (handler.annotation().concurrency()) {
                        threadPoolTaskExecutor.execute(() -> handler.accept(message));
                    } else {
                        handler.accept(message);
                    }
                    if (handler.annotation().shutdown()) {
                        return;
                    }
                }
            }
        }
    }
}
