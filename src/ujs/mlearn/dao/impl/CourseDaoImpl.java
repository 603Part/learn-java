package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Course;

public class CourseDaoImpl implements CourseDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public String add(Course course, String mType) {
		if (course == null) {
			throw new IllegalArgumentException();
		} else {
			String sql0 = "select * from course where courseName=? and teacherNumber=?";
			Object[] params0 = { course.getCourseName(), course.getTeacherNumber() };
			Course course0 = null;
			try {
				course0 = runner.query(sql0, new BeanHandler<Course>(Course.class), params0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (course0 != null) {
				return "couse of teacher has exited";
			}

			String sql1 = "insert into course (courseName,teacherNumber,courseAbstract,detailInfo) values (?,?,?,?)";
			Object[] params1 = { course.getCourseName(), course.getTeacherNumber(), course.getCourseAbstract(),
					course.getDetailInfo() };
			try {
				runner.update(sql1, params1);
				System.out.println("插入课程成功");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String sql2 = "select * from course where courseName=? and teacherNumber=?";
			Object[] params2 = { course.getCourseName(), course.getTeacherNumber() };
			Course course2 = null;
			try {
				course2 = runner.query(sql2, new BeanHandler<Course>(Course.class), params2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String sql3 = "update course set courseUrl = ? where courseID = ?";
			String path = "res/course/" + course2.getCourseID();
			Object[] params3 = { path + "/cover" + mType, course2.getCourseID() };
			try {
				runner.update(sql3, params3);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return path;
		}
	}

	@Override
	public void del(int courseID) {
		String sql = "delete from course where courseID = ? ";
		try {
			runner.update(sql, courseID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCourse(Course course) {
		String sql = "update course set courseName=?,courseAbstract=?,detailInfo=?,courseUrl=?,teacherNumber=? where courseID=?";
		Object[] params = new Object[] { course.getCourseName(), course.getCourseAbstract(), course.getDetailInfo(),
				course.getCourseUrl(), course.getTeacherNumber(), course.getCourseID() };
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Course> findAll() {
		String sql = "select * from course order by teacherNumber";
		try {
			List<Course> courses = runner.query(sql, new BeanListHandler<Course>(Course.class));
			return courses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Course> findMyCourse(String teacherNumber) {
		String sql = "select * from course where teacherNumber=?";
		try {
			List<Course> courses = runner.query(sql, new BeanListHandler<Course>(Course.class), teacherNumber);
			return courses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Course findIdByName(String courseName, String teacherNumber) {
		String sql = "select * from course where courseName=? and teacherNumber=?";
		Object[] params = new Object[] { courseName, teacherNumber };
		try {
			Course course = runner.query(sql, new BeanHandler<Course>(Course.class), params);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Course findById(int courseid) {
		String sql = "select * from course where courseID = ?";
		try {
			Course course = runner.query(sql, new BeanHandler<Course>(Course.class), courseid);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
