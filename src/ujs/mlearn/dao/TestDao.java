package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Test;

public interface TestDao {

	public void addtest(Test test);

	public void deltest(int testid);

	public List<Test> findCourseTest(int courseID);

	public List<Test> findAllTest();

	public Test findTest(int testID);

	public void updateTest(Test test);

	int getCourseTestNumber(int courseID);
}
