package ujs.mlearn.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.HomeworkDao;
import ujs.mlearn.dao.StuHomeworkDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.HomeworkDaoImpl;
import ujs.mlearn.dao.impl.StuHomeworkDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Homework;
import ujs.mlearn.entity.StuHomework;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebHomeworkServlet
 */
public class WebHomeworkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebHomeworkServlet() {
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
		if (op.equals("goShowPage")) {
			goShowPage(request, response);
		} else if (op.equals("addHomework")) {
			addHomework(request, response);
		} else if (op.equals("findCourseHw")) {// 找到这么门所有的作业
			findCourseHw(request, response);
		} else if (op.equals("findMyCourse")) {// 查看布置的作业之前要先看有哪些课�?
			findMyCourse(request, response);
		} else if (op.equals("findHwDetail")) {
			findHwDetail(request, response);// 查看作业详细信息
		} else if (op.equals("modHomework")) {
			modHomework(request, response);
		} else if (op.equals("delHw")) {
			delHomework(request, response);
		} else if (op.equals("findStuHw")) {
			findStuHw(request, response);
		} else if (op.equals("downloadWork")) {
			downloadWork(request, response);
		} else if (op.equals("deleteWork")) {
			delStuHomework(request, response);
		}
	}

	private void downloadWork(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		int shwID = Integer.parseInt((request.getParameter("shwID")));
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		StuHomeworkDao sDao = new StuHomeworkDaoImpl();
		StuHomework sHomework = sDao.findOneMyHomework(shwID);
		String fileName = sHomework.getStuWorkTitle();
		response.setContentType(getServletContext().getMimeType(fileName));
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String path = getServletContext().getRealPath("/") + sHomework.getHwUrl();
		path = path.replaceAll("/", "\\\\");
		File file = new File(path);
		response.setHeader("Content-Length", String.valueOf(file.length()));

		try {
			InputStream in;
			in = new BufferedInputStream(new FileInputStream(path));
			byte[] buff = new byte[1024];
			int len = 0;
			OutputStream out;
			out = response.getOutputStream();
			while ((len = in.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void findStuHw(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StuHomeworkDao sDao = new StuHomeworkDaoImpl();
		int hwID = Integer.parseInt((request.getParameter("hwID")));
		List<StuHomework> sHomeworks = sDao.findStuHwByHwID(hwID);

		HomeworkDao hDao = new HomeworkDaoImpl();
		Homework homework = hDao.findHwByHwID(hwID);
		request.setAttribute("homeworkTitle", homework.getHwTitle());
		request.setAttribute("sHomeworks", sHomeworks);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/stuWork.jsp").forward(request, response);
	}

	private void modHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		int courseID = Integer.parseInt((request.getParameter("courseID")));
		int hwID = Integer.parseInt((request.getParameter("hwID")));
		String hwContent = request.getParameter("hwContent");
		String hwTitle = request.getParameter("hwTitle");
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月秒与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒与MySQL中的TIME相对应
		String publishTime = date.toString() + " " + time.toString();// 中间要加空格才行
		Homework homework = new Homework(hwID, courseID, teacherNumber, hwContent, publishTime, hwTitle);

		HomeworkDao hDao = new HomeworkDaoImpl();
		hDao.updateHomework(homework);

		request.getRequestDispatcher("/WebHomeworkServlet?action=findCourseHw&courseID=" + courseID).forward(request,
				response);
	}

	private void findHwDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HomeworkDao hDao = new HomeworkDaoImpl();
		int hwID = Integer.parseInt((request.getParameter("hwID")));
		Homework homework = hDao.findHwByHwID(hwID);
		request.setAttribute("homework", homework);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/homeworkDetail.jsp").forward(request, response);
	}

	private void findMyCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherNumber);
		request.setAttribute("cList", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/courselist.jsp").forward(request, response);
	}

	private void findCourseHw(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt((request.getParameter("courseID")));
		HomeworkDao hDao = new HomeworkDaoImpl();
		List<Homework> hList = hDao.findHwByCourseID(courseID);

		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findById(courseID);
		request.setAttribute("courseName", course.getCourseName());
		request.setAttribute("hList", hList);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/hwList.jsp").forward(request, response);
	}

	private void delHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int hwID = Integer.parseInt((request.getParameter("hwID")));
		StuHomeworkDao stuHwDao = new StuHomeworkDaoImpl();
		List<StuHomework> stuHws = stuHwDao.findStuHwByHwID(hwID);
		for (int i = 0; i < stuHws.size(); i++) {
			new File((getServletContext().getRealPath("/") + stuHws.get(i).getHwUrl()).replaceAll("/", "\\\\"))
					.delete();
		}

		HomeworkDao hDao = new HomeworkDaoImpl();
		hDao.deleteHomework(hwID);
		findCourseHw(request, response);
	}

	private void delStuHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int shwID = Integer.parseInt((request.getParameter("shwID")));
		System.out.println(shwID);
		StuHomeworkDao stuHwDao = new StuHomeworkDaoImpl();
		StuHomework stuHw = stuHwDao.findOneMyHomework(shwID);
		new File((getServletContext().getRealPath("/") + stuHw.getHwUrl()).replaceAll("/", "\\\\")).delete();
		stuHwDao.delStuHomework(shwID);
		findStuHw(request, response);
	}

	private void addHomework(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		String courseName = request.getParameter("courseName");
		CourseDao courseDao = new CourseDaoImpl();
		Course course = courseDao.findIdByName(courseName, teacherNumber);
		int courseID = course.getCourseID();
		String hwContent = request.getParameter("hwContent");
		String hwTitle = request.getParameter("hwTitle");
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月秒与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒与MySQL中的TIME相对应
		String publishTime = date.toString() + " " + time.toString();// 中间要加空格才行

		System.out.println(publishTime);
		Homework homework = new Homework(0, courseID, teacherNumber, hwContent, publishTime, hwTitle);
		HomeworkDao hDao = new HomeworkDaoImpl();
		hDao.addHomework(homework);

		request.getRequestDispatcher("/WebHomeworkServlet?action=findCourseHw&courseID=" + courseID).forward(request,
				response);
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/teacher/homework/addhomework.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
