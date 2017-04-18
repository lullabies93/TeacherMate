package com.example.yannis.dianming.bean;

/**
 * Created by yannis on 2017/1/18.
 */

public class HomeworkGrade {

    /**
     * course_number : D1209770
     * course_id : 46
     * grade_id : 491
     * student_number : 16058119
     * score : 23
     * student_name : 孙圣翔
     * course_name : 国际动漫赏析
     */

    private String course_number;
    private int course_id;
    private int grade_id;
    private String student_number;
    private int score;
    private String student_name;
    private String course_name;

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
