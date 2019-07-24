package com.cetc.plan;

import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.ResultEntry;
import com.cetc.plan.demand.service.CalcService;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.utils.DemandUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class DemandsApplicationTests {

    @Resource
    DemandMapper demandMapper;
    @Resource
    DemandService demandService;
    @Resource
    DemandUtils demandUtils;
    @Resource
    CalcService calcService;

    @Test
    public void contextLoads() {
        List<double[]> list = new ArrayList<>();
        double[] kd = {78.45687,12.4576};
        list.add(kd);
        ResultEntry<String> resultEntry =  calcService.createVisitCalcRequestFile("GF1","2019-07-19 9:11:24","2019-07-20 9:11:24",list);
        System.out.println(resultEntry);
    }








}
