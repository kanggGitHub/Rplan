package com.cetc.plan.utils;

import com.cetc.plan.config.StaticConst;
import com.cetc.plan.demand.dao.DemandMapper;
import com.cetc.plan.demand.model.ResultEntry;
import com.cetc.plan.demand.model.TargetVisitResponse;
import com.cetc.plan.demand.model.TargetVisitSubEntity;
import com.cetc.plan.demand.model.demand.CoretargetEntity;
import com.cetc.plan.demand.model.demand.SateliteEntity;
import com.cetc.plan.demand.model.demand.TargetInfoEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import com.cetc.plan.demand.service.CalcService;
import com.cetc.plan.demand.service.DemandRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 需求信息 数据转换、数据处理类 //TODO
 * @Author kg
 * @Date 10:15 2019/6/20
 **/
@Slf4j
@Component
public class DemandUtils extends StringUtils{


    @Resource
    StaticConst staticConst;

    @Resource
    DemandMapper demandMapper;

    @Resource
    DemandRedisService demandRedisService;

    private static int  mbbh = 0;



    public DemandUtils() {
        super();
    }



    /**
     * @Description //TODO 获取程序生成需求标识号
     * @Author kg
     * @Param [code] 需求标识号
     * @Date 15:53 2019/7/11
     */
    public static String getDemandSign(String code) {
        StringBuilder builder = new StringBuilder(code);
        DateFormat format = new SimpleDateFormat("yyMMdd");
        if (builder.length() < 5) {
            for (int i = builder.length(); i < 5; i++) {
                builder.insert(0, "0");
            }
        } else {
            builder = new StringBuilder(builder.substring(builder.length() - 5, builder.length()));
        }
        return format.format(new Date()) + "_ZZZHZX_" + builder;
    }

    /**
     * @Description 数据分组 查询重点目标库专用
     * @Author kg
     * @Param [collect]
     * @return java.util.List<java.util.Map>
     * @Date 15:19 2019/6/20
     */
    public  List<Map> dataGrouping(Map<String,List<CoretargetEntity>> collect){
        if(collect.size()<=0)return null;
        List<Map> returnList = new ArrayList<Map>();
        Map<String, Object> itemMap;
        for(String key : collect.keySet()) {
            itemMap = new HashMap<String,Object>();
            List<CoretargetEntity> value = collect.get(key);
            itemMap.put("countryName",key);
            itemMap.put("list",value);
            returnList.add(itemMap);
        }
        return returnList;
    }

    /**
     * @Description //TODO 获取 点目标point、目标库aim、行政区域region 信息组装（需求保存）
     * @Author kg
     * @Param [map]
     * @Date 9:53 2019/7/1
     */
    public  List<TargetInfoEntity> getARPList(Map<String, List<TargetInfoEntity>> map,Integer xqbh){
        List<TargetInfoEntity> arpList = new ArrayList<>();
        //获取点目标point
        List<TargetInfoEntity> pointList = map.get(staticConst.MBXX_MBLX_DMB_ID);
        //获取目标库aim
        List<TargetInfoEntity> aimList = map.get(staticConst.MBXX_MBLX_ZDMB_ID);
        //获取行政区域region
        List<TargetInfoEntity> regionList = map.get(staticConst.MBXX_MBLX_XZQY_ID);
        if(isNotEmpty(pointList))
            arpList.addAll(pointList);
        if(isNotEmpty(aimList))
            arpList.addAll(aimList);
        if(isNotEmpty(regionList))
            arpList.addAll(regionList);
        return arpList;
    }

    /**
     * @Description //TODO 处理所有目标 增加需求编号 目标编号（需求保存）
     * @Author kg
     * @Param [list, xqbh]
     * @Date 15:11 2019/7/4
     */
    public void setAllbh(List<TargetInfoEntity> list,Integer xqbh){
        String mblx = "";
        String targetName = "";
        Integer mbbh ;
        for(TargetInfoEntity targetInfoEntity : list){
            mbbh = staticConst.MBXX_MBBH_ID+=1;
            targetInfoEntity.setMbbh(mbbh);
            targetInfoEntity.setXqbh(xqbh);
            targetInfoEntity.setZbdxh(staticConst.MBXX_ZBDXH1);
            //目标类型
            mblx = targetInfoEntity.getMblx();
            if("area".equals(mblx)){
                targetInfoEntity.setMblx(staticConst.MBXX_MBLX_QYMB_ID);
            }else if("aim".equals(mblx)){
                targetName = demandMapper.getTargetName(targetInfoEntity.getZdmbbh());
                targetInfoEntity.setMblx(staticConst.MBXX_MBLX_ZDMB_ID);
                targetInfoEntity.setMbmc(targetName);
            }else if("region".equals(mblx)){
                targetInfoEntity.setMblx(staticConst.MBXX_MBLX_XZQY_ID);
            }else if("point".equals(mblx)){
                targetInfoEntity.setMblx(staticConst.MBXX_MBLX_DMB_ID);
            }
        }
    }

