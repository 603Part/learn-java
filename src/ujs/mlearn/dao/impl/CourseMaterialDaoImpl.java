package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.CourseMaterialDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.CourseMaterial;

public class CourseMaterialDaoImpl implements CourseMaterialDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void addRes(CourseMaterial courseMaterial) {
		if (courseMaterial == null) {
			System.out.println("资源为空");
			return;
		}
		System.out.println("准备上传资源");
		String sql = "insert into coursematerial (courseID,publishTime,resTitle,resUrl,teacherNumber,size) values (?,?,?,?,?,?)";
		Object[] params = { courseMaterial.getCourseID(), courseMaterial.getPublishTime(), courseMaterial.getResTitle(),
				courseMaterial.getResUrl(), courseMaterial.getTeacherNumber(), courseMaterial.getSize() };
		try {
			runner.update(sql, params);
			System.out.println("上传成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CourseMaterial> findCourseMaterial(int courseID) {
		String sql = "select * from coursematerial where courseID=?";
		try {
			List<CourseMaterial> materials = runner.query(sql,
					new BeanListHandler<CourseMaterial>(CourseMaterial.class), courseID);
			for (CourseMaterial cMaterial : materials) {
				cMaterial.setPublishTime(cMaterial.getPublishTime().substring(0, 19));
			}
			return materials;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CourseMaterial findMaterialByID(int resID) {
		String sql = "select * from coursematerial where resID=?";
		try {
			CourseMaterial material = runner.query(sql, new BeanHandler<CourseMaterial>(CourseMaterial.class), resID);
			if (material != null) {
				material.setPublishTime(material.getPublishTime().substring(0, 19));
				return material;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delMaterialByID(int resID) {
		String sql = "delete from coursematerial where resID=?";
		try {
			runner.update(sql, resID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<CourseMaterial> findAllMaterial() {
		String sql = "select * from coursematerial";
		try {
			List<CourseMaterial> materials = runner.query(sql,
					new BeanListHandler<CourseMaterial>(CourseMaterial.class));
			if (materials != null) {
				for (int i = 0; i < materials.size(); i++) {
					materials.get(i).setPublishTime(materials.get(i).getPublishTime().substring(0, 19));
				}
			}
			return materials;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateMaterial(CourseMaterial courseMaterial) {
		String sql = "update coursematerial set teacherNumber=?,courseID=?,resTitle=?,publishTime=? where resID=?";
		Object[] params = { courseMaterial.getTeacherNumber(), courseMaterial.getCourseID(),
				courseMaterial.getResTitle(), courseMaterial.getPublishTime(), courseMaterial.getResID() };
		try {
			runner.update(sql, params);
			System.out.println("修改成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
