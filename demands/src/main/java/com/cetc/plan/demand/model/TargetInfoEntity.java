package com.cetc.plan.demand.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 需求筹划_需求信息_目标信息、目标坐标信息
 * </p>
 *
 * @author kg
 * @since 2019-06-28
 */
public class TargetInfoEntity implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 需求编号
     */
    @TableId("XQBH")
    private String xqbh;

    /**
     * 目标编号
     */
    @TableField("MBBH")
    private String mbbh;

    /**
     * 目标名称
     */
    @TableField("MBMC")
    private String mbmc;

    /**
     * 坐标点序号
     */
    @TableField("ZDMBBH")
    private String zdmbbh;

    /**
     * 优先级
     */
    @TableField("YXJ")
    private String yxj;

    /**
     * 业务类型
     */
    @TableField("RWLX")
    private String rwlx;

    /**
     * 分辨率要求
     */
    @TableField("FBLYQ")
    private String fblyq;

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
     * 入库时间
     */
    @TableField("RKSJ")
    private Date rksj;


    /**
     * 坐标点序号
     */
    @TableField("ZBDXH")
    private String zbdxh;

    /**
     * 经度
     */
    @TableField("JD")
    private String jd;

    /**
     * 纬度
     */
    @TableField("WD")
    private String wd;

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

    public String getXqbh() {
        return xqbh;
    }

    public void setXqbh(String xqbh) {
        this.xqbh = xqbh;
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

    public String getZdmbbh() {
        return zdmbbh;
    }

    public void setZdmbbh(String zdmbbh) {
        this.zdmbbh = zdmbbh;
    }

    public String getYxj() {
        return yxj;
    }

    public void setYxj(String yxj) {
        this.yxj = yxj;
    }

    public String getRwlx() {
        return rwlx;
    }

    public void setRwlx(String rwlx) {
        this.rwlx = rwlx;
    }

    public String getFblyq() {
        return fblyq;
    }

    public void setFblyq(String fblyq) {
        this.fblyq = fblyq;
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

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    public String getZbdxh() {
        return zbdxh;
    }

    public void setZbdxh(String zbdxh) {
        this.zbdxh = zbdxh;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
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

    @Override
    public String toString() {
        return "TargetInfoEntity{" +
                "xqbh='" + xqbh + '\'' +
                ", mbbh='" + mbbh + '\'' +
                ", mbmc='" + mbmc + '\'' +
                ", zdmbbh='" + zdmbbh + '\'' +
                ", yxj='" + yxj + '\'' +
                ", rwlx='" + rwlx + '\'' +
                ", fblyq='" + fblyq + '\'' +
                ", mblx='" + mblx + '\'' +
                ", gjdq='" + gjdq + '\'' +
                ", dwlx='" + dwlx + '\'' +
                ", rksj=" + rksj +
                ", zbdxh='" + zbdxh + '\'' +
                ", jd='" + jd + '\'' +
                ", wd='" + wd + '\'' +
                ", bj='" + bj + '\'' +
                ", sj=" + sj +
                ", hx='" + hx + '\'' +
                ", hs='" + hs + '\'' +
                '}';
    }
}
