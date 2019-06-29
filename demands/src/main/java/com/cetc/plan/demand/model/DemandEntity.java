package com.cetc.plan.demand.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 需求筹划_需求信息
 * </p>
 *
 * @author kg
 * @since 2019-06-20
 */
@TableName("XQCH_XQXX")
public class DemandEntity implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 需求编号
     */
    @TableId("XQBH")
    private Integer xqbh;

    /**
     * 需求标识号
     */
    @TableField("XQBSH")
    private String xqbsh;

    /**
     * 需求名称
     */
    @TableField("XQMC")
    private String xqmc;

    /**
     * 业务类型
     */
    @TableField("RWLX")
    private String rwlx;

    /**
     * 观测最早时间
     */
    @TableField("YXQKSSJ")
    private Date yxqkssj;

    /**
     * 观测最晚时间
     */
    @TableField("YXQJSSJ")
    private Date yxqjssj;

    /**
     * 优先级
     */
    @TableField("YXJ")
    private String yxj;

    /**
     * 需求正文
     */
    @TableField("XQZW")
    private String xqzw;

    /**
     * 来源
     */
    @TableField("LY")
    private String ly;

    /**
     * 需求状态
     */
    @TableField("GCPC")
    private String gcpc;

    /**
     * 文件内容
     */
    @TableField("WJNR")
    private Blob wjnr;

    /**
     * 入库时间
     */
    @TableField("RKSJ")
    private Date rksj;


    public Integer getXqbh() {
        return xqbh;
    }

    public void setXqbh(Integer xqbh) {
        this.xqbh = xqbh;
    }

    public String getXqbsh() {
        return xqbsh;
    }

    public void setXqbsh(String xqbsh) {
        this.xqbsh = xqbsh;
    }

    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    public String getRwlx() {
        return rwlx;
    }

    public void setRwlx(String rwlx) {
        this.rwlx = rwlx;
    }

    public Date getYxqkssj() {
        return yxqkssj;
    }

    public void setYxqkssj(Date yxqkssj) {
        this.yxqkssj = yxqkssj;
    }

    public Date getYxqjssj() {
        return yxqjssj;
    }

    public void setYxqjssj(Date yxqjssj) {
        this.yxqjssj = yxqjssj;
    }

    public String getYxj() {
        return yxj;
    }

    public void setYxj(String yxj) {
        this.yxj = yxj;
    }

    public String getXqzw() {
        return xqzw;
    }

    public void setXqzw(String xqzw) {
        this.xqzw = xqzw;
    }

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    public String getGcpc() {
        return gcpc;
    }

    public void setGcpc(String gcpc) {
        this.gcpc = gcpc;
    }

    public Blob getWjnr() {
        return wjnr;
    }

    public void setWjnr(Blob wjnr) {
        this.wjnr = wjnr;
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    @Override
    public String toString() {
        return "DemandEntity{" +
        "xqbh=" + xqbh +
        ", xqbsh=" + xqbsh +
        ", xqmc=" + xqmc +
        ", rwlx=" + rwlx +
        ", yxqkssj=" + yxqkssj +
        ", yxqjssj=" + yxqjssj +
        ", yxj=" + yxj +
        ", xqzw=" + xqzw +
        ", ly=" + ly +
        ", gcpc=" + gcpc +
        ", wjnr=" + wjnr +
        ", rksj=" + rksj +
        "}";
    }
}
