package com.mkyd.common.base.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mkyd.common.base.adapter.BasePageAdapter;
import com.mkyd.common.base.contract.IViewPAger;
import com.mkyd.common.base.mvp.BaseMvpPresent;
import com.mkyd.common_base.R;
import com.mkyd.common_base.R2;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Description:公共viewpager+ fragment
 * Data：2020/3/13-18:46
 * Author: ly
 */
public abstract class BaseViewPagerActivity extends BaseUIActiivty implements ViewPager.OnPageChangeListener , IViewPAger {

    @BindView(R2.id.baseTabLayout)
    TabLayout baseTabLayout;
    @BindView(R2.id.baseViewPager)
    ViewPager baseViewPager;

    public abstract String[] getTitles();

    public abstract ArrayList<Fragment> getFragment();

    //可重写
    public int selectPosition(){
        return  0 ;
    }

    @Override
    public void initView() {
        baseViewPager.setAdapter(new BasePageAdapter(getSupportFragmentManager() , getFragment() , getTitles()));
        baseViewPager.setOffscreenPageLimit(getFragment().size());
        baseViewPager.setCurrentItem(selectPosition());
        baseTabLayout.setupWithViewPager(baseViewPager);
        baseViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_base_viewpager;
    }

    @Override
    public BaseMvpPresent ProvidePresnet() {
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        onViewPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
