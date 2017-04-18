package com.example.yannis.dianming.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.GradeAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.DialogListener;
import com.example.yannis.dianming.base.ShowDialogUtil;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.HomeworkGrade;
import com.example.yannis.dianming.bean.HomeworkRecord;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.InjectView;

public class HomeworkDetailActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.homeworkName)
    TextView homeworkName;
    @InjectView(R.id.gradeListview)
    ListView gradeListview;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.sort)
    TextView sort;

    private HomeworkRecord homeworkRecord;
    private ArrayList<HomeworkGrade> homeworkGrades;
    private GradeAdapter gradeAdapter;
    private HomeworkDetailActivity activity;

    @Override
    public int getLayoutID() {
        return R.layout.activity_homework_detail;
    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        homeworkRecord = (HomeworkRecord) getIntent().getSerializableExtra(ConstantValues.homeworkItem);
        homeworkGrades = new ArrayList<>();
        gradeAdapter = new GradeAdapter(homeworkGrades, activity);
    }

    @Override
    public void initViews() {
        homeworkName.setText(homeworkRecord.getName());
        gradeListview.setAdapter(gradeAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        gradeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeworkGrade grade = homeworkGrades.get(position);
                showDialog(grade);
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGrades();
            }
        });
    }

    private void sortGrades() {
        Collections.sort(homeworkGrades, new Comparator<HomeworkGrade>() {
            @Override
            public int compare(HomeworkGrade lhs, HomeworkGrade rhs) {
                //按照学生的年龄进行升序排列
                if (lhs.getScore() < rhs.getScore()) {
                    return 1;
                }
                if (lhs.getScore() == rhs.getScore()) {
                    return 0;
                }
                return -1;
            }
        });
        gradeAdapter.gradeArrayList = homeworkGrades;
        gradeAdapter.notifyDataSetChanged();
    }

    private void showDialog(final HomeworkGrade grade) {
        ShowDialogUtil.showModifyGradeDialog(activity, grade.getStudent_name(), grade.getScore(), new
                DialogListener<String>() {
            @Override
            public void onDialogClick(String ret) {
                changeGrade(grade.getGrade_id(), ret);
            }
        });
    }

    @Override
    public void loadData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            showDialog();
        }
        CommonRequest.createGetRequest(ConstantValues.GET_HOMEWORK_GRADES + "?" + ConstantValues
                .homeworkRecordId + "=" +
                homeworkRecord.getHomework_record_id(), null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    homeworkGrades = (ArrayList<HomeworkGrade>) object;
                    refreshListview();
                } else {
                    Util.showToast(activity, "data error");
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                dismissDialog();
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                dismissDialog();
            }
        }, HomeworkGrade.class));

    }

    private void refreshListview() {

        gradeAdapter.gradeArrayList = homeworkGrades;
        gradeAdapter.notifyDataSetChanged();
        if (homeworkGrades.size() == 0) {
            Util.showToast(activity, "作业记录为空");
        }
    }


    private void changeGrade(int id, String newgrade) {
        if (Integer.parseInt(newgrade) > 100) {
            Util.showToast(activity, "不得超过100分");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            jsonObject.put(ConstantValues.gradeId, id);
            jsonObject.put(ConstantValues.score, Integer.parseInt(newgrade));
            array.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonRequest.createPutRequest(ConstantValues.GET_HOMEWORK_GRADES, array.toString(), new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof String) {
                    try {
                        JSONObject ret = new JSONObject(String.valueOf(object));
                        if (ret.getInt(ConstantValues.status) == 1) {
                            loadData();
                            Util.showToast(activity, "修改成功");
                        } else {
                            Util.showToast(activity, "修改失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, "修改失败");
            }
        }));
    }

}