    /**
     * @Description //TODO 处理区域目标信息（需求保存）
     * @Author kg
     * @Param [list, xqbh]
     * @Date 10:35 2019/7/1
     */
    public  List<TargetInfoEntity> getAreaList(List<TargetInfoEntity> list,Integer xqbh){
        if(isEmpty(list))return  null;
        List<TargetInfoEntity> saveTargetInfo = new ArrayList<>();
        //创建新对象
        TargetInfoEntity targetInfo1 = new TargetInfoEntity();
        TargetInfoEntity targetInfo2 = new TargetInfoEntity();
        for(TargetInfoEntity targetInfoEntity : list){
            //给创建的新对象赋值
            setTargetDate(targetInfo1,targetInfo2,targetInfoEntity,xqbh);
            //返回的数据集合
            saveTargetInfo.add(targetInfo1);
            saveTargetInfo.add(targetInfo2);
        }
        return saveTargetInfo;
    }


    /**
     * @Description //TODO  给新对象赋值
     * @Author kg
     * @Param [targetInfo1, targetInfo2, targetInfoEntity, xqbh, mbbh]
     * @Date 10:58 2019/7/1
     */
    @SuppressWarnings("all")
    private  void setTargetDate(TargetInfoEntity targetInfo1, TargetInfoEntity targetInfo2,
                                      TargetInfoEntity targetInfoEntity, Integer xqbh) {
        targetInfo1.setMbbh(targetInfoEntity.getMbbh());
        targetInfo1.setXqbh(targetInfoEntity.getXqbh());
        targetInfo1.setBj(targetInfoEntity.getBj());
        targetInfo1.setDwlx(targetInfoEntity.getDwlx());
        targetInfo1.setFblyq(targetInfoEntity.getFblyq());
        targetInfo1.setGjdq(targetInfoEntity.getGjdq());
        targetInfo1.setHs(targetInfoEntity.getHs());
        targetInfo1.setHx(targetInfoEntity.getHx());
        targetInfo1.setJd(targetInfoEntity.getLeftlog());
        targetInfo1.setWd(targetInfoEntity.getLeftlat());
        targetInfo1.setMblx(targetInfoEntity.getMblx());
        targetInfo1.setMbmc(targetInfoEntity.getMbmc());
        targetInfo1.setRksj(targetInfoEntity.getRksj());
        targetInfo1.setRwlx(targetInfoEntity.getRwlx());
        targetInfo1.setSj(targetInfoEntity.getSj());
        targetInfo1.setWxbs(targetInfoEntity.getWxbs());
        targetInfo1.setYxj(targetInfoEntity.getYxj());
        targetInfo1.setZbdxh(staticConst.MBXX_ZBDXH1);
        targetInfo1.setZdmbbh(targetInfoEntity.getZdmbbh());

        targetInfo2.setMbbh(targetInfoEntity.getMbbh());
        targetInfo2.setXqbh(targetInfoEntity.getXqbh());
        targetInfo2.setBj(targetInfoEntity.getBj());
        targetInfo2.setDwlx(targetInfoEntity.getDwlx());
        targetInfo2.setFblyq(targetInfoEntity.getFblyq());
        targetInfo2.setGjdq(targetInfoEntity.getGjdq());
        targetInfo2.setHs(targetInfoEntity.getHs());
        targetInfo2.setHx(targetInfoEntity.getHx());
        targetInfo2.setJd(targetInfoEntity.getRightlog());
        targetInfo2.setWd(targetInfoEntity.getRightlat());
        targetInfo2.setMblx(targetInfoEntity.getMblx());
        targetInfo2.setMbmc(targetInfoEntity.getMbmc());
        targetInfo2.setRksj(targetInfoEntity.getRksj());
        targetInfo2.setRwlx(targetInfoEntity.getRwlx());
        targetInfo2.setSj(targetInfoEntity.getSj());
        targetInfo2.setWxbs(targetInfoEntity.getWxbs());
        targetInfo2.setYxj(targetInfoEntity.getYxj());
        targetInfo2.setZbdxh(staticConst.MBXX_ZBDXH2);
        targetInfo2.setZdmbbh(targetInfoEntity.getZdmbbh());
    }


