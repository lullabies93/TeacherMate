package com.example.yannis.dianming.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yannis on 2017/4/15.
 */

public class HomeworkWithTitle implements Serializable{
    private String title;
    private List<HomeworkRecord> datas;
    public HomeworkWithTitle(String title, List<HomeworkRecord> datas){
        this.title = title;
        this.datas = datas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HomeworkRecord> getDatas() {
        return datas;
    }

    public void setDatas(List<HomeworkRecord> datas) {
        this.datas = datas;
    }

    /**
     * 获取项目
     * @param position 如果position为1就返回标题
     * @return
     */
    public Object getItem(int position) {
        if (position == 0) {
            return title;
        } else {
            return datas.get(position - 1);
        }
    }

    public int size(){
        return datas.size()+1;
    }
}
