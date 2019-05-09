package ujs.mlearn.entity;

public class StudentCourse {
	private int relationID;
	private int courseID;
	private int studentID;
	private int studentGrade;
	private String studentAnswer;
	private int testNumber;

	public StudentCourse() {
		super();

	}

	public StudentCourse(int relationID, int courseID, int studentID, int studentGrade, String studentAnswer) {
		super();
		this.relationID = relationID;
		this.courseID = courseID;
		this.studentID = studentID;
		this.studentGrade = studentGrade;
		this.studentAnswer = studentAnswer;
	}

	public int getRelationID() {
		return relationID;
	}

	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public int getStudentGrade() {
		return studentGrade;
	}

	public void setStudentGrade(int studentGrade) {
		this.studentGrade = studentGrade;
	}

	public String getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public int getTestNumber() {
		return testNumber;
	}

	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}

	@Override
	public String toString() {
		return "StudentCourse [relationID=" + relationID + ", courseID=" + courseID + ", studentID=" + studentID
				+ ", studentGrade=" + studentGrade + ", studentAnswer=" + studentAnswer + "]";
	}

}
