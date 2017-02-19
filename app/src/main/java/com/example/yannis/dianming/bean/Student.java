package com.example.yannis.dianming.bean;

import java.io.Serializable;

public class Student implements Serializable{


	/**
	 * class_number : 12052316
	 * major_number : 0523
	 * student_name : 赵健
	 * student_number : 12062136
	 * student_id : 1
	 */

	private String class_number;
	private String major_number;
	private String student_name;
	private String student_number;
	private int student_id;
	private int status = 0;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getClass_number() {
		return class_number;
	}

	public void setClass_number(String class_number) {
		this.class_number = class_number;
	}

	public String getMajor_number() {
		return major_number;
	}

	public void setMajor_number(String major_number) {
		this.major_number = major_number;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getStudent_number() {
		return student_number;
	}

	public void setStudent_number(String student_number) {
		this.student_number = student_number;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
}
