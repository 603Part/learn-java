package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.ReplyPost;

public interface ReplyDao {
	public void addReply(ReplyPost replyPost);

	public List<ReplyPost> findTeacherReply(String teacherNumber);

	public List<ReplyPost> findMyReply(int studentID);

	public List<ReplyPost> findPostReply(int postID);
	
	public List<ReplyPost> findReplyUserNumber(int postID) ;

	public ReplyPost findOneTeacherReply(String teacherNumber, int postID);

	public void updateTeacherReply(String replyContent, int replyID);

	public void deleteRep(int replyID);

	public int findPostReplyCount(int postID);

	void deleteThemeAllRep(int postID);
}
