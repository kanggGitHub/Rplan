package com.cetc.plan.demand.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author kg
 * @since 2019-06-28
 */
public class SateliteEntity implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 卫星标识
     */
    @TableId("WXBS")
    private String wxbs;

    /**
     * 卫星名称
     */
    @TableField("WXMC")
    private String wxmc;

    /**
     * 任务代号
     */
    @TableField("RWDH")
    private String rwdh;

    /**
     * 卫星类型
     */
    @TableField("WXLX")
    private String wxlx;

    /**
     * 卫星用途
     */
    @TableField("WXYT")
    private String wxyt;

    /**
     * 产品级别
     */
    @TableField("CPJB")
    private String cpjb;

    /**
     * 所属机构
     */
    @TableField("SSGJ")
    private String ssgj;

    /**
     * 发射时间
     */
    @TableField("FSSJ")
    private Date fssj;

    /**
     * 主要用户
     */
    @TableField("ZYYH")
    private String zyyh;

    /**
     * 中继数传能力
     */
    @TableField("ZJSCNL")
    private String zjscnl;

    /**
     * 中继测控能力
     */
    @TableField("ZJCKNL")
    private String zjcknl;

    /**
     * 星上处理能力
     */
    @TableField("XSCLNL")
    private String xsclnl;

    /**
     * 广播分发能力
     */
    @TableField("GBFFNL")
    private String gbffnl;

    /**
     * 数传能力
     */
    @TableField("SCNL")
    private String scnl;

    /**
     * 记录码速率
     */
    @TableField("JLMSL")
    private String jlmsl;

    /**
     * 对地数传码速率
     */
    @TableField("DDSCMSL")
    private String ddscmsl;

    /**
     * 对中继数传码速率
     */
    @TableField("DZJSCMSL")
    private String dzjscmsl;

    /**
     * 地面工作模式
     */
    @TableField("DMGZMS")
    private String dmgzms;

    /**
     * 是否关注
     */
    @TableField("SFGZ")
    private String sfgz;

    /**
     * 备注
     */
    @TableField("BZ")
    private String bz;

    /**
     * 状态
     */
    @TableField("ZT")
    private String zt;

    /**
     * 指令清空方式
     */
    @TableField("ZLQKFS")
    private String zlqkfs;

    /**
     * 指令清空操作方
     */
    @TableField("ZLQKCZF")
    private String zlqkczf;

    /**
     * 入库时间
     */
    @TableField("RKSJ")
    private Date rksj;







    /*冗余字段-------------------------------------------卫星要求*/

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
     * 产品类型
     */
    @TableField("CPLX")
    private String cplx;

    /**
     * 产品格式
     */
    @TableField("CPGS")
    private String cpgs;

    /**
     * 产品生产目的地
     */
    @TableField("CPSCMDD")
    private String cpscmdd;

    /**
     * 产品传输目的地
     */
    @TableField("CPCSMDD")
    private String cpcsmdd;


    public String getWxbs() {
        return wxbs;
    }

    public void setWxbs(String wxbs) {
        this.wxbs = wxbs;
    }

    public String getWxmc() {
        return wxmc;
    }

    public void setWxmc(String wxmc) {
        this.wxmc = wxmc;
    }

    public String getRwdh() {
        return rwdh;
    }

    public void setRwdh(String rwdh) {
        this.rwdh = rwdh;
    }

    public String getWxlx() {
        return wxlx;
    }

    public void setWxlx(String wxlx) {
        this.wxlx = wxlx;
    }

    public String getWxyt() {
        return wxyt;
    }

    public void setWxyt(String wxyt) {
        this.wxyt = wxyt;
    }

    public String getCpjb() {
        return cpjb;
    }

    public void setCpjb(String cpjb) {
        this.cpjb = cpjb;
    }

    public String getSsgj() {
        return ssgj;
    }

    public void setSsgj(String ssgj) {
        this.ssgj = ssgj;
    }

    public Date getFssj() {
        return fssj;
    }

    public void setFssj(Date fssj) {
        this.fssj = fssj;
    }

    public String getZyyh() {
        return zyyh;
    }

    public void setZyyh(String zyyh) {
        this.zyyh = zyyh;
    }

    public String getZjscnl() {
        return zjscnl;
    }

    public void setZjscnl(String zjscnl) {
        this.zjscnl = zjscnl;
    }

    public String getZjcknl() {
        return zjcknl;
    }

    public void setZjcknl(String zjcknl) {
        this.zjcknl = zjcknl;
    }

    public String getXsclnl() {
        return xsclnl;
    }

    public void setXsclnl(String xsclnl) {
        this.xsclnl = xsclnl;
    }

    public String getGbffnl() {
        return gbffnl;
    }

    public void setGbffnl(String gbffnl) {
        this.gbffnl = gbffnl;
    }

    public String getScnl() {
        return scnl;
    }

    public void setScnl(String scnl) {
        this.scnl = scnl;
    }

    public String getJlmsl() {
        return jlmsl;
    }

    public void setJlmsl(String jlmsl) {
        this.jlmsl = jlmsl;
    }

    public String getDdscmsl() {
        return ddscmsl;
    }

    public void setDdscmsl(String ddscmsl) {
        this.ddscmsl = ddscmsl;
    }

    public String getDzjscmsl() {
        return dzjscmsl;
    }

    public void setDzjscmsl(String dzjscmsl) {
        this.dzjscmsl = dzjscmsl;
    }

    public String getDmgzms() {
        return dmgzms;
    }

    public void setDmgzms(String dmgzms) {
        this.dmgzms = dmgzms;
    }

    public String getSfgz() {
        return sfgz;
    }

    public void setSfgz(String sfgz) {
        this.sfgz = sfgz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getZlqkfs() {
        return zlqkfs;
    }

    public void setZlqkfs(String zlqkfs) {
        this.zlqkfs = zlqkfs;
    }

    public String getZlqkczf() {
        return zlqkczf;
    }

    public void setZlqkczf(String zlqkczf) {
        this.zlqkczf = zlqkczf;
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

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

    public String getCplx() {
        return cplx;
    }

    public void setCplx(String cplx) {
        this.cplx = cplx;
    }

    public String getCpgs() {
        return cpgs;
    }

    public void setCpgs(String cpgs) {
        this.cpgs = cpgs;
    }

    public String getCpscmdd() {
        return cpscmdd;
    }

    public void setCpscmdd(String cpscmdd) {
        this.cpscmdd = cpscmdd;
    }

    public String getCpcsmdd() {
        return cpcsmdd;
    }

    public void setCpcsmdd(String cpcsmdd) {
        this.cpcsmdd = cpcsmdd;
    }


    @Override
    public String toString() {
        return "SateliteEntity{" +
                "wxbs='" + wxbs + '\'' +
                ", wxmc='" + wxmc + '\'' +
                ", rwdh='" + rwdh + '\'' +
                ", wxlx='" + wxlx + '\'' +
                ", wxyt='" + wxyt + '\'' +
                ", cpjb='" + cpjb + '\'' +
                ", ssgj='" + ssgj + '\'' +
                ", fssj=" + fssj +
                ", zyyh='" + zyyh + '\'' +
                ", zjscnl='" + zjscnl + '\'' +
                ", zjcknl='" + zjcknl + '\'' +
                ", xsclnl='" + xsclnl + '\'' +
                ", gbffnl='" + gbffnl + '\'' +
                ", scnl='" + scnl + '\'' +
                ", jlmsl='" + jlmsl + '\'' +
                ", ddscmsl='" + ddscmsl + '\'' +
                ", dzjscmsl='" + dzjscmsl + '\'' +
                ", dmgzms='" + dmgzms + '\'' +
                ", sfgz='" + sfgz + '\'' +
                ", bz='" + bz + '\'' +
                ", zt='" + zt + '\'' +
                ", zlqkfs='" + zlqkfs + '\'' +
                ", zlqkczf='" + zlqkczf + '\'' +
                ", rksj=" + rksj +
                ", xqbh='" + xqbh + '\'' +
                ", mbbh='" + mbbh + '\'' +
                ", cplx='" + cplx + '\'' +
                ", cpgs='" + cpgs + '\'' +
                ", cpscmdd='" + cpscmdd + '\'' +
                ", cpcsmdd='" + cpcsmdd + '\'' +
                '}';
    }
}
