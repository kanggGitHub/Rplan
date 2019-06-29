package com.cetc.plan.utils;

import com.cetc.plan.demand.model.CoretargetEntity;

import java.util.*;

/**
 * @Description 数据转换、判空工具类 //TODO
 * @Author kg
 * @Date 10:15 2019/6/20
 **/
public class DemandUtils {

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

    public  static Long getUid(){
        UUID uuid = UUID.randomUUID();
        return null;
    }


}
