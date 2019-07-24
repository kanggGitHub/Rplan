package com.cetc.plan.demand.model;

/**
 * 指向计算请求实体
 *
 * @author wyb
 */
public class PointCalcRequest {

    /**
     * 中心点访问时间
     */
    private String zxdfwsj;

    /**
     * 侧摆角
     */
    private double cbj;

    /**
     * 俯仰角
     */
    private double fyj;

    /**
     * 访问圈号
     */
    private String fwqh;


    public PointCalcRequest() {
    }

    public PointCalcRequest(String zxdfwsj, double cbj, double fyj, String fwqh) {
        this.zxdfwsj = zxdfwsj;
        this.cbj = cbj;
        this.fyj = fyj;
        this.fwqh = fwqh;
    }

    public String getZxdfwsj() {
        return zxdfwsj;
    }

    public void setZxdfwsj(String zxdfwsj) {
        this.zxdfwsj = zxdfwsj;
    }

    public double getCbj() {
        return cbj;
    }

    public void setCbj(double cbj) {
        this.cbj = cbj;
    }

    public double getFyj() {
        return fyj;
    }

    public void setFyj(double fyj) {
        this.fyj = fyj;
    }

    public String getFwqh() {
        return fwqh;
    }

    public void setFwqh(String fwqh) {
        this.fwqh = fwqh;
    }

    @Override
    public String toString() {
        return "PointCalcRequest{" +
                "zxdfwsj='" + zxdfwsj + '\'' +
                ", cbj=" + cbj +
                ", fyj=" + fyj +
                ", fwqh='" + fwqh + '\'' +
                '}';
    }
}
