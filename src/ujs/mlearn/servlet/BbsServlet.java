package ujs.mlearn.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.BbsDao;
import ujs.mlearn.dao.ReplyDao;
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.BbsDaoImpl;
import ujs.mlearn.dao.impl.ReplyDaoImpl;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.entity.BbsTheme;
import ujs.mlearn.entity.ReplyPost;
import ujs.mlearn.entity.SentMessage;
import ujs.mlearn.entity.Student;
import ujs.mlearn.entity.StudentCourse;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class BbsServlet
 */
public class BbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BbsServlet() {
		super();
		// TODO Auto-generated constructor stub
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
		System.out.println(op);
		if (op.equals("findCourseList")) {// 论坛显示课程界面
			findCourseList(request, response);
		} else if (op.equals("findPostList")) {// 显示这门课的主题帖
			findPostList(request, response);
		} else if (op.equals("sendPost")) {// 发帖
			addPost(request, response);
		} else if (op.equals("findReplyList")) {
			findReplyList(request, response);// 显示回帖
		} else if (op.equals("sendReply")) {
			sendReply(request, response);// 学生回帖
		} else if (op.equals("deleteReply")) {// 删除回帖
			deleteReply(request, response);
			System.out.println("删除成功");
		} else if (op.equals("deletePost")) {// 删除主题帖
			deletePost(request, response);
			System.out.println("删除帖子成功");

		}

	}

	private void deletePost(HttpServletRequest request, HttpServletResponse response) {
		int postID = Integer.parseInt(request.getParameter("postID"));
		ReplyDao replyDao = new ReplyDaoImpl();
		replyDao.deleteThemeAllRep(postID);
		BbsDao bDao = new BbsDaoImpl();
		bDao.delPost(postID);
		SentMessage message = new SentMessage(1, "删除成功");
		CommonUtil.renderJson(response, message);
	}

	private void deleteReply(HttpServletRequest request, HttpServletResponse response) {
		int replyID = Integer.parseInt(request.getParameter("replyID"));
		ReplyDao replyDao = new ReplyDaoImpl();
		replyDao.deleteRep(replyID);
		SentMessage message = new SentMessage(1, "删除成功");
		CommonUtil.renderJson(response, message);
	}

	private void sendReply(HttpServletRequest request, HttpServletResponse response) {
		int postID = Integer.parseInt(request.getParameter("postID"));
		String replyContent = request.getParameter("replyContent");
		String userNumber = request.getParameter("userNumber");

		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月日 与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒 与MySQL中的TIME相对应
		String replyTime = date.toString() + " " + time.toString();// 中间要加空格才行

		ReplyPost replyPost = new ReplyPost(0, postID, userNumber, replyContent, replyTime, 0, 0);
		ReplyDao replyDao = new ReplyDaoImpl();
		replyDao.addReply(replyPost);
		// 学生回答问题后，主题帖回答数加1
		BbsDao bbsDao = new BbsDaoImpl();
		bbsDao.stateChange(20, postID);
		SentMessage message = new SentMessage(1, "回帖成功");
		CommonUtil.renderJson(response, message);

	}

	private void findReplyList(HttpServletRequest request, HttpServletResponse response) {
		int postID = Integer.parseInt(request.getParameter("postID"));
		ReplyDao replyDao = new ReplyDaoImpl();
		List<ReplyPost> replyPosts = replyDao.findPostReply(postID);
		
		if(replyPosts != null) {
			UserDao uDao = new UserDaoImpl();
			TeacherDao tDao = new TeacherDaoImpl();
			for(int i = 0; i < replyPosts.size(); i++) {
				if(replyPosts.get(i).getUserType() == ReplyPost.STUDENT) {
					System.out.println(replyPosts.get(i).getUserNumber());
					Student student = uDao.findByNumber(replyPosts.get(i).getUserNumber());
					replyPosts.get(i).setUserName(student.getName());
					replyPosts.get(i).setUserPhotoURL(student.getPhoto());
				} else {
					Teacher teacher = tDao.findByNumber(replyPosts.get(i).getUserNumber());
					replyPosts.get(i).setUserName(teacher.getName());
					replyPosts.get(i).setUserPhotoURL(teacher.getPhoto());
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replyList", replyPosts);
		CommonUtil.renderJson(response, map);
	}

	private void addPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	//	int studentID = Integer.parseInt(request.getParameter("studentID"));
		// String studentName=new String(
		// request.getParameter("studentName").getBytes("iso-8859-1"),"utf-8");

		// String postTitle=new String(
		// request.getParameter("title").getBytes("iso-8859-1"),"utf-8");
		// String postContent=new String(
		// request.getParameter("content").getBytes("iso-8859-1"),"utf-8");
		// String studentEmail=new String(
		// request.getParameter("studentEmail").getBytes("iso-8859-1"),"utf-8");
		int courseID = Integer.parseInt(request.getParameter("courseID"));
//		String studentName = request.getParameter("studentName");
		String studentNumber = request.getParameter("studentNumber");
		String postTitle = request.getParameter("title");
		String postContent = request.getParameter("content");
		System.out.println(postContent);
//		String studentEmail = request.getParameter("studentEmail");
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月日 与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒 与MySQL中的TIME相对应
		String postTime = date.toString() + " " + time.toString();// 中间要加空格才行

		BbsTheme bbsTheme = new BbsTheme(0, courseID, studentNumber,postTitle, postContent, postTime, "", 0);
		System.out.println(bbsTheme.toString());
		BbsDao bDao = new BbsDaoImpl();
		bDao.addPost(bbsTheme);// 发帖
		SentMessage message = new SentMessage(1, "发帖成功");
		CommonUtil.renderJson(response, message);
	}

	private void findPostList(HttpServletRequest request, HttpServletResponse response) {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		BbsDao bDao = new BbsDaoImpl();
		List<BbsTheme> bbsThemes = bDao.findNoteByCourseID(courseID);  //获取该门课程对应的评论列表
		if(bbsThemes != null) {
			ReplyDao rDao = new ReplyDaoImpl();
			UserDao uDao = new UserDaoImpl();
			for(int i = 0; i < bbsThemes.size(); i++) {
				bbsThemes.get(i).setReplyCount(rDao.findPostReplyCount(bbsThemes.get(i).getPostID()));
				Student student = uDao.findByNumber(bbsThemes.get(i).getStudentNumber());
				bbsThemes.get(i).setStudentName(student.getName());
				bbsThemes.get(i).setStudentPhotoURL(student.getPhoto());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postList", bbsThemes);    //postList和评论列表之间实现映射
		CommonUtil.renderJson(response, map);

	}

	private void findCourseList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Integer> stuNumMap = new HashMap<String, Integer>();// JSON的key必须为string类型
		Map<String, Integer> noteNumMap = new HashMap<String, Integer>();
		int studentID = Integer.parseInt(request.getParameter("studentID"));

		StudentCourseDao sc = new StudentCourseDaoImpl();
		BbsDao bDao = new BbsDaoImpl();

		List<StudentCourse> sCourses = new ArrayList<>();
		sCourses = sc.findAll(studentID);
		for (StudentCourse sCourse : sCourses) {
			int id = sCourse.getCourseID();
			int stuNum = sc.findCountByCourseID(id);// 遍历每门课学生的数量
			int noteNum = bDao.findCoursePostCount(id);
			String nid = String.valueOf(id);
			stuNumMap.put(nid, stuNum);   //获取课程的关注人数
			noteNumMap.put(nid, noteNum);  //获取课程的评论数
		}
		map.put("relationList", sCourses);
		map.put("stuNum", stuNumMap);
		map.put("themeNum", noteNumMap);
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
