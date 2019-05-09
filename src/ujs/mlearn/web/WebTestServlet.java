package ujs.mlearn.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.TestDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.dao.impl.TestDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Student;
import ujs.mlearn.entity.StudentCourse;
import ujs.mlearn.entity.Teacher;
import ujs.mlearn.entity.Test;

/**
 * Servlet implementation class WebTestServlet
 */
public class WebTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int courseID = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebTestServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (teacher == null) {
			System.out.println("用户未登录");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		String op = request.getParameter("action");
		System.out.println(op);
		if ("addTest".equals(op)) {
			addTest(request, response);
		} else if ("findCourseTest".equals(op)) {
			findCourseTest(request, response);
		} else if ("findMyCourse".equals(op)) {
			// 老师添加试题前要先进入不同的课程
			findMyCourse(request, response);
		} else if ("findTest".equals(op)) {
			// 用于修改或删除试题
			findTest(request, response);
		} else if ("delTest".equals(op)) {
			delTest(request, response);
		} else if ("updateTest".equals(op)) {
			updateTest(request, response);
		} else if ("findResult".equals(op)) {
			// 查找这门课学生的成绩和答题情况
			findResult(request, response);
		} else if (op.equals("goShowPage")) {
			goShowPage(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CourseDao cDao = new CourseDaoImpl();
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		List<Course> courses = cDao.findMyCourse(teacher.gettId());

		request.setAttribute("cList", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/test/addtest.jsp").forward(request, response);
	}

	private void findResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		StudentCourseDao sCourseDao = new StudentCourseDaoImpl();
		List<StudentCourse> sList = sCourseDao.findByCouresID(courseID);
		HashMap<Integer, String> name = new HashMap<>();
		UserDao userDao = new UserDaoImpl();
		for (StudentCourse sCourse : sList) {
			int userID = sCourse.getStudentID();
			Student user = userDao.findById(userID);
			name.put(userID, user.getsId());
		}

		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findById(courseID);
		request.setAttribute("courseName", course.getCourseName());
		request.setAttribute("studentcourse", sList);
		request.setAttribute("name", name);
		request.getRequestDispatcher("/WEB-INF/teacher/test/resultlist.jsp").forward(request, response);
	}

	private void delTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int testID = Integer.parseInt(request.getParameter("testID"));
		TestDao testDao = new TestDaoImpl();
		testDao.deltest(testID);

		request.getRequestDispatcher("/WebTestServlet?action=findCourseTest&courseID=" + WebTestServlet.courseID)
				.forward(request, response);
	}

	private void updateTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		int testID = Integer.parseInt(request.getParameter("testID"));
		int type = Integer.parseInt(request.getParameter("type"));
		String testContent = request.getParameter("testContent");
		String testAnswer = request.getParameter("testAnswer");
		String[] testOptions = request.getParameterValues("testOption");
		String testOption = "";
		for (int i = 0; i < testOptions.length; i++) {
			testOption += testOptions[i].trim() + ";";
		}

		Test test = new Test(testID, courseID, teacherNumber, testContent, type, testAnswer, testOption);
		test.setTeacherNumber(teacherNumber);
		test.setCourseID(courseID);
		test.setTestContent(testContent);
		test.setTestOption(testOption);
		test.setTestAnswer(testAnswer);
		test.setTestID(testID);
		test.setType(type);
		TestDao testDao = new TestDaoImpl();
		testDao.updateTest(test);
		request.getRequestDispatcher("/WebTestServlet?action=findCourseTest&courseID=" + courseID).forward(request,
				response);
	}

	private void findTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int testID = Integer.parseInt(request.getParameter("testID"));
		TestDao testDao = new TestDaoImpl();
		Test test = testDao.findTest(testID);
		String[] testOptions = test.getTestOption().trim().split(";");
		// String testAnswer = test.getTestAnswer();
		// String testContent = test.getTestContent();

		// request.setAttribute("testContent", testContent);
		// request.setAttribute("testAnswer", testAnswer);
		request.setAttribute("testOptions", testOptions);
		request.setAttribute("test", test);
		request.getRequestDispatcher("/WEB-INF/teacher/test/modtest.jsp").forward(request, response);
	}

	private void findCourseTest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt((request.getParameter("courseID")));
		WebTestServlet.courseID = courseID;
		TestDao testdao = new TestDaoImpl();
		List<Test> tests = testdao.findCourseTest(courseID);
		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findById(courseID);
		request.setAttribute("courseName", course.getCourseName());
		request.setAttribute("tList", tests);
		request.getRequestDispatcher("/WEB-INF/teacher/test/testlist.jsp").forward(request, response);
	}

	private void findMyCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherNumber);
		request.setAttribute("cList", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/test/courselist.jsp").forward(request, response);
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

		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		String courseName = request.getParameter("courseName");
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
		WebTestServlet.courseID = course.getCourseID();// 全局变量的courseID
		request.getRequestDispatcher("/WebTestServlet?action=findCourseTest&courseID=" + course.getCourseID())
				.forward(request, response);
	}

}
