package ujs.mlearn.globalParams;

import ujs.mlearn.entity.Student;
import ujs.mlearn.entity.Teacher;

public class GlobalParams {
	public static Teacher teacher;
	public static Student student;

	private GlobalParams() {
	}

	private static GlobalParams single = new GlobalParams();

	public static GlobalParams getInstance() {
		return single;
	}
}
