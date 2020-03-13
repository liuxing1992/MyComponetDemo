package com.mkyd.common.base.status;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.kingja.loadsir.callback.Callback;
import com.mkyd.common_base.R;
/**
 * Description:
 * Data：2020/3/12-19:50
 * Author: ly
 */
public class CommonNetErrorStatus extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.view_net_error;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
       Toast.makeText(context , "网络异常处理" ,Toast.LENGTH_SHORT).show();
        return true;
    }
}
