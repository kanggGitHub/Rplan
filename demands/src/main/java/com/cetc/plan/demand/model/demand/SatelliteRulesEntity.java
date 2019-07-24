package com.cetc.plan.demand.model.demand;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SatelliteRulesEntity
 * @Description: TODO 卫星匹配规则
 * @author: kg
 * @Date: 2019/7/19 11:18
 */
@Data
public class SatelliteRulesEntity implements Serializable {
    private static final long serialVersionUID=1L;
    /**
     * 卫星标识
     */
    @TableId("WXBS")
    private String wxbs;

    /**
     * 传感器标识
     */
    @TableId("CGQBS")
    private String cgqbs;

    /**
     * 工作模式
     */
    @TableId("GZMS")
    private String gzms;

    /**
     * 卫星类型
     */
    @TableId("WXLX")
    private String wxlx;

    /**
     * 固定目标监视
     */
    @TableId("GDMBJS")
    private String gdmbjs;

    /**
     * 区域目标搜索
     */
    @TableId("QYMBSS")
    private String qymbss;

    /**
     * 动目标跟踪监视
     */
    @TableId("DMBGZJS")
    private String dmbgzjs;

    /**
     * 分辨率
     */
    @TableId("FBL")
    private Integer fbl;

    /**
     * 最小太阳高度角
     */
    @TableId("ZXTYGDJ")
    private Integer zxtygdj;

    /**
     * 气象影响
     */
    @TableId("QXYX")
    private String qxyx;

    /**
     * 合并时长
     */
    @TableId("HBSC")
    private Integer hbsc;

    /**
     * 缺省标记
     */
    @TableId("CGQBS")
    private Integer qsbj;


    @Override
    public String toString() {
        return "SatelliteRulesEntity{" +
                "wxbs='" + wxbs + '\'' +
                ", cgqbs='" + cgqbs + '\'' +
                ", gzms='" + gzms + '\'' +
                ", wxlx='" + wxlx + '\'' +
                ", gdmbjs='" + gdmbjs + '\'' +
                ", qymbss='" + qymbss + '\'' +
                ", dmbgzjs='" + dmbgzjs + '\'' +
                ", fbl=" + fbl +
                ", zxtygdj=" + zxtygdj +
                ", qxyx='" + qxyx + '\'' +
                ", hbsc=" + hbsc +
                ", qsbj=" + qsbj +
                '}';
    }
}
