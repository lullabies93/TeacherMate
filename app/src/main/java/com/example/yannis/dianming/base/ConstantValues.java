package com.example.yannis.dianming.base;

/**
 * Created by yannis on 2016/12/15.
 */

public class ConstantValues {
    public static String COOKIE = "cookie";
    public static String FIRST_LAUNCH = "first_launch";
    public static String PREFENCE_NAME = "dianming";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String TEACHER_NO = "teacherNo";
    public static String GROUPNAME = "groupName";
    public static String courseID = "selected_courses__id";
    public static String remark = "remark";
    public static String absencePunish = "absences_deduce_point";
    public static String latePunish = "lates_deduce_point";
    public static String leavePunish = "vacates_deduce_point";
    public static String signPercent = "attendances_percent_of_total_grade";
    public static String homeworkPercent = "homeworks_percent_of_total_grade";
    public static String studentInfo = "studentInfo";
    public static String homeworkClassId = "homework_class_id";

    public static String captcha_file_name = "captcha_file_name";



    public static String teacherID = "teacher_id";
    public static String teacherName = "teacher_name";
    public static String teacherGroup = "group";
    public static String signName = "name";
    public static String courseName = "name";
    public static String homeworkName = "name";
    public static String week = "week";
    public static String weekday = "weekday";
    public static String courseId = "course_id";
    public static String status = "status";
    public static String recordId = "attendance_record_id";
    public static String ret_recordId = "attendances_record_id";
    public static String sectionLength = "section_length";
    public static String dateFormat = "yyyy-MM-dd";
    public static String sign_result = "attendances_results";
    public static String resultId = "attendance_result_id";
    public static String studentId = "student_id";
    public static String courseInfo = "courseInfo";
    public static String homeworkRecordId = "homework_record_id";
    public static String percent_of_total_grade = "percent_of_total_grade";
    public static String homeworkItem = "homeworkItem";
    public static String gradeId = "grade_id";
    public static String score = "score";
    public static String submit = "提交";
    public static String delete = "删除";
    public static String assistantId = "assistant_id";
    public static String registerUser = "register_user";
    public static String classNumber = "class_number";


    public static String URL = "http://115.29.50.42/demo/";
    public static String ABOUT = "http://115.29.50.42:8080/about.html";

    public static String LOGIN = URL + "log_in/";//登陆
    public static String GET_CODE = "http://115.29.50.42/captcha/";//登陆
    public static String GET_CAPTCHA = URL + "supplment/update_data/";//登陆
    public static String GET_ALL_COURSE = URL + "courses/";//获取所有课程
    //public static String GET_COURSE = URL + "course_names/";
    public static String GET_STUDENT_IN_COURSE = URL + "students/";//获取一门课所有的学生
    public static String GET_RECORD_ID = URL + "attendances_records/";//申请发起一次点名，获取id/获取点名记录
    public static String GET_WEEK = URL + "supplment/week/";//获取周数
    public static String POST_SIGN_RESULT = URL + "attendances_results/";//提交点名结果
    public static String GET_RESULT_BY_RECORD_ID = URL + "attendances_results/";//获取一次点名结果
    public static String GET_HOMEWORK_RECORDS = URL + "homeworks_records/";//获取所有作业记录ID
    public static String GET_HOMEWORK_GRADES = URL + "grades/";//获取一次作业的分数
    public static String GET_ASSISTANT = URL + "assistants/";//获取子账号
    public static String GET_HOMEWORK_CLASSES = URL + "homeworks_classes/";//获取所有作业类型
//
    public static String GET_CLASS = URL + "supplment/class_number/";//获取班级




}
