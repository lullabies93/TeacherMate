package com.example.yannis.dianming.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.SignRecordAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Student;
import com.example.yannis.dianming.bean.StudentSignDetail;
import com.example.yannis.dianming.bean.StudentSignState;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * Created by yannis on 2017/1/13.
 */

public class SignRecordFragment extends BaseFragment {
//
    @InjectView(R.id.sign_detail_listview)
    ListView signDetailListview;
    private StudentSignDetail student;

    private ArrayList<StudentSignState> signStates;

    private SignRecordAdapter adapter;

    public void setLabel(StudentSignDetail student) {
        this.student = student;
    }


    @Override
    public int getLayoutID() {
        return R.layout.sign_detail_fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        signStates = new ArrayList<>();

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(ConstantValues.GET_RESULT_BY_RECORD_ID + "?" + ConstantValues.studentId + "=" + student
                .getStudent_id(), null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    signStates = (ArrayList<StudentSignState>) object;
                    for (int i = 0; i<signStates.size(); i++){
                        if (signStates.get(i).getStatus() == 0){
                            signStates.remove(i);
                            i --;
                        }
                    }
                    adapter = new SignRecordAdapter(signStates, getActivity());
                    signDetailListview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(getActivity(), object.toString());
            }
        }, StudentSignState.class));
    }
}