    /**
     * @Description //TODO 处理卫星标识数据（需求保存）
     * @Author kg
     * @Param [list, xqbh, mbbh]
     * @Date 17:00 2019/7/2
     */
    public  List<SateliteEntity> getHandleSatelite(List<TargetInfoEntity> list){
        if(isEmpty(list))return null;
        List<SateliteEntity> sateliteEntities = new ArrayList<>();
        List<String> satellitesList = new ArrayList<>();
        for(TargetInfoEntity targetInfoEntity : list){
            satellitesList = targetInfoEntity.getSatellites();
            if(isEmpty(satellitesList))continue;
            for(String satellite : satellitesList){
                SateliteEntity sateliteEntity = new SateliteEntity();
                sateliteEntity.setMbbh(targetInfoEntity.getMbbh());
                sateliteEntity.setXqbh(targetInfoEntity.getXqbh());
                sateliteEntity.setWxbs(satellite);
                sateliteEntities.add(sateliteEntity);
            }
        }
        return sateliteEntities;
    }
    /**
     * @Description //TODO  分页信息计算
     * @Author kg
     * @Param [param]
     * @Date 9:34 2019/7/9
     */
    public void setParamPage(ParamEntity param) {
        int currentPage = param.getCurrentPage();
        int pageSize = param.getPageSize();
        param.setCurrentPage((currentPage-1)*pageSize);
    }

    /**
     * @Description //TODO 组装返回数据结果
     * @Author kg
     * @Param [total, list]
     * @Date 9:41 2019/7/9
     */
    public Map<String,Object> retrunMap(String total,List<?> list){
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total",total);
        returnMap.put("list",list);
        return returnMap;
    }

    /**
     * @Description //TODO 合并区域目标坐标点（需求详情）
     * @Author kg
     * @Param [list]
     * @Date 16:55 2019/7/10
     */
    public TargetInfoEntity mergeRegion(List<TargetInfoEntity> list){
        if(isNotEmpty(list)){
            TargetInfoEntity targetInfo = list.get(0);
            for(TargetInfoEntity targetInfos : list){
                if(staticConst.MBXX_ZBDXH1.equals(targetInfos.getZbdxh())){
                    targetInfo.setLeftlog(targetInfos.getJd());
                    targetInfo.setLeftlat(targetInfos.getWd());
                }else if(staticConst.MBXX_ZBDXH2.equals(targetInfos.getZbdxh())){
                    targetInfo.setRightlog(targetInfos.getJd());
                    targetInfo.setRightlat(targetInfos.getWd());
                }
            }
            targetInfo.setWd(null);
            targetInfo.setJd(null);
            return targetInfo;
        }
        return null;
    }

    /**
     * @Description //TODO 处理卫星标识数据(需求详情)
     * @Author kg
     * @Param [targetInfoList, saltelites]
     * @Date 17:07 2019/7/15
     */
    public void mergeSaltelites(List<TargetInfoEntity> targetInfoList, List<Map<String, Object>> saltelites) {
        Map<String, List<Map<String, Object>>> collect = saltelites.stream().collect(Collectors.groupingBy(t -> t.get("mbbh").toString()));
        String mbbh = null;
        List<Map<String,Object>> targetInfo = new ArrayList<>();
        List<String> satelites = null;
        for(TargetInfoEntity targetInfoEntity : targetInfoList){
            mbbh = targetInfoEntity.getMbbh().toString();
            targetInfo = collect.get(mbbh);
            if(isEmpty(targetInfo))continue;
            satelites = new ArrayList<>();
            for(Map<String,Object> map : targetInfo){
                satelites.add(map.get("wxbs").toString());
            }
            targetInfoEntity.setSatellites(satelites);
        }

    }
    /**
     * @Description //TODO 卫星资源匹配
     * @Author kg
     * @Param [list]
     * @Date 15:12 2019/7/19
     */
    public void matchsRules(List<TargetInfoEntity> list) {
        for(TargetInfoEntity targetInfoEntity : list){
            List<String> statelites = targetInfoEntity.getSatellites();
            if(isEmpty(statelites)){
                statelites = demandMapper.getSatelliteRelus(targetInfoEntity.getFblyq(),targetInfoEntity.getRwlx());
                targetInfoEntity.setSatellites(statelites);
            }
        }
    }


