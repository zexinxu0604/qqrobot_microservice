package org.xzx.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzx.bean.enums.MessageBreakCode;
import org.xzx.bean.messageUtil.MessageBreaker;

import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class MessageBreakerConfig {
    @Bean("messageBreaker")
    public ThreadLocal<MessageBreaker> messageBreaker() {
        return new ThreadLocal<MessageBreaker>();
    }
}
