package com.cetc.plan.utils;

import com.cetc.plan.demand.model.CoretargetEntity;
import com.cetc.plan.demand.model.TargetInfoEntity;

import java.util.*;

/**
 * @Description 需求信息 数据转换、判空工具类 //TODO
 * @Author kg
 * @Date 10:15 2019/6/20
 **/
public class DemandUtils {

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
    public static boolean isEmpty(List<?> list){
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
    public static boolean isNotEmpty(List<?> list){
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
    public static boolean isEmpyt(Map<?,?> map){
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
    public static boolean isNotEmpyt(Map<?,?> map){
        if(map == null || map.isEmpty() ){
            return false;
        }
        return true;
    }
    /**
     * @Description //TODO 获取需求编号
     * @Author kg
     * @Param []
     * @Date 10:19 2019/7/1
     */
    public  static String getXqbhUid(){
        long xqbh = System.currentTimeMillis();
        return "XQBH"+xqbh;
    }

    /**
     * @Description //TODO 获取目标编号
     * @Author kg
     * @Param []
     * @Date 10:19 2019/7/1
     */
    public  static String getMbbhUid(){
        long mbbh = System.currentTimeMillis();
        return "MBBH"+mbbh;
    }

    /**
     * @Description 数据分组 查询重点目标库专用
     * @Author kg
     * @Param [collect]
     * @return java.util.List<java.util.Map>
     * @Date 15:19 2019/6/20
     */
    public static List<Map> dataGrouping(Map<String,List<CoretargetEntity>> collect){
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
     * @Description //TODO 获取 点目标point、目标库aim、行政区域region 信息组装
     * @Author kg
     * @Param [map]
     * @Date 9:53 2019/7/1
     */
    public static List<TargetInfoEntity> getARPList(Map<String, List<TargetInfoEntity>> map,String xqbh,String mbbh){
        List<TargetInfoEntity> arpList = new ArrayList<>();
        //获取点目标point
        List<TargetInfoEntity> pointList = map.get("point");
        setXqbh(pointList,xqbh,mbbh);//设置编号
        //获取目标库aim
        List<TargetInfoEntity> aimList = map.get("aim");
        setXqbh(aimList,xqbh,mbbh);//设置编号
        //获取行政区域region
        List<TargetInfoEntity> regionList = map.get("region");
        setXqbh(regionList,xqbh,mbbh);//设置编号
        if(isNotEmpty(pointList))
            arpList.addAll(pointList);
        if(isNotEmpty(aimList))
            arpList.addAll(aimList);
        if(isNotEmpty(regionList))
            arpList.addAll(regionList);

        return arpList;
    }

    /**
     * @Description //TODO 设置编号 目标编号、需求编号
     * @Author kg
     * @Param [list, xqbh, mbbh]
     * @Date 11:42 2019/7/1
     */
    private static void setXqbh(List<TargetInfoEntity> list, String xqbh,String mbbh) {
        if(isNotEmpty(list)){
            for(TargetInfoEntity targetInfoEntity : list){
                targetInfoEntity.setXqbh(xqbh);
                targetInfoEntity.setMbbh(mbbh);
            }
        }
    }

    /**
     * @Description //TODO 处理区域目标信息
     * @Author kg
     * @Param [list, xqbh]
     * @Date 10:35 2019/7/1
     */
    public static List<TargetInfoEntity> getAreaList(List<TargetInfoEntity> list,String xqbh,String mbbh){
        if(isEmpty(list))return  null;
        List<TargetInfoEntity> saveTargetInfo = new ArrayList<>();
        //创建新对象
        TargetInfoEntity targetInfo1 = new TargetInfoEntity();
        TargetInfoEntity targetInfo2 = new TargetInfoEntity();
        List<TargetInfoEntity> targetInfo3 = new ArrayList<>();
        for(TargetInfoEntity targetInfoEntity : list){
            targetInfo3 = targetInfoEntity.getPointList();
            if(isNotEmpty(targetInfo3)){
                for(TargetInfoEntity infoEntity : targetInfo3){
                    infoEntity.setMbbh(mbbh);
                    infoEntity.setXqbh(xqbh);
                }
            }
            //给创建的新对象赋值
            setTargetDate(targetInfo1,targetInfo2,targetInfoEntity,xqbh,mbbh);
            //返回的数据集合
            saveTargetInfo.addAll(targetInfo3);
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
    private static void setTargetDate(TargetInfoEntity targetInfo1, TargetInfoEntity targetInfo2,
                                      TargetInfoEntity targetInfoEntity, String xqbh, String mbbh) {
        targetInfo1.setMbbh(mbbh);
        targetInfo1.setXqbh(xqbh);
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
        targetInfo1.setZbdxh(targetInfoEntity.getZbdxh());
        targetInfo1.setZdmbbh(targetInfoEntity.getZdmbbh());

        targetInfo2.setMbbh(mbbh);
        targetInfo2.setXqbh(xqbh);
        targetInfo2.setBj(targetInfoEntity.getBj());
        targetInfo2.setDwlx(targetInfoEntity.getDwlx());
        targetInfo2.setFblyq(targetInfoEntity.getFblyq());
        targetInfo2.setGjdq(targetInfoEntity.getGjdq());
        targetInfo2.setHs(targetInfoEntity.getHs());
        targetInfo2.setHx(targetInfoEntity.getHx());
        targetInfo2.setJd(targetInfoEntity.getLeftlog());
        targetInfo2.setWd(targetInfoEntity.getLeftlat());
        targetInfo2.setMblx(targetInfoEntity.getMblx());
        targetInfo2.setMbmc(targetInfoEntity.getMbmc());
        targetInfo2.setRksj(targetInfoEntity.getRksj());
        targetInfo2.setRwlx(targetInfoEntity.getRwlx());
        targetInfo2.setSj(targetInfoEntity.getSj());
        targetInfo2.setWxbs(targetInfoEntity.getWxbs());
        targetInfo2.setYxj(targetInfoEntity.getYxj());
        targetInfo2.setZbdxh(targetInfoEntity.getZbdxh());
        targetInfo2.setZdmbbh(targetInfoEntity.getZdmbbh());
    }


}
