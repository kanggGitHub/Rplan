package com.cetc.plan.demand.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 需求筹划_需求信息_目标信息、目标坐标信息
 * </p>
 *
 * @author kg
 * @since 2019-06-28
 */
@Data
public class TargetInfoEntity implements Serializable {

private static final long serialVersionUID=1L;

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
     * 需求类型
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
    private String rksj;


    /**
     * 坐标点序号
     */
    @TableField("ZBDXH")
    private Integer zbdxh;

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
    private String sj;

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
     * 左上角经度
     */
    private String leftlog;

    /**
     * 左上角纬度
     */
    private String leftlat;

    /**
     * 右上角经度
     */
    private String rightlog;

    /**
     * 右上角纬度
     */
    private String rightlat;

    /**
     * 卫星标识
     */
    private String wxbs;

    /**
     * 自定义目标类型
     */
    private String targetType;

    @Override
    public String toString() {
        return "TargetInfoEntity{" +
                "xqbh=" + xqbh +
                ", mbbh=" + mbbh +
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
                ", leftlog='" + leftlog + '\'' +
                ", leftlat='" + leftlat + '\'' +
                ", rightlog='" + rightlog + '\'' +
                ", rightlat='" + rightlat + '\'' +
                ", wxbs='" + wxbs + '\'' +
                ", targettype='" + targetType + '\'' +
                '}';
    }
}
