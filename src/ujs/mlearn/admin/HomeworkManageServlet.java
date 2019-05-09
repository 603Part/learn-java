package ujs.mlearn.admin;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.HomeworkDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.HomeworkDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Homework;
import ujs.mlearn.entity.Teacher;

public class HomeworkManageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeworkManageServlet() {
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
		} else if (op.equals("addHomework")) {
			addHomework(request, response);
		} else if (op.equals("adminHomework")) {
			adminHomework(request, response);
		} else if (op.equals("editHomework")) {
			editHomework(request, response);
		} else if (op.equals("delHomework")) {
			delHomework(request, response);
		} else if (op.equals("modHomework")) {
			modHomework(request, response);
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
		request.getRequestDispatcher("/WEB-INF/admin/homeworkManage/addHomework/addHomework.jsp").forward(request,
				response);
	}

	private void addHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacher = request.getParameter("teacher");
		String teacherNumber = teacher.substring(0, teacher.lastIndexOf(":"));
		String courseName = request.getParameter("courseName");
		TeacherDao teacherDao = new TeacherDaoImpl();
		CourseDao courseDao = new CourseDaoImpl();
		Course course = courseDao.findIdByName(courseName, teacherNumber);
		int courseID = course.getCourseID();
		String hwContent = request.getParameter("hwContent");
		String hwTitle = request.getParameter("hwTitle");
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月秒与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒与MySQL中的TIME相对应
		String publishTime = date.toString() + " " + time.toString();// 中间要加空格才行

		Homework homework = new Homework(0, courseID, teacherNumber, hwContent, publishTime, hwTitle);
		HomeworkDao hDao = new HomeworkDaoImpl();
		hDao.addHomework(homework);

		request.getRequestDispatcher("/HomeworkManageServlet?operation=adminHomework").forward(request, response);
	}

	private void adminHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HomeworkDao hDao = new HomeworkDaoImpl();
		List<Homework> hList = hDao.findAllHomework();
		if (hList != null) {
			TeacherDao tDao = new TeacherDaoImpl();
			CourseDao cDao = new CourseDaoImpl();
			for (int i = 0; i < hList.size(); i++) {
				hList.get(i).setTeacherName(tDao.findByNumber(hList.get(i).getTeacherNumber()).getName());
				hList.get(i).setCourseName(cDao.findById(hList.get(i).getCourseID()).getCourseName());
			}
		}

		request.setAttribute("hList", hList);
		request.getRequestDispatcher("/WEB-INF/admin/homeworkManage/homework/homeworkList.jsp").forward(request,
				response);
	}

	private void editHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int hwID = Integer.parseInt(request.getParameter("hwID"));
		HomeworkDao hDao = new HomeworkDaoImpl();
		Homework homework = hDao.findHwByHwID(hwID);
		TeacherDao tDao = new TeacherDaoImpl();
		List<Teacher> teachers = tDao.findAll();
		CourseDao cDao = new CourseDaoImpl();
		List<Course> courses = cDao.findMyCourse(teachers.get(0).gettId());
		homework.setTeacherName(tDao.findByNumber(homework.getTeacherNumber()).getName());
		homework.setCourseName(cDao.findById(homework.getCourseID()).getCourseName());

		request.setAttribute("courses", courses);
		request.setAttribute("teachers", teachers);
		request.setAttribute("homework", homework);
		request.getRequestDispatcher("/WEB-INF/admin/homeworkManage/homework/modHomework.jsp").forward(request,
				response);
	}

	private void modHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int hwID = Integer.parseInt(request.getParameter("hwID"));
		String teacher = request.getParameter("teacher");
		String teacherNumber = teacher.substring(0, teacher.lastIndexOf(":"));
		String courseName = request.getParameter("course");
		String hwTitle = request.getParameter("hwTitle");
		String hwContent = request.getParameter("hwContent");

		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findIdByName(courseName, teacherNumber);
		int courseID = course.getCourseID();
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月秒与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒与MySQL中的TIME相对应
		String publishTime = date.toString() + " " + time.toString();// 中间要加空格才行
		Homework homework = new Homework(hwID, courseID, teacherNumber, hwContent, publishTime, hwTitle);

		HomeworkDao hDao = new HomeworkDaoImpl();
		hDao.updateHomework(homework);

		adminHomework(request, response);
	}

	private void delHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int hwID = Integer.parseInt(request.getParameter("hwID"));
		HomeworkDao hDao = new HomeworkDaoImpl();
		hDao.deleteHomework(hwID);
		adminHomework(request, response);
	}
}
