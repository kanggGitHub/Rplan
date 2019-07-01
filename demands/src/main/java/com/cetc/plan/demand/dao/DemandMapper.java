package com.cetc.plan.demand.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.model.TargetInfoEntity;
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
     * 获取重点目标库的信息
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
     * @param gjdq
     * @return
     */
    List<CoretargetEntity> selectTargetByName(@Param("gjdq") String gjdq);

    /**
     * @Description 根据需求编号查询需求
     * @Author kg
     * @Param [xqbh]
     * @Date 10:16 2019/6/21
     */
    DemandEntity selectDemandByXqbh(String xqbh);
    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:02 2019/6/24
     */
    void saveDemand(@Param("demandEntity")DemandEntity demandEntity);

    /**
     * @Description //TODO  获取星地资源信息
     * @Author kg
     * @Param []
     * @Date 11:21 2019/6/26
     */
    List<Map<String, Object>> getSatelliteInfos();

    /**
     * @Description //TODO 获取区域内目标 ---重点目标坐标信息
     * @Author kg
     * @Param []
     * @Date 11:12 2019/6/26
     */
    List<CoretargetEntity> getAreaTarget(Map map) throws DemandException;

    /**
     * @Description //TODO 保存目标信息-需求信息
     * @Author kg
     * @Param
     * @Date 9:27 2019/7/1
     */
    int saveTargetInfo(List<TargetInfoEntity> targetInfoList);

    /**
     * @Description //TODO 保存目标坐标信息-需求信息
     * @Author kg
     * @Param [targetInfoList]
     * @Date 9:40 2019/7/1
     */
    int saveTargetZbInfo(List<TargetInfoEntity> targetInfoList);
}
