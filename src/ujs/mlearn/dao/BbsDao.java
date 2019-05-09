package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.BbsTheme;

public interface BbsDao {

	public List<BbsTheme> findNoteByCourseID(int courseID);

	public BbsTheme findByID(int postID);

	public int findCoursePostCount(int courseID);

	public void addPost(BbsTheme bbsTheme);

	public void stateChange(int flag, int postID);

	public void delPost(int postID);

}
