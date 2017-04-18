package com.example.yannis.dianming.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.HomeworkType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yannis on 2017/4/18.
 */

public class ShowDialogUtil {

    static AlertDialog alertDialog = null;

    public static void showAddHomeworkDialog(final Context context, final ArrayList<HomeworkType> clzs,
                                             final DialogListener listener) {


        View dialogView = LayoutInflater.from(context).inflate(R.layout.create_homework_dialog, null);
        final EditText homework_name = (EditText) dialogView.findViewById(R.id.homeworkNameEt);
        final EditText defGrade = (EditText) dialogView.findViewById(R.id.defaultGrade);
        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.typeGroup);
        final TextView ok = (TextView) dialogView.findViewById(R.id.ok);
        final TextView cancel = (TextView) dialogView.findViewById(R.id.cancel);
        RadioButton radioButton = null;
        for (int i = 0, len = clzs.size(); i < len; i++) {
            radioButton = new RadioButton(context);
            String name = clzs.get(i).getName();
            radioButton.setText(name.length() <= 4 ? name : name.substring(0, 4) + "..");
            radioButton.setTextSize(14);
            radioButton.setTextColor(context.getResources().getColorStateList(R.color
                    .text_color_selector));
            radioButton.setBackgroundResource(R.drawable.radiobutton_selector);
            radioButton.setButtonDrawable(android.R.drawable.screen_background_light_transparent);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setPadding(8, 8, 8, 8);
            radioButton.setId(i);
            radioGroup.addView(radioButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                    .LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(homework_name.getText().toString())) {
                    Util.showToast(context, "考核名称不能为空");
                    return;
                }
                int grade = 80;
                if (TextUtils.isEmpty(defGrade.getText().toString())) {
                    grade = Integer.parseInt(defGrade.getHint().toString());
                } else {
                    grade = Integer.parseInt(defGrade.getText().toString());
                }
                if (grade < 0 || grade > 100) {
                    Util.showToast(context, "默认分数应在0-100之间");
                    return;
                }
                if (listener != null) {
                    int typeId = clzs.get(radioGroup.getCheckedRadioButtonId())
                            .getHomework_class_id();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", homework_name.getText().toString());
                        jsonObject.put(ConstantValues.homeworkClassId, typeId);
                        jsonObject.put("grade", String.valueOf(grade));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                    listener.onDialogClick(jsonObject.toString());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog = new AlertDialog.Builder(context).setView(dialogView).create();
        alertDialog.show();
    }

    static AlertDialog confirmDialog;

    public static void showConfirmDialog(Context context, String message, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
        TextView msg = (TextView) view.findViewById(R.id.msgTv);
        final TextView ok = (TextView) view.findViewById(R.id.ok);
        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        msg.setText(message);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
                listener.onDialogClick(1);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });
        builder.setView(view);
        confirmDialog = builder.create();
        confirmDialog.show();
    }

    static AlertDialog tipDialog;

    public static void showTipDialog(Context context, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.to_add_dialog, null);
        final TextView ok = (TextView) view.findViewById(R.id.ok);
        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
                listener.onDialogClick(1);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
            }
        });
        builder.setView(view);
        tipDialog = builder.create();
        tipDialog.show();
    }

    static AlertDialog modifyGradeDialog;
    public static void showModifyGradeDialog(final Context context, String name, int grade, final
    DialogListener<String>
            listener){
        View dialog = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        final EditText editText = (EditText) dialog.findViewById(R.id.et);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint(String.valueOf(grade));
        TextView textView = (TextView) dialog.findViewById(R.id.tv);
        textView.setText(name + " : ");
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyGradeDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyGradeDialog.dismiss();
                if (TextUtils.isEmpty(editText.getText().toString())){
                    listener.onDialogClick(editText.getHint().toString());
                }else {
                    listener.onDialogClick(editText.getText().toString());
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder
                (context);
        builder.setView(dialog);
        modifyGradeDialog = builder.create();
        modifyGradeDialog.show();
    }

    static AlertDialog modifyDialog;

    public static void showModifyDialog(final Context context, String typeName, int max, int percent, final
    DialogListener<Integer>
            listener) {
        AlertDialog.Builder updateBuilder = new AlertDialog.Builder(context);
        View updateDialogView = LayoutInflater.from(context).inflate(R.layout.update_percent_dialog, null);
        final EditText percentEt = (EditText) updateDialogView.findViewById(R.id.et);
        TextView typeTv = (TextView) updateDialogView.findViewById(R.id.tv);
        TextView tips = (TextView) updateDialogView.findViewById(R.id.tips);
        TextView ok = (TextView) updateDialogView.findViewById(R.id.ok);
        TextView cancel = (TextView) updateDialogView.findViewById(R.id.cancel);
        typeTv.setText(typeName);
        tips.setText("最大可设置占比为：" + (max + percent));
        percentEt.setHint(String.valueOf(percent));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=percentEt.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    text = percentEt.getHint().toString();
                }
                int num = Integer.parseInt(text);
                if (num > 100 || num < 0) {
                    Util.showToast(context, "请输入0~100之间的数字");
                    return;
                } else {
                    modifyDialog.dismiss();
                    listener.onDialogClick(num);
                }
            }
        });
        updateBuilder.setView(updateDialogView);
        modifyDialog = updateBuilder.create();
        modifyDialog.show();
    }


    static AlertDialog addMarkDialog;

    public static void showAddMarkDialog(Context context, int weekday, int weekday1, final
    DialogListener<String>
            listener) {
        final View dialog = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        final EditText editText = (EditText) dialog.findViewById(R.id.et);
        final TextView ok = (TextView) dialog.findViewById(R.id.ok);
        final TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        editText.setText(Util.getDateBeforeOrLater(context, weekday - weekday1) + "周" +
                (weekday + 1));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMarkDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMarkDialog.dismiss();
                listener.onDialogClick(editText.getText().toString());
            }
        });
        builder.setView(dialog);
        addMarkDialog = builder.create();
        addMarkDialog.show();

    }

    static AlertDialog addTypeDialog;

    public static void showAddTypeDialog(final Context context, final int maxPercent, final
    ArrayList<HomeworkType> homeworkClass, final DialogListener<String> listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.add_type_dialog, null);
        final EditText typeNameEt = (EditText) dialogView.findViewById(R.id.typeNameEt);
        final EditText typePercentEt = (EditText) dialogView.findViewById(R.id.percentEt);
        final TextView maxPer = (TextView) dialogView.findViewById(R.id.maxPercent);
        maxPer.setText("最大可设置百分比为 " + maxPercent + " %");
        final TextView ok = (TextView) dialogView.findViewById(R.id.ok);
        final TextView cancel = (TextView) dialogView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTypeDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(typeNameEt.getText().toString()) || TextUtils.isEmpty(typePercentEt
                        .getText().toString())) {
                    Util.showToast(context, "输入不能为空");
                    return;
                } else if (Integer.parseInt(typePercentEt.getText().toString()) > maxPercent) {
                    Util.showToast(context, "占比设置超过最大值");
                    return;
                } else {
                    String name = typeNameEt.getText().toString();
                    for (HomeworkType type : homeworkClass) {
                        if (type.getName().equals(name)) {
                            Util.showToast(context, "该名字已存在，请重新输入");
                            return;
                        }
                    }
                    addTypeDialog.dismiss();
                    listener.onDialogClick(name + "," + typePercentEt.getText().toString());
                }
            }
        });
        builder.setView(dialogView);
        addTypeDialog = builder.create();
        addTypeDialog.show();
    }
}
