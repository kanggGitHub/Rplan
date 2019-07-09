package com.cetc.plan.demand.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.model.SateliteEntity;
import com.cetc.plan.demand.model.TargetInfoEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.exception.DemandException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 需求筹划_需求信息 Mapper 接口
 * </p>
 *
 * @author kg
 * @since 2019-06-20
 */
@Mapper
public interface DemandMapper  extends BaseMapper {


    /**
     * 获取重点目标库的信息 暂时废弃
     * @param page 分页信息
     * @param zdmbmc 名称 模糊匹配 重点目标名称
     * @return
     */
    IPage<CoretargetEntity> selectPageCoretarge(Page page, String zdmbmc);

    /**
     * 查询所有国家---重点目标库
     * @return
     */
    List<String> selectAllCountries();

    /**
     * 根据国家名字查询其所有城市
     * @param param
     * @return
     */
    List<CoretargetEntity> selectTargetByName(ParamEntity param);


    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:02 2019/6/24
     */
    void saveDemand(@Param("demandEntity")DemandEntity demandEntity) throws DemandException;

    /**
     * @Description 保存需求状态信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:02 2019/6/24
     */
    void saveDemandStatus(@Param("demandEntity")DemandEntity demandEntity) throws DemandException;

    /**
     * @Description //TODO  获取星地资源信息
     * @Author kg
     * @Param []
     * @Date 11:21 2019/6/26
     */
    List<Map<String, Object>> getSatelliteInfos() throws DemandException;

    /**
     * @Description //TODO 获取区域内目标 ---重点目标坐标信息
     * @Author kg
     * @Param []
     * @Date 11:12 2019/6/26
     */
    List<CoretargetEntity> getAreaTarget(ParamEntity param) throws DemandException;

    /**
     * @Description //TODO 保存目标信息-需求信息
     * @Author kg
     * @Param
     * @Date 9:27 2019/7/1
     */
    int saveTargetInfo(List<TargetInfoEntity> targetInfoList)throws DemandException;

    /**
     * @Description //TODO 保存目标坐标信息-需求信息
     * @Author kg
     * @Param [targetInfoList]
     * @Date 9:40 2019/7/1
     */
    int saveTargetZbInfo(List<TargetInfoEntity> targetInfoList) throws DemandException;

    /**
     * @Description //TODO 保存目标信息-卫星要求
     * @Author kg
     * @Param [sateliteEntities]
     * @Date 17:03 2019/7/2
     */
    int saveSateliteInfo(List<SateliteEntity> sateliteEntities) throws DemandException;
    /**
     * @Description //TODO 分页模糊匹配 国家城市信息
     * @Author kg
     * @Param [param]
     * @Date 10:13 2019/7/2
     */
    List<CoretargetEntity> vagueCountryByname(ParamEntity param) throws DemandException;
    /**
     * @Description //TODO 查询需求类型信息
     * @Author kg
     * @Param []
     * @Date 8:24 2019/7/4
     */
    List<JSONObject> getRunManageConfig() throws  DemandException;

    /**
     * @Description //TODO 获取需求列表信息 复杂项
     * @Author kg
     * @Param [param]
     * @Date 16:04 2019/7/4
     */
    List<DemandEntity> getRequirementsList(ParamEntity param) throws DemandException;

    /**
     * @Description 根据需求编号查询需求
     * @Author kg
     * @Param [xqbh]
     * @Date 10:16 2019/6/21
     */
    List<DemandEntity> getRequirementDetails(Integer xqbh) throws DemandException;

    /**
     * @Description //TODO 查询需求最后一条插入的id
     * @Author kg
     * @Param []
     * @Date 18:51 2019/7/5
     */

    Map<String,Integer> getLastInsertId();
}
