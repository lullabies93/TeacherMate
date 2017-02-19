package com.example.yannis.dianming.bean;

import com.example.yannis.dianming.bean.Record;

import java.util.ArrayList;

import org.json.JSONArray;

public class StudentRecord {
	
	private String classNo, major, studentNo, name;
	private int studentId;
    private ArrayList<Record> records;
	public StudentRecord(String classNo, String major, String studentNo, String name, int studentId,
			ArrayList<Record> records) {
		super();
		this.classNo = classNo;
		this.major = major;
		this.studentNo = studentNo;
		this.name = name;
		this.studentId = studentId;
		this.records = records;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public ArrayList<Record> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<Record> records) {
		this.records = records;
	}
	
    
    
}
