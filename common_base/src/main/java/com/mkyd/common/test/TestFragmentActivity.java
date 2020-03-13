package com.mkyd.common.test;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;

import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common.base.ui.BaseUIActiivty;
import com.mkyd.common_base.R;
import com.mkyd.common_base.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * Data：2020/3/13-14:25
 * Author: ly
 */
public class TestFragmentActivity extends BaseUIActiivty {



    @Override
    public void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.root, new TestFragment()).commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public CharSequence getBarTitle() {
        return "测试fragment";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    public BaseMvpPresent ProvidePresnet() {
        return null;
    }


}
