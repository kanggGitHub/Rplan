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
    @Resource
    DemandMapper demandMapper;

    @Resource
    StaticConst staticConst;

    @Resource
    DemandUtils demandUtils;


    /**
     * @Description //TODO 初始化 获取配置信息 字典表信息
     * @Author kg
     * @Param []
     * @Date 10:48 2019/7/3
     */
    @Override
    public List<JSONObject> getRunManageConfig() throws DemandException {
        try {
            List<JSONObject> countryList = demandMapper.getRunManageConfig();
            return countryList;
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取配置信息失败: getRunManageConfig");
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
     * 暂时废弃接口
     * @Description 根据录入框录入的信息模糊匹配国家或相应的城市 分页
     * @Author kg
     * @Param [zdmbmc]
     * @Date 15:12 2019/6/21
     */
    @Override
    public List<Map> selectPageCoretarge(String zdmbmc) throws DemandException{
        List<Map> returnList = null;
        try {
            Page page = new Page(0,30);
            IPage<CoretargetEntity> iPage = demandMapper.selectPageCoretarge(page,zdmbmc);
            if(iPage.getTotal()>0){
                List<CoretargetEntity> targetLists = iPage.getRecords();
                Map<String, List<CoretargetEntity>> collect = targetLists.stream()
                        .collect(Collectors.groupingBy(t -> t.getGjdq()));
                returnList = demandUtils.dataGrouping(collect);
            }
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: SELECTPAGECORETARGE");
        }
        return returnList;
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
            LOG.info("开始保存需求信息>>>>>>>>>>>>>>>>>>>>>");
            staticConst.XQXX_XQBH_ID+=1;//需求编号
            demandEntity.setXqbh(staticConst.XQXX_XQBH_ID);
            demandEntity.setLy(staticConst.XQXX_LY_ID);
            Integer xqbh = demandEntity.getXqbh();
            //新需求初始化需求状态
            demandEntity.setXqzt(staticConst.XQXX_XQZT_WCH_ID);
            demandMapper.saveDemand(demandEntity);
            LOG.info("保存需求信息完成>>>>>>>>>>>>>>>>>>>>>");

            demandEntity.setJlbh(demandUtils.getUid());//记录编号
            demandMapper.saveDemandStatus(demandEntity);
            LOG.info("保存需求状态信息完成>>>>>>>>>>>>>>>>>>>>>");
            /*获取所有目标信息*/
            List<TargetInfoEntity> valmap = demandEntity.getTaregetinfolist();
            demandUtils.setAllbh(valmap,xqbh,staticConst.MBXX_MBBH_ID);

            /*保存目标信息*/
            demandMapper.saveTargetInfo(valmap);
            LOG.info("保存需求目标信息完成>>>>>>>>>>>>>>>>>>>>>");

            /*目标信息按照目标类型分组*/
            Map<String, List<TargetInfoEntity>> collect = valmap.stream().collect(Collectors.groupingBy(t -> t.getTargetType()));
            /*获取区域目标信息 单独处理*/
            List<TargetInfoEntity> areaList = collect.get("area");
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
            staticConst.XQXX_XQBH_ID-=1;//需求编号
            staticConst.MBXX_MBBH_ID=MBBH;//目标编号
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
     * @Description //TODO 获取需求列表信息 复杂项 分页
     * @Author kg
     * @Param []
     * @Date 16:04 2019/7/4
     */
    @Override
    public Map<String,Object> getRequirementsList(ParamEntity param) throws DemandException {
        try {
            //分页查询
            demandUtils.setParamPage(param);
            List<DemandEntity> demandinfo = demandMapper.getRequirementsList(param);
            param.setCountFlag("true");
            List<DemandEntity> countTotal = demandMapper.getRequirementsList(param);
            String total = "";
            if(countTotal!=null&&countTotal.size()>0){
                total = countTotal.get(0).getTotalCount();
            }
            Map<String,Object> returnMap = demandUtils.retrunMap(total,demandinfo);
            return returnMap;
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取需求列表信息失败: getRequirementsList");
        }
    }

    /**
     * @Description //TODO 获取需求最后一个插入的id数据
     * @Author kg
     * @Param []
     * @Date 18:59 2019/7/5
     */
    @Override
    public Map<String,Integer> getLastInsertId() {
        try {
            Map<String,Integer> lastId = demandMapper.getLastInsertId();
            return lastId;
        }catch (Throwable throwable){
            log.error(throwable.getMessage());
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"获取需求信息ID数据失败: getLastInsertId");
        }
    }
}
