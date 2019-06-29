package com.cetc.plan;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.service.DemandRedisService;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;



@RunWith(SpringRunner.class)
@SpringBootTest
public class DemandsApplicationTests {

    @Resource
    DemandService demandService;

    @Resource
    DemandMapper demandMapper;

    @Autowired
    DemandRedisService demandRedisService;

    @Test
    public void contextLoads() {
        List<CoretargetEntity> countrybynames = demandService.selectTargetByName("中国");
        System.out.println("countrybynames===>>>"+R.ok().put("data",countrybynames));
    }
    @Test
    public void testis(){
        List<String> countryList = demandService.selectAllCountries();
        System.out.println(R.ok().put("data",countryList));
    }

    @Test
    public void testi1(){
        Page page = new Page(1,50);
        IPage<CoretargetEntity> iPage = demandMapper.selectPageCoretarge(page,"美");
        List<CoretargetEntity> listcoretargetentity = iPage.getRecords();
        System.out.println(R.ok().put("data",listcoretargetentity));
        Map<String, List<CoretargetEntity>> collect = listcoretargetentity.stream()
                .collect(Collectors.groupingBy(t -> t.getGjdq()));
        List<Map> returnList = DemandUtils.dataGrouping(collect);
        System.out.println(R.ok().put("data",returnList));
    }
    @Test
    public void testi2(){
        List<Map<String, Object>> countryList = demandService.getSatelliteInfos();
        System.out.println(R.ok().put("data",countryList));
    }

    @Test
    public void testi3(){
        Map<String,Object> map = new HashMap<>();
        map.put("leftLng",84.915356);
        map.put("rightLng",126.469838);
        map.put("leftLat",40.801653);
        map.put("rightLat",47.63171);
        List<CoretargetEntity> targetCoordinate = demandService.getAreaTarget(map);
        System.out.println(R.ok().put("data",targetCoordinate));
    }

    @Test
    public void testi4(){
        UUID uuid = UUID.randomUUID();
        try {
            System.out.println(System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis());
        }catch (Exception e){

        }

    }

}
