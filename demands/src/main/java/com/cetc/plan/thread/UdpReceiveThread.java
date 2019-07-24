package com.cetc.plan.thread;

import com.cetc.plan.demand.model.ResultEntry;
import com.cetc.plan.demand.model.UdpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Vector;

/**
 * Udp接收线程
 */
public class UdpReceiveThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(UdpReceiveThread.class);

    private static UdpReceiveThread instance;

    /**
     * 执行次数标记
     */
    private Integer countNum = 1;

    /**
     * 存储接收信息
     */
    private Vector<String> list = new Vector<>();

    /**
     * Udp对象
     */
    private UdpEntity udp;

    private UdpReceiveThread() {
        super("udp-receive");
    }

    public static synchronized UdpReceiveThread getInstance(UdpEntity udp) {
        if (instance == null) {
            instance = new UdpReceiveThread();
            instance.udp = udp;
        }
        return instance;
    }

    /**
     * 根据key获取udp返回结果，没有则为null
     *
     * @param key     获取的请求key
     * @param timeout 超时时间，单位秒
     * @return
     */
    public ResultEntry<String> getResultByKey(String key, int timeout) {
        // 删除超时缓存数据
        synchronized (countNum) {
            if (countNum++ > 100000) {
                System.out.println("删除超时缓存数据");
                long curr = System.currentTimeMillis();
                for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
                    long temp = Long.parseLong((iterator.next()).split("@")[1]);
                    if (curr - temp > 30 * 60000L) {
                        iterator.remove();
                    }
                }
                countNum = 1;
            }
        }

        int sleepTime = 50;
        int count = timeout * (1000 / sleepTime);
        while (count > 0) {
            for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
                String str = iterator.next();
                if (str.contains(key)) {
                    iterator.remove();
                    return new ResultEntry<>(true, null, str);
                }
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }
        return new ResultEntry<>(false, "udp获取返回数据超时");
    }

    @Override
    public void run() {
        while (true) {
            udp.receive(list);
            try {
                //重启时间S
                int time = 5;
                Thread.sleep(time * 1000);
                logger.error("UDP接收端口异常，" + time + "秒后重试UDP接收");
            } catch (InterruptedException e) {
                logger.error("InterruptedException", e);
            }
        }
    }
}
