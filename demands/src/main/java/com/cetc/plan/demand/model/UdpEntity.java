package com.cetc.plan.demand.model;

import com.cetc.plan.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

/**
 * UDP实体
 *
 * @author wyb
 */
@Component
public class UdpEntity {

    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(UdpEntity.class);

    /**
     * 发送地址
     */
    @Value("${myCustom.udp.sendAddr}")
    private String sendAddr;

    /**
     * 发送端口
     */
    @Value("${myCustom.udp.sendPort}")
    private int sendPort;

    /**
     * 接收地址
     */
    private String receiveUrl = IpUtil.getCurrentIpStr();

    /**
     * 接收端口
     */
    @Value("${myCustom.udp.receivePort}")
    private int receivePort;

    /**
     * 数据内存存储时长
     */
    @Value("${myCustom.udp.tempTimeOut}")
    private int tempTimeOut;

    public String getReceiveUrl() {
        return receiveUrl;
    }

    public void setReceiveUrl(String receiveUrl) {
        this.receiveUrl = receiveUrl;
    }

    public int getReceivePort() {
        return receivePort;
    }

    public void setReceivePort(int receivePort) {
        this.receivePort = receivePort;
    }

    /**
     * 发送信息
     *
     * @param str
     */
    public synchronized ResultEntry<String> send(String str) {
        ResultEntry<String> result = new ResultEntry<>(true, null);

        // 建立udp的服务
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            // 把数据封装 到数据数据包中，然后发送数据。
            DatagramPacket packet = new DatagramPacket(str.getBytes("gbk"), str.getBytes("gbk").length,
                    InetAddress.getByName(sendAddr), sendPort);
            // 把数据发送出去
            socket.send(packet);

            logger.info("udp发送消息：" + str);

        } catch (IOException e) {
            logger.error("error", e);
            result = new ResultEntry<>(false, "发送UDP消息IOException");

        } finally {
            if (socket != null) {
                // 关闭 资源
                socket.close();
            }
        }

        return result;
    }

    /**
     * 接收信息
     *
     * @param list
     */
    public void receive(Vector<String> list) {
        // 建立udp的服务,要监听一个端口
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(receivePort);

            while (true) {
                // 准备空的数据包存储数据
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String result = new String(buf, 0, packet.getLength(), "gbk");
                logger.info("udp结果：" + result);
                list.add(result);

//                long startT = Long.parseLong(list.get(0).split("@")[1]);
//                long curr = System.currentTimeMillis();
//                if (curr - startT > tempTimeOut * 60000L) {
//                    for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
//                        long temp = Long.parseLong((iterator.next()).split("@")[1]);
//                        if (curr - temp > tempTimeOut * 60000L) {
//                            iterator.remove();
//                        }
//
//                    }
//                }

            }

        } catch (IOException e) {
            logger.error("error", e);

        } finally {
            if (socket != null) {
                // 关闭 资源
                socket.close();

            }
        }

    }

}
