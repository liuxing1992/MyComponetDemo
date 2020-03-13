package com.mkyd.common.base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Data：2020/3/10-20:13
 * Author: ly
 */
public class BaseMvpPresent<V extends BaseMvpView> {

    protected V mvpView;

    protected CompositeDisposable mCompositeDisposable;

    public BaseMvpPresent(V mvpView) {
        this.mvpView = mvpView;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void add(Disposable disposable) {
        if (mCompositeDisposable != null)
            mCompositeDisposable.add(disposable);
    }

    public void onDestroy() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
        mvpView = null;
        mCompositeDisposable = null;
    }
}
