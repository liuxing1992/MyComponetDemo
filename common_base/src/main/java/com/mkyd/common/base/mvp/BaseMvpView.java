package com.mkyd.common.base.mvp;

/**
 * Description:
 * Data：2020/3/10-19:59
 * Author: ly
 */
public interface BaseMvpView {

    void showLoadingLayout();
    void showContentLayout();
    void showNetErrorLayout();
    void showEmptyLayout(String emptyTip);
    void onReloadClick();

}
