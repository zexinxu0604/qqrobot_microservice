package org.xzx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class messageServiceApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(messageServiceApplication2.class, args);
    }
}