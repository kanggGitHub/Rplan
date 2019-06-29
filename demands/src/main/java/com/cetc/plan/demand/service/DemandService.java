package com.cetc.plan.demand.service;

import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.exception.DemandException;
import org.apache.ibatis.annotations.Param;

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
     * 获取重点目标库的信息---重点目标库
     * @param zdmbmc 名称 模糊匹配 重点目标名称
     * @return
     */
    List<Map> selectPageCoretarge(String zdmbmc)  throws DemandException;

    /**
     * 查询所有国家---重点目标库
     * @return
     */
    List<String> selectAllCountries() throws DemandException;

    /**
     * 根据国家名字查询其所有城市---重点目标库
     * @param gjdq
     * @return
     */
    List<CoretargetEntity> selectTargetByName(String gjdq) throws DemandException;

    /**
     * @Description 根据需求编号查询需求----需求信息
     * @Author kg
     * @Param [xqbh]
     * @Date 10:16 2019/6/21
     */
    DemandEntity selectDemandByXqbh(Integer xqbh) throws DemandException;
    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:02 2019/6/24
     */
    void saveDemand(DemandEntity demandEntity) throws DemandException;

    /**
     * @Description //TODO 需求创建获取卫星标识。
     * @Author kg
     * @Param []
     * @Date 9:12 2019/6/26
     */
    List<Map<String, Object>> getSatelliteInfos() throws DemandException;

    /**
     * @Description //TODO 获取区域内目标 ---重点目标坐标信息
     * @Author kg
     * @Param []
     * @Date 11:12 2019/6/26
     */
    List<CoretargetEntity> getAreaTarget(Map map) throws DemandException;
}
