package com.mkyd.module.main;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mkyd.common.base.ui.BaseRecyclerViewActivity;
import com.mkyd.common.test.TestContract;
import com.mkyd.common.test.TestFragmentActivity;
import com.mkyd.common.test.TestPrsent;

public class MainActivity extends BaseRecyclerViewActivity<String, TestPrsent> implements TestContract.TestView<String> {


    @Override
    public boolean enableRefresh() {
        return true;
    }


    @Override
    public boolean enableLoadMore() {
        return true;
    }

    @Override
    public void onDataRefresh() {
        getPresent().fresh();

    }

    @Override
    public void onDataLoadMore() {
        getPresent().loadMore();
    }

    @Override
    public BaseQuickAdapter<String, BaseViewHolder> getAdapter() {
        return new MyAdapter(R.layout.item_recycle_test);
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


    @Override
    public void initData() {
        showLoadingLayout();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onDataRefresh();
            }
        }, 1000);
    }

    @Override
    public CharSequence getBarTitle() {
        return "测试刷新";
    }

    @Override
    public TestPrsent ProvidePresnet() {
        return new TestPrsent(this);
    }

    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

        public MyAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {
            baseViewHolder.setText(R.id.tvText, baseViewHolder.getAdapterPosition() + "item");
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        startActivity(new Intent(this , TestFragmentActivity.class));
    }
}
