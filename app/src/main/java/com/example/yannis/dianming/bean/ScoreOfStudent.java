package com.example.dianming.bean;

import java.io.Serializable;

public class ScoreOfStudent implements Serializable{
	
	private String studentName, studentNo;
	private int score, studentId;
	public ScoreOfStudent(String studentName, String studentNo, int score, int studentId) {
		super();
		this.studentName = studentName;
		this.studentNo = studentNo;
		this.score = score;
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	

}
