package org.xzx.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class messageConfigurations {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
