package org.xzx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.xzx.handler.RobotEventPostProcessor;

@SpringBootApplication
@EnableDiscoveryClient
@Import(RobotEventPostProcessor.class)
@MapperScan("org.xzx.dao")
@EnableScheduling
public class MessageServiceApplication1 {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        System.setProperty("druid.mysql.usePingMethod","false");
        SpringApplication.run(MessageServiceApplication1.class, args);
    }
}