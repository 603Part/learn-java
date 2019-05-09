package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.StuHomeworkDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.StuHomework;

public class StuHomeworkDaoImpl implements StuHomeworkDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public List<StuHomework> findStuHwByHwID(int hwID) {
		String sql = "select shwID,hwID,subTime,hwUrl,stuWorkTitle,userID,name,sid from stuhomework,student where hwID=? "
				+ "and stuhomework.studentID=student.userID";
		try {
			List<StuHomework> sList = runner.query(sql, new BeanListHandler<>(StuHomework.class), hwID);
			for (StuHomework stuHomework : sList) {
				stuHomework.setSubTime(stuHomework.getSubTime().substring(0, 19));
			}
			return sList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void uploadHomework(StuHomework sHomework) {
		String sql = "insert into stuhomework(hwID,subTime,hwUrl,stuWorkTitle,studentID,size) values(?,?,?,?,?,?)";
		Object[] params = { sHomework.getHwID(), sHomework.getSubTime(), sHomework.getHwUrl(),
				sHomework.getStuWorkTitle(), sHomework.getUserID(), sHomework.getSize() };
		try {
			runner.update(sql, params);
			System.out.println("上传作业成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public StuHomework findOneMyHomework(int shwID) {
		String sql = "select * from stuhomework where shwID=?";
		try {
			StuHomework sHomework = runner.query(sql, new BeanHandler<>(StuHomework.class), shwID);
			if(sHomework != null) {
				sHomework.setSubTime(sHomework.getSubTime().substring(0, 19));
			}
			return sHomework;
		} catch (Exception e) {
		}
		return null;
	}
	
	@Override
	public List<StuHomework> findMyCourseHomework(int userID, int courseID) {
		String sql = "select s.* from stuhomework s,homework h where s.studentID=? and s.hwID=h.hwID and h.courseID=?";
		try {
			List<StuHomework> sHomeworks = runner.query(sql, new BeanListHandler<>(StuHomework.class), userID, courseID);
			for (StuHomework stuHomework : sHomeworks) {
				stuHomework.setSubTime(stuHomework.getSubTime().substring(0, 19));
			}
			return sHomeworks;
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public StuHomework findHomework(int hwID, int studentID) {
		String sql = "select * from stuhomework where hwID=? and studentID=?";
		Object[] params = { hwID, studentID };
		try {
			StuHomework sHomework = runner.query(sql, new BeanHandler<>(StuHomework.class), params);
			if(sHomework != null) {
				sHomework.setSubTime(sHomework.getSubTime().substring(0, 19));
			}
			return sHomework;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateHomework(StuHomework sHomework, int shwID) {
		String sql = "update stuhomework set hwUrl=?,stuWorkTitle=?,subTime=?, size=? where shwID=?";
		Object[] params = { sHomework.getHwUrl(), sHomework.getStuWorkTitle(),sHomework.getSubTime(), sHomework.getSize(), shwID };
		try {
			runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delStuHomework(int shwID) {
		String sql = "delete from stuhomework where shwID=?";
		try {
			runner.update(sql, shwID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
