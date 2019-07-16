package com.cetc.plan;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.model.TargetInfoEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.R;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class DemandsApplicationTests {

    @Resource
    DemandMapper demandMapper;
    @Resource
    DemandService demandService;
    @Resource
    DemandUtils demandUtils;

    @Test
    public void contextLoads() {
        List<Map<String, String>> saltelites = new ArrayList<>();
        Map map = new HashMap();
        map.put("mbbh","12");
        map.put("wxbs","wx-123");
        saltelites.add(map);
        map = new HashMap();
        map.put("mbbh","12");
        map.put("wxbs","wx-123");
        saltelites.add(map);
        map = new HashMap();
        map.put("mbbh","13");
        map.put("wxbs","wx-123");
        saltelites.add(null);

        System.out.println();
    }








}
