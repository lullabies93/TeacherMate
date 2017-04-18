package com.example.yannis.dianming.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.HomeworkClassAdapter;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.DialogListener;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.ShowDialogUtil;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.HomeworkType;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.InjectView;

public class SetPercentActivity extends BaseActivity {


    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.titleText)
    TextView titleText;
    @InjectView(R.id.punishEdit)
    EditText punishEdit;
    @InjectView(R.id.latePunishEdit)
    EditText latePunishEdit;
    @InjectView(R.id.leavePunishEdit)
    EditText leavePunishEdit;
    @InjectView(R.id.addType)
    Button addType;
    @InjectView(R.id.typeListview)
    ListView typeListview;
    @InjectView(R.id.submit)
    TextView submit;
    @InjectView(R.id.signPercentEt)
    EditText signPercentEt;

    TextView maxPercent;

    private EditText percentEt, typeNameEt, typePercentEt;
    private Button OK, CANCEL;
    private TextView typeTv;

    private SetPercentActivity activity;
    private MyApplication myApplication;
    private Course course;
    private ArrayList<HomeworkType> homeworkClass;
    private HomeworkClassAdapter adapter;

    private AlertDialog.Builder deleteBuilder;
    private AlertDialog.Builder updateBuilder;
    private View updateDialogView;
    private int max_percent = 100;

    @Override
    public int getLayoutID() {
        return R.layout.activity_set_percent;
    }

    @Override
    public void initViews() {

        titleText.setText(course.getCourse_name());
        punishEdit.setText(String.valueOf(course.getAbsences_deduce_point()));
        latePunishEdit.setText(String.valueOf(course.getLates_deduce_point()));
        leavePunishEdit.setText(String.valueOf(course.getVacates_deduce_point()));
        signPercentEt.setText(course.getAttendances_percent_of_total_grade() + "");
        typeListview.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSettings();
            }
        });
        typeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                HomeworkType type = homeworkClass.get(position);
                ShowDialogUtil.showModifyDialog(activity, type.getName(), getMaxPercent(), type
                        .getPercent_of_total_grade(), new DialogListener<Integer>() {
                    @Override
                    public void onDialogClick(Integer ret) {
                        updatePercent(position, ret);
                    }
                });
            }
        });
        typeListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                ShowDialogUtil.showConfirmDialog(activity, "删除  " + homeworkClass.get(position).getName() +
                        "  类型?", new DialogListener() {
                    @Override
                    public void onDialogClick(Object ret) {
                        deleteType(position);
                    }
                });
                return true;
            }
        });
    }

    private void showAddDialog() {
        ShowDialogUtil.showAddTypeDialog(activity, getMaxPercent(), homeworkClass, new
                DialogListener<String>() {
                    @Override
                    public void onDialogClick(String ret) {
                        String[] rets = ret.split(",");
                        submitNewType(rets[0], rets[1]);
                    }
                });
    }

    private int getMaxPercent() {
        max_percent = 100 - course.getAttendances_percent_of_total_grade();
        for (HomeworkType type : homeworkClass) {
            max_percent = max_percent - type.getPercent_of_total_grade();
        }
        return max_percent;
    }

    private void submitNewType(String name, String percent) {
        JSONObject json = new JSONObject();
        try {
            json.put(ConstantValues.signName, name);
            json.put(ConstantValues.courseId, course.getCourse_id());
            json.put(ConstantValues.percent_of_total_grade, Integer.parseInt(percent));
            json.put("default", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonRequest.createJsonPostRequest(ConstantValues.GET_HOMEWORK_CLASSES, json.toString(), new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(object.toString());
                    if (ret.getInt(ConstantValues.status) == 1) {
                        Util.showToast(activity, "添加成功");
                        loadData();
                    } else {
                        Util.showToast(activity, "添加失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
            }
        }));
    }

    private TextView tips;

    private void initUpdateDialog() {
        updateBuilder = new AlertDialog.Builder(activity);
        updateDialogView = getLayoutInflater().inflate(R.layout.update_percent_dialog, null);
        percentEt = (EditText) updateDialogView.findViewById(R.id.et);
        typeTv = (TextView) updateDialogView.findViewById(R.id.tv);
        tips = (TextView) updateDialogView.findViewById(R.id.tips);

        updateBuilder.setView(updateDialogView);

        updateBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    private void initDeleteDialog() {
        deleteBuilder = new AlertDialog.Builder(activity);
        deleteBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void deleteType(int position) {
        JSONArray idArray = new JSONArray();
        idArray.put(homeworkClass.get(position).getHomework_class_id());
        CommonRequest.createDeleteRequest(ConstantValues.GET_HOMEWORK_CLASSES, idArray.toString(), new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(object.toString());
                    if (ret.getInt(ConstantValues.status) == 1) {
                        Util.showToast(activity, "删除成功");
                        loadData();
                    } else {
                        Util.showToast(activity, "删除失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
            }
        }));
    }

    private void updatePercent(int Position, int percent) {
        int max = getMaxPercent() + homeworkClass.get(Position).getPercent_of_total_grade();
        if (percent > max) {
            Util.showToast(activity, "百分比不能大于" + max);
            return;
        }
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            json.put(ConstantValues.percent_of_total_grade, percent);
            json.put(ConstantValues.homeworkClassId, homeworkClass.get(Position).getHomework_class_id());
            array.put(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonRequest.createPutRequest(ConstantValues.GET_HOMEWORK_CLASSES, array.toString(), new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(object.toString());
                    if (ret.getInt(ConstantValues.status) == 1) {
                        Util.showToast(activity, "修改成功");
                        loadData();
                    } else {
                        Util.showToast(activity, "修改失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
            }
        }));

    }

    private void submitSettings() {
        if (TextUtils.isEmpty(punishEdit.getText().toString()) || TextUtils.isEmpty(latePunishEdit.getText
                ().toString()) || TextUtils.isEmpty(leavePunishEdit.getText().toString())) {
            Util.showToast(activity, "扣分设置不能为空,可设置为0");
            return;
        } else if (TextUtils.isEmpty(signPercentEt.getText().toString())) {
            Util.showToast(activity, "考勤占比不能为空");
            return;
        } else if (100 - getMaxPercent() - course.getAttendances_percent_of_total_grade() + Integer
                .parseInt(signPercentEt.getText().toString()) != 100) {
            Util.showToast(activity, "考勤占比加作业考核占比须等于100%");
            return;
        } else {
            JSONObject json = new JSONObject();
            JSONArray array = new JSONArray();
            try {
                json.put("attendances_percent_of_total_grade", Integer.parseInt(signPercentEt.getText()
                        .toString()));
                json.put(ConstantValues.courseId, course.getCourse_id());
                json.put(ConstantValues.absencePunish, Integer.parseInt(punishEdit.getText().toString()));
                json.put(ConstantValues.latePunish, Integer.parseInt(latePunishEdit.getText().toString()));
                json.put(ConstantValues.leavePunish, Integer.parseInt(leavePunishEdit.getText().toString()));
                array.put(json);
                CommonRequest.createPutRequest(ConstantValues.GET_ALL_COURSE, array.toString(), new
                        CommomHandler(new CommomListener() {


                    @Override
                    public void onSuccess(Object object) {
                        try {
                            JSONObject ret = new JSONObject(object.toString());
                            if (ret.getInt(ConstantValues.status) == 1) {
                                Util.showToast(activity, "设置成功");
                                finish();
                            } else {
                                Util.showToast(activity, "设置失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Object object) {
                        Util.showToast(activity, "操作失败");
                    }
                }));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(ConstantValues.GET_HOMEWORK_CLASSES + "?" + ConstantValues.courseId
                + "=" + course.getCourse_id(), null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    homeworkClass = (ArrayList<HomeworkType>) object;
                    adapter.homeworkClass = homeworkClass;
                    adapter.notifyDataSetChanged();
                } else {
                    Util.showToast(activity, "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
            }
        }, HomeworkType.class));
    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        myApplication = (MyApplication) getApplication();
        course = (Course) getIntent().getSerializableExtra(ConstantValues.courseInfo);
        homeworkClass = new ArrayList<>();
        adapter = new HomeworkClassAdapter(activity, homeworkClass);
    }

}
