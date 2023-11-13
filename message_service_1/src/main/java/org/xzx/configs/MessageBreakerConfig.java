package org.xzx.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xzx.bean.enums.MessageBreakCode;
import org.xzx.bean.messageUtil.MessageBreaker;

@Configuration
public class MessageBreakerConfig {
    @Bean("messageBreaker")
    public MessageBreaker messageBreaker() {
        return new MessageBreaker(MessageBreakCode.CONTINUE);
    }
}
