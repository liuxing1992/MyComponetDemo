package com.mkyd.common.base.contract;

import android.graphics.drawable.Drawable;

import com.mkyd.common.base.mvp.BaseMvpView;
import com.mkyd.common.widget.TitleBarLayout;

/**
 * Description:
 * Data：2020/3/12-14:04
 * Author: ly
 */
public interface IFragmentView  {

    boolean enableTitle();

    boolean enableDivideLine();

    void initTitleBar();

    CharSequence getBarTitle();

    //标题栏的背景 可能是shape样式的背景
    int backGroundColor();

    Drawable backGroundDrawable();

    int titleColor();

    int getBackImgRes();

    void onBackImageClick();

    void addTitleAction(TitleBarLayout.Action action);
}
