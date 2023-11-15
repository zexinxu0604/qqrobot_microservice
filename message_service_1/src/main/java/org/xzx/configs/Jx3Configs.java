package org.xzx.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jx3Configs {
    @Bean("jx3ServerOpenStatus")
    public Boolean jx3ServerOpenStatus() {
        return false;
    }
}
