package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Notice;

public interface NoticeDao {

	public void addNotice(Notice notice);

	public void delNotice(int noticeID);

	public void updateNotice(Notice notice);

	public List<Notice> findAllNotice();

	public List<Notice> findMyNotice(int teacherID);

	public List<Notice> findMyNotice(String teacherNumber);

	public List<Notice> findCourseNotice(int courseID);

	public Notice findNoticeByID(int noticeid);

	List<Notice> findAdminNotice();
}
