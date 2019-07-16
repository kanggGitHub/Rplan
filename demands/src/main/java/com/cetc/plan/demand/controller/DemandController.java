package com.cetc.plan.demand.controller;



import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.config.StaticConst;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.demand.service.DemandRedisService;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.exception.DemandException;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.LogUtils;
import com.cetc.plan.utils.R;
import com.cetc.plan.config.ResultCode;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    DemandRedisService demandRedisService;

    /**
     * @Description //TODO 更新缓存信息
     * @Author kg
     * @Param []
     * @Date 10:08 2019/7/11
     */
    @RequestMapping("/updataRedis")
    @ResponseBody
    public R updataRedis(){
        try{
            demandService.updataRedis();
            return R.ok("更新成功");
        }catch (DemandException e){
            LOG.error("更新失败："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("更新失败："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description //TODO 获取数据--字典表
     * @Author kg
     * @Param [typeName：]
     * @Date 10:45 2019/7/3
     */
    @RequestMapping("/getDictionaryData")
    @ResponseBody
    public R DictionaryData(@RequestParam("typeName")String typeName){
        try{
            if (StringUtils.isEmpty(typeName)){
                return R.error(ResultCode.PARAMETER.getValue(),"参数错误");
            }
            Map<String,Object> map = demandRedisService.getMapRedisByKey("dictionary");
            List<JSONObject> list = new ArrayList<>();
                if("demandType".equals(typeName)){ //需求类型
                list  = (List<JSONObject>) map.get("REQUIREMENTTYPE");
            }else if("demandStatus".equals(typeName)){ //需求状态
                list  = (List<JSONObject>) map.get("REQUIREMENTSTATUS");
            }else if("sourceType".equals(typeName)){ //来源
                list  = (List<JSONObject>) map.get("SOURCETYPE");
            }else if("targetType".equals(typeName)){ //目标类型
                list  = (List<JSONObject>) map.get("TARGETTYPE");
            }
            return R.ok().put("data",list);
        }catch (DemandException e){
            LOG.error("查询异常："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description //TODO 获取目标类型--字典表
     * @Author kg
     * @Param []
     * @Date 10:45 2019/7/3
     */
    @RequestMapping("/getRequirementType")
    @ResponseBody
    public R getXqlx(){
        try{
            Map<String,Object> map = demandRedisService.getMapRedisByKey("dictionary");
            List<JSONObject> list  = (List<JSONObject>) map.get("REQUIREMENTTYPE");
            return R.ok().put("data",list);
        }catch (DemandException e){
            LOG.error("查询异常："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description //TODO 获取需求状态 ---需求状态
     * @Author kg
     * @Param []
     * @Date 10:45 2019/7/3
     */
    @RequestMapping("/getRequirementStatus")
    @ResponseBody
    public R getRequirementStatus(){
        try{
            Map<String,Object> map = demandRedisService.getMapRedisByKey("dictionary");
            List<JSONObject> list  = (List<JSONObject>) map.get("REQUIREMENTSTATUS");
            return R.ok().put("data",list);
        }catch (DemandException e){
            LOG.error("查询异常："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }


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
    public R getTargetByCountryName(ParamEntity param){
        try {
            if (StringUtils.isEmpty(param.getCountryName())){
                return R.error(ResultCode.PARAMETER.getValue(),"参数错误");
            }
            Map<String,Object> returnMap = demandService.selectTargetByName(param);
            return R.ok().put("data",returnMap);
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
    public R getAreaTarget(ParamEntity param){
        //判断前台传值是否为空 如果为空直接返回
        try{
            Map<String,Object> returnMap = demandService.getAreaTarget(param);
            return R.ok().put("data",returnMap);
        }catch (DemandException e){
            LOG.error("查询异常："+e.getMessage());
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }


    /**
     * @Description //TODO 模糊搜索国家、城市列表
     * @Author kg
     * @Param [vagueName, countryName, page]
     * @Date 8:22 2019/7/2
     */
    @RequestMapping("/getVagueCountries")
    @ResponseBody
    public R getVagueCountries(ParamEntity param){
        try {
            Map<String,Object> returnMap = demandService.vagueCountryByname(param);
            return R.ok().put("data",returnMap);
        }catch (DemandException e){
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }

    /**
     * @Description //TODO 获取需求列表 复杂项
     * @Author kg
     * @Param [page]
     * @Date 8:04 2019/7/5
     */
    @RequestMapping("/getRequirementsList")
    @ResponseBody
    public R getRequirementsList(ParamEntity param){
        try {
            Map<String,Object> returnMap = demandService.getRequirementsList(param);
            return R.ok().put("data",returnMap);
        }catch (DemandException e){
            return R.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            LOG.error("数据库服务器异常："+e.getMessage());
            return R.error();
        }
    }
}

