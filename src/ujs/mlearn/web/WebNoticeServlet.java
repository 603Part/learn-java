package ujs.mlearn.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.NoticeDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.NoticeDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Notice;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebNoticeServlet
 */
public class WebNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebNoticeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		if ("goShowPage".equals(op)) {
			goShowPage(request, response);
		} else if ("addNotice".equals(op)) {
			addNotice(request, response);
		} else if ("findMyCourseList".equals(op)) {
			findMyCourseList(request, response);
		} else if ("findCourseNoticeList".equals(op)) {
			findCourseNoticeList(request, response);
		} else if ("findNotice".equals(op)) {
			findNotice(request, response);
		} else if ("delNotice".equals(op)) {
			delNotice(request, response);
		} else if ("updateNotice".equals(op)) {
			updatedNotice(request, response);
		}
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CourseDao cDao = new CourseDaoImpl();
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		List<Course> courses = cDao.findMyCourse(teacherNumber);
		request.setAttribute("courses", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/notice/addnotice.jsp").forward(request, response);
	}

	private void addNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NoticeDao nDao = new NoticeDaoImpl();
		CourseDao cDao = new CourseDaoImpl();
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");

		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		String courseName = request.getParameter("course");

		Course course = cDao.findIdByName(courseName, teacherNumber);

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String dateString = formatter.format(currentTime);
		String notictime = dateString;

		Notice notice = new Notice();
		notice.setNoticeContent(noticeContent);
		notice.setNoticeTime(notictime);
		notice.setNoticeTitle(noticeTitle);
		notice.setTeacherNumber(teacherNumber);
		notice.setCourseName(course.getCourseName());
		notice.setCourseID(course.getCourseID());
		nDao.addNotice(notice);

		request.setAttribute("message", "添加通知成功！");
		request.getRequestDispatcher("/WebNoticeServlet?action=findCourseNoticeList&courseID=" + course.getCourseID())
				.forward(request, response);
	}

	private void delNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NoticeDao noti = new NoticeDaoImpl();
		int noticeID = Integer.parseInt(request.getParameter("noticeID"));
		noti.delNotice(noticeID);
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		request.getRequestDispatcher("/WebNoticeServlet?action=findCourseNoticeList&courseID=" + courseID)
				.forward(request, response);
	}

	private void findMyCourseList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherNumber);
		request.setAttribute("cList", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/notice/courselist.jsp").forward(request, response);
	}

	private void findCourseNoticeList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		NoticeDao nDao = new NoticeDaoImpl();
		List<Notice> notics = nDao.findCourseNotice(courseID);
		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findById(courseID);

		request.setAttribute("courseID", courseID);
		request.setAttribute("courseName", course.getCourseName());
		request.setAttribute("nList", notics);
		request.getRequestDispatcher("/WEB-INF/teacher/notice/noticelist.jsp").forward(request, response);
	}

	private void findNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int noticeID = Integer.parseInt(request.getParameter("noticeID"));
		NoticeDao nDao = new NoticeDaoImpl();
		Notice notice = nDao.findNoticeByID(noticeID);

		System.out.println(notice);
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/teacher/notice/modnotice.jsp").forward(request, response);
	}

	private void updatedNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int noticeID = Integer.parseInt(request.getParameter("noticeID"));
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String noticeTime = dateString;
		Notice notice = new Notice();
		notice.setNoticeContent(noticeContent);
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeTime(noticeTime);
		notice.setNoticeID(noticeID);

		NoticeDao nDao = new NoticeDaoImpl();
		nDao.updateNotice(notice);
		request.getRequestDispatcher("/WebNoticeServlet?action=findCourseNoticeList&courseID=" + courseID)
				.forward(request, response);
	}

}
