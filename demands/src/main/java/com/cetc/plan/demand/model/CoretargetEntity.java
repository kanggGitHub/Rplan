package com.cetc.plan.demand.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 重点目标库 目标信息、目标坐标信息
 */

public class CoretargetEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 重点目标编号
     */
    @TableId("ZDMBBH")
    private String zdmbbh;

    /**
     * 重点目标名称
     */
    @TableField("ZDMBMC")
    private String zdmbmc;

    /**
     * 目标类型
     */
    @TableField("MBLX")
    private String mblx;

    /**
     * 国家地区
     */
    @TableField("GJDQ")
    private String gjdq;

    /**
     * 地物类型
     */
    @TableField("DWLX")
    private String dwlx;

    /**
     * 坐标点序号
     */
    @TableField("ZBDXH")
    private String zbdxh;

    /**
     * 经度
     */
    @TableField("JD")
    private Double jd;

    /**
     * 纬度
     */
    @TableField("WD")
    private Double wd;

    /**
     * 半径
     */
    @TableField("BJ")
    private String bj;

    /**
     * 时间
     */
    @TableField("SJ")
    private Date sj;

    /**
     * 航向
     */
    @TableField("HX")
    private String hx;

    /**
     * 航速
     */
    @TableField("HS")
    private String hs;

    /**
     * 入库时间
     */
    @TableField("RKSJ")
    private Date rksj;

    /**
     * 目标信息、目标坐标信息
     */
    private List<TargetInfoEntity> targetInfoList;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getZdmbbh() {
        return zdmbbh;
    }

    public void setZdmbbh(String zdmbbh) {
        this.zdmbbh = zdmbbh;
    }

    public String getZbdxh() {
        return zbdxh;
    }

    public void setZbdxh(String zbdxh) {
        this.zbdxh = zbdxh;
    }

    public Double getJd() {
        return jd;
    }

    public void setJd(Double jd) {
        this.jd = jd;
    }

    public Double getWd() {
        return wd;
    }

    public void setWd(Double wd) {
        this.wd = wd;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }

    public Date getSj() {
        return sj;
    }

    public void setSj(Date sj) {
        this.sj = sj;
    }

    public String getHx() {
        return hx;
    }

    public void setHx(String hx) {
        this.hx = hx;
    }

    public String getHs() {
        return hs;
    }

    public void setHs(String hs) {
        this.hs = hs;
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    public String getZdmbmc() {
        return zdmbmc;
    }

    public void setZdmbmc(String zdmbmc) {
        this.zdmbmc = zdmbmc;
    }

    public String getMblx() {
        return mblx;
    }

    public void setMblx(String mblx) {
        this.mblx = mblx;
    }

    public String getGjdq() {
        return gjdq;
    }

    public void setGjdq(String gjdq) {
        this.gjdq = gjdq;
    }

    public String getDwlx() {
        return dwlx;
    }

    public void setDwlx(String dwlx) {
        this.dwlx = dwlx;
    }

    public List<TargetInfoEntity> getTargetInfoList() {
        return targetInfoList;
    }

    public void setTargetInfoList(List<TargetInfoEntity> targetInfoList) {
        this.targetInfoList = targetInfoList;
    }

    @Override
    public String toString() {
        return "CoretargetEntity{" +
                "zdmbbh='" + zdmbbh + '\'' +
                ", zdmbmc='" + zdmbmc + '\'' +
                ", mblx='" + mblx + '\'' +
                ", gjdq='" + gjdq + '\'' +
                ", dwlx='" + dwlx + '\'' +
                ", zbdxh='" + zbdxh + '\'' +
                ", jd=" + jd +
                ", wd=" + wd +
                ", bj='" + bj + '\'' +
                ", sj=" + sj +
                ", hx='" + hx + '\'' +
                ", hs='" + hs + '\'' +
                ", rksj=" + rksj +
                ", targetInfoList=" + targetInfoList +
                '}';
    }
}
