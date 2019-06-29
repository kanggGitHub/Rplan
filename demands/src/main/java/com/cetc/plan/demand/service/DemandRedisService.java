package com.cetc.plan.demand.service;


import java.util.List;
import java.util.Map;

/**
 * @Interface //TODO redis缓存 接口服务类
 * @Author kg
 * @Param
 * @Date 14:44 2019/6/24
 */
public interface DemandRedisService {
    void setRedisByKeyAndValue(String key,Map value);
    Map<String,Object> getMapRedisByKey(String key);
    void setRedisByKeyAndValue(String key,Object value);
    Object getObjectRedisByKey(String key);
    void setRedisByKeyAndValue(String key, List<?> value);
    List<?> getListRedisByKey(String key);
}
