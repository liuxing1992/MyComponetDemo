package com.mkyd.common.base.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.mkyd.common.base.contract.IActivityView;
import com.mkyd.common.base.contract.IStatusView;
import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common.base.mvp.BaseMvpView;
import com.mkyd.common.constant.AppConstant;
import com.mkyd.common.widget.TitleBarLayout;
import com.mkyd.common_base.R;

import butterknife.ButterKnife;

/**
 * Description: todo 页面状态没完成
 * Data：2020/3/11-10:36
 * Author: ly
 */
public abstract class BaseUIActiivty<P extends BaseMvpPresent> extends BaseActivity<P> implements IActivityView, BaseMvpView, IStatusView,ViewTreeObserver.OnGlobalLayoutListener {

    protected TitleBarLayout titleBar;
    protected FrameLayout flContainer;
    private LinearLayout llTitleLayout;
    private View divideLine;
    private ImmersionBar mImmersionBar;

    protected LoadService mBaseLoadService;

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
        System.out.println("----showLoadingLayout");
       mBaseLoadService.showCallback(getLoadingStatus().getClass());
    }

    @Override
    public void showContentLayout() {
        System.out.println("----showContentLayout");
       mBaseLoadService.showSuccess();
    }

    @Override
    public void showNetErrorLayout() {
        System.out.println("----showNetErrorLayout");
        mBaseLoadService.showCallback(getNetErrorStatus().getClass());
    }

    @Override
    public void showEmptyLayout() {
        showEmptyLayout(0,"");
    }

    @Override
    public void showEmptyLayout(String emptyTip) {
        showEmptyLayout(0 , emptyTip);
    }


    @Override
    public void showEmptyLayout(int emptyImgID, String emptyTip) {
        mBaseLoadService.setCallBack(getEmptyStatus().getClass(), new Transport() {
            @Override
            public void order(Context context, View view) {
                //notice 空布局id全部要一样 tvEmptyTip ivEmpty
                TextView tvEmpty = view.findViewById(R.id.tvEmptyTip);
                if (tvEmpty==null) return;
                if (!TextUtils.isEmpty(emptyTip)){
                    tvEmpty.setText(emptyTip);
                }
                ImageView ivEmpty = view.findViewById(R.id.ivEmpty);
                if (ivEmpty==null) return;
                if (emptyImgID>0)
                    ivEmpty.setImageResource(emptyImgID);
            }
        });
        mBaseLoadService.showCallback(getEmptyStatus().getClass());
    }

    @Override
    public void showErrorLayout() {
        mBaseLoadService.showCallback(getErrorStatus().getClass());
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
        LoadSir.Builder builder = new LoadSir.Builder()
                .addCallback(getLoadingStatus())
                .addCallback(getEmptyStatus())
                .addCallback(getNetErrorStatus())
                .addCallback(getErrorStatus());
        mBaseLoadService = builder.build().register(flContainer, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                Toast.makeText(getActivity() , "reload" , Toast.LENGTH_SHORT).show();
            }
        });
        mBaseLoadService.showSuccess();
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
