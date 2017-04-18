package com.example.yannis.dianming.bean;

import java.io.Serializable;

/**
 * Created by yannis on 2017/2/21.
 */

public class StudentSignDetail implements Serializable{

    /**
     * major : 编辑出版学
     * student_id : 839
     * attendance_detail : {"vacate_count":1,"late_count":2,"absence_count":8}
     * student_number : 14120103
     * class_number : 15123911
     * student_name : 陈雅莉
     */

    private String major;
    private int student_id;
    private AttendanceDetailBean attendance_detail;
    private String student_number;
    private String class_number;
    private String student_name;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public AttendanceDetailBean getAttendance_detail() {
        return attendance_detail;
    }

    public void setAttendance_detail(AttendanceDetailBean attendance_detail) {
        this.attendance_detail = attendance_detail;
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

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public static class AttendanceDetailBean implements Serializable{
        /**
         * vacate_count : 1
         * late_count : 2
         * absence_count : 8
         */

        private int vacate_count;
        private int late_count;
        private int absence_count;

        public int getVacate_count() {
            return vacate_count;
        }

        public void setVacate_count(int vacate_count) {
            this.vacate_count = vacate_count;
        }

        public int getLate_count() {
            return late_count;
        }

        public void setLate_count(int late_count) {
            this.late_count = late_count;
        }

        public int getAbsence_count() {
            return absence_count;
        }

        public void setAbsence_count(int absence_count) {
            this.absence_count = absence_count;
        }
    }
}
