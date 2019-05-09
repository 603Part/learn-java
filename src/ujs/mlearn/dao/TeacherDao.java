package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Teacher;

public interface TeacherDao {
	public void add(Teacher teacher);

	public void del(int id);

	public void update(Teacher teacher);

	public Teacher login(String teacherNumber, String pwd);

	public List<Teacher> findAll();

	public Teacher findById(int userid);

	public void updateUser(String attribute, String value, int userid);

	public Teacher findByNumber(String teacherNumber);

	void changeLoginState(String userNumber, int state);

}
