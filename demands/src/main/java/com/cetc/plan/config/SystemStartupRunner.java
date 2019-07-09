package com.cetc.plan.config;

import com.alibaba.fastjson.JSONObject;
import com.cetc.plan.demand.service.DemandRedisService;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    StaticConst staticConst;


    @Resource
    DemandRedisService demandRedisService;

    @Override
    public void run(String... args) throws Exception {
        Logger logger = LogUtils.getLogger(SystemStartupRunner.class);

        try {
            logger.info("初始化字典信息");
            List<JSONObject> list = demandService.getRunManageConfig();
            Map<String,Integer> lastId = demandService.getLastInsertId();
            if(lastId != null){
                staticConst.XQXX_XQBH_ID = lastId.get("XQBH");
                staticConst.MBXX_MBBH_ID = lastId.get("mbbh");
            }
            Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(t -> t.getString("classification")));
            demandRedisService.setRedisByKeyAndValue("dictionary",collect);
            String name = "";
            String value = "";
            String id = "";
            //初始化信息
            staticConst.setData(list,name,value,id);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
