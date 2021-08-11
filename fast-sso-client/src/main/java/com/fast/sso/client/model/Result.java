package com.fast.sso.client.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by:ly
 * @ClassName: Result
 * @Description: TODO
 * @Date: 2021/7/14 15:58
 **/
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;
    private String msg;
    private Object data;
    private Pagination pagination;


    public static Result success(){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setData(data);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }
    public static Result failure(int errorCode,String msg){
        Result result = new Result();
        result.setCode(errorCode);
        result.setMsg(msg);
        return result;
    }
    public static Result failure(ResultCode info){
        Result result = new Result();
        result.setMsg(info.getMsg());
        result.setCode(info.getCode());
        return result;
    }
}
