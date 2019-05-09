package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.TestDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Test;

public class TestDaoImpl implements TestDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void addtest(Test test) {
		String sql = "insert into test(courseID,teacherNumber,testContent,testAnswer,testOption,type) values(?,?,?,?,?,?)";
		Object[] params = { test.getCourseID(), test.getTeacherNumber(), test.getTestContent(), test.getTestAnswer(),
				test.getTestOption(), test.getType() };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deltest(int testID) {
		String sql = "delete from test where testID=?";
		try {
			runner.update(sql, testID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Test> findCourseTest(int courseID) {
		String sql = "select * from test where courseID=? order by type";
		try {
			List<Test> tests = runner.query(sql, new BeanListHandler<Test>(Test.class), courseID);
			return tests;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int getCourseTestNumber(int courseID) {
		String sql = "select * from test where courseID=? order by type";
		try {
			List<Test> tests = runner.query(sql, new BeanListHandler<Test>(Test.class), courseID);
			if(tests!= null) {
				return tests.size();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Test> findAllTest() {
		String sql = "select * from test order by testID";
		try {
			List<Test> test = runner.query(sql, new BeanListHandler<Test>(Test.class));
			return test;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Test findTest(int testID) {
		String sql = "select * from test where testID = ? order by type";
		try {
			Test test = runner.query(sql, new BeanHandler<Test>(Test.class), testID);
			return test;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateTest(Test test) {
		String sql = "update test set testContent=?,testAnswer=?,testOption=?,type=?,teacherNumber=?,courseID=? where testID=?";

		Object[] params = { test.getTestContent(), test.getTestAnswer(), test.getTestOption(), test.getType(),
				test.getTeacherNumber(), test.getCourseID(), test.getTestID() };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
