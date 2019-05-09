package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Course;

public interface CourseDao {
	public String add(Course course, String mType);

	public void del(int courseID);

	public void updateCourse(Course course);

	public List<Course> findMyCourse(String teacherNumber);

	public List<Course> findAll();

	public Course findById(int courseID);

	public Course findIdByName(String courseName, String teacherNumber);

}
