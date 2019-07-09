package com.cetc.plan.demand.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
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
     * @Description //TODO 初始化字典表信息
     * @Author kg
     * @Param []
     * @Date 10:36 2019/7/3
     */
    List<JSONObject> getRunManageConfig()  throws DemandException;

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
     * @param param
     * @return
     */
    Map<String,Object> selectTargetByName(ParamEntity param) throws DemandException;


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
    Map<String,Object> getAreaTarget(ParamEntity param) throws DemandException;
    /**
     * @Description //TODO
     * @Author kg
     * @Param [page, vagueName, countryName]
     * @Date 11:06 2019/7/2
     */
    Map<String,Object> vagueCountryByname(ParamEntity param) throws DemandException;

    /**
     * @Description //TODO 获取需求列表信息 复杂项
     * @Author kg
     * @Param []
     * @Date 16:04 2019/7/4
     */
    Map<String,Object> getRequirementsList(ParamEntity param) throws DemandException;

    /**
     * @Description //TODO 查询需求最后一条插入的id
     * @Author kg
     * @Param []
     * @Date 18:51 2019/7/5
     */
    Map<String,Integer> getLastInsertId();
}
