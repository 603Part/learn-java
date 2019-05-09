package ujs.mlearn.entity;

public class StuHomework {
	private int shwID;
	private int courseID;
	private int hwID;
	private String subTime;
	private String hwUrl;
	private String stuWorkTitle;
	private int userID;
	private long size;
	
	private String courseName;
	private String hwTitle;
	private String studentName;
	private String sid;

	public StuHomework() {
		super();
	}

	public StuHomework(int shwID, int courseID, int hwID, String subTime, String hwUrl, String stuWorkTitle, int userID,
			long size) {
		super();
		this.shwID = shwID;
		this.courseID = courseID;
		this.hwID = hwID;
		this.subTime = subTime;
		this.hwUrl = hwUrl;
		this.stuWorkTitle = stuWorkTitle;
		this.userID = userID;
		this.size = size;
	}

	public int getShwID() {
		return shwID;
	}

	public void setShwID(int shwID) {
		this.shwID = shwID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public int getHwID() {
		return hwID;
	}

	public void setHwID(int hwID) {
		this.hwID = hwID;
	}

	public String getSubTime() {
		return subTime;
	}

	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}

	public String getHwUrl() {
		return hwUrl;
	}

	public void setHwUrl(String hwUrl) {
		this.hwUrl = hwUrl;
	}

	public String getStuWorkTitle() {
		return stuWorkTitle;
	}

	public void setStuWorkTitle(String stuWorkTitle) {
		this.stuWorkTitle = stuWorkTitle;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getHwTitle() {
		return hwTitle;
	}

	public void setHwTitle(String hwTitle) {
		this.hwTitle = hwTitle;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return "StuHomework [shwID=" + shwID + ", courseID=" + courseID + ", hwID=" + hwID + ", subTime=" + subTime
				+ ", hwUrl=" + hwUrl + ", stuWorkTitle=" + stuWorkTitle + ", userID =" + userID + ", studentName =" + studentName + ", size="+size+"]";
	}

}
