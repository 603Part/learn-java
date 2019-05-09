package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Student;

public interface UserDao {
	public void add(Student user);

	public void del(int id);

	public void update(Student user);

	public Student findByNumber(String studentNumber);

	public Student login(String studentNumber, String pwd);

	public List<Student> findAll();

	public Student findById(int userid);

	public void updateuser(String attribute, String value, int userid);

	public void updateuser(String attribute, String value, String studentNumber);

	public int changeLoginState(String userNumber, int state);
}
