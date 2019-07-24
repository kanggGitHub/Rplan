package com.cetc.plan.demand.model.demand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 重点目标库 目标信息、目标坐标信息
 */
@Data
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
     * 入库时间
     */
    @TableField("RKSJ")
    private String rksj;

    private String totalCount;

    /**
     * 目标信息、目标坐标信息
     */
    private List<TargetInfoEntity> targetInfoList;


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
                ", totalcount=" + totalCount +
                ", targetInfoList=" + targetInfoList +
                '}';
    }
}
