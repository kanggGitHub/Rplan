package com.cetc.plan.demand.model;

import java.io.Serializable;

/**
 * 响应结果实体类
 *
 * @param <T> 结束data类型
 */
public class ResultEntry<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private boolean status;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据结果集
     */
    private T data;

    public ResultEntry() {
    }

    public ResultEntry(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResultEntry(boolean status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntry{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
