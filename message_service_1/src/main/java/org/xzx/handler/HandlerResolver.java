package org.xzx.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.xzx.annotation.RobotListenerHandler;
import org.xzx.pojo.messageBean.Message;
import org.xzx.pojo.messageBean.Received_Group_Message;
import org.xzx.pojo.messageBean.Received_Private_Message;
import org.xzx.pojo.messageBean.Send_Message.Group_Message;

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

    public HandlerResolver(Object bean, BeanFactory factory, Method... declaredMethods) {
        this.bean = bean;
        this.factory = factory;
        this.declaredMethods = declaredMethods;
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
            throw new IllegalArgumentException("监听器Handler方法的第一个参数必须是Event及其子类型!");
        return parameters[0].getType().asSubclass(Message.class);
    }

    private void addHandlerMethod(RobotListenerHandler annotation, Class<? extends Message> messageClazz, Method method) {
        Consumer<Message> invoke = event -> this.invokeMethod(method, event);
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
            final boolean[] shouldExitLoop = {false}; // 标志变量，用于跳出循环
            if (messageClazz.isAssignableFrom(message.getClass())) {
                handlers.get(messageClazz).forEach(handler -> {
                    if (shouldExitLoop[0]) {
                        return; // 如果标志为true，直接退出循环
                    }
                    if (handler.annotation().concurrency()) {
                        new Thread(() -> {
                            if (message instanceof Received_Group_Message receivedGroupMessage) {
                                handler.acceptIfContainsId(receivedGroupMessage.getGroup_id(), receivedGroupMessage);
                            } else if (message instanceof Received_Private_Message receivedPrivateMessage) {
                                handler.acceptIfContainsId(receivedPrivateMessage.getSender().getUser_id(), receivedPrivateMessage);
                            } else {
                                handler.accept(message);
                            }
                        }, "robot-handler-" + System.currentTimeMillis()).start();
                        if (handler.annotation().isBlock()) {
                            shouldExitLoop[0] = true; // 设置标志为true，以便在下一次循环时退出
                        }
                    } else {
                        if (message instanceof Received_Group_Message receivedGroupMessage) {
                            handler.acceptIfContainsId(receivedGroupMessage.getGroup_id(), receivedGroupMessage);
                        } else if (message instanceof Received_Private_Message receivedPrivateMessage) {
                            handler.acceptIfContainsId(receivedPrivateMessage.getSender().getUser_id(), receivedPrivateMessage);
                        } else {
                            handler.accept(message);
                        }
                        if (handler.annotation().isBlock()) {
                            shouldExitLoop[0] = true; // 设置标志为true，以便在下一次循环时退出
                        }
                    }
                });
            }
        });
    }
}
