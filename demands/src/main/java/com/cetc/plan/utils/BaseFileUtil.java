package com.cetc.plan.utils;


import com.cetc.plan.demand.model.AskFileEntity;
import com.cetc.plan.demand.model.PointCalcRequest;
import com.cetc.plan.demand.model.ResultEntry;
import com.cetc.plan.demand.model.TargetVisitRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * smd访问共享工具类
 *
 * @author wyb
 */
public class BaseFileUtil {

    private static Logger logger = LoggerFactory.getLogger(BaseFileUtil.class);
    /**
     * 常量： 敏捷访问计算
     */
    private static final String FWJS_BASE_NAME = "敏捷访问计算";
    /**
     * 常量： 图像中心计算
     */
    private static final String ZXJS_BASE_NAME = "图像中心计算";

    /**
     * 创建新的文件名
     *
     * @param wxdh           卫星代号
     * @param fileBasePath   文件基础路径
     * @param fileNamePrefix 文件名前缀
     * @param fileNameSuffix 文件名后缀
     * @return
     */
    public static File createFileName(String wxdh, String fileBasePath, String fileNamePrefix, String fileNameSuffix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String random = String.format("%04d", new Random().nextInt(10000));
        String fileName = String.format("%s_%s_%s.%s", fileNamePrefix, wxdh, sdf.format(new Date()) + random, fileNameSuffix);

        File file = null;
        try {
            file = new File(fileBasePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(fileBasePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            logger.error("IOException", e);
        }
        return file;
    }

    /**
     * 读取共享文件流
     *
     * @param filePath 文件路径
     * @return 文件流
     * @throws FileNotFoundException
     */
    public static FileInputStream readFileStream(String filePath) throws FileNotFoundException {

        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        return fileInputStream;
    }

    /**
     * 读取共享文件
     *
     * @param filePath 文件路径
     * @return ResultEntry<List                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                               String>> 返回结果对象
     */
    public static ResultEntry<List<String>> readFile(String filePath) {
        List<String> list = new ArrayList<>();

        //文件输入流
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {

            fileInputStream = readFileStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                list.add(str);
            }

        } catch (IOException e) {
            logger.error("IOException", e);
            return new ResultEntry<>(false, "读取共享文件- IOException");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                logger.error("IOException", e);
            }

        }
        return new ResultEntry<>(true, "", list);
    }

    /**
     * 写入文件
     *
     * @param askFileEntity 请求实体
     * @param fileBasePath  文件基础路径
     * @param fileType      访问计算1、指向计算2
     * @return ResultEntry<String> 结果类，data为文件名
     */
    public static ResultEntry<String> writeFile(AskFileEntity askFileEntity, String fileBasePath, int fileType) {
        ResultEntry<String> result = new ResultEntry<>(true, "");

        String fileNamePrefix;
        String fileNameSufix = "txt";
        // 指向计算请求头
        List<PointCalcRequest> pointList = askFileEntity.getPointList();
        // 访问计算请求头
        List<TargetVisitRequest> targetList = askFileEntity.getTargetList();
        // 访问计算
        if (fileType == 1) {
            fileNamePrefix = FWJS_BASE_NAME;
            // 指向计算
        } else if (fileType == 2) {
            fileNamePrefix = ZXJS_BASE_NAME;
        } else {
            return new ResultEntry<>(false, "错误的计算类型");
        }

        FileOutputStream outputStream = null;
        BufferedWriter bw = null;
        try {
            File file = createFileName(askFileEntity.getWxdh(), fileBasePath, fileNamePrefix, fileNameSufix);
            outputStream = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bw.write(String.format("%s %s %s%n", askFileEntity.getWxdh(),
                    TimeUtil.formatToStr(askFileEntity.getKssj(), null, "yyyy MM dd HH mm ss"),
                    TimeUtil.formatToStr(askFileEntity.getJssj(), null, "yyyy MM dd HH mm ss")));

            //访问计算头文件
            if (!targetList.isEmpty()) {
                for (TargetVisitRequest entity : targetList) {
                    bw.write(String.format("%s %s %.4f %.4f%n", entity.getMbbh(), entity.getMbmc(), entity.getMbjd(), entity.getMbwd()));
                }

                //指向计算头文件
            } else if (!pointList.isEmpty()) {
                for (PointCalcRequest entity : pointList) {
                    String time1 = TimeUtil.formatToStr(entity.getZxdfwsj(), null, "yyyy MM dd HH mm ss");
                    String time2 = TimeUtil.formatToStr(entity.getZxdfwsj(), null, "yyyyMMddHHmmss");
                    bw.write(String.format("%s %.4f %.4f %s%n", time1, entity.getCbj(), entity.getFyj(), time2 + "#" + entity.getFwqh()));
                }
            }
            result.setData(file.getName());

        } catch (IOException e) {
            logger.error("IOException", e);
            result = new ResultEntry<>(false, "写入申请文件异常");

        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                logger.error("IOException", e);
                result = new ResultEntry<>(false, e.getMessage());
            }
        }

        return result;
    }

}
