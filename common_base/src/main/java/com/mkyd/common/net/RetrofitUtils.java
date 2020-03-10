package com.mkyd.common.net;

import androidx.annotation.NonNull;

import com.mkyd.common.net.interceptor.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:
 * Data：2020/3/10-15:32
 * Author: ly
 */
public class RetrofitUtils {

    private static  final String TAG = "RetrofitUtils";
    private static  ApiService sService ;

    private RetrofitUtils() {}

    //获取service
    public static ApiService getService(){
        if (sService ==null){
            synchronized (RetrofitUtils.class){
                if (sService==null){
                    sService = new RetrofitUtils().getRetrofit();
                }
            }
        }
        return sService ;
    }


    public ApiService getRetrofit(){
        ApiService apiService = initRetrofit(initOkHttp()).create(ApiService.class);
        return apiService ;
    }


    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        return new OkHttpClient().newBuilder()
                .readTimeout(ApiService.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(ApiService.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(ApiService.DEFAULT_TIMEOUT,TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(LogInterceptor.getLogInterceptor())//添加打印拦截器
                .build();
    }
}
