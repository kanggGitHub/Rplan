package com.cetc.plan.demand.service;



import com.cetc.plan.demand.model.PointCalcRequest;
import com.cetc.plan.demand.model.PointCalcResponse;
import com.cetc.plan.demand.model.ResultEntry;
import com.cetc.plan.demand.model.TargetVisitResponse;
import com.cetc.plan.demand.model.demand.TargetInfoEntity;

import java.util.List;

/**
 * 指向计算
 *
 * @author wyb
 */
public interface CalcService {

    /**
     * 创建指向请求文件
     *
     * @param wxdh             卫星代号
     * @param kssj             开始时间
     * @param jssj             结束时间
     * @param pointCalcRequest 指向计算请求
     * @return
     */
    ResultEntry<String> createPointCalcRequestFile(String wxdh, String kssj, String jssj, List<PointCalcRequest> pointCalcRequest);

    /**
     * 创建访问请求文件
     *
     * @param wxdh            卫星代号
     * @param kssj            开始时间
     * @param jssj            结束时间
     * @param visitCalcRequst 访问计算请求
     * @return
     */
    ResultEntry<String> createVisitCalcRequestFile(String wxdh, String kssj, String jssj, List<double[]> visitCalcRequst);

    /**
     * 执行指向计算服务
     *
     * @param wxdh            卫星代号
     * @param requestFileName 请求文件
     * @return 结果对象
     */
    ResultEntry<List<PointCalcResponse>> invokePointCalcService(String wxdh, String requestFileName);

    /**
     * 执行访问计算服务
     *
     * @param wxdh            卫星代号
     * @param valmap
     * @param requestFileName 请求文件
     * @return 结果对象
     */
    ResultEntry<List<TargetVisitResponse>> invokeVisitCalcService(String wxdh, List<TargetInfoEntity> valmap, String requestFileName,boolean isPoint);

}
