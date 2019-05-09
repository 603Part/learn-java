package ujs.mlearn.entity;

public class Homework {
	private int hwID;
	private int courseID;
	private String teacherNumber;
	private String hwContent;
	private String publishTime;
	private String hwTitle;

	private String teacherName;
	private String courseName;

	public Homework() {
		super();
	}

	public Homework(int hwID, int courseID, String teacherNumber, String hwContent, String publishTime,
			String hwTitle) {
		super();
		this.hwID = hwID;
		this.courseID = courseID;
		this.teacherNumber = teacherNumber;
		this.hwContent = hwContent;
		this.publishTime = publishTime;
		this.hwTitle = hwTitle;
	}

	public int getHwID() {
		return hwID;
	}

	public void setHwID(int hwID) {
		this.hwID = hwID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getTeacherNumber() {
		return teacherNumber;
	}

	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	public String getHwContent() {
		return hwContent;
	}

	public void setHwContent(String hwContent) {
		this.hwContent = hwContent;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getHwTitle() {
		return hwTitle;
	}

	public void setHwTitle(String hwTitle) {
		this.hwTitle = hwTitle;
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
		return "Homework [hwID=" + hwID + ", courseID=" + courseID + ", teacherNumber=" + teacherNumber + ", hwContent="
				+ hwContent + ", publishTime=" + publishTime + ", hwTitle=" + hwTitle + "]";
	}

}
