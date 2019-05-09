package ujs.mlearn.entity;

public class Test {
	public static final int PAN_DUAN = 0;
	public static final int DAN_XUAN = 1;
	public static final int DUO_XUAN = 2;

	private int testID;
	private int courseID;
	private String teacherNumber;
	private String testContent;
	private int type;
	private String testAnswer;
	private String testOption;

	private String teacherName;
	private String courseName;

	public Test(int testID, int courseID, String teacherNumber, String testContent, int type, String testAnswer,
			String testOption) {
		super();
		this.testID = testID;
		this.courseID = courseID;
		this.teacherNumber = teacherNumber;
		this.testContent = testContent;
		this.type = type;
		this.testAnswer = testAnswer;
		this.testOption = testOption;
	}

	public Test() {
		super();
	}

	public int getTestID() {
		return testID;
	}

	public void setTestID(int testID) {
		this.testID = testID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getTestContent() {
		return testContent;
	}

	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}

	public String getTestAnswer() {
		return testAnswer;
	}

	public void setTestAnswer(String testAnswer) {
		this.testAnswer = testAnswer;
	}

	public String getTestOption() {
		return testOption;
	}

	public void setTestOption(String testOption) {
		this.testOption = testOption;
	}

	public String getTeacherNumber() {
		return teacherNumber;
	}

	public void setTeacherNumber(String teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	public int getType() {
		return type;
	}

	public String getTypeString() {
		if (type == Test.PAN_DUAN) {
			return "判断题";
		} else if (type == Test.DAN_XUAN) {
			return "单选题";
		} else {
			return "多选题";
		}
	}

	public void setType(int type) {
		this.type = type;
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
		return "Test [testID=" + testID + ", courseID=" + courseID + ", teacherNumber=" + teacherNumber
				+ ", testContent=" + testContent + ", type=" + getType() + ", testAnswer=" + testAnswer
				+ ", testOption=" + testOption + "]";
	}

}
