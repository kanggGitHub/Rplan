package com.cetc.plan.utils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @Classname StringUtils
 * @Description: TODO 数据判空工具类
 * @author: kg
 * @Date: 2019/7/19 11:30
 */
public class StringUtils {


    /**
     * @Description //TODO 判断String是否为空
     * @Author kg
     * @Param
     * @Date 11:31 2019/7/19
     */
    public boolean isEmpty(String str){
        if(str == null ||  str == ""){
            return true;
        }
        return false;
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
     * @Description //TODO 四舍五入四位小数
     * @Author kg
     * @Param [str]
     * @Date 8:59 2019/7/23
     */
    public static String formatDouble(String str) {
        if(str!=null||str != "") {
            double d = Double.valueOf(str);
            DecimalFormat df = new DecimalFormat("#.0000");
            return df.format(d);
        }else{
            return null;
        }

    }

}
