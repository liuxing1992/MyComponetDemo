package com.mkyd.common.base.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;
import com.mkyd.common.base.contract.IFragmentView;
import com.mkyd.common.base.contract.IStatusView;
import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common.base.mvp.BaseMvpView;
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
public abstract class BaseFragment<P extends BaseMvpPresent> extends RxFragment implements IFragmentView, BaseMvpView, IStatusView {
    //    private LinearLayout llroot ;
    protected FrameLayout flContainer;
    //    protected LinearLayout llTitle;
//    protected TitleBarLayout titleBar;
//    protected View divideLine;
    protected P mPresent;
    private Unbinder mUnbinder;

    private View mRootView;//根布局

    protected LoadService mBaseLoadService;

    protected abstract P createPresenter();

    protected abstract void initView();

    protected abstract int getContentLayoutID();


    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_commo_base, container, false);
        flContainer = mRootView.findViewById(R.id.flContainer);
        if (getContentLayoutID() > 0) {
            inflater.inflate(getContentLayoutID(), flContainer, true);
        }
        mPresent = getPresent();
        initTitleBar();
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusViewLayout();
    }

    @Override
    public void initTitleBar() {

    }

    @Override
    public void addTitleAction(TitleBarLayout.Action action) {

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
        Toast.makeText(getActivity(), "返回点击", Toast.LENGTH_SHORT).show();
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

        LoadSir.Builder builder = new LoadSir.Builder()
                .addCallback(getLoadingStatus())
                .addCallback(getEmptyStatus())
                .addCallback(getNetErrorStatus())
                .addCallback(getErrorStatus());
        mBaseLoadService = builder.build().register(flContainer, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                Toast.makeText(getActivity(), "reload", Toast.LENGTH_SHORT).show();
            }
        });
        mBaseLoadService.showSuccess();
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
        showEmptyLayout(0, "");
    }

    @Override
    public void showEmptyLayout(String emptyTip) {
        showEmptyLayout(0, emptyTip);
    }


    @Override
    public void showEmptyLayout(int emptyImgID, String emptyTip) {
        mBaseLoadService.setCallBack(getEmptyStatus().getClass(), new Transport() {
            @Override
            public void order(Context context, View view) {
                //notice 空布局id全部要一样 tvEmptyTip ivEmpty
                TextView tvEmpty = view.findViewById(R.id.tvEmptyTip);
                if (tvEmpty == null) return;
                if (!TextUtils.isEmpty(emptyTip)) {
                    tvEmpty.setText(emptyTip);
                }
                ImageView ivEmpty = view.findViewById(R.id.ivEmpty);
                if (ivEmpty == null) return;
                if (emptyImgID > 0)
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
        if (mUnbinder != null)
            mUnbinder.unbind();
    }


}
