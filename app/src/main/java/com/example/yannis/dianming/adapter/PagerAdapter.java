package com.example.yannis.dianming.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yannis.dianming.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by yannis on 2017/2/16.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> fragments;
    public PagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }



    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
