package org.xzx.aspect;

import jakarta.annotation.PostConstruct;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xzx.annotation.GroupServiceAuth;
import org.xzx.bean.enums.GroupServiceEnum;
import org.xzx.bean.messageBean.ReceivedGroupMessage;
import org.xzx.service.GroupServiceService;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
@Order(2)
public class GroupServiceAuthAspect {

    @Autowired
    private GroupServiceService groupServiceService;

    @Autowired
    private ApplicationContext applicationContext;

    private Map<GroupServiceEnum, String> groupServiceMap;

    @PostConstruct
    public void init() {
        this.groupServiceMap = GroupServiceEnum.getGroupServiceEnumMap();
    }

    @Before("@annotation(org.xzx.annotation.GroupServiceAuth)")
    public void groupServiceAuth(JoinPoint joinPoint) throws Throwable {
        System.out.println("groupServiceAuth");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GroupServiceAuth groupServiceAuth = signature.getMethod().getAnnotation(GroupServiceAuth.class);
        ReceivedGroupMessage receivedGroupMessage = (ReceivedGroupMessage) joinPoint.getArgs()[0];
        GroupServiceEnum service = groupServiceAuth.service();
        if (!groupServiceMap.containsKey(service)) {
            throw new IllegalArgumentException("未知的服务");
        }
        if (!groupServiceService.checkServiceStatus(receivedGroupMessage.getGroup_id(), service)) {
            throw new IllegalArgumentException(service.getServiceDesc() + "服务未开启");
        }
    }

}
