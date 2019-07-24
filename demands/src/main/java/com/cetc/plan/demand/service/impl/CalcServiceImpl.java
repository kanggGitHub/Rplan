package com.cetc.plan.demand.service.impl;


import com.cetc.plan.config.StaticConst;
import com.cetc.plan.demand.model.*;
import com.cetc.plan.demand.model.demand.TargetInfoEntity;
import com.cetc.plan.demand.service.CalcService;
import com.cetc.plan.thread.UdpReceiveThread;
import com.cetc.plan.utils.BaseFileUtil;
import com.cetc.plan.utils.DemandUtils;
import com.cetc.plan.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.cetc.plan.utils.BaseFileUtil.writeFile;

/**
 * 计算服务
 *
 * @author wyb
 */
@Service
public class CalcServiceImpl implements CalcService {

    /**
     * 结果字符常量
     */
    private static final String RESULT = "结果";

    @Autowired
    ConfigEntity config;

    @Autowired
    UdpEntity udp;

    @Resource
    DemandUtils demandUtils;

    @Resource
    StaticConst staticConst;

    /**
     * 是否使用并行调度
     */
    @Value("${myCustom.bxdd.useBxdd}")
    private boolean useBxdd;

    /**
     * 并行调度URL
     */
    @Value("${myCustom.bxdd.url}")
    private String baseUrl;

    @Override
    public ResultEntry<String> createPointCalcRequestFile(String wxdh, String kssj, String jssj, List<PointCalcRequest> pointCalcRequest) {
        //请求头类
        AskFileEntity askFileEntity = new AskFileEntity();
        askFileEntity.setWxdh(wxdh);
        askFileEntity.setKssj(kssj);
        askFileEntity.setJssj(jssj);
        //添加指向计算请求集合
        askFileEntity.setPointList(pointCalcRequest);
        //根据请求类，写入请求文件
        return writeFile(askFileEntity, config.getBasePath(), 2);

    }

    @Override
    public ResultEntry<String> createVisitCalcRequestFile(String wxdh, String kssj, String jssj, List<double[]> visitCalcRequst) {
        //请求头类
        AskFileEntity askFileEntity = new AskFileEntity();
        askFileEntity.setWxdh(wxdh);
        askFileEntity.setKssj(kssj);
        askFileEntity.setJssj(jssj);
        //遍历点集，添加目标访问实体
        for (int j = 0; visitCalcRequst != null && j < visitCalcRequst.size(); j++) {
            askFileEntity.addTarget(new TargetVisitRequest(
                    "MB-" + (j + 1), "目标" + (j + 1), visitCalcRequst.get(j)[0], visitCalcRequst.get(j)[1]));
        }
        //根据请求类，写入请求文件
        return writeFile(askFileEntity, config.getBasePath(), 1);

    }

    @Override
    public ResultEntry<List<PointCalcResponse>> invokePointCalcService(String wxdh, String requestFileName) {
        String prefixStr = "进行指向计算错误，";

        // 申请文件全路径
        String fileFullPath = config.getBasePath() + requestFileName;
        // 执行请求
        ResultEntry<String> taskResult = interfaceCall(fileFullPath);
        // 结果为异常状态
        if (!taskResult.getStatus()) {
            return new ResultEntry<>(false, prefixStr + taskResult.getMsg());
        }
        // 结果为空
        if (StringUtils.isEmpty(taskResult.getData())) {
            return new ResultEntry<>(false, prefixStr + "结果数据为空！");
        }

        String resultData = taskResult.getData();
        String errorMsg = resultData.contains("任务参数：") ? resultData.substring(0, resultData.indexOf("任务参数：")) : "";

        if (resultData.contains(RESULT)) {
            //结果文件全路径
            String resultFileFullPath = resultData.split("@")[0];
            //读取文件
            ResultEntry<List<String>> listResultEntry = BaseFileUtil.readFile(resultFileFullPath);
            //若处理成功则添加数据
            if (listResultEntry.getStatus()) {
                // 所有执行的点
                List<PointCalcResponse> pointList = new ArrayList<>();
                for (int i = 0; i < listResultEntry.getData().size(); i++) {
                    String[] arr = listResultEntry.getData().get(i).split("\\s+");
                    //防止数组越界
                    if (arr.length >= 3) {
                        PointCalcResponse pointResultEntity = new PointCalcResponse();
                        pointResultEntity.setJd(Double.parseDouble(arr[0]));
                        pointResultEntity.setWd(Double.parseDouble(arr[1]));
                        String[] timeAndFwqh = arr[2].split("#");
                        pointResultEntity.setTime(timeAndFwqh[0]);
                        pointResultEntity.setFwqh(timeAndFwqh.length >= 2 ? timeAndFwqh[1] : "");
                        pointList.add(pointResultEntity);

                    }
                }
                return new ResultEntry<>(true, null, pointList);
            }
            return new ResultEntry<>(false, prefixStr + listResultEntry.getMsg());

        }

        return new ResultEntry<>(false, prefixStr + errorMsg);
    }

