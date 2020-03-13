package com.mkyd.common.base.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mkyd.common.base.mvp.BaseMvpView;

import java.util.List;

/**
 * Description:
 * Data：2020/3/11-15:13
 * Author: ly
 */
public interface IRecyclerView<T> extends BaseMvpView {

    int getPageNo() ;

    //开始刷新
    void onDataRefresh();
    //开始加载更多
    void onDataLoadMore();
    //刷新成功 或首次加载 回调
    void refreshUI(List<T> data);
    //加载更多成功回调
    void loadMore(List<T> data, boolean hasMore);

    void onloadMoreFail();

    void onRefreshFail();

    boolean enableRefresh();

    boolean enableLoadMore();


    int getReHeaderViewID();

    int getReFooterViewID();

    /**
     *  新版的adapter框架 adapter是要实现LoadMoreModule接口才行
     */
    BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    RecyclerView.LayoutManager getLayoutManager();

    BaseAnimation getAdapterAnimtion();
}
