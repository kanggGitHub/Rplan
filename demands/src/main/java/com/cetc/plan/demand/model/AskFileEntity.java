package com.cetc.plan.demand.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求文件实体
 *
 * @author wyb
 */
public class AskFileEntity {

    /**
     * 卫星代号
     */
    private String wxdh;

    /**
     * 类型
     */
    private String type;

    /**
     * 开始时间
     */
    private String kssj;

    /**
     * 结束时间
     */
    private String jssj;

    /**
     * 访问计算实体列表
     */
    private List<TargetVisitRequest> targetList = new ArrayList<>();

    /**
     * 指向请求实体列表
     */
    private List<PointCalcRequest> pointList = new ArrayList<>();

    public AskFileEntity() {
    }

    public AskFileEntity(String wxdh, String type, String kssj, String jssj, List<PointCalcRequest> pointList, List<TargetVisitRequest> targetList) {
        this.wxdh = wxdh;
        this.type = type;
        this.kssj = kssj;
        this.jssj = jssj;
        this.pointList = pointList;
        this.targetList = targetList;
    }

    public String getWxdh() {
        return wxdh;
    }

    public void setWxdh(String wxdh) {
        this.wxdh = wxdh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public List<PointCalcRequest> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointCalcRequest> pointList) {
        this.pointList = pointList;
    }

    public List<TargetVisitRequest> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<TargetVisitRequest> targetList) {
        this.targetList = targetList;
    }

    public void addPoint(PointCalcRequest point) {
        this.pointList.add(point);
    }

    public void addTarget(TargetVisitRequest target) {
        this.targetList.add(target);
    }

    @Override
    public String toString() {
        return "AskFileEntity{" +
                "wxdh='" + wxdh + '\'' +
                ", type='" + type + '\'' +
                ", kssj='" + kssj + '\'' +
                ", jssj='" + jssj + '\'' +
                ", pointList=" + pointList +
                ", targetList=" + targetList +
                '}';
    }
}
