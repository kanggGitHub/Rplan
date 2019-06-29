package com.cetc.plan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan({"com.cetc.plan.demand.dao"})
@EnableEurekaClient
public class DemandsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemandsApplication.class, args);
    }

}
