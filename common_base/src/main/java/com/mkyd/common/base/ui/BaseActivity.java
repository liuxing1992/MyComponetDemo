package com.mkyd.common.base.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Data：2020/3/11-10:00
 * Author: ly
 */
public abstract class BaseActivity<P extends BaseMvpPresent> extends RxAppCompatActivity{

    protected P mPresent;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutResID() != 0) {
            setContentView(layoutResID());
            //绑定到butterknife
            mUnbinder = ButterKnife.bind(this);
            mPresent = getPresent();
            init();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        if (mPresent != null)
            mPresent.onDestroy();
        this.mPresent = null;
    }

    public abstract int layoutResID();

    public abstract void init();

    public abstract P ProvidePresnet();

    protected P getPresent(){
        if (null == mPresent) {
            mPresent = ProvidePresnet();
        }
        return mPresent;
    }
}
