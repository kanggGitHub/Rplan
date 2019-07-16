package com.cetc.plan.config;

/**
 * @Description 状态字典
 * @Author kg
 * @Param
 * @return
 * @Date 16:25 2019/6/20
 */
public enum ResultCode {

    SUCCESS(0),
    FAILURE(-1),

    PARAMETER(-201),//参数错误

    DATABASES_OPERATION_FAIL(-1001)   //数据库操作失败
    ;

    private Integer value;

    ResultCode(Integer value) {

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
