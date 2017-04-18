package com.example.yannis.dianming.bean;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable{


	/**
	 * absences_deduce_point : 2
	 * vacates_deduce_point : 1
	 * lates_deduce_point : 20
	 * section : ["3,4"]
	 * classroom : ["第12教研楼402"]
	 * course_number : A1204260
	 * attendances_percent_of_total_grade : 31
	 * course_name : 校对学
	 * course_id : 44
	 * weekday : ["4"]
	 */

	private int absences_deduce_point;
	private int vacates_deduce_point;
	private int lates_deduce_point;
	private String course_number;
	private int attendances_percent_of_total_grade;
	private String course_name;
	private int course_id;
	private List<String> section;
	private List<String> classroom;
	private List<String> weekday;

	public int getAbsences_deduce_point() {
		return absences_deduce_point;
	}

	public void setAbsences_deduce_point(int absences_deduce_point) {
		this.absences_deduce_point = absences_deduce_point;
	}

	public int getVacates_deduce_point() {
		return vacates_deduce_point;
	}

	public void setVacates_deduce_point(int vacates_deduce_point) {
		this.vacates_deduce_point = vacates_deduce_point;
	}

	public int getLates_deduce_point() {
		return lates_deduce_point;
	}

	public void setLates_deduce_point(int lates_deduce_point) {
		this.lates_deduce_point = lates_deduce_point;
	}

	public String getCourse_number() {
		return course_number;
	}

	public void setCourse_number(String course_number) {
		this.course_number = course_number;
	}

	public int getAttendances_percent_of_total_grade() {
		return attendances_percent_of_total_grade;
	}

	public void setAttendances_percent_of_total_grade(int attendances_percent_of_total_grade) {
		this.attendances_percent_of_total_grade = attendances_percent_of_total_grade;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public List<String> getSection() {
		return section;
	}

	public void setSection(List<String> section) {
		this.section = section;
	}

	public List<String> getClassroom() {
		return classroom;
	}

	public void setClassroom(List<String> classroom) {
		this.classroom = classroom;
	}

	public List<String> getWeekday() {
		return weekday;
	}

	public void setWeekday(List<String> weekday) {
		this.weekday = weekday;
	}
}
