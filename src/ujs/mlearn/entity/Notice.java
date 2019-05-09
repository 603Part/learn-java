package ujs.mlearn.entity;

public class Notice {
	public static final int CLASS = 0;
	public static final int ALL = 1;

	private int noticeID;
	private int courseID;
	private String teacherNumber;
	private String noticeTitle;
	private String noticeContent;
	private String noticeTime;
	private int type;

	private String courseName;
	private String teacherName;

	public Notice(int noticeID, String teacherNumber, int courseID, String noticeTitle, String noticeContent,
			String noticeTime) {
		super();
		this.noticeID = noticeID;
		this.courseID = courseID;
		this.teacherNumber = teacherNumber;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeTime = noticeTime;
	}

	public Notice(int noticeID, String teacherNumber, int courseID, String noticeTitle, String noticeContent,
			String noticeTime, int type) {
		super();
		this.noticeID = noticeID;
		this.courseID = courseID;
		this.teacherNumber = teacherNumber;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeTime = noticeTime;
		this.type = type;
	}

	public Notice() {
		super();
	}

	public int getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(int noticeID) {
		this.noticeID = noticeID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeString() {
		if (this.type == 0) {
			return "课程通知";
		} else {
			return "全体通知";
		}
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherNumber() {
		return teacherNumber;
	}

	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

}
