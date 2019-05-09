package ujs.mlearn.entity;

public class BbsTheme {
	private int postID = 0;// 帖子id,默认为0，也不允许用户修改
	private int courseID; // 课程id
	private String studentNumber; // 学生学号，可以用于加载用户头像
	private String postTitle;// 帖子内容
	private String postContent;// 帖子内容
	private String postTime;// 发帖时间
	private String replyTime;// 最后回复时间，非用户填写
	private int state = 0;// 状态，0表示老师未回复，1表示老师已回复

	private int replyCount = 0;// 回复数量，默认为0
	
	private String studentPhotoURL;
	private String studentName;

	public BbsTheme() {
		super();
	}

	public BbsTheme(int postID, int courseID, String studentNumber, String postTitle,String postContent, String postTime,
			String replyTime, int state) {
		super();
		this.postID = postID;
		this.courseID = courseID;
		this.studentNumber = studentNumber;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postTime = postTime;
		this.replyTime = replyTime;
		this.state = state;
	}

	public BbsTheme(int postID, int courseID, String studentNumber, String postContent, String postTime,
			String replyTime, int state, int replyCount) {
		super();
		this.postID = postID;
		this.courseID = courseID;
		this.studentNumber = studentNumber;
		this.postContent = postContent;
		this.postTime = postTime;
		this.replyTime = replyTime;
		this.state = state;

	//	this.studentName = studentName;
		this.replyCount = replyCount;
	}

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	
	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentPhotoURL() {
		return studentPhotoURL;
	}

	public void setStudentPhotoURL(String studentPhotoURL) {
		this.studentPhotoURL = studentPhotoURL;
	}

	@Override
	public String toString() {
		return "BbsTheme [postID=" + postID + ", studentNumber=" + studentNumber + ", studentName=" + studentName
				+ ", courseID=" + courseID + ", postContent=" + postContent + ", replyCount=" + replyCount
				+ ", postTime=" + postTime + ", replyTime=" + replyTime + ", state=" + state + "]";
	}

}
