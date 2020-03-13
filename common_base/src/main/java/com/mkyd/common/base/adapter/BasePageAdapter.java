package com.mkyd.common.base.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description:
 * Data：2020/3/13-19:19
 * Author: ly
 */
public class BasePageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private CharSequence[] mTitles;

    public BasePageAdapter(@NonNull FragmentManager fm, List<Fragment> list, CharSequence[] titles) {
        super(fm);
        mList = list;
        mTitles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }
}
