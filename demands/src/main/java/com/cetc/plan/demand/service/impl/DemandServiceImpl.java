package com.cetc.plan.demand.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.DemandEntity;
import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.TargetInfoEntity;
import com.cetc.plan.demand.service.DemandService;
import com.cetc.plan.exception.DemandException;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.LogUtils;
import com.cetc.plan.utils.ResultCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private static final String RWLX_WCH="WCH";

    @Resource
    DemandMapper demandMapper;

    private DemandUtils demandUtils = new DemandUtils();

    /**
     * @Description 查询所有国家
     * @Author kg
     * @Param []
     * @Date 15:12 2019/6/21
     */
    @Override
    public List<String> selectAllCountries() throws DemandException{
        List<String> countryList = new ArrayList<String>();
        try {
            countryList = demandMapper.selectAllCountries();
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: SELECTALLCOUNTRYS");
        }
        return countryList;
    }

    /**
     * @Description 根据国家名称查询相应的城市
     * @Author kg
     * @Param [gjdq]
     * @Date 15:12 2019/6/21
     */
    @Override
    public List<CoretargetEntity> selectTargetByName(String gjdq) throws DemandException{
        List<CoretargetEntity> targetList = new ArrayList<CoretargetEntity>();
        try {
            targetList=demandMapper.selectTargetByName(gjdq);
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: SELECTCONTRYBYNAME");
        }
        return targetList;
    }

    /**
     * @Description 根据录入框录入的信息模糊匹配国家或相应的城市
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
                returnList = DemandUtils.dataGrouping(collect);
            }
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: SELECTPAGECORETARGE");
        }
        return returnList;
    }

    /**
     * @Description 根据需求编号查询相应的需求
     * @Author kg
     * @Param [xqbh]
     * @Date 15:26 2019/6/21
     */
    @Override
    public DemandEntity selectDemandByXqbh(String xqbh) throws DemandException {
        try {
            DemandEntity demandEntity =  demandMapper.selectDemandByXqbh(xqbh);
            return demandEntity;
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"查询数据库失败: SELECTDEMANDBYXQBH");
        }
    }
    /**
     * @Description 保存需求信息
     * @Author kg
     * @Param [demandEntity]
     * @Date 11:03 2019/6/24
     */
    @Override
    public void saveDemand(DemandEntity demandEntity) throws DemandException {
        try {
            /*保存需求信息*/
            LOG.info("开始保存需求信息>>>>>>>>>>>>>>>>>>>>>");
            String xqbh = DemandUtils.getXqbhUid();
            String mbbh = DemandUtils.getMbbhUid();
            demandEntity.setXqbh(xqbh);
            demandEntity.setGcpc(RWLX_WCH);
            demandEntity.setYxqjssj(new Date());
            demandEntity.setYxqkssj(new Date());
            demandMapper.saveDemand(demandEntity);
            LOG.info("保存需求信息完成>>>>>>>>>>>>>>>>>>>>>");
            /*获取所有目标信息*/
            List<TargetInfoEntity> valmap = demandEntity.getTaregetinfolist();
            /*目标信息按照目标类型分组*/
            Map<String, List<TargetInfoEntity>> collect = valmap.stream().collect(Collectors.groupingBy(t -> t.getMblx()));
            /*获取区域目标信息 单独处理*/
            List<TargetInfoEntity> areaList = collect.get("area");
            List<TargetInfoEntity> ResultAreaList = DemandUtils.getAreaList(areaList,xqbh,mbbh);
            /*获取 点目标point、目标库amin、行政区域region 信息*/
            List<TargetInfoEntity> arpList = demandUtils.getARPList(collect,xqbh,mbbh);
            if(DemandUtils.isNotEmpty(ResultAreaList)){
                arpList.addAll(ResultAreaList);
            }
            LOG.info("开始保存目标信息>>>>>>>>>>>>>>>>>>>>>");
            /*保存目标信息*/
            demandMapper.saveTargetInfo(arpList);
            LOG.info("保存需求目标信息完成>>>>>>>>>>>>>>>>>>>>>");
            LOG.info("开始保存目标坐标信息>>>>>>>>>>>>>>>>>>>>>");
            /*保存目标坐标信息*/
            demandMapper.saveTargetZbInfo(arpList);
            LOG.info("保存需求目标坐标信息完成>>>>>>>>>>>>>>>>>>>>>");
        }catch (Throwable throwable){
            throwable.printStackTrace();
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
            throwable.printStackTrace();
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"保存需求信息失败: SAVEDEMAND");
        }
    }
    /**
     * @Description //TODO 查询 重点目标库、坐标信息
     * @Author kg
     * @Param [map]
     * @Date 12:00 2019/6/26
     */
    @Override
    public List<CoretargetEntity> getAreaTarget(Map map) throws DemandException {
        try {
            List<CoretargetEntity> Coretargetinfo = demandMapper.getAreaTarget(map);
            return Coretargetinfo;
        }catch (Throwable throwable){
            throwable.printStackTrace();
            throw new DemandException(ResultCode.DATABASES_OPERATION_FAIL.getValue(),"保存需求信息失败: GETAREATARGET");
        }
    }
}
