package com.cetc.plan.demand.model;

/**
 * 指向计算响应实体
 *
 * @author wyb
 */
public class PointCalcResponse {

    /**
     * 经度
     */
    private double jd;
    /**
     * 纬度
     */
    private double wd;

    /**
     * 时间标识
     */
    private String time;

    /**
     * 访问圈号
     */
    private String fwqh;

    public PointCalcResponse() {
    }

    public PointCalcResponse(double jd, double wd, String time, String fwqh) {
        this.jd = jd;
        this.wd = wd;
        this.time = time;
        this.fwqh = fwqh;
    }

    public double getJd() {
        return jd;
    }

    public void setJd(double jd) {
        this.jd = jd;
    }

    public double getWd() {
        return wd;
    }

    public void setWd(double wd) {
        this.wd = wd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFwqh() {
        return fwqh;
    }

    public void setFwqh(String fwqh) {
        this.fwqh = fwqh;
    }

    @Override
    public String toString() {
        return "PointCalcResponse{" +
                "jd=" + jd +
                ", wd=" + wd +
                ", time='" + time + '\'' +
                ", fwqh='" + fwqh + '\'' +
                '}';
    }
}
