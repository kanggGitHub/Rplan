package com.cetc.plan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  日志创建基类
 * @author kg
 */
public class LogUtils {


    /**
     * Return a logger.
     *
     * @param clz
     * @return
     */
    public static Logger getLogger(Class clz) {
        return LoggerFactory.getLogger(clz);
    }


}
