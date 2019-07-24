package com.cetc.plan.demand.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 卫星配置信息
 *
 * @author wyb
 */
@Component
public class ConfigEntity {

    /**
     * 文件基础路径
     */
    @Value("${myCustom.shareDir.basePath}")
    private String basePath;

    /**
     * 轨道默认圈数时间间隔，单位毫秒
     */
    @Value("${myCustom.gdqs.gdqIntervalTime}")
    private int gdqIntervalTime;

    /**
     * 指向计算时间前后添加时间毫秒值
     */
    @Value("${myCustom.point.requestFile.addTimeValue}")
    private int addTimeValue;

    /**
     * 条带点间隔时长毫秒值
     */
    @Value("${myCustom.point.requestFile.tdIntervalTime}")
    private int tdIntervalTime;

    /**
     * udp访问超时时长，单位秒
     */
    @Value("${myCustom.udp.timeout}")
    private int udpTimeout;

    /**
     * 点处理-最大点数
     */
    @Value("${myCustom.pointDispose.maxSize}")
    private int maxSize;

    /**
     * 点处理-两点最大距离，单位千米
     */
    @Value("${myCustom.pointDispose.range}")
    private double range;

    /**
     * 点处理-三间最大距离，单位千米
     */
    @Value("${myCustom.pointDispose.threePointMaxRage}")
    private double threePointMaxRage;

    /**
     * 覆盖率-全覆盖最大处理天数
     */
    @Value("${myCustom.fgl.maxFullCoverDay}")
    private int maxFullCoverDay;



    public ConfigEntity() {
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public int getGdqIntervalTime() {
        return gdqIntervalTime;
    }

    public void setGdqIntervalTime(int gdqIntervalTime) {
        this.gdqIntervalTime = gdqIntervalTime;
    }

    public int getAddTimeValue() {
        return addTimeValue;
    }

    public void setAddTimeValue(int addTimeValue) {
        this.addTimeValue = addTimeValue;
    }

    public int getTdIntervalTime() {
        return tdIntervalTime;
    }

    public void setTdIntervalTime(int tdIntervalTime) {
        this.tdIntervalTime = tdIntervalTime;
    }

    public int getUdpTimeout() {
        return udpTimeout;
    }

    public void setUdpTimeout(int udpTimeout) {
        this.udpTimeout = udpTimeout;
    }


    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getThreePointMaxRage() {
        return threePointMaxRage;
    }

    public void setThreePointMaxRage(double threePointMaxRage) {
        this.threePointMaxRage = threePointMaxRage;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getMaxFullCoverDay() {
        return maxFullCoverDay;
    }

    public void setMaxFullCoverDay(int maxFullCoverDay) {
        this.maxFullCoverDay = maxFullCoverDay;
    }



    @Override
    public String toString() {
        return "ConfigEntity{" +
                "basePath='" + basePath + '\'' +
                ", gdqIntervalTime=" + gdqIntervalTime +
                ", addTimeValue=" + addTimeValue +
                ", tdIntervalTime=" + tdIntervalTime +
                ", udpTimeout=" + udpTimeout +
                ", maxSize=" + maxSize +
                ", range=" + range +
                ", maxFullCoverDay=" + maxFullCoverDay +
                '}';
    }
}
