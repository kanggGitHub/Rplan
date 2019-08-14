package com.cetc.plan.demand.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetc.plan.demand.model.TargetVisitResponse;
import com.cetc.plan.demand.model.TargetVisitSubEntity;
import com.cetc.plan.demand.model.demand.CoretargetEntity;
import com.cetc.plan.demand.model.demand.DemandEntity;
import com.cetc.plan.demand.model.demand.SateliteEntity;
import com.cetc.plan.demand.model.demand.TargetInfoEntity;
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
    List<SateliteEntity> getSatelliteInfos() throws DemandException;

    /**
     * @Description //TODO 获取区域内目标 ---重点目标坐标信息
     * @Author kg
     * @Param []
     * @Date 11:12 2019/6/26
     */
    List<CoretargetEntity> getAreaTarget(ParamEntity param) throws DemandException;

    /**
     * @Description //TODO 根据重点目标编号查询重点目标名称
     * @Author kg
     * @Param [targetId]
     * @Date 8:45 2019/7/24
     */
    String getTargetName(String zdmbbh);

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
     * @Description 根据需求编号查询需求信息、目标跟目标坐标信息
     * @Author kg
     * @Param [xqbh]
     * @Date 10:16 2019/6/21
     */
    Map<String,Object> getRequirementInfo(Integer xqbh) throws DemandException;
    List<TargetInfoEntity> getRequirementZbInfo(Integer xqbh) throws DemandException;// 获取需求目标信息、目标坐标信息
    List<Map<String,Object>> getSatelites(Integer xqbh);//获取卫星信息
    /**
     * @Description //TODO 删除所有数据信息根据目标编号
     * @Author kg
     * @Param [xqbh]
     * @Date 8:47 2019/7/24
     */
    int delDemandInfo(Integer xqbh);

    /**
     * @Description //TODO 查询需求最后一条插入的id
     * @Author kg
     * @Param []
     * @Date 18:51 2019/7/5
     */
    Map<String,Object> getLastInsertId();

    /**
     * @Description //TODO 根据需求编号查询需求状态
     * @Author kg
     * @Param [xqbh]
     * @Date 16:23 2019/7/12
     */
    String getRequirementStatu(Integer xqbh);

    /**
     * @Description //TODO 获取卫星资源匹配
     * @Author kg
     * @Param [targetInfoEntity]
     * @Date 11:36 2019/7/19
     */
    List<String> getSatelliteRelus(@Param("fblyq")String fblyq,@Param("rwlx")String rwlx);

    /**
     * @Description //TODO 保存侦查元任务
     * @Author kg
     * @Param []
     * @Date 9:24 2019/7/22
     */
    int saveMetatask(List<TargetVisitResponse> list);

    /**
     * @Description //TODO 获取侦查元任务 分页
     * @Author kg
     * @Param [paramEntity]
     * @Date 9:44 2019/7/22
     */
    List<TargetVisitResponse> getMetatask(ParamEntity paramEntity);
    /**
     * @Description //TODO 获取元任务编号最大值
     * @Author kg
     * @Param []
     * @Date 10:45 2019/7/22
     */
    Integer getLastMetataskId();

    /**
     * @Description //TODO 保存元任务目标信息
     * @Author kg
     * @Param [subInfo]
     * @Date 8:47 2019/7/24
     */
    int saveMetataskTargetInfo(List<TargetVisitSubEntity> subInfo);
    /**
     * @Description //TODO 保存元任务状态信息
     * @Author kg
     * @Param [subInfo]
     * @Date 8:47 2019/7/24
     */
    int saveMetataskStatus(List<TargetVisitSubEntity> subInfo);
    /**
     * @Description //TODO 取消需求
     * @Author kg
     * @Param [xqbh, xqzt]
     * @Date 9:47 2019/7/26
     */
    int demandCancel(Integer xqbh,String xqzt);
    /**
     * @Description //TODO 取消需求
     * @Author kg
     * @Param [xqbh, xqzt]
     * @Date 9:47 2019/7/26
     */
    int demandPlanned(Integer xqbh,String xqzt);
    /**
     * @Description //TODO 查询卫星的默认观测时长
     * @Author kg
     * @Param [wxbs]
     * @Date 9:47 2019/7/26
     */
    Double mergeDefaultTime(String wxbs);
    /**
     * @Description //TODO 获取时间
     * @Author kg
     * @Param [demandId]
     * @Date 17:31 2019/8/5
     */
    Map<String, String> getDemandsTime(Integer demandId);
}