    /**
     * @Description //TODO 根据卫星标识获取卫星类型
     * @Author kg
     * @Param
     * @Date 15:17 2019/7/20
     */
    public String getSatelliteBybs(String wxbs){
        List sateliteEntities =  demandRedisService.getListRedisByKey("satelliteInfos");
        if(isEmpty((sateliteEntities))){
           sateliteEntities = demandMapper.getSatelliteInfos();
        }
        for(SateliteEntity sateliteInfo : (List<SateliteEntity>)sateliteEntities){
            if(wxbs.equals(sateliteInfo.getWxbs())){
                return sateliteInfo.getWxlx();
            }
        }
        return null;
    }
    /**
     * @Description //TODO 匹配相应的目标编号 行政区域、区域目标除外--访问计算
     * @Author kg
     * @Param [valmap]
     * @Date 16:52 2019/7/22
     */
    public Integer mergeTargetVS(List<TargetInfoEntity> valmap,String jd,String wd,String wxbs) {
        String mbjd;
        String mbwd;
        List<String> mbwxbs;
        Integer mbbh = 0;
        for(TargetInfoEntity targetInfoEntity : valmap){
            mbjd = targetInfoEntity.getJd();
            mbwd = targetInfoEntity.getWd();
            mbwxbs = targetInfoEntity.getSatellites();
            if(targetInfoEntity.getMblx().equals(staticConst.MBXX_MBLX_QYMB_ID)||targetInfoEntity.getMblx().equals(staticConst.MBXX_MBLX_XZQY_ID))continue;
            if(formatDouble(jd).equals(formatDouble(mbjd)) && formatDouble(wd).equals(formatDouble(mbwd)) && mbwxbs.contains(wxbs)){
                mbbh = targetInfoEntity.getMbbh();
            }
        }
        return mbbh;
    }

    /**
     * @Description //TODO 封装保存的实体对象---访问计算
     * @Author kg
     * @Param [vslist, xqbh]
     * @Date 10:12 2019/7/23
     */
    public List<TargetVisitSubEntity> mergeTargetVsInfo(List<TargetVisitResponse> vslist,Integer xqbh) {
        List<TargetVisitSubEntity> subInfo = new ArrayList<>();
        for(TargetVisitResponse targetVisitResponse : vslist){
            TargetVisitSubEntity subEntity = new TargetVisitSubEntity();
            subEntity.setYrwbh(targetVisitResponse.getYrwbh());
            subEntity.setMbbh(targetVisitResponse.getMbbh());
            subEntity.setJlbh(targetVisitResponse.getYrwbh());
            subEntity.setXqbh(xqbh);
            subEntity.setYrwzt(staticConst.XQXX_XQZT_YCH_ID);
            subInfo.add(subEntity);
        }
        return subInfo;
    }
    /**
     * @Description //TODO 按照卫星分组 处理目标经纬度--访问计算
     * @Author kg
     * @Param [valmap 目标集合, statellites目标携带的卫星集合, statelliteMap每个卫星分组后携带的经纬度, allStatellites所有卫星标识集合]
     * @Date 14:47 2019/7/24
     */
    public void mergeStatellite(List<TargetInfoEntity> valmap, List<String> statellites, Map<String, List<double[]>> statelliteMap,List<String> allStatellites) {
        for(TargetInfoEntity targetInfoEntity:valmap){
            if(targetInfoEntity.getMblx().equals(staticConst.MBXX_MBLX_QYMB_ID)
                    ||targetInfoEntity.getMblx().equals(staticConst.MBXX_MBLX_XZQY_ID))continue;
            statellites = targetInfoEntity.getSatellites();
            for(String wxbs : statellites){
                if(!allStatellites.contains(wxbs)){
                    allStatellites.add(wxbs);
                }
                if(statelliteMap.containsKey(wxbs)){
                    List<double[]> points = statelliteMap.get(wxbs);
                    double[] point = {Double.valueOf(targetInfoEntity.getJd()),Double.valueOf(targetInfoEntity.getWd())};
                    points.add(point);
                    statelliteMap.put(wxbs,points);
                }else{
                    List<double[]> points = new ArrayList<>();
                    double[] point = {Double.valueOf(targetInfoEntity.getJd()),Double.valueOf(targetInfoEntity.getWd())};
                    points.add(point);
                    statelliteMap.put(wxbs,points);
                }
            }
        }
    }

