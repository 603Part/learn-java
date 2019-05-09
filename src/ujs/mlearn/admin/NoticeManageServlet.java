package ujs.mlearn.admin;

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
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.NoticeDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Notice;
import ujs.mlearn.entity.Teacher;

public class NoticeManageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeManageServlet() {
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
		} else if (op.equals("addNotice")) {
			addNotice(request, response);
		} else if (op.equals("adminNotice")) {
			adminNotice(request, response);
		} else if (op.equals("editNotice")) {
			editNotice(request, response);
		} else if (op.equals("delNotice")) {
			delNotice(request, response);
		} else if (op.equals("modNotice")) {
			modNotice(request, response);
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
		request.getRequestDispatcher("/WEB-INF/admin/noticeManage/addNotice/addNotice.jsp").forward(request, response);
	}

	private void addNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String type = request.getParameter("ra");
		String noticeTitle = request.getParameter("noticeTitle"); // 获取标题和内容
		String noticeContent = request.getParameter("noticeContent");

		Date currentTime = new Date(); // 日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String noticetime = dateString;

		Notice notice = new Notice();
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent); // 设置通知的标题、内容和时间
		notice.setNoticeTime(noticetime);
		if (type.equals("class")) {
			String tnumber = request.getParameter("teacher").substring(0,
					request.getParameter("teacher").lastIndexOf(":"));
			String courseName = request.getParameter("course"); // 获取课程名
			CourseDao courseDao = new CourseDaoImpl();
			Course course = courseDao.findIdByName(courseName, tnumber);
			TeacherDao teacherDao = new TeacherDaoImpl();
			Teacher teacher = teacherDao.findByNumber(tnumber);

			notice.setTeacherNumber(teacher.gettId());
			notice.setCourseID(course.getCourseID());
			notice.setType(0);
		} else if (type.equals("all")) {
			notice.setTeacherNumber("00000");
			notice.setCourseID(0);
			notice.setCourseName("all");
			notice.setType(1);
		}

		NoticeDao noticeDao = new NoticeDaoImpl();
		noticeDao.addNotice(notice);

		request.setAttribute("noticeTitle", noticeTitle);
		request.setAttribute("noticeContent", noticeContent);

		request.setAttribute("message", "添加通知成功！");
		request.getRequestDispatcher("/NoticeManageServlet?operation=adminNotice").forward(request, response);
	}

	private void adminNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		NoticeDao nDao = new NoticeDaoImpl();
		List<Notice> nList = nDao.findAllNotice();
		System.out.println(nList.get(0).getCourseID());
		if (nList != null) {
			TeacherDao tDao = new TeacherDaoImpl();
			CourseDao cDao = new CourseDaoImpl();
			for (int i = 0; i < nList.size(); i++) {
				if (nList.get(i).getType() == Notice.ALL) {
					nList.get(i).setTeacherName("管理员");
				} else {
					nList.get(i).setTeacherName(tDao.findByNumber(nList.get(i).getTeacherNumber()).getName());
				}
				if (nList.get(i).getType() == Notice.ALL) {
					nList.get(i).setCourseName("全体");
				} else {
					System.out.println(nList.get(i).getCourseID());
					nList.get(i).setCourseName(cDao.findById(nList.get(i).getCourseID()).getCourseName());
				}

			}
		}

		request.setAttribute("nList", nList);
		request.getRequestDispatcher("/WEB-INF/admin/noticeManage/notice/noticeList.jsp").forward(request, response);
	}

	private void editNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int noticeID = Integer.parseInt(request.getParameter("noticeID"));
		NoticeDao nDao = new NoticeDaoImpl();
		Notice notice = nDao.findNoticeByID(noticeID);
		if (notice != null) {
			TeacherDao tDao = new TeacherDaoImpl();
			CourseDao cDao = new CourseDaoImpl();
			if (notice.getType() == Notice.ALL) {
				notice.setTeacherName("管理员");
			} else {
				notice.setTeacherName(tDao.findByNumber(notice.getTeacherNumber()).getName());
			}
			if (notice.getType() == Notice.ALL) {
				notice.setCourseName("全体");
			} else {
				notice.setCourseName(cDao.findById(notice.getCourseID()).getCourseName());
			}
		}
		TeacherDao tDao = new TeacherDaoImpl();
		List<Teacher> teachers = tDao.findAll();
		request.setAttribute("teachers", teachers);
		CourseDao cDao = new CourseDaoImpl();
		List<Course> courses = cDao.findMyCourse(teachers.get(0).gettId());
		request.setAttribute("courses", courses);
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/admin/noticeManage/notice/modNotice.jsp").forward(request, response);
	}

	private void modNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int noticeID = Integer.parseInt(request.getParameter("noticeID"));
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeType = request.getParameter("noticeType");
		String noticeContent = request.getParameter("noticeContent");
		String teacher = request.getParameter("teacher");
		String teacherNumber = "00000";
		if (teacher != null) {
			teacherNumber = teacher.substring(0, teacher.lastIndexOf(":"));
		}
		String courseName = request.getParameter("course");

		Notice notice = new Notice();
		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findIdByName(courseName, teacherNumber);
		if (course == null) {
			notice.setCourseID(0);
		} else {
			notice.setCourseID(course.getCourseID());
		}
		notice.setTeacherNumber(teacherNumber);
		notice.setNoticeID(noticeID);
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		if (noticeType.equals("课程通知")) {
			notice.setType(0);
		} else if (noticeType.equals("全体通知")) {
			notice.setType(1);
		}
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String noticetime = dateString;
		notice.setNoticeTime(noticetime);
		NoticeDao nDao = new NoticeDaoImpl();
		nDao.updateNotice(notice);

		adminNotice(request, response);
	}

	private void delNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int noticeID = Integer.parseInt(request.getParameter("noticeID"));
		NoticeDao nDao = new NoticeDaoImpl();
		nDao.delNotice(noticeID);
		adminNotice(request, response);
	}
}
