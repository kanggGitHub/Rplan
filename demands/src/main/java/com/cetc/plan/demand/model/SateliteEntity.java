package com.cetc.plan.demand.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kg
 * @since 2019-06-28
 */
@Data
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
    private String fssj;

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
    private String rksj;







    /*冗余字段-------------------------------------------卫星要求*/

    /**
     * 需求编号
     */
    @TableId("XQBH")
    private Integer xqbh;

    /**
     * 目标编号
     */
    @TableField("MBBH")
    private Integer mbbh;

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
