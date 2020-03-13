package com.mkyd.common.test;

import android.os.Handler;

import com.mkyd.common.base.mvp.BaseMvpPresent;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2020/3/11-18:50
 * Author: ly
 */
public class TestPrsent extends BaseMvpPresent<TestContract.TestView<String>> implements TestContract.present  {

    private int count ;

    private int loadMoreCount;

    public TestPrsent(TestContract.TestView mvpView) {
        super(mvpView);
    }

    @Override
    public void fresh() {

        if (count==0){
            mvpView.refreshUI(getList(1));
        }else if (count==1){
            mvpView.refreshUI(getList(2));
        }else if (count==2){
            mvpView.refreshUI(getList(10));
        }else if (count>=3){
            mvpView.onRefreshFail();
        }
        count++;

    }

    @Override
    public void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadMoreCount==0){
                    mvpView.loadMore(getList(10) ,false);
                }else if (loadMoreCount==1){
                    mvpView.loadMore(getList(10) ,false);
                }else {
                    mvpView.onloadMoreFail();
                }
                loadMoreCount++;
            }
        } , 1000);
    }

    private List<String> getList(int size){
        ArrayList<String> list= new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("");
        }
        return list ;
    }
}
