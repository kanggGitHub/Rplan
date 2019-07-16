package com.cetc.plan.config;

import com.cetc.plan.demand.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Classname SystemStartupRunner
 * @Description: TODO   初始化字典表信息
 * @author: kg
 * @Date: 2019/7/3 10:01
 */
@Component
@Order(value = 0)
public class SystemStartupRunner implements CommandLineRunner {

    @Autowired
    DemandService demandService;


    @Override
    public void run(String... args) throws Exception {
        try {
            demandService.updataRedis();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
