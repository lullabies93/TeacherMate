package com.example.dianming.bean;

import org.json.JSONArray;

public class StudentHistory {
    private String major, courseId, name, studentNo, classNo;
    private int leave, absence,late, appear, studentId;
    private JSONArray absenceArray, lateArray, leaveArray;
	public StudentHistory(String major, String courseId, String name, String studentNo, String classNo, int leave,
			int absence, int late, int appear, int studentId, JSONArray absenceArray, JSONArray lateArray, JSONArray leaveArray) {
		super();
		this.major = major;
		this.courseId = courseId;
		this.name = name;
		this.studentNo = studentNo;
		this.classNo = classNo;
		this.leave = leave;
		this.absence = absence;
		this.late = late;
		this.appear = appear;
		this.absenceArray = absenceArray;
		this.lateArray = lateArray;
		this.leaveArray = leaveArray;
		this.studentId = studentId;
	}
	
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public int getLeave() {
		return leave;
	}
	public void setLeave(int leave) {
		this.leave = leave;
	}
	public int getAbsence() {
		return absence;
	}
	public void setAbsence(int absence) {
		this.absence = absence;
	}
	public int getLate() {
		return late;
	}
	public void setLate(int late) {
		this.late = late;
	}
	public int getAppear() {
		return appear;
	}
	public void setAppear(int appear) {
		this.appear = appear;
	}
	public JSONArray getAbsenceArray() {
		return absenceArray;
	}
	public void setAbsenceArray(JSONArray absenceArray) {
		this.absenceArray = absenceArray;
	}
	public JSONArray getLateArray() {
		return lateArray;
	}
	public void setLateArray(JSONArray lateArray) {
		this.lateArray = lateArray;
	}
	public JSONArray getLeaveArray() {
		return leaveArray;
	}
	public void setLeaveArray(JSONArray leaveArray) {
		this.leaveArray = leaveArray;
	}
    
    
}
