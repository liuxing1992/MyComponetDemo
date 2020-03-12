package com.mkyd.common.net;

import android.content.Context;

import com.mkyd.common.net.bean.BaseResponse;
import com.mkyd.common.net.exception.CodeFailException;
import com.mkyd.common.net.exception.RxExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Data：2020/3/10-16:19
 * Author: ly
 */
public abstract class MyObserver<T extends BaseResponse> implements Observer<T> {

    //todo 设置全局loading dialog
    private Context mContext;

    private boolean showLoadingDialog;


    public MyObserver(Context context) {
        this(context, false);
    }

    public MyObserver(Context context, boolean showLoadingDialog) {
        mContext = context;
        this.showLoadingDialog = showLoadingDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        //onstart 开始工作 可以判断些网络连接情况
        onStart(d);
    }

    @Override
    public void onNext(T response) {
        if (response.getCode() == 0) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }

    @Override
    public void onError(Throwable e) {
        RxExceptionUtil.handleException(e);
    }

    @Override
    public void onComplete() {

    }


    public abstract void onSuccess(T response);

    public void onStart(Disposable d) {

    }

    public void onFail(T response) {
        //code 值不是成功的处理 比如未登录 参数错误等
        if (response != null) {
            CodeFailException.handleCodeException(mContext, response);
        }
    }
}
