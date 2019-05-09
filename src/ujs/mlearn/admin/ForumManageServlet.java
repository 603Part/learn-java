package ujs.mlearn.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.BbsDao;
import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.ReplyDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.BbsDaoImpl;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.ReplyDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.entity.BbsTheme;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.ReplyPost;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class ForumServlet
 */
@WebServlet("/ForumServlet")
public class ForumManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForumManageServlet() {
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
		String op = request.getParameter("operation");
		System.out.println("operation=" + op);
		if (request.getSession().getAttribute("admin") == null) {
			System.out.println("请重新登录！");
			request.getRequestDispatcher(request.getContextPath() + "/adminLogin.jsp").forward(request, response);
		}
		if (op.equals("adminForum")) {
			adminForum(request, response);
		} else if(op.equals("findPostList")) {
			findPostList(request, response);
		} else if(op.equals("findReplyList")) {
			findReplyList(request, response);
		} else if (op.equals("delPost")) {
			delPost(request, response);
		} else if (op.equals("delReply")) {
			delReply(request, response);
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
	
	private void adminForum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CourseDao cDao = new CourseDaoImpl();
		List<Course> cList = cDao.findAll();

		TeacherDao tDao = new TeacherDaoImpl();
		BbsDao bDao = new BbsDaoImpl();
		for (int i = 0; i < cList.size(); i++) {
			cList.get(i).setTeacherName(tDao.findByNumber(cList.get(i).getTeacherNumber()).getName());
			cList.get(i).setCoursePostCount(bDao.findCoursePostCount(cList.get(i).getCourseID()));
		}

		request.setAttribute("cList", cList);
		request.getRequestDispatcher("/WEB-INF/admin/forumManage/forumcourseList.jsp").forward(request, response);
	}
	
	private void findPostList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		BbsDao bbsDao = new BbsDaoImpl();
		List<BbsTheme> bbsThemes = bbsDao.findNoteByCourseID(courseID);
		if (bbsThemes != null) {
			ReplyDao rDao = new ReplyDaoImpl();
			UserDao uDao = new UserDaoImpl();
			for (int i = 0; i < bbsThemes.size(); i++) {
				bbsThemes.get(i).setReplyCount(rDao.findPostReplyCount(bbsThemes.get(i).getPostID()));
				bbsThemes.get(i).setStudentName(uDao.findByNumber(bbsThemes.get(i).getStudentNumber()).getName());
			}
		}

		request.setAttribute("bbsThemes", bbsThemes);
		request.getRequestDispatcher("/WEB-INF/admin/forumManage/forumpostList.jsp").forward(request, response);
	}
	
	private void delPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));
		BbsDao bbsDao = new BbsDaoImpl();
		ReplyDao rDao = new ReplyDaoImpl();
		bbsDao.delPost(postID);
		rDao.deleteThemeAllRep(postID);
		findPostList(request, response);
	}
	
	private void findReplyList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));
		ReplyDao rDao = new ReplyDaoImpl();
		List<ReplyPost> rList = rDao.findPostReply(postID);
		if(rList != null) {
			TeacherDao tDao = new TeacherDaoImpl();
			UserDao uDao = new UserDaoImpl();
			for (int i = 0; i < rList.size(); i++) {
				rList.get(i).setUserType(rList.get(i).getUserType());
				if(rList.get(i).getUserType() == ReplyPost.TEACHER) {
					rList.get(i).setUserTypeString("教师");
					rList.get(i).setUserName(tDao.findByNumber(rList.get(i).getUserNumber()).getName());
				} else {
					rList.get(i).setUserTypeString("学生");
					rList.get(i).setUserName(uDao.findByNumber(rList.get(i).getUserNumber()).getName());
				}
			}
		}
		
		request.setAttribute("rList", rList);
		request.getRequestDispatcher("/WEB-INF/admin/forumManage/forumreplyList.jsp").forward(request, response);
	}
	
	private void delReply(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int postID = Integer.parseInt(request.getParameter("postID"));
		int replyID = Integer.parseInt(request.getParameter("replyID"));
		int userType = Integer.parseInt(request.getParameter("userType"));
		ReplyDao rDao = new ReplyDaoImpl();
		rDao.deleteRep(replyID);
		if(userType == ReplyPost.TEACHER) {
			BbsDao bbsDao = new BbsDaoImpl();
			bbsDao.stateChange(11, postID);
		}
		findReplyList(request, response);
	}

}