    /**
     * @Description //TODO 访问计算进行
     * @Author kg
     * @Param [calcService, allStatellites, statelliteMap, visitResult, valmap, listResult, yxqkssj, yxqjssj, returnMap]
     * @Date 17:43 2019/8/5
     */
    public Map<String, Object> visitCalculation(CalcService calcService, List<String> allStatellites, Map<String, List<double[]>> statelliteMap,
                                                List<TargetVisitResponse> visitResult, List<TargetInfoEntity> valmap, ResultEntry<List<TargetVisitResponse>> listResult,
                                                String yxqkssj,String yxqjssj,Map<String, Object> returnMap) {
        for(String wxbs : allStatellites){
            //生成访问文件
            ResultEntry<String> resultEntry =  calcService.createVisitCalcRequestFile(wxbs,yxqkssj,yxqjssj,statelliteMap.get(wxbs));
            if(!resultEntry.getStatus()){
                ResultEntry result = new ResultEntry(false, resultEntry.getMsg());
                returnMap.put("status",false);
                returnMap.put("msg",result.getMsg());
                return returnMap;
            }
            // 执行目标访问服务，返回目标访问响应对象集合
            listResult = calcService.invokeVisitCalcService(wxbs, valmap,resultEntry.getData(),true);
            if (listResult.getStatus()) {
                visitResult.addAll(listResult.getData());
            } else {
                ResultEntry result = new ResultEntry(false, listResult.getMsg());
                returnMap.put("status",false);
                returnMap.put("msg",result.getMsg());
                return returnMap;
            }
        }
        return returnMap;
    }

    /**
     * @Description //TODO 处理卫星观测开始时间 跟观测结束时间
     * @Author kg
     * @Param [visitResult]
     * @Date 9:52 2019/7/26
     */
    public void mergeSatelliteTime(List<TargetVisitResponse> visitResult) {
        Map<String,List<TargetVisitResponse>> collect = visitResult.stream().collect(Collectors.groupingBy(t -> t.getWxbs()));
        Set<String> keys = collect.keySet();
        String startTime = null;
        String endTime = null;
        String centralTime = null;
        List<TargetVisitResponse> targetInfos = new ArrayList<>();
        for(String wxbs : keys){
            Double defaultTime = demandMapper.mergeDefaultTime(wxbs);
            if(defaultTime==null)continue;
            targetInfos = collect.get(wxbs);
            for(TargetVisitResponse targetVisitResponse : targetInfos){
                centralTime = targetVisitResponse.getZxdfwsj();
                if(centralTime==null||centralTime=="")continue;
                startTime = TimeUtil.timeStrAddValue(centralTime,defaultTime,null);
                endTime = TimeUtil.timeStrSubValue(centralTime,defaultTime,null);
                targetVisitResponse.setGckssj(startTime);
                targetVisitResponse.setGcjssj(endTime);
            }
        }
    }

    /**
     * @Description //TODO 保存元任务信息
     * @Author kg
     * @Param [visitResult, demandId]
     * @Date 8:45 2019/8/6
     */
    public void saveMetatask(List<TargetVisitResponse> visitResult, Integer demandId) {
        if(isNotEmpty(visitResult)){
            //如果数据为空则还是未筹划状态
            demandMapper.demandStatus(demandId,staticConst.XQXX_XQZT_YCH_ID);
            log.info("更新需求状态————完成》》》》》》》》》》》》》》》》》》》》》》》》");
            //保存元任务信息
            mergeSatelliteTime(visitResult);
            demandMapper.saveMetatask(visitResult);
            log.info("保存元任务信息完成》》》》》》》》》》》》》》》》》》》》》》》》");

            List<TargetVisitSubEntity> targetVisitSubInfo = mergeTargetVsInfo(visitResult,demandId);
            demandMapper.saveMetataskTargetInfo(targetVisitSubInfo);
            log.info("保存元任务————坐标信息完成》》》》》》》》》》》》》》》》》》》》》》》》");

            demandMapper.saveMetataskStatus(targetVisitSubInfo);
            log.info("保存元任务————状态信息完成》》》》》》》》》》》》》》》》》》》》》》》》");
        }
    }
}
