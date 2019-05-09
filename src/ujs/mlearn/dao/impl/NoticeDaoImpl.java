package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.NoticeDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Notice;

public class NoticeDaoImpl implements NoticeDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void addNotice(Notice notice) {
		String sql = "insert into notice (noticeTitle,noticeContent,teacherNumber,courseID,noticeTime,type) values (?,?,?,?,?,?)";
		Object[] params = { notice.getNoticeTitle(), notice.getNoticeContent(), notice.getTeacherNumber(),
				notice.getCourseID(), notice.getNoticeTime(), notice.getType() };
		try {
			runner.update(sql, params);
			System.out.println("添加公告成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delNotice(int noticID) {
		String sql = "delete from notice where noticeID = ?";

		try {
			runner.update(sql, noticID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateNotice(Notice notice) {
		String sql = "update notice set noticeTitle = ?,noticeContent = ?, noticeTime = ?, type=?, teacherNumber = ?, courseID= ?  where noticeID = ?";
		Object[] params = { notice.getNoticeTitle(), notice.getNoticeContent(), notice.getNoticeTime(),
				notice.getType(), notice.getTeacherNumber(), notice.getCourseID(), notice.getNoticeID() };
		try {
			runner.update(sql, params);
			System.out.println("编辑成功！");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Notice findNoticeByID(int noticeID) {
		String sql = "select * from notice where noticeID = ?";

		try {
			Notice notice = runner.query(sql, new BeanHandler<Notice>(Notice.class), noticeID);
			return notice;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> findAllNotice() {
		String sql = "select * from notice";
		try {
			List<Notice> notices = runner.query(sql, new BeanListHandler<Notice>(Notice.class));
			return notices;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> findMyNotice(int teacherID) {
		String sql = "select * from notice where teacherID=? and type = 0";
		Object[] params = { teacherID };
		try {
			List<Notice> notics = runner.query(sql, new BeanListHandler<Notice>(Notice.class), params);
			return notics;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> findMyNotice(String teacherNumber) {
		String sql = "select * from notice where teacherNumber=?  and type = 0";
		Object[] params = { teacherNumber };
		try {
			List<Notice> notics = runner.query(sql, new BeanListHandler<Notice>(Notice.class), params);
			return notics;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> findCourseNotice(int courseID) {
		String sql = "select * from notice where courseID=? order by noticeTime desc";
		try {
			List<Notice> notics = runner.query(sql, new BeanListHandler<Notice>(Notice.class), courseID);
			return notics;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Notice> findAdminNotice() {
		String sql = "select * from notice where type=? order by noticeTime desc";
		try {
			List<Notice> notics = runner.query(sql, new BeanListHandler<Notice>(Notice.class), Notice.ALL);
			return notics;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
