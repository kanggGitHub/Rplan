package com.cetc.plan.demand.service;

import com.cetc.plan.demand.model.demand.DemandEntity;
import com.cetc.plan.demand.model.demand.SateliteEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.exception.DemandException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 需求筹划_需求信息 服务类
 * </p>
 *
 * @author kg
 * @since 2019-06-20
 */
public interface DemandService {


    /**
     * @Description //TODO 更新redis缓存
     * @Author kg
     * @Param []
     * @Date 15:11 2019/7/11
     */
    @Transactional
    void updataRedis() throws  DemandException;

    /**
     * 查询所有国家---重点目标库
     * @return
     */
    @Transactional
    List<String> selectAllCountries() throws DemandException;

    /**
     * 根据国家名字查询其所有城市---重点目标库
     * @param param
     * @return
     */
    @Transactional
    Map<String,Object> selectTargetByName(ParamEntity param) throws DemandException;


    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:02 2019/6/24
     */
    @Transactional
    Integer saveDemand(DemandEntity demandEntity) throws DemandException;

    /**
     * @Description //TODO 需求创建获取卫星标识。
     * @Author kg
     * @Param []
     * @Date 9:12 2019/6/26
     */
    @Transactional
    List<SateliteEntity> getSatelliteInfos() throws DemandException;

    /**
     * @Description //TODO 获取区域内目标 ---重点目标坐标信息
     * @Author kg
     * @Param []
     * @Date 11:12 2019/6/26
     */
    @Transactional
    Map<String,Object> getAreaTarget(ParamEntity param) throws DemandException;
    /**
     * @Description //TODO
     * @Author kg
     * @Param [page, vagueName, countryName]
     * @Date 11:06 2019/7/2
     */
    @Transactional
    Map<String,Object> vagueCountryByname(ParamEntity param) throws DemandException;

    /**
     * @Description //TODO 获取需求列表信息 复杂项
     * @Author kg
     * @Param []
     * @Date 16:04 2019/7/4
     */
    @Transactional
    Map<String,Object> getRequirementsList(ParamEntity param) throws DemandException;
    /**
     * @Description //TODO 访问计算
     * @Author kg
     * @Param []
     * @Date 8:28 2019/7/19
     */
    @Transactional
    Map<String,Object> demandPlan(DemandEntity demandEntity) throws DemandException;
    /**
     * @Description //TODO 获取侦查元任务 分页
     * @Author kg
     * @Param [paramEntity]
     * @Date 9:44 2019/7/22
     */
    @Transactional
    Map<String, Object> getMetatask(ParamEntity paramEntity)throws DemandException;

    /**
     * @Description //TODO 取消需求
     * @Author kg
     * @Param [param]
     * @Date 15:27 2019/7/24
     */
    @Transactional
    void demandCancel(ParamEntity param)throws DemandException;
}
