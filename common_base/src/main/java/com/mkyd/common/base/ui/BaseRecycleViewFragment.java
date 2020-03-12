package com.mkyd.common.base.ui;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.mkyd.common.base.contract.IRecyclerView;
import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common.constant.AppConstant;
import com.mkyd.common_base.R;
import com.mkyd.common_base.R2;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * Description: 针对页面是recyclerview
 * SmartRefreshLayout刷新
 * BaseQuickAdapter 加载更多
 * Data：2020/3/12-15:51
 * Author: ly
 */
public abstract class BaseRecycleViewFragment<T, P extends BaseMvpPresent> extends BaseLazyFragment<P> implements IRecyclerView<T>, OnItemClickListener, OnItemChildClickListener, OnRefreshListener, OnLoadMoreListener {

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.recycleView)
    RecyclerView mRecyclerView;
    private BaseQuickAdapter mAdapter;
    private int pageNo;

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_base_recycler;
    }

    @Override
    protected void initView() {
        mAdapter = getAdapter();
        if (getReHeaderViewID() != 0) {
            mAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(getReHeaderViewID(), null, false));
        }
        if (getReFooterViewID() != 0) {
            mAdapter.addFooterView(LayoutInflater.from(getActivity()).inflate(getReFooterViewID(), null, false));
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        if (getAdapterAnimtion() != null) {
            mAdapter.setAdapterAnimation(getAdapterAnimtion());
        }
        mRefreshLayout.setEnableRefresh(enableRefresh());
        mRefreshLayout.setEnableLoadMore(false);
        if (enableRefresh()) {
            mRefreshLayout.setOnRefreshListener(this);
        }

        if (mAdapter.getLoadMoreModule() != null) {
            mAdapter.getLoadMoreModule().setEnableLoadMore(enableLoadMore());
            mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
            if (enableLoadMore()) {
                mAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
            }
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //静止没有滚动
                    Glide.with(getActivity()).resumeRequests();
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //正在被外部拖拽,一般为用户正在用手指滚动
                    Glide.with(getActivity()).pauseRequests();
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    //自动滚动
                    Glide.with(getActivity()).pauseRequests();
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean enableRefresh() {
        return false;
    }

    @Override
    public boolean enableLoadMore() {
        return false;
    }

    @Override
    public int getReHeaderViewID() {
        return 0;
    }

    @Override
    public int getReFooterViewID() {
        return 0;
    }

    @Override
    public BaseAnimation getAdapterAnimtion() {
        return null;
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 0;
        if (mAdapter.getLoadMoreModule() != null)
            mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        onDataRefresh();
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.setEnableRefresh(false);
        onDataLoadMore();
    }

    @Override
    public void refreshUI(List<T> data) {
        mRefreshLayout.finishRefresh();
        if (data != null) {
            pageNo++;
            mAdapter.setNewData(data);
            //刷新成功
            if (data.size() == 0) {
                //数据空
                if (getReHeaderViewID() == 0 && getReHeaderViewID() == 0) {
                    showEmptyLayout("");
                }
            } else {
                //数据不为空
                if (data.size() < AppConstant.PAGE_SIZE) {
                    mAdapter.getLoadMoreModule().loadMoreEnd(true);
                }
            }
        }
        if (mAdapter.getLoadMoreModule() != null)
            mAdapter.getLoadMoreModule().setEnableLoadMore(enableLoadMore());
    }

    @Override
    public void loadMore(List<T> data, boolean hasMore) {
        if (data != null) {
            pageNo++;
            mAdapter.addData(data);
            if (data.size() == 0 || data.size() < AppConstant.PAGE_SIZE) {
                mAdapter.getLoadMoreModule().loadMoreEnd();
            } else {
                mAdapter.getLoadMoreModule().loadMoreComplete();
            }
        }
        mRefreshLayout.setEnableRefresh(enableRefresh());
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void onloadMoreFail() {
        mAdapter.getLoadMoreModule().loadMoreFail();
    }

    @Override
    public void onRefreshFail() {
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

    }
}

