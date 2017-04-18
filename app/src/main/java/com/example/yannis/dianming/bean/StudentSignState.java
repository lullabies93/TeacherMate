package com.example.yannis.dianming.bean;

import java.io.Serializable;

public class StudentSignState implements Serializable{


	/**
	 * attendances_record_id : 126
	 * student_id : 856
	 * course_number : A1204260
	 * course_name : 校对学
	 * student_number : 15123920
	 * student_name : 赵露露
	 * create_time : 2017-02-14T21:14:12.901
	 * attendance_result_id : 284
	 * course_id : 44
	 * status : 0
	 */

	private int attendances_record_id;
	private int student_id;
	private String course_number;
	private String course_name;
	private String student_number;
	private String student_name;
	private String create_time;
	private int attendance_result_id;
	private int course_id;
	private int status;

	public StudentSignState(int student_id, String student_number, String student_name) {
		this.student_id = student_id;
		this.student_number = student_number;
		this.student_name = student_name;
	}

	public int getAttendances_record_id() {
		return attendances_record_id;
	}

	public void setAttendances_record_id(int attendances_record_id) {
		this.attendances_record_id = attendances_record_id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getCourse_number() {
		return course_number;
	}

	public void setCourse_number(String course_number) {
		this.course_number = course_number;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getStudent_number() {
		return student_number;
	}

	public void setStudent_number(String student_number) {
		this.student_number = student_number;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getAttendance_result_id() {
		return attendance_result_id;
	}

	public void setAttendance_result_id(int attendance_result_id) {
		this.attendance_result_id = attendance_result_id;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
