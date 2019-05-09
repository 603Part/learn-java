package ujs.mlearn.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.BbsDao;
import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.ReplyDao;
import ujs.mlearn.dao.impl.BbsDaoImpl;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.ReplyDaoImpl;
import ujs.mlearn.entity.BbsTheme;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.ReplyPost;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebBbsServlet
 */
public class WebBbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebBbsServlet() {
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
		if (op.equals("findQuestion")) {// 查看学生的问题
			findMyCourse(request, response);
		} else if (op.equals("findQuestionList")) {// 用户点击了某门课程的查看问题，列出这门课的问题列表
			findCourseQuestionList(request, response);
		} else if (op.equals("delPost")) {// 修改回答
			delPost(request, response);
		} else if (op.equals("findPostDetail")) {
			findPostDetail(request, response);
		} else if (op.equals("sendReply")) {// 老师回答问题
			sendReply(request, response);
		} else if (op.equals("showReplyList")) {// 查看回答列表
			showReplyList(request, response);
		} else if (op.equals("findReplyDetail")) {// 查看详细回答
			findReplyDetail(request, response);
		} else if (op.equals("modReply")) {// 修改回答
			modReply(request, response);
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

	private void modReply(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int replyID = Integer.parseInt(request.getParameter("replyID"));
		String replyContent = request.getParameter("reply");
		ReplyDao replyDao = new ReplyDaoImpl();
		replyDao.updateTeacherReply(replyContent, replyID);
		showReplyList(request, response);
	}

	private void delPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));
		BbsDao bbsDao = new BbsDaoImpl();
		ReplyDao rDao = new ReplyDaoImpl();
		bbsDao.delPost(postID);
		rDao.deleteThemeAllRep(postID);
		findCourseQuestionList(request, response);
	}

	private void findReplyDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));
	//	int teacherID = ((Teacher) request.getSession().getAttribute("teacher")).getUserId();
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		ReplyDao replyDao = new ReplyDaoImpl();
		ReplyPost replyPost = replyDao.findOneTeacherReply(teacherNumber, postID);

		BbsDao bbsDao = new BbsDaoImpl();
		BbsTheme bbsTheme = bbsDao.findByID(postID);

		request.setAttribute("replyPost", replyPost);
		request.setAttribute("bbsTheme", bbsTheme);
		request.getRequestDispatcher("/WEB-INF/teacher/bbs/replydetail.jsp").forward(request, response);
	}

	private void showReplyList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		int teacherID = ((Teacher) request.getSession().getAttribute("teacher")).getUserId();
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		ReplyDao replyDao = new ReplyDaoImpl();
		List<ReplyPost> rList = replyDao.findTeacherReply(teacherNumber);
		BbsDao bbsDao = new BbsDaoImpl();// 用这个查找主题帖信息，可行但是略耗时
		List<BbsTheme> bbsThemes = new ArrayList<>();
		BbsTheme bbsTheme;
		for (ReplyPost rPost : rList) {
			bbsTheme = bbsDao.findByID(rPost.getPostID());
			bbsThemes.add(bbsTheme);
		}
		request.setAttribute("bbsThemes", bbsThemes);
		request.getRequestDispatcher("/WEB-INF/teacher/bbs/replylist.jsp").forward(request, response);
	}

	private void sendReply(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String content = request.getParameter("reply");
		String teacherName = teacher.getName();
		String teacherNumber = teacher.gettId();
		int userID = teacher.getUserId();
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月�? 与MySQL中的DATE相对�?
		Time time = new Time(currentTimeMillis); // 只有时分�? 与MySQL中的TIME相对�?
		String replyTime = date.toString() + " " + time.toString();// 中间要加空格才行

		ReplyPost replyPost = new ReplyPost(0, postID, teacherNumber, content, replyTime, 1, 0);
		ReplyDao replyDao = new ReplyDaoImpl();
		replyDao.addReply(replyPost);
		// 老师回答问题后，主题帖回答数加1，状态改为1，表示教师已回复
		BbsDao bbsDao = new BbsDaoImpl();
		bbsDao.stateChange(10, postID);
		request.getRequestDispatcher("/WebBbsServlet?action=showReplyList&teacherID=" + userID).forward(request,
				response);
	}

	private void findPostDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));
		BbsDao bbsDao = new BbsDaoImpl();
		BbsTheme bbsTheme = bbsDao.findByID(postID);
		request.setAttribute("bbsTheme", bbsTheme);
		request.getRequestDispatcher("/WEB-INF/teacher/bbs/postdetail.jsp").forward(request, response);
	}

	private void findCourseQuestionList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		BbsDao bbsDao = new BbsDaoImpl();
		List<BbsTheme> bbsThemes = bbsDao.findNoteByCourseID(courseID);
		if (bbsThemes != null) {
			ReplyDao rDao = new ReplyDaoImpl();
			for (int i = 0; i < bbsThemes.size(); i++) {
				bbsThemes.get(i).setReplyCount(rDao.findPostReplyCount(bbsThemes.get(i).getPostID()));
			}
		}

		request.setAttribute("bbsThemes", bbsThemes);
		request.getRequestDispatcher("/WEB-INF/teacher/bbs/bbspostlist.jsp").forward(request, response);
	}

	private void findMyCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherNumber);
		List<Integer> BBSNumber = new ArrayList<Integer>();
		if (courses != null) {
			BbsDao bbsDao = new BbsDaoImpl();
			for (int i = 0; i < courses.size(); i++) {
				BBSNumber.add(bbsDao.findCoursePostCount(courses.get(i).getCourseID()));
			}
		}

		request.setAttribute("BBSNumber", BBSNumber);
		request.setAttribute("cList", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/bbs/bbscourselist.jsp").forward(request, response);
		;
	}

}
