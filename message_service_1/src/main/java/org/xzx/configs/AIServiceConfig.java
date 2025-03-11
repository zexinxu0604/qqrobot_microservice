package org.xzx.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzx.bean.chatBean.ChatAIRole;
import org.xzx.bean.chatBean.GroupAIContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AIServiceConfig {
    @Bean(name = "group-chat-context")
    public Map<Long, GroupAIContext> getGroupAIChatContext() {
        return new ConcurrentHashMap<>();
    }
}
