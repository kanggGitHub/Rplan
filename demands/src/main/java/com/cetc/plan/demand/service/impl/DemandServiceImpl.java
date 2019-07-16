package com.cetc.plan.demand.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetc.plan.config.StaticConst;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.SateliteEntity;
import com.cetc.plan.demand.model.TargetInfoEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.demand.service.DemandRedisService;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.exception.DemandException;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.LogUtils;
import com.cetc.plan.config.ResultCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class DemandServiceImpl extends ServiceImpl implements DemandService {

    private static final Logger LOG = LogUtils.getLogger(DemandServiceImpl.class);
    private static Integer MBBH;
    private static Boolean FULLBACK;
    @Resource
    DemandMapper demandMapper;

    @Resource
    StaticConst staticConst;

    @Resource
    DemandUtils demandUtils;

    @Resource
    DemandRedisService demandRedisService;



    /**
     * @Description //TODO 缓存信息
     * @Author kg
     * @Param []
     * @Date 10:06 2019/7/11
     */
    @Override
    public void updataRedis() throws DemandException {
        try {
            LOG.info("初始化字典信息");
            List<JSONObject> list = demandMapper.getRunManageConfig();
            Map<String,Object> lastId = demandMapper.getLastInsertId();
            staticConst.XQXX_XQBH_ID = Integer.parseInt(String.valueOf(lastId.get("xqbh")));
            staticConst.MBXX_MBBH_ID = Integer.parseInt(String.valueOf(lastId.get("mbbh")));
            Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(t -> t.getString("classification")));
            demandRedisService.setRedisByKeyAndValue("dictionary",collect);
            //初始化信息
            staticConst.setData(list);
        }catch (Exception e){
            LOG.error(e.getMessage());
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
            List<String> countryList = demandMapper.selectAllCountries();
            return countryList;
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: SELECTALLCOUNTRYS");
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
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: SELECTCONTRYBYNAME");
        }
    }


    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:03 2019/6/24
     */
    @Transactional
    @Override
    public void saveDemand(DemandEntity demandEntity) throws DemandException {
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
                LOG.info("更新保存需求信息>>>>>>>>>>>>>>>>>>>>>");
                demandMapper.delDemandInfo(xqbh);//删除该编号相关信息
            }else{
                FULLBACK = false;
                LOG.info("开始保存需求信息>>>>>>>>>>>>>>>>>>>>>");
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
            LOG.info("保存需求信息完成>>>>>>>>>>>>>>>>>>>>>");

            demandEntity.setJlbh(xqbh);//记录编号
            demandMapper.saveDemandStatus(demandEntity);
            LOG.info("保存需求状态信息完成>>>>>>>>>>>>>>>>>>>>>");
            /*获取所有目标信息*/
            List<TargetInfoEntity> valmap = demandEntity.getTaregetinfolist();

            demandUtils.setAllbh(valmap,xqbh);

            /*保存目标信息*/
            demandMapper.saveTargetInfo(valmap);
            LOG.info("保存需求目标信息完成>>>>>>>>>>>>>>>>>>>>>");

            /*目标信息按照目标类型分组*/
            Map<String, List<TargetInfoEntity>> collect = valmap.stream().collect(Collectors.groupingBy(t -> t.getMblx()));
            /*获取区域目标信息 单独处理*/
            List<TargetInfoEntity> areaList = collect.get(staticConst.MBXX_MBLX_QYMB_ID);
            List<TargetInfoEntity> ResultAreaList = demandUtils.getAreaList(areaList,xqbh);
            /*获取 点目标point、目标库amin、行政区域region 信息*/
            List<TargetInfoEntity> arpList = demandUtils.getARPList(collect,xqbh);
            if(demandUtils.isNotEmpty(ResultAreaList)){
                arpList.addAll(ResultAreaList);
            }
            /*保存目标坐标信息*/
            demandMapper.saveTargetZbInfo(arpList);
            LOG.info("保存需求目标坐标信息完成>>>>>>>>>>>>>>>>>>>>>");

            /*获取目标信息-卫星要求数据*/
            List<SateliteEntity> sateliteEntities = demandUtils.getHandleSatelite(valmap,xqbh);
            /*保存目标信息-卫星要求*/
            demandMapper.saveSateliteInfo(sateliteEntities);
            LOG.info("保存需求目标信息---卫星要求完成>>>>>>>>>>>>>>>>>>>>>");
        }catch (Throwable throwable){
            log.error("保存失败，回滚所有操作！");
            if(!FULLBACK) {
                staticConst.XQXX_XQBH_ID -= 1;//需求编号
                staticConst.MBXX_MBBH_ID = MBBH;//目标编号
            }
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"保存需求信息失败: SAVEDEMAND");
        }
    }
    /**
     * @Description //TODO 需求创建获取卫星标识。
     * @Author kg
     * @Param []
     * @Date 9:10 2019/6/26
     */
    @Override
    public List<Map<String, Object>> getSatelliteInfos() throws DemandException {
        try {
            List<Map<String,Object>> satelliteInfos = demandMapper.getSatelliteInfos();
            return satelliteInfos;
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取卫星标识信息失败: GETSATELLITEINFOS");
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
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取信息失败: GETAREATARGET");
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
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取国家、城市信息失败: VAGUECOUNTRYBYNAME");
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
            if(-1 == xqbh){//获取需求列表
                //分页查询
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
                Map<String,Object> demandInfo = demandMapper.getRequirementInfo(xqbh);
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
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取需求信息失败: getRequirementsList");
        }
    }

}
