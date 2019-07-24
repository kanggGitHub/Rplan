package com.cetc.plan.demand.model.demand;

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
     * 卫星代号
     */
    @TableField("WXDH")
    private String wxdh;

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
     * 状态
     */
    @TableField("ZT")
    private String zt;

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




    @Override
    public String toString() {
        return "SateliteEntity{" +
                "wxbs='" + wxbs + '\'' +
                ", wxmc='" + wxmc + '\'' +
                ", rwdh='" + rwdh + '\'' +
                ", wxlx='" + wxlx + '\'' +
                ", wxyt='" + wxyt + '\'' +
                ", rksj=" + rksj +
                ", xqbh='" + xqbh + '\'' +
                ", mbbh='" + mbbh + '\'' +
                '}';
    }
}
