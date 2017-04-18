package com.example.yannis.dianming.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.SPUtils;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.fragments.SettingFragment;
import com.example.yannis.dianming.fragments.SignFragment;
import com.example.yannis.dianming.fragments.WorkFragment;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;
import com.example.yannis.dianming.widget.TabItem;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.InjectView;

public class HomeActivity extends BaseActivity {

    @InjectView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @InjectView(android.R.id.tabs)
    TabWidget tabs;
    @InjectView(android.R.id.tabhost)
    FragmentTabHost tabhost;
    @InjectView(R.id.title)
    TextView title;
    private ArrayList<TabItem> tabItems;
    private MyApplication myApplication;
    private HomeActivity activity;
    private PushAgent pushAgent;

    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void initViews() {
        initTabs();
        initTabHost();
    }

    private void initTabHost() {
        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabhost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabItems.size(); i++) {
            TabItem tabItem = tabItems.get(i);
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tabItem.getTitle_string()).setIndicator(tabItem
                    .getView());
            tabhost.addTab(tabSpec, tabItem.getFragmentClass(), null);
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
            if (i == 0) {
                tabItem.setChecked(true);
            }
        }

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabItems.size(); i++) {
                    TabItem tabItem = tabItems.get(i);
                    if (tabId.equals(tabItem.getTitle_string())) {
                        tabItem.setChecked(true);
                        title.setText(tabId);
                    } else {
                        tabItem.setChecked(false);
                    }
                }
            }
        });

        //tabhost.det
    }

    private void initTabs() {
        tabItems = new ArrayList<TabItem>();
        tabItems.add(new TabItem(this, R.mipmap.sign_in_copy, R.mipmap.sign_in, R.string.rollcall,
                SignFragment.class));
        tabItems.add(new TabItem(this, R.mipmap.grade, R.mipmap.grade_select, R.string.ordinary_work,
                WorkFragment.class));
        tabItems.add(new TabItem(this, R.mipmap.setting_copy, R.mipmap.setting, R.string.setting,
                SettingFragment.class));

    }

    @Override
    public void loadData() {
        getWeek();
    }

    private void getWeek() {
        CommonRequest.createGetRequest(ConstantValues.GET_WEEK, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                String ret = String.valueOf(object);
                try {
                    JSONObject jsonObject = new JSONObject(ret);
                    if (jsonObject.getInt("status") == 1) {
                        int week = jsonObject.getInt("week");

                        myApplication.setWeek(week);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(HomeActivity.this, String.valueOf(object));
            }
        }));
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getApplication();
        activity = this;
//        pushAgent = PushAgent.getInstance(this);
//        pushAgent.addAlias(String.valueOf(myApplication.getTeacher_id()), ConstantValues.teacherID, new UTrack.ICallBack() {
//
//
//            @Override
//            public void onMessage(boolean b, String s) {
//                Util.logHelper(String.valueOf(b)+":"+s);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myApplication.getTeacher_id()==0){
            myApplication.appExit();
        }
    }
}
