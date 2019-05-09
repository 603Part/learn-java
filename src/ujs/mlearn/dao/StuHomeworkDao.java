package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.StuHomework;

public interface StuHomeworkDao {
	public List<StuHomework> findStuHwByHwID(int hwID);

	public void uploadHomework(StuHomework sHomework);

	public void updateHomework(StuHomework sHomework, int shwID);

	public StuHomework findOneMyHomework(int shwID);

	public StuHomework findHomework(int hwID, int studentID);

	public void delStuHomework(int shwID);

	List<StuHomework> findMyCourseHomework(int userID, int courseID);
}
