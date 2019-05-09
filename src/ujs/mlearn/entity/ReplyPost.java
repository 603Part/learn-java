package ujs.mlearn.entity;

public class ReplyPost {
	public static int TEACHER = 1;
	public static int STUDENT = 0;
	
	private int replyID;
	private int postID;
	private String userNumber;
	private String replyContent;
	private String replyTime;
	private int userType;// 用户身份0表示学生，1表示老师
	private int starNum = 0;// 赞数，默认为0

	private String userName;
	private String userPhotoURL;
	private String userTypeString;

	public ReplyPost() {
		super();
	}

	public ReplyPost(int replyID, int postID, String userNumber, String replyContent, String replyTime, int userType, int starNum) 
	{
		super();
		this.replyID = replyID;
		this.postID = postID;
		this.userNumber =userNumber;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
		this.userType = userType;
		this.starNum = starNum;
	}
	public int getReplyID() {
		return replyID;
	}

	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}
	
	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
		if(userType == TEACHER) {
			userTypeString = "教师";
		} else {
			userTypeString = "学生";
		}
	}

	public String getUserTypeString() {
		return userTypeString;
	}

	public void setUserTypeString(String userTypeString) {
		this.userTypeString = userTypeString;
	}

	public int getStarNum() {
		return starNum;
	}

	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhotoURL() {
		return userPhotoURL;
	}

	public void setUserPhotoURL(String userPhotoURL) {
		this.userPhotoURL = userPhotoURL;
	}

	@Override
	public String toString() {
		return "ReplyPost [replyID=" + replyID + ", postID=" + postID + ", userNumber=" + userNumber + ", replyContent="
				+ replyContent + ", replyTime=" + replyTime + ", userStatus=" + userType + ", starNum=" + starNum
				+ ", ]";
	}

}
