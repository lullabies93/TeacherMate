package com.example.yannis.dianming.bean;

public class CourseOfPercent {
	
	private String homeworkper, courseName, lessonPeriod;
	private int week, deduct_points, courseId;
	public CourseOfPercent(String homeworkper, String courseName, String lessonPeriod, int week, int deduct_points,
			int courseId) {
		super();
		this.homeworkper = homeworkper;
		this.courseName = courseName;
		this.lessonPeriod = lessonPeriod;
		this.week = week;
		this.deduct_points = deduct_points;
		this.courseId = courseId;
	}
	public String getHomeworkper() {
		return homeworkper;
	}
	public void setHomeworkper(String homeworkper) {
		this.homeworkper = homeworkper;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getLessonPeriod() {
		return lessonPeriod;
	}
	public void setLessonPeriod(String lessonPeriod) {
		this.lessonPeriod = lessonPeriod;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getDeduct_points() {
		return deduct_points;
	}
	public void setDeduct_points(int deduct_points) {
		this.deduct_points = deduct_points;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	

}
