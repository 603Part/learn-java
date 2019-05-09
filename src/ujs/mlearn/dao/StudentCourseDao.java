package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.StudentCourse;

public interface StudentCourseDao {
	public void add(int courseID, int studentID);

	public void del(int id);

	public void del(int courseID, int studentID);

	public void update(StudentCourse sc);

	public StudentCourse find(int id);

	public StudentCourse find(int courseID, int studentID);

	public List<StudentCourse> findAll();

	public List<StudentCourse> findAll(int id);

	public void update(int courseID, int studentID, int studentGrade, String studnetAnswer);

	public List<StudentCourse> findByCouresID(int courseID);

	public int findCountByCourseID(int courseID);
}
