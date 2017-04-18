package com.example.yannis.dianming.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.DialogListener;
import com.example.yannis.dianming.base.ShowDialogUtil;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.HomeworkRecord;
import com.example.yannis.dianming.bean.HomeworkWithTitle;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/4/15.
 */

public class WithTitleAdapter extends BaseAdapter {
    private Context context;
    public List<HomeworkWithTitle> datas;
    private LayoutInflater inflater;
    private android.support.v4.app.FragmentManager fragmentManager;
    private onDeleteClickListener listener;

    private static final int TYPE_HEADER = 0;  //代表标题
    private static final int TYPE_ITEM = 1;    //代表项目item

    public interface onDeleteClickListener {
        public void onDeleteClick(String msg, int id);
    }

    public void setOnDeleteClickListener(onDeleteClickListener listener){
        this.listener = listener;
    }

    public WithTitleAdapter(Context context, List<HomeworkWithTitle> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if (datas != null) {
            int count = 0;
            for (HomeworkWithTitle inst : datas) {
                count += inst.size();
            }
            return count;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        int head = 0;  //标题位置
        for (HomeworkWithTitle type : datas) {
            int size = type.size();
            int current = position - head;
            if (current < size) {
                //返回对应位置的值
                return type.getItem(current);
            }
            head += size;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @return 返回item类型数目
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 获取当前item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int head = 0;
        for (HomeworkWithTitle type : datas) {
            int size = type.size();
            int current = position - head;
            if (current == 0) {
                return TYPE_HEADER;
            }
            head += size;
        }
        return TYPE_ITEM;
    }

    /**
     * 判断当前的item是否可以点击
     *
     * @param position
     * @return
     */
    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != TYPE_HEADER;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.title_item, parent, false);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.title.setText((CharSequence) getItem(position));
                break;
            case TYPE_ITEM:
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.with_title_item, parent, false);
                    viewHolder.homeworkName = (TextView) convertView.findViewById(R.id.homeworkName);
                    viewHolder.homeworkWeek = (TextView) convertView.findViewById(R.id.homeworkWeek);
                    viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                final HomeworkRecord record = (HomeworkRecord) getItem(position);
                viewHolder.homeworkName.setText(record.getName());
                Util.logHelper(record.getName());
                viewHolder.homeworkWeek.setText("第" + record.getWeek() + "周");
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = record.getName() + "-" + "第" + record.getWeek() + "周";
                        listener.onDeleteClick(msg, record.getHomework_record_id());
                    }
                });
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView homeworkName;
        TextView homeworkWeek;
        ImageButton delete;
    }
}
