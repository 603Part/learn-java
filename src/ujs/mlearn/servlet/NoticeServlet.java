package ujs.mlearn.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.NoticeDao;
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.NoticeDaoImpl;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.entity.Notice;
import ujs.mlearn.entity.StudentCourse;

/**
 * Servlet implementation class NoticeServlet
 */
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op = request.getParameter("operation");
		System.out.println(op);
		if ("findMyNotice".equals(op)) {
			findMyNotice(request, response);// 这是用于查找学生所订阅的课程的所有通知
		}
	}

	private void findMyNotice(HttpServletRequest request, HttpServletResponse response) {
		NoticeDao nDao = new NoticeDaoImpl();
		int studentID = Integer.parseInt(request.getParameter("studentID"));
		List<Notice> notices = nDao.findAdminNotice();
		// 先通过学生Id取得学生所订阅的课程
		StudentCourseDao studentCourseDao = new StudentCourseDaoImpl();
		List<StudentCourse> scRelation = (List<StudentCourse>) studentCourseDao.findAll(studentID);// 取出学生的选课关系
		for (StudentCourse studentCourse : scRelation) {
			int courseID = studentCourse.getCourseID();
			System.out.println(courseID);
			notices.addAll(nDao.findCourseNotice(courseID));
		}
		if (notices != null) {
			TeacherDao tDao = new TeacherDaoImpl();
			CourseDao cDao = new CourseDaoImpl();
			for (int i = 0; i < notices.size(); i++) {
				if (notices.get(i).getType() == Notice.ALL) {
					notices.get(i).setTeacherName("管理员");
				} else {
					notices.get(i).setTeacherName(tDao.findByNumber(notices.get(i).getTeacherNumber()).getName());
				}
				if (notices.get(i).getType() == Notice.ALL) {
					notices.get(i).setCourseName("教务处");
				} else {
					notices.get(i).setCourseName(cDao.findById(notices.get(i).getCourseID()).getCourseName());
				}

			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("notices", notices);
		CommonUtil.renderJson(response, map);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
