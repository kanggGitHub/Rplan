package com.cetc.plan.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname StaticConst 字典信息 初始化配置
 * @Description: TODO
 * @author: kg
 * @Date: 2019/7/3 8:56
 */
@Service
public class StaticConst {
    /*目标信息库中的目标类型*/
    public   String MBXX_MBLX_GDMB;//固定目标
    public   String MBXX_MBLX_QYMB;//区域目标
    public   String MBXX_MBLX_YDMB;//移动目标
    public   String MBXX_MBLX_DMB;//点目标
    public   String MBXX_MBLX_ZDMB;//重点目标
    public   String MBXX_MBLX_XZQY;//行政区域

    /*需求状态*/
    public   String XQXX_XQZT_WCH;//未筹划
    public   String XQXX_XQZT_YCH;//已筹划
    public   String XQXX_XQZT_YQX;//已取消

    /*目标信息库中的任务类型------需求类型*/
    public  String MBXX_XQLX_GDMBJC;//固定目标检测
    public  String MBXX_XQLX_QYMBSS;//区域目标搜索
    public  String MBXX_XQLX_DMBGZJS;//动目标跟踪监视


    /*目标信息-坐标信息-坐标点序号*/
    public Integer MBXX_ZBDXH1=1;
    public Integer MBXX_ZBDXH2=2;
    public Integer MBXX_ZBDXH3=3;
    public Integer MBXX_ZBDXH4=4;

    /*来源信息*/
    public String XQXX_LY;

    /*元任务信息*/
    public String ZCYRW_YRW_ZT;

    /*对应数据库字段---初始化使用*/
    /*需求状态*/
    public  String XQXX_XQZT_WCH_FIELD="UNPLANNED";//未筹划
    public  String XQXX_XQZT_YCH_FIELD="PLANNED";//已筹划
    public  String XQXX_XQZT_YQX_FIELD="CANCELLED";//已取消
    /*目标信息库中的目标类型*/
    public  String MBXX_MBLX_GDMB_FIELD="FIXEDTARGET";//固定目标
    public  String MBXX_MBLX_QYMB_FIELD="AREATARGET";//区域目标
    public  String MBXX_MBLX_YDMB_FIELD="MOVETARGET";//移动目标
    public   String MBXX_MBLX_DMB_FIELD="POINTTARGET";//点目标
    public   String MBXX_MBLX_ZDMB_FIELD="AIMTARGET";//重点目标
    public   String MBXX_MBLX_XZQY_FIELD="REGIONTARGET";//行政区域
    /*目标信息库中的任务类型------需求类型*/
    public  String MBXX_XQLX_GDMBJC_FIELD="FIXEDTARGETDETECTION";//固定目标检测
    public  String MBXX_XQLX_QYMBSS_FIELD="REGIONTARGETSEARCH";//区域目标搜索
    public  String MBXX_XQLX_DMBGZJS_FIELD="MOVETARGETTS";//动目标跟踪监视

    public String XQXX_LY_FIELD="SOURCE";

    public String ZCYRW_YRW_ZT_FIELD="SOURCE";
    /*初始数据id*/
    public  String XQXX_XQZT_WCH_ID;
    public  String XQXX_XQZT_YCH_ID;
    public  String XQXX_XQZT_YQX_ID;

    public  String MBXX_MBLX_GDMB_ID;//固定目标
    public  String MBXX_MBLX_YDMB_ID;//移动目标

    public  String MBXX_MBLX_QYMB_ID;//区域目标
    public   String MBXX_MBLX_DMB_ID;//点目标
    public   String MBXX_MBLX_ZDMB_ID;//重点目标
    public   String MBXX_MBLX_XZQY_ID;//行政区域

    public  String MBXX_XQLX_GDMBJC_ID;
    public  String MBXX_XQLX_QYMBSS_ID;
    public  String MBXX_XQLX_DMBGZJS_ID;

    public String XQXX_LY_ID;
    public String ZCYRW_YRW_ZT_ID;

    /*需求信息数据XQBH字段自增值*/
    public Integer XQXX_XQBH_ID = 1;
    public Integer MBXX_MBBH_ID = 1;
    public Integer ZCYRW_YRWBH_ID = 1;


    /**
     * @Description //TODO 初始化信息
     * @Author kg
     * @Param [list, name, value, id]
     * @Date 7:53 2019/7/4
     */
    public void setData(List<JSONObject> list ){
        String name = "";
        String value = "";
        String id = "";
        for(Object obj : list){
            name = ((JSONObject) obj).getString("fieldname");
            value = ((JSONObject) obj).getString("fielddata");
            id = ((JSONObject) obj).getString("id");
            if(MBXX_MBLX_GDMB_FIELD.equals(name)){
                MBXX_MBLX_GDMB = value;
                MBXX_MBLX_GDMB_ID = id;
            }else if(MBXX_MBLX_QYMB_FIELD.equals(name)){
                MBXX_MBLX_QYMB = value;
                MBXX_MBLX_QYMB_ID = id;
            }else if(MBXX_MBLX_YDMB_FIELD.equals(name)){
                MBXX_MBLX_YDMB = value;
                MBXX_MBLX_YDMB_ID = id;
            }else if(MBXX_MBLX_DMB_FIELD.equals(name)){
                MBXX_MBLX_DMB = value;
                MBXX_MBLX_DMB_ID = id;
            }else if(MBXX_MBLX_ZDMB_FIELD.equals(name)){
                MBXX_MBLX_ZDMB = value;
                MBXX_MBLX_ZDMB_ID = id;
            }else if(MBXX_MBLX_XZQY_FIELD.equals(name)){
                MBXX_MBLX_XZQY = value;
                MBXX_MBLX_XZQY_ID = id;
            }else if(MBXX_XQLX_DMBGZJS_FIELD.equals(name)){
                MBXX_XQLX_DMBGZJS = value;
                MBXX_XQLX_DMBGZJS_ID = id;
            }else if(MBXX_XQLX_GDMBJC_FIELD.equals(name)){
                MBXX_XQLX_GDMBJC = value;
                MBXX_XQLX_GDMBJC_ID = id;
            }else if(MBXX_XQLX_QYMBSS_FIELD.equals(name)){
                MBXX_XQLX_QYMBSS = value;
                MBXX_XQLX_QYMBSS_ID = id;
            }else if(XQXX_XQZT_WCH_FIELD.equals(name)){
                XQXX_XQZT_WCH = value;
                XQXX_XQZT_WCH_ID = id;
            }else if(XQXX_XQZT_YCH_FIELD.equals(name)){
                XQXX_XQZT_YCH = value;
                XQXX_XQZT_YCH_ID = id;
            }else if(XQXX_XQZT_YQX_FIELD.equals(name)){
                XQXX_XQZT_YQX = value;
                XQXX_XQZT_YQX_ID = id;
            }else if(XQXX_LY_FIELD.equals(name)){
                XQXX_LY = value;
                XQXX_LY_ID = id;
            }else if(ZCYRW_YRW_ZT_FIELD.equals(name)){
                ZCYRW_YRW_ZT = value;
                ZCYRW_YRW_ZT_ID = id;
            }
        }
    }
}
