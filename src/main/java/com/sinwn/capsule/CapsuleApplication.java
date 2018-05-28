package com.sinwn.capsule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.sinwn.capsule.mapper")
public class CapsuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapsuleApplication.class, args);
    }
}
