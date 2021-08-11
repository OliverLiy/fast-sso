package com.fast.sso.client.model;

/**
 * @author by:ly
 * @ClassName: ResultCode
 * @Description: TODO
 * @Date: 2021/7/14 15:59
 **/
public enum ResultCode {

    /**
     * 全局的状态码及状态信息
     *
     */
    SUCCESS(200,"success"),
    FAILURE(500,"服务器内部错误"),

    //通用模块
    ILLEGAL_ARGUMENT(10000, "参数不合法"),
    REPETITIVE_OPERATION(10001, "请勿重复操作"),
    ACCESS_LIMIT(10002, "请求太频繁, 请稍后再试"),
    ERROR_ARGUMENT(10003,"参数值错误"),
    DATA_EXISTS(10004,"数据已存在"),
    DATA_NOT_EXISTS(10005,"数据不存在"),
    UPDATE_ERROR(10006,"更新异常"),
    ADD_ERROR(10007,"新增异常"),
    ;



    private int code;
    private String msg;

    ResultCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

