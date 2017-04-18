package com.example.yannis.dianming.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yannis on 2017/2/18.
 */

public class HomeworkType implements Serializable{

    /**
     * homework_class_id : 3
     * percent_of_total_grade : 20
     * default : false
     * course_number : C1201150
     * name : NEW TYPE
     * course_name : 应用写作
     * course_id : 45
     */

    private int homework_class_id;
    private int percent_of_total_grade;
    @SerializedName("default")
    private boolean defaultX;
    private String course_number;
    private String name;
    private String course_name;
    private int course_id;

    public HomeworkType(int homework_class_id, int percent_of_total_grade, boolean defaultX, String
            course_number, String name, String course_name, int course_id) {
        this.homework_class_id = homework_class_id;
        this.percent_of_total_grade = percent_of_total_grade;
        this.defaultX = defaultX;
        this.course_number = course_number;
        this.name = name;
        this.course_name = course_name;
        this.course_id = course_id;
    }

    public int getHomework_class_id() {
        return homework_class_id;
    }

    public void setHomework_class_id(int homework_class_id) {
        this.homework_class_id = homework_class_id;
    }

    public int getPercent_of_total_grade() {
        return percent_of_total_grade;
    }

    public void setPercent_of_total_grade(int percent_of_total_grade) {
        this.percent_of_total_grade = percent_of_total_grade;
    }

    public boolean isDefaultX() {
        return defaultX;
    }

    public void setDefaultX(boolean defaultX) {
        this.defaultX = defaultX;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
