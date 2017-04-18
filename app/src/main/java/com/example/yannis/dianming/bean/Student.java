package com.example.yannis.dianming.bean;

import java.io.Serializable;

public class Student implements Serializable{


	/**
	 * student_id : 839
	 * class_number : 15123911
	 * student_number : 14120103
	 * major : 编辑出版学
	 * student_name : 陈雅莉
	 */

	private int student_id;
	private String class_number;
	private String student_number;
	private String major;
	private String student_name;

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getClass_number() {
		return class_number;
	}

	public void setClass_number(String class_number) {
		this.class_number = class_number;
	}

	public String getStudent_number() {
		return student_number;
	}

	public void setStudent_number(String student_number) {
		this.student_number = student_number;
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
}
