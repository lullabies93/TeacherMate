package com.example.yannis.dianming.bean;

public class Record {


	/**
	 * name : 2017年01月15日
	 * section_length : 2
	 * attendance_record_id : 50
	 * week : 3
	 * weekday : 1
	 */

	private String name;
	private int section_length;
	private int attendance_record_id;
	private int week;
	private int weekday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSection_length() {
		return section_length;
	}

	public void setSection_length(int section_length) {
		this.section_length = section_length;
	}

	public int getAttendance_record_id() {
		return attendance_record_id;
	}

	public void setAttendance_record_id(int attendance_record_id) {
		this.attendance_record_id = attendance_record_id;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}
}
