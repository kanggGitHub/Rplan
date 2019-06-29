package com.cetc.plan.demand.controller;



import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.exception.DemandException;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.LogUtils;
import com.cetc.plan.utils.R;
import com.cetc.plan.utils.ResultCode;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 需求筹划_需求信息 前端控制器
 * </p>
 *
 * @author kg
 * @since 2019-06-20
 */
@CrossOrigin
@Controller
@RequestMapping("/demands")
public class DemandController {
    private static final Logger LOG = LogUtils.getLogger(DemandController.class);

    @Resource
    DemandService demandService;

   /**
    * @Description 获取所有国家信息--目标库
    * @Author kg
    * @Param []
    * @Date 17:06 2019/6/20
    */
    @RequestMapping(value = "/getAllCountries")
    @ResponseBody
    public R getAllCountries(){
        try {
            List<String> countryList = demandService.selectAllCountries();
            return R.ok().put("data",countryList);
        }catch (DemandException e){
            LOG.error(e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description 根据国家名字查询其所有城市--目标库
     * @Author kg
     * @Param [name]
     * @Date 8:28 2019/6/21
     */
    @RequestMapping("/getTargetByCountryName")
    @ResponseBody
    public R getTargetByCountryName(@RequestParam(value = "countryName",required = false) String  countryName){
        try {
            if (StringUtils.isEmpty(countryName)){
                return R.error(ResultCode.PARAMETER.getValue(),"参数错误");
            }
            List<CoretargetEntity> targetList = demandService.selectTargetByName(countryName);
            return R.ok().put("data",targetList);
        }catch (DemandException e){
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description 根据录入信息模糊搜索--目标库
     * @Author kg
     * @Param [name]
     * @Date 9:56 2019/6/21
     */
    @RequestMapping("/getTargetByName")
    @ResponseBody
    public R getTargetByName(@RequestParam(value = "name",required = false) String name){
        try{
            if (StringUtils.isEmpty(name)){
                return R.error(ResultCode.PARAMETER.getValue(),"参数错误");
            }
            List<Map> targetList = demandService.selectPageCoretarge(name);
            return R.ok().put("data",targetList);
        }catch (DemandException e){
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param []
     * @Date 10:18 2019/6/24
     */
    @RequestMapping(value = "/saveDemand",method = RequestMethod.POST)
    @ResponseBody
    public R saveDemand(@RequestBody DemandEntity demandModel){
        try{
            DemandEntity saveDemand = demandService.selectDemandByXqbh(demandModel.getXqbh());
            if(saveDemand!=null){
                return R.error("编号已存在，请重新录入！");
            }
            demandService.saveDemand(demandModel);
            return R.ok();
        }catch (DemandException e){
            LOG.error("查询异常："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description //TODO 需求创建获取卫星标识。
     * @Author kg
     * @Param []
     * @Date 9:10 2019/6/26
     */
    @RequestMapping(value = "getSatelliteInfos")
    @ResponseBody
    public R getSatelliteInfos() {
        try{
            List<Map<String, Object>> satelliteInfos = demandService.getSatelliteInfos();
            return R.ok().put("data",satelliteInfos);
        }catch (DemandException e){
            LOG.error("查询异常："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description //TODO 获取区域内目标--目标库
     * @Author kg
     * @Param [params]
     * @Date 10:58 2019/6/26
     */
    @RequestMapping("/getAreaTarget")
    @ResponseBody
    public R getAreaTarget(@RequestParam Map params){
        //判断前台传值是否为空 如果为空直接返回
        if (DemandUtils.isEmpyt(params)) {
            return R.error("参数不能为空");
        }
        try{
            List<CoretargetEntity> targetCoordinate = demandService.getAreaTarget(params);
            return R.ok().put("data",targetCoordinate);
        }catch (DemandException e){
            LOG.error("查询异常："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }
}

