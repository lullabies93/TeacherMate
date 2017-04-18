package com.example.yannis.dianming.bean;

public class Record {


	/**
	 * course_name : 校对学
	 * attendance_record_id : 126
	 * section_length : 2
	 * course_number : A1204260
	 * name : 2017/2/14 - 第0周
	 * weekday : 0
	 * week : 0
	 * course_id : 44
	 */

	private String course_name;
	private int attendance_record_id;
	private int section_length;
	private String course_number;
	private String name;
	private int weekday;
	private int week;
	private int course_id;

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public int getAttendance_record_id() {
		return attendance_record_id;
	}

	public void setAttendance_record_id(int attendance_record_id) {
		this.attendance_record_id = attendance_record_id;
	}

	public int getSection_length() {
		return section_length;
	}

	public void setSection_length(int section_length) {
		this.section_length = section_length;
	}

	public String getCourse_number() {
		return course_number;
	}

	public void setCourse_number(String course_number) {
		this.course_number = course_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
}
