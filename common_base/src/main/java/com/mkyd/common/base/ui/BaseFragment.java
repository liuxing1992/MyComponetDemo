package com.mkyd.common.base.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mkyd.common.base.contract.IFragmentView;
import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common.constant.AppConstant;
import com.mkyd.common.stateview.OnNetworkListener;
import com.mkyd.common.stateview.OnRetryListener;
import com.mkyd.common.stateview.StateLayoutManager;
import com.mkyd.common.widget.TitleBarLayout;
import com.mkyd.common_base.R;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Data：2020/3/11-19:31
 * Author: ly
 */
public abstract class BaseFragment<P extends BaseMvpPresent> extends RxFragment implements IFragmentView {

    protected FrameLayout flContainer;
    protected LinearLayout llTitle;
    protected TitleBarLayout titleBar;
    protected View divideLine;
    protected P mPresent;
    private Unbinder mUnbinder;

    protected StateLayoutManager mStateLayoutManager;

    protected abstract P createPresenter();

    protected abstract int getContentLayoutID();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commo_base, container, false);
        flContainer = view.findViewById(R.id.flContainer);
        llTitle = view.findViewById(R.id.llTitle);
        titleBar = view.findViewById(R.id.titleBar);
        divideLine = view.findViewById(R.id.divideLine);
        mUnbinder = ButterKnife.bind(this, view);
        if (getContentLayoutID() > 0) {
            inflater.inflate(getContentLayoutID(), flContainer, true);
        }
        mPresent = getPresent() ;
        initTitleBar();
        initStatusViewLayout();
        return view;
    }


    @Override
    public void initTitleBar() {
        llTitle.setVisibility(enableTitle() ? View.VISIBLE : View.GONE);
        if (enableTitle()){
            divideLine.setVisibility(enableDivideLine() ? View.VISIBLE : View.GONE);
            titleBar.setTitle(getBarTitle());
            titleBar.setTitleSize(AppConstant.APP_TITLE_TEXT_SIZE);
            if (getBackImgRes()>0)
            titleBar.setLeftImageResource(getBackImgRes());
            titleBar.setTitleColor(titleColor());
            if (backGroundDrawable() == null) {
                llTitle.setBackgroundColor(backGroundColor());
            } else {
                llTitle.setBackground(backGroundDrawable());
            }
            titleBar.setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackImageClick();
                }
            });
        }
    }

    @Override
    public void addTitleAction(TitleBarLayout.Action action) {
        titleBar.addAction(action);
    }

    @Override
    public CharSequence getBarTitle() {
        return null;
    }

    @Override
    public int getBackImgRes() {
        return 0;
    }

    @Override
    public int titleColor() {
        return R.color.color_black;
    }

    @Override
    public int backGroundColor() {
        return R.color.color_white;
    }

    @Override
    public Drawable backGroundDrawable() {
        return null;
    }


    @Override
    public void onBackImageClick() {
        //
        Toast.makeText(getActivity() , "返回点击" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean enableDivideLine() {
        return false;
    }

    @Override
    public boolean enableTitle() {
        return false;
    }

    //布局状态切换

    @Override
    public void initStatusViewLayout() {
        mStateLayoutManager = StateLayoutManager.newBuilder(getActivity())
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
        Toast.makeText(getActivity() , "重新加载" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetWorkErrorClick() {
        Toast.makeText(getActivity() , "网络错误" , Toast.LENGTH_SHORT).show();
    }


    @Override
    public int contentViewID() {
        return getContentLayoutID();
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

    public P getPresent() {
        if (mPresent == null)
            mPresent = createPresenter();
        return mPresent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresent != null)
            mPresent.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //解决java.lang.IllegalStateException: Activity has been destroyed 的错误
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
