package com.cetc.plan.demand.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname TargetVisitSubEntity  侦察元任务_目标信息、侦察元任务_状态记录
 * @Description: TODO
 * @author: kg
 * @Date: 2019/7/22 16:43
 */
@Data
public class TargetVisitSubEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //侦察元任务_目标信息
    /**
     * 元任务编号
     */
    private Integer yrwbh;

    /**
     * 需求编号
     */
    private Integer xqbh;

    /**
     * 目标编号
     */
    private Integer mbbh;

    /**
     * 入库时间
     */
    private String rksj;



    //侦察元任务_状态记录
    /**
     * 元任务状态
     */
    private String yrwzt;

    /**
     * 记录编号
     */
    private Integer jlbh;

    /**
     * 更新时间
     */
    private String gxsj;

    /**
     * 更新原因
     */
    private String gxyy;

    /**
     * 操作人
     */
    private String czr;


    @Override
    public String toString() {
        return "TargetVisitSubEntity{" +
                "yrwbh='" + yrwbh + '\'' +
                ", xqbh='" + xqbh + '\'' +
                ", mbbh='" + mbbh + '\'' +
                ", rksj='" + rksj + '\'' +
                ", yrwzt='" + yrwzt + '\'' +
                ", jlbh='" + jlbh + '\'' +
                ", gxsj='" + gxsj + '\'' +
                ", gxyy='" + gxyy + '\'' +
                ", czr='" + czr + '\'' +
                '}';
    }
}
