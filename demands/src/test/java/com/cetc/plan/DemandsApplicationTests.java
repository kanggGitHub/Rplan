package com.cetc.plan;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.PageUtils;
import com.cetc.plan.utils.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Test
    public void contextLoads() {
//        Page page = new Page(1,15);
//        IPage<CoretargetEntity> coretargetEntityList = demandMapper.vagueCountryByname(page,"北","中国");
//        List<CoretargetEntity> coretargetEntities = coretargetEntityList.getRecords();
//        Map map = new HashMap<>();
//        map.put("list",coretargetEntities);
//        map.put("total",coretargetEntityList.getTotal());
//        System.out.println(R.ok().put("data",map));
    }

    @Test
    public void contextLoads1() {
        Page page = new Page(1,15);
        IPage<CoretargetEntity> coretargetEntityList = demandMapper.selectPageCoretarge(page,"中");
        List<CoretargetEntity> coretargetEntities = coretargetEntityList.getRecords();
        System.out.println(R.ok(coretargetEntityList.getTotal()).put("data",coretargetEntities));
    }
    @Test
    public void contextLoads12() {
//        Page page = new Page(1,15);
//        Map<String,Object> returnMap = demandService.selectTargetByName(page,"中国");
//        System.out.println(R.ok().put("data",returnMap));
        System.out.println(demandUtils.getUid());
        System.out.println(demandUtils.getUid());
        System.out.println(demandUtils.getUid());
        System.out.println(demandUtils.getUid());
    }


    @Test
    public void test5(){
//        Map<String,Integer> lastId = demandService.getLastInsertId();
//        System.out.println(lastId);
        List<DemandEntity> demandEntity = demandMapper.getRequirementDetails(6);
        ParamEntity paramEntity = new ParamEntity();
        List<DemandEntity> demandEntitys = demandMapper.getRequirementsList(paramEntity);

        System.out.println(R.ok().put("data",demandEntity));
    }


}
