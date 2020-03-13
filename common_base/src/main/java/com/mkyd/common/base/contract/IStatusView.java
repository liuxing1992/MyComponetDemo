package com.mkyd.common.base.contract;

import com.kingja.loadsir.callback.Callback;
import com.mkyd.common.base.status.CommonEmptyStatus;
import com.mkyd.common.base.status.CommonErrorStatus;
import com.mkyd.common.base.status.CommonLoadingStatus;
import com.mkyd.common.base.status.CommonNetErrorStatus;

/**
 * Description:
 * Data：2020/3/12-19:52
 * Author: ly 默认的状态切换 如果要自定义 请重写某个方法
 */
public interface IStatusView {

    default Callback getLoadingStatus() {
        return new CommonLoadingStatus();
    }

    default Callback getEmptyStatus() {
        return new CommonEmptyStatus();
    }

    default Callback getErrorStatus() {
        return new CommonErrorStatus();
    }

    default Callback getNetErrorStatus() {
        return new CommonNetErrorStatus();
    }

}
