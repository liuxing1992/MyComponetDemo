package com.mkyd.common.base.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.mkyd.common.base.contract.UIActivityView;
import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common.base.mvp.BaseMvpView;
import com.mkyd.common.constant.AppConstant;
import com.mkyd.common.widget.TitleBarLayout;
import com.mkyd.common_base.R;

/**
 * Description: todo 页面状态没完成
 * Data：2020/3/11-10:36
 * Author: ly
 */
public abstract class BaseUIActiivty< P extends BaseMvpPresent> extends BaseActivity<P> implements UIActivityView, BaseMvpView, ViewTreeObserver.OnGlobalLayoutListener {


    protected TitleBarLayout titleBar;
    protected FrameLayout flContainer;
    private LinearLayout llTitleLayout;
    private View divideLine ;
    private ImmersionBar mImmersionBar;


    @Override
    public int layoutResID() {
        return R.layout.activity_common_base;
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0) {
            View rootView = getLayoutInflater().inflate(layoutResID, null);
            setContentView(rootView);
            titleBar = findViewById(R.id.titleBar);
            flContainer = findViewById(R.id.flContainer);
            llTitleLayout = findViewById(R.id.llTitle);
            divideLine = findViewById(R.id.divideLine);
            //将子view add进flContainer
            getLayoutInflater().inflate(getContentViewId(), flContainer, true);
        }
    }

    @Override
    public void init() {
        //沉浸式
        if (isStatusBarEnabled()) {
            statusBarConfig().init();
            //设置标题栏高度
            ImmersionBar.setTitleBar(this, llTitleLayout);
        }
        //标题
        initTitleBar();
        initView();
        initData();
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    public int statusBarColor() {
        return 0;
    }

    @Override
    public boolean statusBarDarkFont() {
        return true;
    }

    @Override
    public ImmersionBar statusBarConfig() {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为白色
                .statusBarColor(statusBarColor() == 0 ? R.color.color_transparent : statusBarColor())
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mImmersionBar;
    }


    @Override
    public void initTitleBar() {
        titleBar.setTitle(getBarTitle());
        titleBar.setTitleSize(AppConstant.APP_TITLE_TEXT_SIZE);
//        titleBar.setLeftImageResource(); 返回图标
        titleBar.setTitleColor(titleColor());
        if (backGroundDrawable() == null) {
            llTitleLayout.setBackgroundColor(backGroundColor());
        } else {
            llTitleLayout.setBackground(backGroundDrawable());
        }
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void addTitleAction(TitleBarLayout.Action action) {
        titleBar.addAction(action);
    }

    @Override
    public int backGroundColor() {
        return ContextCompat.getColor(this, R.color.color_white);
    }

    @Override
    public int titleColor() {
        return ContextCompat.getColor(this, R.color.color_black);
    }

    @Override
    public Drawable backGroundDrawable() {
        return null;
    }

    @Override
    public void setDivideLine(boolean visible) {
        divideLine.setVisibility(visible? View.VISIBLE : View.GONE );
    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void showContentLayout() {

    }

    @Override
    public void showNetErrorLayout() {

    }

    @Override
    public void showEmptyLayout(String emptyTip) {

    }

    @Override
    public void onReloadClick() {

    }

    @Override
    public void onGlobalLayout() {

    }

    @Override
    public Activity getActiivty() {
        return this;
    }
}
