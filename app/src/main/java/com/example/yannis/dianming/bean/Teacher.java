package com.example.yannis.dianming.bean;

import java.util.ArrayList;

public class Teacher {
	
	private String username, groupName, teacherNo, cookie;
	private ArrayList<Course> courses;
	
	public Teacher(String username, String groupName, String teacherNo, String cookie, ArrayList<Course> courses) {
		super();
		this.username = username;
		this.groupName = groupName;
		this.teacherNo = teacherNo;
		this.cookie = cookie;
		this.courses = courses;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getTeacherNo() {
		return teacherNo;
	}
	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public ArrayList<Course> getCourses() {
		return courses;
	}
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	

}
