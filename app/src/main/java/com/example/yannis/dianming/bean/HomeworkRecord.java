package com.example.yannis.dianming.bean;

import java.io.Serializable;

/**
 * Created by yannis on 2017/1/18.
 */

public class HomeworkRecord implements Serializable{

    /**
     * name : 修改后的名字
     * variety : 0
     * homework_record_id : 1
     * percent_of_total_grade : 0
     * week : 3
     */

    private String name;
    private int variety;
    private int homework_record_id;
    private int percent_of_total_grade;
    private int week;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVariety() {
        return variety;
    }

    public void setVariety(int variety) {
        this.variety = variety;
    }

    public int getHomework_record_id() {
        return homework_record_id;
    }

    public void setHomework_record_id(int homework_record_id) {
        this.homework_record_id = homework_record_id;
    }

    public int getPercent_of_total_grade() {
        return percent_of_total_grade;
    }

    public void setPercent_of_total_grade(int percent_of_total_grade) {
        this.percent_of_total_grade = percent_of_total_grade;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
