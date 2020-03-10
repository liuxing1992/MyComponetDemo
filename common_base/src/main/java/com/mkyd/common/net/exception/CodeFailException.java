package com.mkyd.common.net.exception;

import android.content.Context;

import com.mkyd.common.net.bean.BaseResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Description: 请求code不成功处理
 * Data：2020/3/10-18:27
 * Author: ly
 */
public class CodeFailException {

    public static void handleCodeException(Context context , BaseResponse response){
        if (response!=null){
            int code = response.getCode();
            try {
                String message = response.getMsg();
                String decodeMsg = URLDecoder.decode(message, "UTF-8");//urldecode
                //过滤一些不要弹出toast的code
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
