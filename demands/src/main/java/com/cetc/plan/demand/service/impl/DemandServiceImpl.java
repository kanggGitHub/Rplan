package com.cetc.plan.demand.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetc.plan.config.StaticConst;
import com.cetc.plan.demand.model.ResultEntry;
import com.cetc.plan.demand.model.TargetVisitResponse;
import com.cetc.plan.demand.model.demand.CoretargetEntity;
import com.cetc.plan.demand.model.demand.DemandEntity;
import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.demand.SateliteEntity;
import com.cetc.plan.demand.model.demand.TargetInfoEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.demand.service.CalcService;
import com.cetc.plan.demand.service.DemandRedisService;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.exception.DemandException;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.config.ResultCode;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 需求筹划_需求信息 服务实现类
 * </p>
 *
 * @author kg
 * @since 2019-06-20
 */
@Slf4j
@Service
public class DemandServiceImpl extends ServiceImpl implements DemandService {

    private static Integer MBBH;
    private static Integer YRWBH;
    private static Boolean FULLBACK;
    @Resource
    DemandMapper demandMapper;

    @Resource
    StaticConst staticConst;

    @Resource
    DemandUtils demandUtils;

    @Resource
    DemandRedisService demandRedisService;

    @Resource
    CalcService calcService;



    /**
     * @Description //TODO 缓存信息
     * @Author kg
     * @Param []
     * @Date 10:06 2019/7/11
     */
    @Override
    public void updataRedis() throws DemandException {
        try {
            log.info("初始化字典信息");
            List<JSONObject> list = demandMapper.getRunManageConfig();
            Map<String,Object> lastId = demandMapper.getLastInsertId();
            Integer metataskId = demandMapper.getLastMetataskId();//侦查元任务编号
            staticConst.XQXX_XQBH_ID = Integer.parseInt(String.valueOf(lastId.get("xqbh")));
            staticConst.MBXX_MBBH_ID = Integer.parseInt(String.valueOf(lastId.get("mbbh")));
            staticConst.ZCYRW_YRWBH_ID = metataskId;
            Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(t -> t.getString("classification")));
            demandRedisService.setRedisByKeyAndValue("dictionary",collect);
            //初始化信息
            staticConst.setData(list);
        }catch (RedisException e){
            log.error("Redis没有开启或没有配置：",e);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"更新缓存失败: updataRedis");
        }catch (Exception e){
            log.error("初始化失败：",e);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"更新缓存失败: updataRedis");
        }
    }



    /**
     * @Description 查询所有国家
     * @Author kg
     * @Param []
     * @Date 15:12 2019/6/21
     */
    @Override
    public List<String> selectAllCountries() throws DemandException{
        try {
            log.info("查询所有国家信息================================");
            List<String> countryList = demandMapper.selectAllCountries();
            return countryList;
        }catch (Throwable t){
            log.error("查询失败: ",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: selectAllCountries");
        }

    }

    /**
     * @Description 根据国家名称查询相应的城市 分页
     * @Author kg
     * @Param [gjdq]
     * @Date 15:12 2019/6/21
     */
    @Override
    public Map<String,Object> selectTargetByName(ParamEntity param) throws DemandException{
        try {
            //分页查询
            log.info("根据国家名称查询相应的城市分页查询信息================================");
            demandUtils.setParamPage(param);
            List<CoretargetEntity> targetList=demandMapper.selectTargetByName(param);
            param.setCountFlag("true");
            List<CoretargetEntity> countTotal=demandMapper.selectTargetByName(param);
            String total= "";
            if(countTotal.size()>0){
                total = countTotal.get(0).getTotalCount();
            }
            //组装返回结果
            Map<String,Object> map = demandUtils.retrunMap(total,targetList);
            return map;
        }catch (Throwable t){
            log.error("ERROR:",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: selectTargetByName");
        }
    }


    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:03 2019/6/24
     */
    @Override
    public Integer saveDemand(DemandEntity demandEntity) throws DemandException {
        try {
            /*保存需求信息*/
            MBBH = staticConst.MBXX_MBBH_ID;
            Integer xqbh = demandEntity.getXqbh();
            //查询需求状态
            String xqzt = demandMapper.getRequirementStatu(xqbh);
            if(xqzt != null && !xqzt.equals(staticConst.XQXX_XQZT_WCH_ID)){
                throw new DemandException(ResultCode.FAILURE.getValue(),"需求已筹划或已取消，不能编辑！");
            }
            if(xqbh!=null){
                FULLBACK = true;
                log.info("更新保存需求信息>>>>>>>>>>>>>>>>>>>>>");
                demandMapper.delDemandInfo(xqbh);//删除该编号相关信息
            }else{
                FULLBACK = false;
                log.info("开始保存需求信息>>>>>>>>>>>>>>>>>>>>>");
                staticConst.XQXX_XQBH_ID+=1;//需求编号
                xqbh = staticConst.XQXX_XQBH_ID;
                demandEntity.setXqbh(xqbh);
            }
            // 根据需求编号生成需求标识号
            demandEntity.setXqbsh(demandUtils.getDemandSign(String.valueOf(xqbh)));
            //设置需求来源
            demandEntity.setLy(staticConst.XQXX_LY_ID);
            //初始化需求状态
            demandEntity.setXqzt(staticConst.XQXX_XQZT_WCH_ID);
            demandMapper.saveDemand(demandEntity);
            log.info("保存需求信息完成>>>>>>>>>>>>>>>>>>>>>");

            demandEntity.setJlbh(xqbh);//记录编号
            demandMapper.saveDemandStatus(demandEntity);
            log.info("保存需求状态信息完成>>>>>>>>>>>>>>>>>>>>>");


            /*获取所有目标信息*/
            List<TargetInfoEntity> valmap = demandEntity.getTaregetinfolist();
            demandUtils.setAllbh(valmap,xqbh);
            /*保存目标信息*/
            demandMapper.saveTargetInfo(valmap);
            log.info("保存需求目标信息完成>>>>>>>>>>>>>>>>>>>>>");

            /*目标信息按照目标类型分组*/
            Map<String, List<TargetInfoEntity>> collect = valmap.stream().collect(Collectors.groupingBy(t -> t.getMblx()));
            /*获取区域目标信息 单独处理*/
            List<TargetInfoEntity> areaList = collect.get(staticConst.MBXX_MBLX_QYMB_ID);
            List<TargetInfoEntity> ResultAreaList = demandUtils.getAreaList(areaList,xqbh);
            /*获取 点目标point、目标库amin、行政区域region 信息*/
            List<TargetInfoEntity> arpList = demandUtils.getARPList(collect,xqbh);
            if(demandUtils.isNotEmpty(ResultAreaList))
                arpList.addAll(ResultAreaList);
            /*保存目标坐标信息*/
            demandMapper.saveTargetZbInfo(arpList);
            log.info("保存需求目标坐标信息完成>>>>>>>>>>>>>>>>>>>>>");

            /*获取目标信息-卫星要求数据*/
            List<SateliteEntity> sateliteEntities = demandUtils.getHandleSatelite(valmap);
            /*保存目标信息-卫星要求*/
            if(demandUtils.isNotEmpty(sateliteEntities)) {
                demandMapper.saveSateliteInfo(sateliteEntities);
                log.info("保存需求目标信息---卫星要求完成>>>>>>>>>>>>>>>>>>>>>");
            }
            return demandEntity.getXqbh();
        }catch (DemandException d){
            throw new DemandException(d.getCode(),d.getMessage());
        }catch (Throwable t){
            if(!FULLBACK) {
                staticConst.XQXX_XQBH_ID -= 1;//需求编号
                staticConst.MBXX_MBBH_ID = MBBH;//目标编号
            }
            log.error("保存失败，回滚所有操作！:",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"保存需求信息失败: saveDemand");
        }
    }
    /**
     * @Description //TODO 需求创建获取卫星标识。
     * @Author kg
     * @Param []
     * @Date 9:10 2019/6/26
     */
    @Override
    public List<SateliteEntity> getSatelliteInfos() throws DemandException {
        try {
            List satelliteInfos = demandRedisService.getListRedisByKey("satelliteInfos");
            if(demandUtils.isNotEmpty(satelliteInfos)){
                satelliteInfos = (List<SateliteEntity>)satelliteInfos;
            }else {
                satelliteInfos = demandMapper.getSatelliteInfos();
                demandRedisService.setRedisByKeyAndValue("satelliteInfos", satelliteInfos);
            }
            return satelliteInfos;
        }catch (Throwable t){
            log.error("获取信息失败: ",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取卫星标识信息失败: getSatelliteInfos");
        }
    }
    /**
     * @Description //TODO 查询 重点目标库、坐标信息 分页
     * @Author kg
     * @Param [map]
     * @Date 12:00 2019/6/26
     */
    @Override
    public Map<String,Object>  getAreaTarget(ParamEntity param) throws DemandException {
        try {
            //分页查询
            log.info("获取重点目标库、坐标信息（分页查询）==========================");
            demandUtils.setParamPage(param);
            List<CoretargetEntity> coretargetinfo = demandMapper.getAreaTarget(param);
            param.setCountFlag("true");
            List<CoretargetEntity> countTotal = demandMapper.getAreaTarget(param);
            String total = "";
            if(countTotal!=null&&countTotal.size()>0){
                total = countTotal.get(0).getTotalCount();
            }
            Map<String,Object> map = demandUtils.retrunMap(total,coretargetinfo);
            return map;
        }catch (Throwable t){
            log.error("获取信息失败: ",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取信息失败: getAreaTarget");
        }
    }

    /**
     * @Description //TODO 获取城市国家信息 分页
     * @Author kg
     * @Param [page, vagueName, countryName]
     * @Date 11:07 2019/7/2
     */
    @Override
    public Map<String,Object> vagueCountryByname(ParamEntity param) throws DemandException {
        try {
            //分页查询
            log.info("获取城市国家信息（分页查询）==========================");
            demandUtils.setParamPage(param);
            List<CoretargetEntity> coretargetinfo = demandMapper.vagueCountryByname(param);
            param.setCountFlag("true");
            List<CoretargetEntity> countTotal = demandMapper.vagueCountryByname(param);
            String total = "";
            if(countTotal!=null&&countTotal.size()>0){
                total = countTotal.get(0).getTotalCount();
            }
            Map<String,Object> Map = demandUtils.retrunMap(total,coretargetinfo);
            return Map;
        }catch (Throwable t){
            log.error("获取信息失败: ",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取国家、城市信息失败: vagueCountryByname");
        }
    }

    /**
     * @Description //TODO 获取需求列表信息 详细信息 复杂项 分页
     * @Author kg
     * @Param []
     * @Date 16:04 2019/7/4
     */
    @Override
    public Map<String,Object> getRequirementsList(ParamEntity param) throws DemandException {
        try {
            Integer xqbh = param.getXqbh();
            if(-1 == xqbh||xqbh == null){//获取需求列表
                //分页查询
                log.info("获取需求列表信息==========================");
                demandUtils.setParamPage(param);
                List<DemandEntity> demandInfoList = demandMapper.getRequirementsList(param);
                param.setCountFlag("true");
                List<DemandEntity> countTotal = demandMapper.getRequirementsList(param);
                String total = "";
                if (countTotal != null && countTotal.size() > 0) {
                    total = countTotal.get(0).getTotalCount();
                }
                Map<String, Object> returnMap = demandUtils.retrunMap(total, demandInfoList);
                return returnMap;
            }else {
                //获取需求详情
                log.info("获取需求详情信息==========================编号："+xqbh);
                Map<String,Object> demandInfo = demandMapper.getRequirementInfo(xqbh);
                if(demandInfo==null){
                    throw new DemandException(ResultCode.FAILURE.getValue(),"无此需求，请重新选择！");
                }
                //获取需求目标、目标坐标信息
                List<TargetInfoEntity> targetInfoList = demandMapper.getRequirementZbInfo(xqbh);
                //分组处理区域目标
                Map<String, List<TargetInfoEntity>> collect = targetInfoList.stream().collect(Collectors.groupingBy(t -> t.getMblx()));
                List<TargetInfoEntity> areaList = collect.get(staticConst.MBXX_MBLX_QYMB_ID);
                int size = targetInfoList.size()-1;
                TargetInfoEntity targetInfo = null;
                for(int i = size;i>=0 ;i--){
                    targetInfo = targetInfoList.get(i);
                    if(staticConst.MBXX_MBLX_QYMB_ID.equals(targetInfo.getMblx())){
                        targetInfoList.remove(targetInfo);
                    }
                }
                TargetInfoEntity areatargetInfo = demandUtils.mergeRegion(areaList);
                //组装返回结果
                if(areatargetInfo!=null)
                    targetInfoList.add(areatargetInfo);
                //获取卫星标识并处理
                List<Map<String,Object>> saltelites = demandMapper.getSatelites(xqbh);
                demandUtils.mergeSaltelites(targetInfoList,saltelites);
                demandInfo.put("list",targetInfoList);
                return  demandInfo;
            }
        }catch (DemandException d){
            throw new DemandException(d.getCode(),d.getMessage());
        }catch (Throwable t){
            log.error("获取信息失败: ",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取需求信息失败: getRequirementsList");
        }
    }

    /**
     * @Description //TODO 访问计算
     * @Author kg
     * @Param []
     * @Date 8:28 2019/7/19
     */
    @SuppressWarnings("all")
    @Override
    public Map<String,Object> demandPlan(DemandEntity demandEntity) {
        try {

            YRWBH=staticConst.ZCYRW_YRWBH_ID;
            //返回的结果集
            Map<String,Object> returnMap = new HashMap<>();
            //获取所有目标信息
            List<TargetInfoEntity> valmap = new ArrayList<>();
            Integer xqbh = demandEntity.getXqbh();
            List<Integer> demandsId = demandEntity.getDemandsId();
            Map<String,String> demandTime;
            String yxqkssj = demandEntity.getYxqkssj();
            String yxqjssj = demandEntity.getYxqjssj();
            //卫星对应的目标经纬度集合
            Map<String,List<double[]>> statelliteMap = new HashMap<>();
            //目标携带的卫星
            List<String> statellites = new ArrayList<>();
            //卫星集合
            List<String> allStatellites = new ArrayList<>();
            // 访问计算合集
            List<TargetVisitResponse> visitResult = new ArrayList<>();
            //进行访问计算结果集
            ResultEntry<List<TargetVisitResponse>> listResult = new ResultEntry<>();
            if(xqbh!=null){
                //查询需求状态
                String status = demandMapper.getRequirementStatu(xqbh);
                if(status != null && !status.equals(staticConst.XQXX_XQZT_WCH_ID)){
                    throw new DemandException(ResultCode.FAILURE.getValue(),"需求已筹划或已取消，不能进行筹划！");
                }
                demandTime = demandMapper.getDemandsTime(xqbh);
                yxqkssj = demandTime.get("yxqkssj");
                yxqjssj = demandTime.get("yxqjssj");
                //获取所有目标信息
                valmap = demandMapper.getRequirementZbInfo(xqbh);
                //获取卫星标识并处理
                List<Map<String,Object>> saltelites = demandMapper.getSatelites(xqbh);
                demandUtils.mergeSaltelites(valmap,saltelites);
                //访问计算
                metataskMerge(valmap,statellites,allStatellites,statelliteMap,visitResult,listResult,yxqkssj,yxqjssj,returnMap,xqbh);
                returnMap.put("xqbh",xqbh);
            }else if(demandUtils.isNotEmpty(demandsId)){
                String status;
                List<TargetInfoEntity> targetmap;
                List<Map<String,Object>> saltelites;
                for(Integer demandId : demandsId){
                    demandTime = demandMapper.getDemandsTime(demandId);
                    yxqkssj = demandTime.get("yxqkssj");
                    yxqjssj = demandTime.get("yxqjssj");
                    statelliteMap = new HashMap<>();
                    allStatellites = new ArrayList<>();
                    //查询需求状态
                    status = demandMapper.getRequirementStatu(demandId);
                    if(status != null && !status.equals(staticConst.XQXX_XQZT_WCH_ID)){
                        throw new DemandException(ResultCode.FAILURE.getValue(),"需求已筹划或已取消，不能进行筹划！");
                    }
                    targetmap = demandMapper.getRequirementZbInfo(demandId);
                    //获取卫星标识并处理
                    saltelites = demandMapper.getSatelites(demandId);
                    demandUtils.mergeSaltelites(targetmap,saltelites);
                    valmap.addAll(targetmap);
                    //访问计算
                    metataskMerge(valmap,statellites,allStatellites,statelliteMap,visitResult,listResult,yxqkssj,yxqjssj,returnMap,demandId);
                }
                returnMap.put("demandsId",demandsId);
            }else{
                //保存需求信息
                saveDemand(demandEntity);
                //获取所有目标信息
                valmap = demandEntity.getTaregetinfolist();
                returnMap.put("xqbh",demandEntity.getXqbh());
                //访问计算
                metataskMerge(valmap,statellites,allStatellites,statelliteMap,visitResult,listResult,yxqkssj,yxqjssj,returnMap,demandEntity.getXqbh());
                returnMap.put("xqbh",demandEntity.getXqbh());
            }
            returnMap.put("status",true);
            return returnMap;
        }catch (DemandException d){
            throw new DemandException(d.getCode(),d.getMessage());
        }catch (Throwable t){
            staticConst.ZCYRW_YRWBH_ID=YRWBH;
            staticConst.XQXX_XQBH_ID -= 1;//需求编号
            staticConst.MBXX_MBBH_ID = MBBH;//目标编号
            log.error("访问计算错误，回滚所有操作！",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"访问计算错误: demandPlan");
        }
    }
    /**
     * @Description //TODO 访问计算数据处理
     * @Author kg
     * @Param [valmap, statellites, allStatellites, statelliteMap, visitResult, listResult, yxqkssj, yxqjssj, returnMap, xqbh]
     * @Date 17:36 2019/8/6
     */
    private void metataskMerge(List<TargetInfoEntity> valmap, List<String> statellites, List<String> allStatellites, Map<String, List<double[]>> statelliteMap,
                               List<TargetVisitResponse> visitResult, ResultEntry<List<TargetVisitResponse>> listResult,
                               String yxqkssj, String yxqjssj, Map<String, Object> returnMap, Integer xqbh) {
        //卫星资源匹配
        demandUtils.matchsRules(valmap);
        //根据卫星分组 不计算区域跟行政与区域数据
        demandUtils.mergeStatellite(valmap,statellites,statelliteMap,allStatellites);
        //进行访问计算
        demandUtils.visitCalculation(calcService,allStatellites,statelliteMap,visitResult,valmap,listResult,yxqkssj,yxqjssj,returnMap);
        //保存元任务信息
        demandUtils.saveMetatask(visitResult,xqbh);
    }

    /**
     * @Description //TODO 查询元任务信息 分页
     * @Author kg
     * @Param [param]
     * @Date 10:22 2019/7/22
     */
    @Override
    public Map<String, Object> getMetatask(ParamEntity param) {
        try{
            //分页查询
            log.info("获取元任务列表信息==========================");
            List<Integer> demandsId = param.getDemandsId();
            if(demandUtils.isEmpty(demandsId)){
                throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"参数错误");
            }
            demandUtils.setParamPage(param);
            List<TargetVisitResponse> targetVisitList = demandMapper.getMetatask(param);
            param.setCountFlag("true");
            List<TargetVisitResponse> countTotal = demandMapper.getMetatask(param);
            String total = "";
            if (countTotal != null && countTotal.size() > 0) {
                total = countTotal.get(0).getTotalCount();
            }
            Map<String, Object> returnMap = demandUtils.retrunMap(total, targetVisitList);
            return returnMap;
        }catch (DemandException d){
            throw new DemandException(d.getCode(),d.getMessage());
        }catch (Throwable t){
            log.error("获取信息失败：",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"分页查询元任务信息失败: getMetatask");
        }
    }
    /**
     * @Description //TODO 取消需求
     * @Author kg
     * @Param [param]
     * @Date 15:27 2019/7/24
     */
    @Override
    public void demandCancel(ParamEntity param){
        try {
            List<Integer> demandsId = param.getDemandsId();
            for(Integer xqbh : demandsId) {
                //查询需求状态
                String status = demandMapper.getRequirementStatu(xqbh);
                log.info("查询编号"+xqbh+"的需求状态=============="+status);
                if (!status.equals(staticConst.XQXX_XQZT_WCH_ID)) {
                    throw new DemandException(ResultCode.FAILURE.getValue(), "需求已筹划或已取消，不能取消！");
                }
                String xqzt = staticConst.XQXX_XQZT_YQX_ID;
                log.info("执行取消需求状态=============="+xqzt);
                demandMapper.demandStatus(xqbh, xqzt);
            }
        }catch (DemandException d){
            throw new DemandException(d.getCode(),d.getMessage());
        }catch (Throwable t){
            log.error("取消失败：",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"取消需求失败: demandCancel");
        }
    }
    /**
     * @Description //TODO 获取元任务信息---画点/区域条带
     * @Author kg
     * @Param [paramEntity]
     * @Date 17:38 2019/8/29
     */
    @Override
    @SuppressWarnings("all")
    public Map<String,Object> getMetatasInfo(ParamEntity param) {
        try {
            Integer xqbh = param.getXqbh();
            log.info("获取保存区域目标信息、点目标信息-------上图信息展示》》》》》》》》》》》");
            //获取需求目标、目标坐标信息
            List<TargetInfoEntity> targetInfoList = demandMapper.getRequirementZbInfo(xqbh);
            //分组处理区域目标
            Map<String, List<TargetInfoEntity>> collect = targetInfoList.stream().collect(Collectors.groupingBy(t -> t.getMblx()));
            List<TargetInfoEntity> areaList = collect.get(staticConst.MBXX_MBLX_QYMB_ID);
            int size = targetInfoList.size()-1;
            TargetInfoEntity targetInfo = null;
            for(int i = size;i>=0 ;i--){
                targetInfo = targetInfoList.get(i);
                if(staticConst.MBXX_MBLX_QYMB_ID.equals(targetInfo.getMblx())){
                    targetInfoList.remove(targetInfo);
                }
            }
            TargetInfoEntity areatargetInfo = demandUtils.mergeRegion(areaList);
            //组装返回结果
            if(areatargetInfo!=null)
                targetInfoList.add(areatargetInfo);
            //获取卫星标识并处理
            List<Map<String,Object>> saltelites = demandMapper.getSatelites(xqbh);
            demandUtils.mergeSaltelites(targetInfoList,saltelites);

            log.info("获取侦查元任务信息-------上图信息展示》》》》》》》》》》》");
            //获取侦查元任务信息
            List<TargetVisitResponse> list = demandMapper.getMetatasInfo(xqbh);
            //分组处理访问计算结果信息
            List<Map<String, Object>> computationalResult = new ArrayList<>();
            if(demandUtils.isNotEmpty(list)) {
                Map<String, Map<String, List<TargetVisitResponse>>> collectc = list.stream().collect(Collectors.groupingBy(t -> t.getZxjd(), Collectors.groupingBy(t -> t.getZxwd())));
                //处理分组
                collectc.forEach((key, value) -> {
                    value.forEach((key2, value2) -> {
                        Map<String, Object> computationalMap = new HashMap<String, Object>();
                        computationalMap.put("zxjd", key);
                        computationalMap.put("zxwd", key2);
                        computationalMap.put("list", value2);
                        computationalResult.add(computationalMap);
                    });
                });
            }
            //返回封装结果
            Map<String,Object> returnMap = new HashMap<>();
            returnMap.put("preserveResults",targetInfoList);
            returnMap.put("computationalResults",computationalResult);
            return returnMap;
        }catch (DemandException d){
            throw new DemandException(d.getCode(),d.getMessage());
        }catch (Throwable t){
            log.error("获取侦查元任务信息失败--上图：",t);
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取侦查元任务信息失败: getMetatasInfo");
        }
    }

}
