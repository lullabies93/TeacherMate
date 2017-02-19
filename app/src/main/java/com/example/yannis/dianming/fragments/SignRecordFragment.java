package com.example.yannis.dianming.fragments;

import android.os.Bundle;
import android.view.View;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.BaseFragment;

/**
 * Created by yannis on 2017/1/13.
 */

public class SignRecordFragment extends BaseFragment {

    private String label;

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int getLayoutID() {
        return R.layout.sign_detail_fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void loadData() {

    }
}
