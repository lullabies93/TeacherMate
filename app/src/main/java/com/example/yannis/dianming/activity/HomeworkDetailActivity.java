package com.example.yannis.dianming.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.GradeAdapter;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
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

import butterknife.ButterKnife;
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
        homeworkRecord = (HomeworkRecord) getIntent().getSerializableExtra(APIs.homeworkItem);
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
    }

    private void showDialog(final HomeworkGrade grade) {
        View dialog = getLayoutInflater().inflate(R.layout.dialog, null);
        final EditText editText = (EditText) dialog.findViewById(R.id.et);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText(String.valueOf(grade.getScore()));
        TextView textView = (TextView) dialog.findViewById(R.id.tv);
        textView.setText(grade.getStudent_name() + " : ");
        AlertDialog.Builder builder = new AlertDialog.Builder
                (activity);
        builder.setTitle("修改成绩");
        builder.setMessage(null);
        builder.setView(dialog);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                changeGrade(grade.getGrade_id(), editText.getText().toString());
            }
        });
        builder.show();
    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(APIs.GET_HOMEWORK_GRADES + "?" + APIs.homeworkRecordId + "=" +
                homeworkRecord.getHomework_record_id(), null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    homeworkGrades = (ArrayList<HomeworkGrade>) object;
                    refreshListview();
                }
            }

            @Override
            public void onFailure(Object object) {

            }
        }, HomeworkGrade.class));
    }

    private void refreshListview() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        gradeAdapter.gradeArrayList = homeworkGrades;
        gradeAdapter.notifyDataSetChanged();
    }


    private void changeGrade(int id, String newgrade) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            jsonObject.put(APIs.gradeId, id);
            jsonObject.put(APIs.score, Integer.parseInt(newgrade));
            array.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonRequest.createPutRequest(APIs.GET_HOMEWORK_GRADES, array.toString(), new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof String) {
                    try {
                        JSONObject ret = new JSONObject(String.valueOf(object));
                        if (ret.getInt(APIs.status) == 1) {
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
