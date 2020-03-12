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
import com.mkyd.common.stateview.OnNetworkListener;
import com.mkyd.common.stateview.OnRetryListener;
import com.mkyd.common.stateview.StateLayoutManager;
import com.mkyd.common.widget.TitleBarLayout;
import com.mkyd.common_base.R;

/**
 * Description: todo 页面状态没完成
 * Data：2020/3/11-10:36
 * Author: ly
 */
public abstract class BaseUIActiivty<P extends BaseMvpPresent> extends BaseActivity<P> implements UIActivityView, BaseMvpView, ViewTreeObserver.OnGlobalLayoutListener {

    protected TitleBarLayout titleBar;
    protected FrameLayout flContainer;
    private LinearLayout llTitleLayout;
    private View divideLine;
    private ImmersionBar mImmersionBar;

    protected StateLayoutManager mStateLayoutManager;

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
        //多状态
        initStatusViewLayout();
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
        titleBar.setLeftImageResource(getBackImgRes());
        titleBar.setTitleColor(titleColor());
        if (backGroundDrawable() == null) {
            llTitleLayout.setBackgroundColor(backGroundColor());
        } else {
            llTitleLayout.setBackground(backGroundDrawable());
        }
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackImageClick();
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
        divideLine.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoadingLayout() {
        if (mStateLayoutManager != null)
            mStateLayoutManager.showLoading();
    }

    @Override
    public void showContentLayout() {
        if (mStateLayoutManager != null)
            mStateLayoutManager.showContent();
    }

    @Override
    public void showNetErrorLayout() {
        if (mStateLayoutManager != null)
            mStateLayoutManager.showNetWorkError();
    }

    @Override
    public void showEmptyLayout() {
        showEmptyLayout(0, "");
    }

    @Override
    public void showEmptyLayout(String emptyTip) {
        showEmptyLayout(0, emptyTip);
    }


    @Override
    public void showEmptyLayout(int emptyImgID, String emptyTip) {
        if (mStateLayoutManager != null)
            mStateLayoutManager.showEmptyData(emptyImgID, emptyTip);
    }

    @Override
    public void showErrorLayout() {
        if (mStateLayoutManager != null)
            mStateLayoutManager.showError();
    }

    @Override
    public void onReloadClick() {

    }

    @Override
    public void onNetWorkErrorClick() {

    }

    @Override
    public void onGlobalLayout() {

    }


    @Override
    public void initStatusViewLayout() {
        mStateLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(contentViewID())
                .emptyDataView(emptyViewID())
                .netWorkErrorView(netWorkErrorViewID())
                .errorView(errorViewID())
                .loadingView(loadingViewID())
                //设置空数据页面图片控件id
                .emptyDataIconImageId(R.id.ivEmpty)
                //设置空数据页面文本控件id
                .emptyDataTextTipId(R.id.tvEmptyTip)
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        //加载异常error
                        onReloadClick();
                    }
                })
                .onNetworkListener(new OnNetworkListener() {
                    @Override
                    public void onNetwork() {
                        onNetWorkErrorClick();
                    }
                })
                .build();
    }

    @Override
    public int contentViewID() {
        return getContentViewId();
    }

    @Override
    public int emptyViewID() {
        return R.layout.view_empty;
    }

    @Override
    public int loadingViewID() {
        return R.layout.view_loading;
    }

    @Override
    public int netWorkErrorViewID() {
        return R.layout.view_net_error;
    }

    @Override
    public int errorViewID() {
        return R.layout.view_error;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onBackImageClick() {
        finish();
    }

    @Override
    public int getBackImgRes() {
        return R.drawable.icon_black_back;
    }
}
