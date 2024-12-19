package org.xzx.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xzx.annotation.GroupServiceAuth;
import org.xzx.bean.enums.GroupServiceEnum;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.service.Gocq_service;

@Component
@Aspect
@Order(1)
public class ExceptionAspect {

    @Autowired
    private Gocq_service gocqService;

    @Around("@annotation(org.xzx.annotation.GroupServiceAuth)")
    public Object groupServiceAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("groupServiceAuth around");
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (IllegalArgumentException e) {
            ReceivedGroupMessage receivedGroupMessage = (ReceivedGroupMessage) joinPoint.getArgs()[0];
            GroupServiceAuth groupServiceAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(GroupServiceAuth.class);
            gocqService.send_group_message(receivedGroupMessage.getGroup_id(), e.getMessage());
        } catch (Throwable e) {
            ReceivedGroupMessage receivedGroupMessage = (ReceivedGroupMessage) joinPoint.getArgs()[0];
            GroupServiceAuth groupServiceAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(GroupServiceAuth.class);
            GroupServiceEnum service = groupServiceAuth.service();
            gocqService.send_group_message(receivedGroupMessage.getGroup_id(), service.getServiceDesc() + " 服务异常" + e.getMessage());
        }
        return null;
    }

}
