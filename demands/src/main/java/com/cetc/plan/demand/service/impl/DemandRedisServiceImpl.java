package com.cetc.plan.demand.service.impl;

import com.cetc.plan.demand.service.DemandRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Classname DemandRedisServiceImpl
 * @Description: TODO redis缓存实现类 存取服务类
 * @author: kg
 * @Date: 2019/6/24 14:42
 */
@Service
public class DemandRedisServiceImpl implements DemandRedisService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void setRedisByKeyAndValue(String key, Map value) {
        redisTemplate.opsForHash().putAll(key,value);
    }

    @Override
    public Map<String, Object> getMapRedisByKey(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void setRedisByKeyAndValue(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public Object getObjectRedisByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setRedisByKeyAndValue(String key, List<?> value) {
        redisTemplate.opsForList().leftPush(key,value);
    }

    @Override
    public List<?> getListRedisByKey(String key) {
        return null;
    }


}
