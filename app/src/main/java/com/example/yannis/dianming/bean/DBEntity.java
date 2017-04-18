package com.example.yannis.dianming.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yannis on 2017/2/21.
 */
@Table(name = "pushList")
public class DBEntity extends Model {
    @Column(name = "student_id")
    private int student_id;
    @Column(name = "student_number")
    private String student_number;
    @Column(name = "class_number")
    private String class_number;
    @Column(name = "exception_times")
    private int exception_times;
    @Column(name = "major")
    private String major;
    @Column(name = "student_name")
    private String student_name;
    @Column(name = "teacher_id")
    private int teacher_id;
    @Column(name = "solved")
    private boolean solved;

    public DBEntity() {
        super();
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    public int getException_times() {
        return exception_times;
    }

    public void setException_times(int exception_times) {
        this.exception_times = exception_times;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public DBEntity(int student_id, String student_number, String class_number, int exception_times, String
            major, String student_name, int teacher_id) {
        super();
        this.student_id = student_id;
        this.student_number = student_number;
        this.class_number = class_number;
        this.exception_times = exception_times;
        this.major = major;
        this.student_name = student_name;
        this.teacher_id = teacher_id;
        this.solved = false;
    }
}
