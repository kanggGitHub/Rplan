package com.cetc.plan.demand.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.*;

/**
 * 访问计算响应实体
 *
 * @author wyb
 */
@Data
public class TargetVisitResponse implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 元任务编号
     */
    @TableId("YRWBH")
    private Integer yrwbh;

    /**
     * 目标编号
     */
    private Integer mbbh;

    /**
     * 目标名称
     */
    private String mbmc;

    /**
     * 需求名称
     */
    private String xqmc;

    /**
     * 卫星标识
     */
    @TableField("WXBS")
    private String wxbs;

    /**
     * 传感器标识
     */
    @TableField("CGQBS")
    private String cgqbs;

    /**
     * 传感器模式
     */
    @TableField("CGQMS")
    private String cgqms;

    /**
     * 优先次序
     */
    @TableField("YXCX")
    private String yxcx;

    /**
     * 中心经度
     */
    @TableField("ZXJD")
    private String zxjd;

    /**
     * 中心纬度
     */
    @TableField("ZXWD")
    private String zxwd;

    /**
     * 左上角经度
     */
    @TableField("ZSJJD")
    private String zsjjd;

    /**
     * 左上角纬度
     */
    @TableField("ZSJWD")
    private String zsjwd;

    /**
     * 右上角经度
     */
    @TableField("YSJJD")
    private String ysjjd;

    /**
     * 右上角纬度
     */
    @TableField("YSJWD")
    private String ysjwd;

    /**
     * 左下角经度
     */
    @TableField("ZXJJD")
    private String zxjjd;

    /**
     * 左下角纬度
     */
    @TableField("ZXJWD")
    private String zxjwd;

    /**
     * 右下角经度
     */
    @TableField("YXJJD")
    private String yxjjd;

    /**
     * 右下角纬度
     */
    @TableField("YXJWD")
    private String yxjwd;

    /**
     * 轨道圈号
     */
    @TableField("GDQH")
    private String gdqh;

    /**
     * 观测开始时间
     */
    @TableField("GCKSSJ")
    private String gckssj;

    /**
     * 观测结束时间
     */
    @TableField("GCJSSJ")
    private String gcjssj;

    /**
     * 中心点访问时间
     */
    @TableField("ZXDFWSJ")
    private String zxdfwsj;

    /**
     * 侧视角
     */
    @TableField("CSJ")
    private String csj;

    /**
     * 前俯仰角
     */
    @TableField("QFYJ")
    private String qfyj;

    /**
     * 后俯仰角
     */
    @TableField("HFYJ")
    private String hfyj;

    /**
     * 太阳高度角
     */
    @TableField("TYGDJ")
    private String tygdj;

    /**
     * 可选波位
     */
    @TableField("KXBW")
    private String kxbw;

    /**
     * 云量值
     */
    @TableField("YLZ")
    private String ylz;

    /**
     * 安排情况
     */
    @TableField("APQK")
    private String apqk;

    /**
     * 影响任务域
     */
    @TableField("YXRWY")
    private String yxrwy;

    /**
     * 原始元任务编号
     */
    @TableField("YSYRWBH")
    private String ysyrwbh;

    /**
     * 新元任务编号
     */
    @TableField("XYRWBH")
    private String xyrwbh;

    /**
     * 入库时间
     */
    @TableField("RKSJ")
    private String rksj;


    /**
     * 分辨率
     */
    private String fbl;

    private String totalCount;


    public TargetVisitResponse() {
    }

    @Override
    public String toString() {
        return "TargetVisitResponse{" +
                "yrwbh='" + yrwbh + '\'' +
                "mbbh='" + mbbh + '\'' +
                "mbmc='" + mbmc + '\'' +
                ", wxbs='" + wxbs + '\'' +
                ", cgqbs='" + cgqbs + '\'' +
                ", cgqms='" + cgqms + '\'' +
                ", yxcx='" + yxcx + '\'' +
                ", zxjd='" + zxjd + '\'' +
                ", zxwd='" + zxwd + '\'' +
                ", zsjjd='" + zsjjd + '\'' +
                ", zsjwd='" + zsjwd + '\'' +
                ", ysjjd='" + ysjjd + '\'' +
                ", ysjwd='" + ysjwd + '\'' +
                ", zxjjd='" + zxjjd + '\'' +
                ", zxjwd='" + zxjwd + '\'' +
                ", yxjjd='" + yxjjd + '\'' +
                ", yxjwd='" + yxjwd + '\'' +
                ", gdqh='" + gdqh + '\'' +
                ", gckssj='" + gckssj + '\'' +
                ", gcjssj='" + gcjssj + '\'' +
                ", zxdfwsj='" + zxdfwsj + '\'' +
                ", csj='" + csj + '\'' +
                ", qfyj='" + qfyj + '\'' +
                ", hfyj='" + hfyj + '\'' +
                ", tygdj='" + tygdj + '\'' +
                ", kxbw='" + kxbw + '\'' +
                ", ylz='" + ylz + '\'' +
                ", apqk='" + apqk + '\'' +
                ", yxrwy='" + yxrwy + '\'' +
                ", ysyrwbh='" + ysyrwbh + '\'' +
                ", xyrwbh='" + xyrwbh + '\'' +
                ", rksj='" + rksj + '\'' +
                ", fbl='" + fbl + '\'' +
                ", totalCount='" + totalCount + '\'' +
                '}';
    }

    public Object deepClone() throws IOException, ClassNotFoundException {
        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (oi.readObject());

    }
    public TargetVisitResponse(String wxbs, String gdqh, String zxdfwsj, String csj, String tygdj, String gckssj,
                               String gcjssj, String qfyj, String hfyj) {
        this.wxbs = wxbs;
        this.gdqh = gdqh;
        this.zxdfwsj = zxdfwsj;
        this.csj = csj;
        this.tygdj = tygdj;
        this.gckssj = gckssj;
        this.gcjssj = gcjssj;
        this.qfyj = qfyj;
        this.hfyj = hfyj;
    }

}
