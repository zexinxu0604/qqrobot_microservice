package org.xzx.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.xzx.handler.RobotEventPostProcessor;

@Configuration
public class messageConfigurations {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
