package com.cetc.plan.utils;

import com.cetc.plan.config.StaticConst;
import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.SateliteEntity;
import com.cetc.plan.demand.model.TargetInfoEntity;
import com.cetc.plan.demand.model.param.ParamEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 需求信息 数据转换、判空工具类 //TODO
 * @Author kg
 * @Date 10:15 2019/6/20
 **/
@Component
public class DemandUtils {

    @Resource
    StaticConst staticConst;

    private static int  mbbh = 0;


    public DemandUtils() {
        super();
    }

    /**
     * @Description 判断List是否为空
     * @Author kg
     * @Param [mapList]
     * @return boolean
     * @Date 15:19 2019/6/20
     */
    public  boolean isEmpty(List<?> list){
        if(list == null || list.isEmpty() || list.size()<=0  ){
            return true;
        }
        return false;
    }

    /**
     * @Description 判断List是否不为空
     * @Author kg
     * @Param [mapList]
     * @return boolean
     * @Date 15:19 2019/6/20
     */
    public  boolean isNotEmpty(List<?> list){
        if(list == null || list.isEmpty() || list.size()<=0  ){
            return false;
        }
        return true;
    }

    /**
     * @Description //判断map是否为空
     * @Author kg
     * @Param [map]
     * @return boolean
     * @Date 15:25 2019/6/20
     */
    public  boolean isEmpyt(Map<?,?> map){
        if(map == null || map.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * @Description //判断map是否不为空
     * @Author kg
     * @Param [map]
     * @return boolean
     * @Date 15:25 2019/6/20
     */
    public  boolean isNotEmpyt(Map<?,?> map){
        if(map == null || map.isEmpty() ){
            return false;
        }
        return true;
    }

    /**
     * @Description //TODO 获取程序生成需求标识号
     * @Author kg
     * @Param [code] 需求编号
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
                targetInfoEntity.setMblx(staticConst.MBXX_MBLX_ZDMB_ID);
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
    public  List<SateliteEntity> getHandleSatelite(List<TargetInfoEntity> list,Integer xqbh){
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

}
