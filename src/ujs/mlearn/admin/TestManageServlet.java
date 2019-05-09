package ujs.mlearn.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.TestDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.dao.impl.TestDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Teacher;
import ujs.mlearn.entity.Test;

public class TestManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestManageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op = request.getParameter("operation");
		System.out.println("operation=" + op);
		if (request.getSession().getAttribute("admin") == null) {
			System.out.println("请重新登录！");
			request.getRequestDispatcher(request.getContextPath() + "/adminLogin.jsp").forward(request, response);
		}
		if (op.equals("add")) {
			add(request, response);
		} else if (op.equals("addTest")) {
			addTest(request, response);
		} else if (op.equals("adminTest")) {
			adminTest(request, response);
		} else if (op.equals("editTest")) {
			editTest(request, response);
		} else if (op.equals("delTest")) {
			delTest(request, response);
		} else if (op.equals("modTest")) {
			modTest(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TeacherDao tDao = new TeacherDaoImpl();
		List<Teacher> teachers = tDao.findAll();
		request.setAttribute("teachers", teachers);
		CourseDao cDao = new CourseDaoImpl();
		List<Course> courses = cDao.findMyCourse(teachers.get(0).gettId());
		request.setAttribute("courses", courses);
		request.getRequestDispatcher("/WEB-INF/admin/testManage/addTest/addTest.jsp").forward(request, response);
	}

	private void addTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String testContent = request.getParameter("testContent");
		String testAnswer = request.getParameter("testAnswer");
		int testType = Integer.parseInt(request.getParameter("type"));
		String[] testOptions = request.getParameterValues("testOption");
		String testOption = "";
		for (int i = 0; i < testOptions.length; i++) {
			testOption += testOptions[i].trim() + ";";
		}
		String teacher = request.getParameter("teacher");
		String teacherNumber = teacher.substring(0, teacher.lastIndexOf(":"));
		String courseName = request.getParameter("course");
		CourseDao courseDao = new CourseDaoImpl();
		Course course = courseDao.findIdByName(courseName, teacherNumber);
		Test test = new Test();
		test.setCourseID(course.getCourseID());
		test.setTeacherNumber(teacherNumber);
		test.setTestContent(testContent);
		test.setType(testType);
		test.setTestOption(testOption);
		test.setTestAnswer(testAnswer.trim());

		TestDao testdao = new TestDaoImpl();
		testdao.addtest(test);
		request.getRequestDispatcher("/TestManageServlet?operation=adminTest").forward(request, response);
	}

	private void adminTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TestDao testDao = new TestDaoImpl();
		List<Test> tList = testDao.findAllTest();
		if (tList != null) {
			TeacherDao tDao = new TeacherDaoImpl();
			CourseDao cDao = new CourseDaoImpl();
			for (int i = 0; i < tList.size(); i++) {
				tList.get(i).setTeacherName(tDao.findByNumber(tList.get(i).getTeacherNumber()).getName());
				tList.get(i).setCourseName(cDao.findById(tList.get(i).getCourseID()).getCourseName());
			}
		}

		request.setAttribute("tList", tList);
		request.getRequestDispatcher("/WEB-INF/admin/testManage/test/testList.jsp").forward(request, response);
	}

	private void editTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int testID = Integer.parseInt(request.getParameter("testID"));
		TestDao testDao = new TestDaoImpl();
		Test test = testDao.findTest(testID);
		TeacherDao tDao = new TeacherDaoImpl();
		List<Teacher> teachers = tDao.findAll();
		CourseDao cDao = new CourseDaoImpl();
		List<Course> courses = cDao.findMyCourse(teachers.get(0).gettId());
		test.setTeacherName(tDao.findByNumber(test.getTeacherNumber()).getName());
		test.setCourseName(cDao.findById(test.getCourseID()).getCourseName());

		request.setAttribute("courses", courses);
		request.setAttribute("teachers", teachers);
		request.setAttribute("test", test);
		request.getRequestDispatcher("/WEB-INF/admin/testManage/test/modTest.jsp").forward(request, response);
	}

	private void modTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacher = request.getParameter("teacher");
		String teacherNumber = teacher.substring(0, teacher.lastIndexOf(":"));
		String courseName = request.getParameter("course");
		int testID = Integer.parseInt(request.getParameter("testID"));
		int type = Integer.parseInt(request.getParameter("type"));
		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findIdByName(courseName, teacherNumber);
		String testContent = request.getParameter("testContent");
		String testAnswer = request.getParameter("testAnswer");
		String[] testOptions = request.getParameterValues("testOption");
		String testOption = "";
		for (int i = 0; i < testOptions.length; i++) {
			testOption += testOptions[i].trim() + ";";
		}

		Test test = new Test(testID, course.getCourseID(), teacherNumber, testContent, type, testAnswer, testOption);
		test.setTeacherNumber(teacherNumber);
		test.setCourseID(course.getCourseID());
		test.setTestContent(testContent);
		test.setTestOption(testOption);
		test.setTestAnswer(testAnswer);
		test.setTestID(testID);
		test.setType(type);
		TestDao testDao = new TestDaoImpl();
		testDao.updateTest(test);

		adminTest(request, response);
	}

	private void delTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int testID = Integer.parseInt(request.getParameter("testID"));
		TestDao testDao = new TestDaoImpl();
		testDao.deltest(testID);

		adminTest(request, response);
	}
}
