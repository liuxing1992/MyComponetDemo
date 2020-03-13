package com.mkyd.common.base.status;
import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.mkyd.common_base.R;

/**
 * Description:
 * Data：2020/3/12-19:41
 * Author: ly
 * onReloadEvent 返回true 则自己处理 不会响应onReload方法
 */
public class CommonLoadingStatus extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.view_loading;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
