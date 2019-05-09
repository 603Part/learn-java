package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Teacher;

public class TeacherDaoImpl implements TeacherDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void add(Teacher teacher) {
		System.out.println("准备插入教师");
		if (teacher == null) {
			throw new IllegalArgumentException();
		} else {
			String sql = "insert into teacher (tid,password,name,sex,phone,email,logintime,college,specialty,islogin) values (?,?,?,?,?,?,?,?,?,?)";
			Object[] params = { teacher.gettId(), teacher.getPassword(), teacher.getName(), teacher.getSex(),
					teacher.getPhone(), teacher.getEmail(), teacher.getLogintime(), teacher.getCollege(), teacher.getSpecialty(),teacher.getIslogin()};
			try {
				runner.update(sql, params);
				System.out.println("插入教师成功");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void del(int teacherID) {
		String sql = "delete from teacher where userID = ? ";
		try {
			runner.update(sql, teacherID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void changeLoginState(String userNumber, int state) {
		String sql = "update teacher set islogin= ? where tid = ? ";
		try {
			runner.update(sql, state, userNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Teacher teacher) {
		if (teacher.getPhoto() == null) {
			if (teacher.getPassword() == null) {
				String sql = "update teacher set tid=?,name=?,sex=?,phone=?,email=?,college=?,specialty=? where userID=?";
				Object[] params = { teacher.gettId(), teacher.getName(), teacher.getSex(), teacher.getPhone(),
						teacher.getEmail(), teacher.getCollege(), teacher.getSpecialty(),teacher.getUserId()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				String sql = "update teacher set tid=?,name=?,sex=?,phone=?,email=?,password=?,college=?,specialty=? where userID=?";
				Object[] params = { teacher.gettId(), teacher.getName(), teacher.getSex(), teacher.getPhone(),
						teacher.getEmail(), teacher.getPassword(), teacher.getUserId(), teacher.getCollege(), teacher.getSpecialty()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (teacher.getPassword() == null) {
				String sql = "update teacher set tid=?,name=?,sex=?,phone=?,email=?,photo=?,college=?,specialty=? where userID=?";
				Object[] params = { teacher.gettId(), teacher.getName(), teacher.getSex(), teacher.getPhone(),
						teacher.getEmail(), teacher.getPhoto(), teacher.getUserId(), teacher.getCollege(), teacher.getSpecialty()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				String sql = "update teacher set password=?,sex=?,phone=?,email=?,photo=?,password=?,college=?,specialty=? where userID = ?";
				Object[] params = { teacher.getPassword(), teacher.getSex(), teacher.getPhone(), teacher.getEmail(),
						teacher.getPhoto(), teacher.getPassword(), teacher.getUserId(), teacher.getCollege(), teacher.getSpecialty()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Teacher login(String teacherNumber, String pwd) {
		String sql = "select * from teacher where tid = ? and password = ?";
		Object[] params = new Object[] { teacherNumber, pwd };
		try {
			Teacher teacher = runner.query(sql, new BeanHandler<Teacher>(Teacher.class), params);
			return teacher;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Teacher> findAll() {
		String sql = "select * from teacher order by tid";

		try {
			List<Teacher> teachers = runner.query(sql, new BeanListHandler<Teacher>(Teacher.class));
			for (Teacher teacher : teachers) {
				teacher.setLogintime(teacher.getLogintime().substring(0, 19));
			}
			return teachers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Teacher findById(int teacherID) {
		String sql = "select * from teacher where userID = ?";
		try {
			Teacher teacher = runner.query(sql, new BeanHandler<Teacher>(Teacher.class), teacherID);
			return teacher;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Teacher findByNumber(String teacherNumber) {
		String sql = "select * from teacher where tid = ?";
		try {
			Teacher teacher = runner.query(sql, new BeanHandler<Teacher>(Teacher.class), teacherNumber);
			return teacher;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(String attribute, String value, int userid) {
		String sql = "update teacher set xxx = ? where userID = ? ";

		sql = sql.replace("xxx", attribute);
		Object[] params = { value, userid };

		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
