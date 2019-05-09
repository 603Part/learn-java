package ujs.mlearn.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebCourseServlet
 */
public class WebCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebCourseServlet() {
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
		if (op.equals("findMyCourse")) {
			findMyCourse(request, response);
		} else if (op.equals("addCourse")) {
			addCourse(request, response);
		} else if (op.equals("goShowPage")) {
			goShowPage(request, response);
		} else if (op.equals("findCourse")) {
			findCourse(request, response);
		} else if (op.equals("editCourse")) {
			editCourse(request, response);
		} else if (op.equals("delCourse")) {
			delCourse(request, response);
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

	private void editCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String tnumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		int courseID = 0;
		String courseName = null;
		String courseAbstract = null;
		String detailInfo = null;
		String courseUrl = null;

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		FileItem photo = null;
		for (FileItem item : list) {
			if (!item.isFormField()) {// 如果是文件
				if (item.getName() != null && item.getName() != "") {
					photo = item;
				}
			} else {
				if ("courseID".equals(item.getFieldName())) {
					courseID = Integer.parseInt(new String(item.getString().getBytes("iso-8859-1"), "utf-8"));
				} else if ("courseName".equals(item.getFieldName())) {
					courseName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
				} else if ("courseAbstract".equals(item.getFieldName())) {
					courseAbstract = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
				} else if ("detailInfo".equals(item.getFieldName())) {
					detailInfo = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
				} else if ("courseUrl".equals(item.getFieldName())) {
					courseUrl = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
				}
			}
		}

		if (photo != null) {
			String fileName = photo.getName();// 这是上传的文件名，包括后缀
			int point_index = fileName.lastIndexOf(".");
			String maType = fileName.substring(point_index, fileName.length());// 文件格式
			if (!maType.equals(".jpg") && !maType.equals(".png") && !maType.equals(".gif")) {
				System.out.println("文件格式不对！");
				request.setAttribute("message", "图片格式不对！");
				courseID = Integer.parseInt(request.getParameter("courseID"));
				CourseDao cDao = new CourseDaoImpl();
				Course course = cDao.findById(courseID);
				request.setAttribute("course", course);
				request.getRequestDispatcher("/WEB-INF/admin/classManage/class/modClass.jsp").forward(request,
						response);
				return;
			}

			new File(this.getServletContext().getRealPath("/") + courseUrl).delete();
			String fileDir = this.getServletContext().getRealPath("/");// 文件目录
			fileDir = fileDir.replaceAll("\\\\", "/");
			File dir = new File(fileDir);
			if (!dir.exists()) {// 如果目录不存在就创建目录
				System.out.println(fileDir + "目录不存在");
				dir.mkdirs();
				System.out.println(fileDir + "目录创建成功");
			}

			String serverFileURL = fileDir + "res/course/" + courseID + "/cover" + maType;
			File serverFile = new File(serverFileURL);
			System.out.println(serverFile);
			try {
				photo.write(serverFile);
				courseUrl = "res/course/" + courseID + "/cover" + maType;
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "上传失败，请检查网络！");
				request.getRequestDispatcher("/WebCourseServlet?action=findMyCourse").forward(request, response);
				return;
			}
		}
		Course course = new Course(courseID, courseName, tnumber, courseAbstract, detailInfo, courseUrl);
		CourseDao cDao = new CourseDaoImpl();
		cDao.updateCourse(course);
		request.getRequestDispatcher("/WebCourseServlet?action=findMyCourse").forward(request, response);
	}

	private void findCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		CourseDao courseDao = new CourseDaoImpl();
		Course course = courseDao.findById(courseID);

		request.setAttribute("course", course);
		request.getRequestDispatcher("/WEB-INF/teacher/course/editcourse.jsp").forward(request, response);
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TeacherDao tDao = new TeacherDaoImpl();
		List<Teacher> teachers = tDao.findAll();
		request.setAttribute("teachers", teachers);
		request.getRequestDispatcher("/WEB-INF/teacher/course/addcourse.jsp").forward(request, response);
	}

	private void addCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String courseName = null;
		String courseAbstract = null;
		String detailInfo = null;

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		FileItem photo = null;
		for (FileItem item : list) {
			if (!item.isFormField()) {// 如果是文件
				if (item.getName() != null && item.getName() != "") {
					photo = item;
				}
			} else {
				if ("courseName".equals(item.getFieldName())) {
					courseName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
				} else if ("courseAbstract".equals(item.getFieldName())) {
					courseAbstract = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
				} else if ("detailInfo".equals(item.getFieldName())) {
					detailInfo = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
				}
			}
		}

		request.setAttribute("courseName", courseName);
		request.setAttribute("courseAbstract", courseAbstract);
		request.setAttribute("detailInfo", detailInfo);

		String fileName = photo.getName();// 这是上传的文件名，包括后缀
		int point_index = fileName.lastIndexOf(".");
		String maType = fileName.substring(point_index, fileName.length());// 文件格式
		if (!maType.equals(".jpg") && !maType.equals(".png") && !maType.equals(".gif") && !maType.equals(".jpeg")) {
			request.setAttribute("message", "图片格式不对！");
			goShowPage(request, response);
			return;
		}
		String tnumumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cDao = new CourseDaoImpl();
		Course course = new Course(0, courseName, tnumumber, courseAbstract, detailInfo);
		String courseUrl = cDao.add(course, maType);
		if (courseUrl.equals("course has exited")) {
			request.setAttribute("message", "您已有一门名为《" + courseName + "》的课程！");
			goShowPage(request, response);
			return;
		}

		String fileDir = this.getServletContext().getRealPath("/") + courseUrl;// 文件目录
		fileDir = fileDir.replaceAll("\\\\", "/");
		File dir = new File(fileDir);
		if (!dir.exists()) {// 如果目录不存在就创建目录
			System.out.println(fileDir + "目录不存在");
			dir.mkdirs();
			System.out.println(fileDir + "目录创建成功");
		}

		String serverFileURL = fileDir + "/cover" + maType;
		File serverFile = new File(serverFileURL);
		try {
			photo.write(serverFile);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "上传失败，请检查网络！");
			return;
		}
		request.setAttribute("message", "添加课程成功！");
		request.getRequestDispatcher("/WebCourseServlet?action=findMyCourse").forward(request, response);
	}

	private void findMyCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherNumber);
		request.setAttribute("cList", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/course/courselist.jsp").forward(request, response);
	}

	private void delCourse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		CourseDao cDao = new CourseDaoImpl();
		cDao.del(courseID);
		request.getRequestDispatcher("/WebCourseServlet?action=findMyCourse").forward(request, response);
	}

}
