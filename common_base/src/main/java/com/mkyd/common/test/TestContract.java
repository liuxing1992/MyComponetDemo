package com.mkyd.common.test;

import com.mkyd.common.base.contract.IRecyclerView;

/**
 * Description:
 * Data：2020/3/11-19:02
 * Author: ly
 */
public interface TestContract {


    interface TestView<T> extends IRecyclerView<T> {

    }

    interface present {

        void fresh();

        void loadMore();
    }
}
