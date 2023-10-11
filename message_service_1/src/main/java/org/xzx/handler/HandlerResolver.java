package org.xzx.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.pojo.messageBean.Message;
import org.xzx.pojo.messageBean.ReceivedGroupMessage;
import org.xzx.pojo.messageBean.ReceivedPrivateMessage;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Iterator;
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


    public HandlerResolver(Object bean, BeanFactory factory, Method... declaredMethods) {
        this.bean = bean;
        this.factory = factory;
        this.declaredMethods = declaredMethods;
        threadPoolTaskExecutor = (ThreadPoolTaskExecutor) factory.getBean("taskExecutor");
        this.resolve();
    }

    private void resolve() {
        for (Method method : declaredMethods) {
            RobotListenerHandler annotation = method.getAnnotation(RobotListenerHandler.class);
            if (annotation == null) continue;
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
        if (!handlers.containsKey(messageClazz))
            handlers.put(messageClazz, new PriorityQueue<>(EventHandler::compareOrder));
        handlers.get(messageClazz).offer(new EventHandler(annotation, invoke));
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
        handlers.keySet().forEach(messageClazz -> {
            if (messageClazz.isAssignableFrom(message.getClass())) {
                handlers.get(messageClazz).forEach(handler -> {
                    if (handler.annotation().concurrency()) {
                        threadPoolTaskExecutor.execute(() -> {
                            handler.accept(message);
                        });
                    } else {
                        handler.accept(message);
                    }
                });
            }
        });
    }
}
