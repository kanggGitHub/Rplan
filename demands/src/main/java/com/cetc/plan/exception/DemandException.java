package com.cetc.plan.exception;


/**
 * @Classname DemandException
 * @Description: TODO 自定义异常信息类
 * @author: kg
 * @Date:  2019/06/20 15:43
 */
public class DemandException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    public DemandException(){
        super();
    }

    public DemandException(String message){
        super(message);
    }

    public DemandException(Integer code){
        super();
        this.code = code;
    }

    public DemandException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
