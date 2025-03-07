package org.xzx.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzx.bean.enums.GroupServiceEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class GroupLockConfig {

    @Bean("groupServiceLockMap")
    public Map<Long, Map<GroupServiceEnum, ReentrantLock>> getGroupServiceLocks() {
        return new ConcurrentHashMap<>();
    }

}
