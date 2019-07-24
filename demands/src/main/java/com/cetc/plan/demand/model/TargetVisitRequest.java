package com.cetc.plan.demand.model;

/**
 * 访问计算请求实体
 *
 * @author wyb
 */
public class TargetVisitRequest {

    /**
     * 目标编号
     */
    private String mbbh;

    /**
     * 目标名称
     */
    private String mbmc;

    /**
     * 目标经度
     */
    private double mbjd;

    /**
     * 目标纬度
     */
    private double mbwd;

    public TargetVisitRequest() {
    }

    public TargetVisitRequest(String mbbh, String mbmc, double mbjd, double mbwd) {
        this.mbbh = mbbh;
        this.mbmc = mbmc;
        this.mbjd = mbjd;
        this.mbwd = mbwd;
    }

    public String getMbbh() {
        return mbbh;
    }

    public void setMbbh(String mbbh) {
        this.mbbh = mbbh;
    }

    public String getMbmc() {
        return mbmc;
    }

    public void setMbmc(String mbmc) {
        this.mbmc = mbmc;
    }

    public double getMbjd() {
        return mbjd;
    }

    public void setMbjd(double mbjd) {
        this.mbjd = mbjd;
    }

    public double getMbwd() {
        return mbwd;
    }

    public void setMbwd(double mbwd) {
        this.mbwd = mbwd;
    }

    @Override
    public String toString() {
        return "TargetVisitRequest{" +
                "mbbh='" + mbbh + '\'' +
                ", mbmc='" + mbmc + '\'' +
                ", mbjd=" + mbjd +
                ", mbwd=" + mbwd +
                '}';
    }
}
