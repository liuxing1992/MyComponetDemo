package com.mkyd.common.base.contract;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.gyf.immersionbar.ImmersionBar;
import com.mkyd.common.base.mvp.BaseMvpView;
import com.mkyd.common.widget.TitleBarLayout;

/**
 * Description:
 * Data：2020/3/11-11:26
 * Author: ly
 */
public interface IActivityView {

    //沉浸式开启
    boolean isStatusBarEnabled();

    //状态栏文字字体  fasle 白色 true 黑色
    boolean statusBarDarkFont();

    //状态栏颜色
    int statusBarColor();

    ImmersionBar statusBarConfig();

    void initView();

    void initData();

    void initTitleBar();

    CharSequence getBarTitle();

    //标题栏的背景 可能是shape样式的背景
    int backGroundColor();

    Drawable backGroundDrawable();

    int titleColor();

    int getBackImgRes();

    void addTitleAction(TitleBarLayout.Action action);

    //真实的view layout
    int getContentViewId();

    void setDivideLine(boolean visible);

    Activity getActivity();

    void  onBackImageClick();
}
