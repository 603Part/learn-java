package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.HomeworkDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Homework;

public class HomeworkDaoImpl implements HomeworkDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void addHomework(Homework homework) {
		System.out.println("开始添加");
		String sql = "insert into homework(courseID,teacherNumber,hwTitle,hwContent,publishTime) values(?,?,?,?,?)";
		Object[] params = { homework.getCourseID(), homework.getTeacherNumber(), homework.getHwTitle(),
				homework.getHwContent(), homework.getPublishTime() };
		try {

			runner.update(sql, params);
			System.out.println("添加成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Homework> findAllHomework() {
		String sql = "select * from homework";
		try {
			List<Homework> hList = runner.query(sql, new BeanListHandler<>(Homework.class));
			for (Homework homework : hList) {
				homework.setPublishTime(homework.getPublishTime().substring(0, 19));
			}
			return hList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Homework> findHwByCourseID(int courseID) {
		String sql = "select * from homework where courseID=?";
		try {
			List<Homework> hList = runner.query(sql, new BeanListHandler<>(Homework.class), courseID);
			for (Homework homework : hList) {
				homework.setPublishTime(homework.getPublishTime().substring(0, 19));
			}
			return hList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateHomework(Homework homework) {
		String sql = "update homework set hwContent=?,hwTitle=?,teacherNumber=?,courseID=? where hwID=?";
		Object[] params = { homework.getHwContent(), homework.getHwTitle(), homework.getTeacherNumber(),
				homework.getCourseID(), homework.getHwID() };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteHomework(int hwID) {
		String sql = "delete from homework where hwID=?";
		try {
			runner.update(sql, hwID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql = "delete from stuhomework where hwID=?";
		try {
			runner.update(sql, hwID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Homework findHwByHwID(int hwID) {
		String sql = "select * from homework where hwID=?";
		try {
			Homework homework = runner.query(sql, new BeanHandler<>(Homework.class), hwID);
			if(homework != null) {
				homework.setPublishTime(homework.getPublishTime().substring(0, 19));
			}
			return homework;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
