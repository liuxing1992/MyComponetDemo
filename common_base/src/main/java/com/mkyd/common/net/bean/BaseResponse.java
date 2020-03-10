package com.mkyd.common.net.bean;

/**
 * Description:
 * Data：2020/3/10-16:10
 * Author: ly
 */
public class BaseResponse {
    private int code ;
    private String msg ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
