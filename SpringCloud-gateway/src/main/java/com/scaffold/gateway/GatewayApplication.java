package com.scaffold.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 网关启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.scaffold.commons.*")
public class GatewayApplication {
    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class, args);
    }
}
