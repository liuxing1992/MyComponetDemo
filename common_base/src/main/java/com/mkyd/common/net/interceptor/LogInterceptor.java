package com.mkyd.common.net.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Description:
 * Data：2020/3/10-15:55
 * Author: ly
 */
public class LogInterceptor {

    public static Interceptor getLogInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    //log 打印
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return interceptor;
    }
}
