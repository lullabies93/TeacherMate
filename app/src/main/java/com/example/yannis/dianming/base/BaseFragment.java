package com.example.yannis.dianming.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by yannis on 2017/1/10.
 */

public abstract class BaseFragment extends Fragment {

    public abstract int getLayoutID();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutID(), null);
        ButterKnife.inject(this, rootView);
        initData(savedInstanceState);
        initView(rootView);
        loadData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public abstract void initData(Bundle bundle);
    public abstract void initView(View view);
    public abstract void loadData();
}
