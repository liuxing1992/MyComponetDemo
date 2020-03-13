package com.mkyd.common.test;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common.base.ui.BaseLazyFragment;
import com.mkyd.common_base.R;
import com.mkyd.common_base.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Data：2020/3/13-14:32
 * Author: ly
 * todo 组件化butterknife要用R2.id
 * 对于组件化 butterknife 查看
 * https://www.jianshu.com/p/b6990f643422
 */
public class TestFragment extends BaseLazyFragment {


    @BindView(R2.id.btn1)
    Button btn1;
    @BindView(R2.id.btn2)
    Button btn2;
    @BindView(R2.id.btn3)
    Button btn3;
    @BindView(R2.id.btn4)
    Button btn4;
    @BindView(R2.id.llroot)
    LinearLayout llroot;

    @Override
    protected void initData() {

    }

    @Override
    protected BaseMvpPresent createPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.fragment_test;
    }


    @OnClick({R2.id.btn1, R2.id.btn2, R2.id.btn3, R2.id.btn4})
    public void onViewClicked(View view) {
        if (view.getId()==R.id.btn1){
            showLoadingLayout();
            postDelay();
        }else if (view.getId()==R.id.btn2){
            showEmptyLayout();
            postDelay();
        }else if (view.getId()==R.id.btn3){
            showErrorLayout();
            postDelay();
        }else if (view.getId()==R.id.btn4){
            showNetErrorLayout();
            postDelay();
        }
    }

    private void postDelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentLayout();
            }
        },1000);
    }
}
