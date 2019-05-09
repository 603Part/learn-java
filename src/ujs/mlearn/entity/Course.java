package ujs.mlearn.entity;

public class Course {

	private int courseID;
	private String courseName;
	private String teacherNumber;
	private String courseUrl;
	private String courseAbstract;
	private String detailInfo;

	private String teacherName;
	private int coursePostCount;

	public Course() {
	}

	public Course(int courseID, String courseName, String teacherNumber, String courseAbstract, String detailInfo) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.teacherNumber = teacherNumber;
		this.courseAbstract = courseAbstract;
		this.detailInfo = detailInfo;
	}

	public Course(int courseID, String courseName, String teacherNumber, String courseAbstract, String detailInfo,
			String courseUrl) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.teacherNumber = teacherNumber;
		this.courseAbstract = courseAbstract;
		this.detailInfo = detailInfo;
		this.courseUrl = courseUrl;
	}

	public Course(int courseID, String courseName, String teacherName, String teacherNumber, String courseAbstract,
			String detailInfo, String courseUrl) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.teacherNumber = teacherNumber;
		this.courseAbstract = courseAbstract;
		this.detailInfo = detailInfo;
		this.courseUrl = courseUrl;
	}

	public String getTeacherNumber() {
		return teacherNumber;
	}

	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getCourseUrl() {
		return courseUrl;
	}

	public void setCourseUrl(String courseUrl) {
		this.courseUrl = courseUrl;
	}

	public String getCourseAbstract() {
		return courseAbstract;
	}

	public void setCourseAbstract(String courseAbstract) {
		this.courseAbstract = courseAbstract;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getCoursePostCount() {
		return coursePostCount;
	}

	public void setCoursePostCount(int coursePostCount) {
		this.coursePostCount = coursePostCount;
	}

	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", courseName=" + courseName + ", teacherName=" + teacherName
				+ ", courseUrl=" + courseUrl + ", courseAbstract=" + courseAbstract + ", detailInfo=" + detailInfo
				+ "coursePostCount="+coursePostCount+"]";
	}

	public String getLogintime() {
		return null;
	}

}
