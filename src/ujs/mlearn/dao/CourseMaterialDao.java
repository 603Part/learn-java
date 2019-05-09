package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.CourseMaterial;

public interface CourseMaterialDao {
	public void addRes(CourseMaterial courseMaterial);

	public List<CourseMaterial> findCourseMaterial(int courseID);

	public CourseMaterial findMaterialByID(int resID);

	public void delMaterialByID(int resID);

	public List<CourseMaterial> findAllMaterial();

	public void updateMaterial(CourseMaterial courseMaterial);
}
