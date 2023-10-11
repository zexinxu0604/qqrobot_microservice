package org.xzx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.xzx.dao")
public class GroupImageApplication1 {
    public static void main(String[] args) {
        SpringApplication.run(GroupImageApplication1.class, args);
    }

}
