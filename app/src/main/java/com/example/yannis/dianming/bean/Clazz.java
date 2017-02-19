package com.example.yannis.dianming.bean;

import java.util.ArrayList;

public class Clazz {
	
	private String classNo;
	private int classId;
	private ArrayList<StudentRecord> students;
	public Clazz(String classNo, int classId, ArrayList<StudentRecord> students) {
		super();
		this.classNo = classNo;
		this.classId = classId;
		this.students = students;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public ArrayList<StudentRecord> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<StudentRecord> students) {
		this.students = students;
	}
	

}
