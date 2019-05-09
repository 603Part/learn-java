package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.UserDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Student;

public class UserDaoImpl implements UserDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void add(Student student) {
		System.out.println("准备插入学生");
		if (student == null) {
			throw new IllegalArgumentException();
		} else {
			if (student.getPhoto() == null) {
				String sql = "insert into student (sid,password,name,sex,phone,email,signature,logintime) values (?,?,?,?,?,?,?,?)";
				Object[] params = { student.getsId(), student.getPassword(), student.getName(), student.getSex(),
						student.getPhone(), student.getEmail(), student.getSignature(), student.getLogintime()};
				try {
					runner.update(sql, params);
					System.out.println("插入用户成功");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				String sql = "insert into student (sid,password,name,sex,photo,phone,email,signature,logintime) values (?,?,?,?,?,?,?,?,?)";
				Object[] params = { student.getsId(), student.getPassword(), student.getName(), student.getSex(),
						student.getPhoto(), student.getPhone(), student.getEmail(), student.getSignature(),
						student.getLogintime()};
				try {
					runner.update(sql, params);
					System.out.println("插入用户成功");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public int changeLoginState(String userNumber, int state) {
		if(userNumber.equals("unLogin")) {
			return 1;
		}
		String sql = "update student set islogin= ? where sid = ? ";
		try {
			return runner.update(sql, state, userNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void del(int userID) {
		String sql = "delete from student where userID = ? ";
		try {
			runner.update(sql, userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Student student) {
		if (student.getPhoto() == null) {
			if (student.getPassword() == null) {
				String sql = "update student set sid=?,name=?,sex=?,phone=?,email=?,signature=?,photo=?,college=?,specialty=? where userID=?";
				Object[] params = { student.getsId(), student.getName(), student.getSex(), student.getPhone(),
						student.getEmail(), student.getSignature(), student.getPhoto(), student.getUserId(),
						student.getCollege(), student.getSpecialty()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				String sql = "update student set sid=?,name=?,sex=?,phone=?,email=?,signature=?,password=?,college=?,specialty=? where userID=?";
				Object[] params = { student.getsId(), student.getName(), student.getSex(), student.getPhone(),
						student.getEmail(), student.getSignature(), student.getPassword(), student.getUserId(),
						student.getCollege(), student.getSpecialty()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (student.getPassword() == null) {
				String sql = "update student set sid=?,name=?,sex=?,phone=?,email=?,signature=?,photo=?,college=?,specialty=? where userID=?";
				Object[] params = { student.getsId(), student.getName(), student.getSex(), student.getPhone(),
						student.getEmail(), student.getSignature(), student.getPhoto(), student.getUserId(),
						student.getCollege(), student.getSpecialty()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				String sql = "update student set sid=?,name=?,sex=?,phone=?,email=?,signature=?,photo=?,password=?,college=?,specialty=? where userID=?";
				Object[] params = { student.getsId(), student.getName(), student.getSex(), student.getPhone(),
						student.getEmail(), student.getSignature(), student.getPhoto(), student.getPassword(),
						student.getUserId(), student.getCollege(), student.getSpecialty()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Student findByNumber(String studentNumber) {
		String sql = "select * from student where sid = ?";

		try {
			Student student = runner.query(sql, new BeanHandler<Student>(Student.class), studentNumber);
			return student;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Student login(String studentNumber, String password) {
		String sql = "select * from student where sid = ? and password = ?";
		Object[] params = new Object[] { studentNumber, password };
		try {
			Student user = runner.query(sql, new BeanHandler<Student>(Student.class), params);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Student> findAll() {
		String sql = "select * from student order by logintime desc";

		try {
			List<Student> users = runner.query(sql, new BeanListHandler<Student>(Student.class));
			System.out.println(users);
			for (Student student : users) {
				student.setLogintime(student.getLogintime().substring(0, 19));
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Student findById(int userid) {
		String sql = "select * from student where userID = ?";

		try {
			Student user = runner.query(sql, new BeanHandler<Student>(Student.class), userid);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateuser(String attribute, String value, int userid) {
		String sql = "update student set xxx = ? where userID = ? ";

		sql = sql.replace("xxx", attribute);
		Object[] params = { value, userid };

		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateuser(String attribute, String value, String studentNumber) {
		String sql = "update student set xxx = ? where sid = ? ";

		sql = sql.replace("xxx", attribute);
		Object[] params = { value, studentNumber };

		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
