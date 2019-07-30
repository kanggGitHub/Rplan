package com.cetc.plan.demand.model.demand;

import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 需求筹划_需求信息
 * </p>
 *
 * @author kg
 * @since 2019-06-20
 */
@Data
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
    private String yxqkssj;

    /**
     * 观测最晚时间
     */
    @TableField("YXQJSSJ")
    private String yxqjssj;

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
     * 观测频次
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
    private String rksj;

    //*********冗余字段 ------需求状态

    /**
     * 记录编号
     */
    @TableField("JLBH")
    private Integer jlbh;

    /**
     * 需求状态
     */
    @TableField("XQZT")
    private String xqzt;

    /**
     * 更新时间
     */
    @TableField("GXSJ")
    private String gxsj;

    /**
     * 更新原因
     */
    @TableField("GXYY")
    private String gxyy;

    /**
     * 操作人
     */
    @TableField("CZR")
    private String czr;

    private List<Integer> denamdsId;

    private String totalCount;

    private List<TargetInfoEntity> taregetinfolist;

    @Override
    public String toString() {
        return "DemandEntity{" +
                "xqbh=" + xqbh +
                ", xqbsh='" + xqbsh + '\'' +
                ", xqmc='" + xqmc + '\'' +
                ", rwlx='" + rwlx + '\'' +
                ", yxqkssj=" + yxqkssj +
                ", yxqjssj=" + yxqjssj +
                ", yxj='" + yxj + '\'' +
                ", xqzw='" + xqzw + '\'' +
                ", ly='" + ly + '\'' +
                ", gcpc='" + gcpc + '\'' +
                ", wjnr=" + wjnr +
                ", rksj=" + rksj +
                ", jlbh=" + jlbh +
                ", xqzt=" + xqzt +
                ", gxsj=" + gxsj +
                ", gxyy=" + gxyy +
                ", czr=" + czr +
                ", denamdsId=" + denamdsId +
                ", totalcount=" + totalCount +
                ", taregetinfolist=" + taregetinfolist +
                '}';
    }
}
