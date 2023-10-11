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
public class MessageServiceApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication2.class, args);
    }
}