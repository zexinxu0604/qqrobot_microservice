package org.xzx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.xzx.handler.RobotEventPostProcessor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import(RobotEventPostProcessor.class)
public class MessageServiceApplication1 {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(MessageServiceApplication1.class, args);
    }
}