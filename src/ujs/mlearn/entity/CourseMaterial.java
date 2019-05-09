package ujs.mlearn.entity;

import java.sql.Time;

public class CourseMaterial {
	private int resID;
	private int courseID;
	private String teacherNumber;
	private String publishTime;
	private String resTitle;
	private String resUrl;
	private long size;

	private String teacherName;
	private String courseName;

	public CourseMaterial() {
		super();
	}

	public CourseMaterial(int resID, int courseID, String publishTime, String resTitle, String resUrl,
			String teacherNumber, long size) {
		super();
		this.resID = resID;
		this.courseID = courseID;
		this.resTitle = resTitle;
		this.resUrl = resUrl;
		this.teacherNumber = teacherNumber;
		this.size = size;
		if (publishTime == null || publishTime.equals("")) {
			long currentTimeMillis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(currentTimeMillis);
			Time time = new Time(currentTimeMillis);
			this.publishTime = date.toString() + " " + time.toString();
		} else {
			this.publishTime = publishTime;
		}
	}

	public int getResID() {
		return resID;
	}

	public void setResID(int resID) {
		this.resID = resID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		if (publishTime == null || publishTime.equals("")) {
			long currentTimeMillis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(currentTimeMillis);
			Time time = new Time(currentTimeMillis);
			this.publishTime = date.toString() + " " + time.toString();
		} else {
			this.publishTime = publishTime;
		}
	}

	public String getResTitle() {
		return resTitle;
	}

	public void setResTitle(String resTitle) {
		this.resTitle = resTitle;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getTeacherNumber() {
		return teacherNumber;
	}

	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "CourseMaterial [resID=" + resID + ", courseID=" + courseID + ", publishTime=" + publishTime
				+ ", resTitle=" + resTitle + ", resUrl=" + resUrl + ", teacherNumber=" + teacherNumber + "]";
	}

}
