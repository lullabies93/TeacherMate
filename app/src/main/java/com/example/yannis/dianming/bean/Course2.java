package com.example.yannis.dianming.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yannis on 2017/1/14.
 */

public class Course2 implements Serializable{
    private String course_number;
    private int homeworks_percent_of_total_grade;
    private int attendances_percent_of_total_grade;
    private String course_name;
    private int lates_deduce_point;
    private int absences_deduce_point;
    private int credit;
    private int course_id;
    private int weekday;
    private String section;
    private String classroom;

    public Course2(String section, String classroom, int weekday, int course_id, String course_name) {
        this.section = section;
        this.classroom = classroom;
        this.weekday = weekday;
        this.course_id = course_id;
        this.course_name = course_name;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public int getHomeworks_percent_of_total_grade() {
        return homeworks_percent_of_total_grade;
    }

    public void setHomeworks_percent_of_total_grade(int homeworks_percent_of_total_grade) {
        this.homeworks_percent_of_total_grade = homeworks_percent_of_total_grade;
    }

    public int getAttendances_percent_of_total_grade() {
        return attendances_percent_of_total_grade;
    }

    public void setAttendances_percent_of_total_grade(int attendances_percent_of_total_grade) {
        this.attendances_percent_of_total_grade = attendances_percent_of_total_grade;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getLates_deduce_point() {
        return lates_deduce_point;
    }

    public void setLates_deduce_point(int lates_deduce_point) {
        this.lates_deduce_point = lates_deduce_point;
    }

    public int getAbsences_deduce_point() {
        return absences_deduce_point;
    }

    public void setAbsences_deduce_point(int absences_deduce_point) {
        this.absences_deduce_point = absences_deduce_point;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
