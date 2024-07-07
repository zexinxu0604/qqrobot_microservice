package org.xzx.handler;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.type.AnnotationMetadata;
import org.xzx.annotation.RobotListener;

import java.lang.reflect.Method;

public class RobotEventPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory factory;

    private final Logger logger = LoggerFactory.getLogger(RobotEventPostProcessor.class);

    @Override
    public void setBeanFactory(@NotNull BeanFactory beanFactory) throws BeansException {
        this.factory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if(factory.containsBean(beanName) && factory.getBeanDefinition(beanName) instanceof AnnotatedBeanDefinition definition) {
            AnnotationMetadata metadata = definition.getMetadata();
            if(metadata.hasAnnotation(RobotListener.class.getName())) {
                Class<?> beanClass = bean.getClass();
                Method[] declaredMethods = beanClass.getDeclaredMethods();
                HandlerResolver resolver = new HandlerResolver(bean, factory, declaredMethods);
            }
        }
        return bean;
    }
}