    @SuppressWarnings("all")
    @Override
    public ResultEntry<List<TargetVisitResponse>> invokeVisitCalcService(String wxbs, List<TargetInfoEntity> valmap, String requestFileName) {
        String prefixStr = "进行访问计算错误，";

        //根据卫星标识获取卫星类型
        String wxType = demandUtils.getSatelliteBybs(wxbs);

        // 申请文件全路径
        String fileFullPath = config.getBasePath() + requestFileName;
        // 执行请求
        ResultEntry<String> taskResult = interfaceCall(fileFullPath);
        // 结果为异常状态
        if (!taskResult.getStatus()) {
            return new ResultEntry<>(false, prefixStr + taskResult.getMsg());
        }
        // 结果为空
        if (StringUtils.isEmpty(taskResult.getData())) {
            return new ResultEntry<>(false, prefixStr + "结果数据为空！");
        }

        String resultData = taskResult.getData();
        String errorMsg = resultData.contains("任务参数：") ? resultData.substring(0, resultData.indexOf("任务参数：")) : "";

        if (resultData.contains(RESULT)) {
            //结果文件全路径
            String resultFileFullPath = resultData.split("@")[0];
            //读取文件
            ResultEntry<List<String>> listResultEntry = BaseFileUtil.readFile(resultFileFullPath);
            //若处理成功则添加数据
            if (listResultEntry.getStatus()) {
                // 所有执行的点
                List<TargetVisitResponse> visitResponseList = new ArrayList<>();
                for (int i = 0; i < listResultEntry.getData().size(); i++) {
                    String[] arr = listResultEntry.getData().get(i).split("\\s+");
                    if (arr.length >= 15 && !StringUtils.isBlank(arr[4])) {
                        //根据卫星类型分别处理
                        //光学和SAR，单一时间点
                        staticConst.ZCYRW_YRWBH_ID+=1;
                        if ("光学".equals(wxType) || "SAR".equals(wxType)) {
                            TargetVisitResponse targetVisitResponse = new TargetVisitResponse();
                            targetVisitResponse.setYrwbh(staticConst.ZCYRW_YRWBH_ID);
                            targetVisitResponse.setMbbh(demandUtils.mergeTargetVS(valmap,arr[2],arr[3],arr[4]));
                            targetVisitResponse.setWxbs(arr[4]);
                            targetVisitResponse.setGdqh(arr[5]);
                            targetVisitResponse.setZxdfwsj(String.format("%s-%s-%s %s:%s:%s", arr[6], arr[7], arr[8], arr[9], arr[10], arr[11]));
                            targetVisitResponse.setCsj(arr[12]);
                            targetVisitResponse.setTygdj(arr[13]);
                            visitResponseList.add(targetVisitResponse);

                            //电子
                        } else if ("电子".equals(wxType)) {
                            //防止数组越界
                            if (arr.length >= 20) {
                                TargetVisitResponse targetVisitResponse = new TargetVisitResponse();
                                targetVisitResponse.setYrwbh(staticConst.ZCYRW_YRWBH_ID);
                                targetVisitResponse.setWxbs(arr[4]);
                                targetVisitResponse.setGdqh(arr[5]);
                                targetVisitResponse.setGckssj(String.format("%s-%s-%s %s:%s:%s", arr[6], arr[7], arr[8], arr[9], arr[10], arr[11]));
                                targetVisitResponse.setGcjssj(String.format("%s-%s-%s %s:%s:%s", arr[12], arr[13], arr[14], arr[15], arr[16], arr[17]));
                                targetVisitResponse.setQfyj(arr[18]);
                                targetVisitResponse.setHfyj(arr[19]);
                                visitResponseList.add(targetVisitResponse);
                            }
                        }

                    }
                }
                return new ResultEntry<>(true, null, visitResponseList);
            }
            return new ResultEntry<>(false, prefixStr + listResultEntry.getMsg());
        }
        return new ResultEntry<>(false, prefixStr + errorMsg);
    }

    /**
     * 接口调用处理
     *
     * @param fileFullPath 文件全路径
     * @return 结果对象
     */
    private ResultEntry<String> interfaceCall(String fileFullPath) {

        ResultEntry<String> resultEntry;
        //使用并行调度
        if (useBxdd) {
            if (StringUtils.isNotBlank(udp.getReceiveUrl())) {
                String result = HttpClientUtil.sendGet(baseUrl + "&requestFilePath=" + fileFullPath);
                JSONObject json = JSONObject.fromObject(result);
                resultEntry = new ResultEntry<>(json.getBoolean("status"), json.getString("msg"), json.get("data").toString());

            } else {
                resultEntry = new ResultEntry<>(false, "无法获取服务端IP地址，请检查网络！");
            }

            // 使用单机目标访问计算
        } else {
            //获取接收线程
            UdpReceiveThread t1 = UdpReceiveThread.getInstance(udp);
            long tid = System.nanoTime();
            //文件全路径
            fileFullPath = fileFullPath.replaceAll("/", "\\\\");
            if (StringUtils.isNotBlank(udp.getReceiveUrl())) {
                String requestStr = String.format("%s@%s@%s@%s", fileFullPath, udp.getReceiveUrl(), udp.getReceivePort(), tid);
                //发送udp消息
                resultEntry = udp.send(requestStr);
                if (resultEntry.getStatus()) {
                    resultEntry = t1.getResultByKey(tid + "", config.getUdpTimeout());
                }
            } else {
                resultEntry = new ResultEntry<>(false, "无法获取服务端IP地址，请检查网络！");
            }
        }


        return resultEntry;
    }

}
