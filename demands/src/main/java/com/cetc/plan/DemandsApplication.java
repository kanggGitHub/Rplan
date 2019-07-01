package com.cetc.plan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan({"com.cetc.plan.demand.dao"})
@EnableEurekaClient
public class DemandsApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DemandsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(DemandsApplication.class);
    }
}
