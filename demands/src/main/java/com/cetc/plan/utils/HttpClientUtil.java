package com.cetc.plan.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description //TODO
 * @Author HttpClient客户端调用工具类
 * @Param
 * @Date 15:07 2019/7/18
 */
public class HttpClientUtil {

    /**
     * 默认字符集
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static void main(String[] args) {
        /*
        Map<String, String> params = new HashMap<>();
        params.put("jwdlist", "[[[123,12],[123,14]],[[123,15],[126,24]],[[111,44],[112,55]]]");
        params.put("allcord", "");

        String post = post("http://192.168.200.185:8088/fgl/getFGL", params);
        System.out.println(post);
        */

        ExecutorService service = Executors.newFixedThreadPool(300);
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    sendGet("http://localhost:8081/insertTask?requestFilePath=//yeluo/访问计算临时文件/敏捷访问计算_GF1C_201903121702361168911.txt&taskMethod=MBFWJS");
                }
            });
        }
        service.shutdown();

    }

    /**
     * sendPost
     *
     * @param url    请求地址
     * @param params 请求实体
     * @return String
     * @author wyb
     */
    public static String post(String url, Map<String, String> params) {
        // 请求返回结果
        String result = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost post = new HttpPost(url);
        // 设置参数
        List<NameValuePair> kvList = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            kvList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            StringEntity entity = new UrlEncodedFormEntity(kvList, "UTF-8");
            post.setEntity(entity);
            CloseableHttpResponse response = client.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }

        return result;
    }

    /**
     * sendPost
     *
     * @param url      请求地址
     * @param headers  请求头
     * @param data     请求实体
     * @param encoding 字符集
     * @return String
     * @author wyb
     */
    public static String sendPost(String url, Map<String, String> headers, JSONObject data, String encoding) {
        logger.info("进入post请求方法...");
        logger.info("请求入参：URL= " + url);
        logger.info("请求入参：headers=" + JSON.toJSONString(headers));
        logger.info("请求入参：data=" + JSON.toJSONString(data));

        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();

        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpPost.setHeaders(allHeader);
            }
            // 设置实体
            httpPost.setEntity(new StringEntity(JSON.toJSONString(data)));
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            } else {
                logger.error("响应失败，状态码：" + status);
            }

        } catch (Exception e) {
            logger.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        return resultJson;
    }

    /**
     * sendPost
     *
     * @param url  请求地址
     * @param data 请求实体
     * @return String
     * @author wyb
     */
    public static String sendPost(String url, JSONObject data) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");

        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * sendPost
     *
     * @param url    请求地址
     * @param params 请求实体
     * @return String
     * @author wyb
     */
    public static String sendPost(String url, Map<String, Object> params) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * sendPost
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param data    请求实体
     * @return String
     * @author wyb
     */
    public static String sendPost(String url, Map<String, String> headers, JSONObject data) {
        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * sendPost
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  请求实体
     * @return String
     * @author wyb
     */
    public static String sendPost(String url, Map<String, String> headers, Map<String, String> params) {
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * sendGet
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param encoding 编码
     * @return String
     * @author wyb
     */
    public static String sendGet(String url, Map<String, Object> params, String encoding) {
        logger.info("进入get请求方法...");
        // 请求结果
        String resultJson = null;
        // 创建client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            // 封装参数
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key).toString());
                }
            }
            URI uri = builder.build();
            logger.info("请求地址：" + uri);
            // 设置请求地址
            httpGet.setURI(uri);
            // 发送请求，返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应数据
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            } else {
                logger.error("响应失败，状态码：" + status);
            }
        } catch (Exception e) {
            logger.error("发送get请求失败", e);
        } finally {
            httpGet.releaseConnection();
        }
        return resultJson;
    }

    /**
     * sendGet
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return String
     * @author wyb
     */
    public static String sendGet(String url, Map<String, Object> params) {
        return sendGet(url, params, ENCODING);
    }

    /**
     * sendGet
     *
     * @param url 请求地址
     * @return String
     * @author wyb
     */
    public static String sendGet(String url) {
        return sendGet(url, null, ENCODING);
    }

}
