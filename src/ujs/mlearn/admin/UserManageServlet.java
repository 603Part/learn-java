package ujs.mlearn.admin;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
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

import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.encrypt.Encrypt;
import ujs.mlearn.entity.Student;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class AdminFunServlet
 */
public class UserManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserManageServlet() {
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
			response.sendRedirect(request.getContextPath() + "/adminLogin.jsp");
		}
		if (op.equals("register")) {
			register(request, response);
		} else if (op.equals("addUser")) {
			addUser(request, response);
		} else if (op.equals("adminTea")) {
			adminTea(request, response);
		} else if (op.equals("adminStu")) {
			adminStu(request, response);
		} else if (op.equals("editStu")) {
			editStu(request, response);
		} else if (op.equals("delStu")) {
			delStu(request, response);
		} else if (op.equals("editTea")) {
			editTea(request, response);
		} else if (op.equals("delTea")) {
			delTea(request, response);
		} else if (op.equals("modStu")) {
			modStu(request, response);
		} else if (op.equals("modTea")) {
			modTea(request, response);
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

	private void modTea(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		int userID = 0;
		String userName = "";
		String userSex = "";
		String userNumber = "";
		String userCollege = "";
		String userSpecialty = "";
		String userPass1 = "";
		String userPass2 = "";
		String userPhone = "";
		String userEmail = "";
		FileItem photo = null;
		String photoURL = null;
		for (FileItem item : list) {
			if (!item.isFormField()) {// 如果是文件
				if (item.getName() != null && item.getName() != "") {
					photo = item;
				}
			} else if ("userID".equals(item.getFieldName())) {
				userID = Integer.parseInt(new String(item.getString().getBytes("iso-8859-1"), "utf-8"));
			} else if ("userName".equals(item.getFieldName())) {
				userName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userSex".equals(item.getFieldName())) {
				userSex = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userNumber".equals(item.getFieldName())) {
				userNumber = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if("userCollege".equals(item.getFieldName())) {
				userCollege = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if("userSpecialty".equals(item.getFieldName())) {
				userSpecialty = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass1".equals(item.getFieldName())) {
				userPass1 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass2".equals(item.getFieldName())) {
				userPass2 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPhone".equals(item.getFieldName())) {
				userPhone = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userEmail".equals(item.getFieldName())) {
				userEmail = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			}
		}
		Teacher teacher = new Teacher(userID, userNumber, "", userName, userSex, userPhone, userEmail, "", "");
		String maType = "";
		if (photo != null) {
			String fileName = photo.getName();// 这是上传的文件名，包括后缀
			int point_index = fileName.lastIndexOf(".");
			maType = fileName.substring(point_index, fileName.length());// 文件格式
			if (!maType.equals(".png") && !maType.equals(".jpg") && !maType.equals(".gif") && !maType.equals(".jpeg")) {
				request.setAttribute("msg", "文件格式不对！");
				request.setAttribute("teacher", teacher);
				adminTea(request, response);
				return;
			}
			String photoDir = this.getServletContext().getRealPath("/") + "res/"; // 文件目录
			photoDir += "teacher/" + userNumber;
			photoURL = "res/teacher/" + userNumber + "/pic" + maType;
			photoDir = photoDir.replaceAll("\\\\", "/");
			File dir = new File(photoDir);
			if (!dir.exists()) {// 如果目录不存在就创建目录
				System.out.println(photoDir + "目录不存在");
				dir.mkdirs();
				System.out.println(photoDir + "目录创建成功");
			}
			File photoFile = new File(photoDir + "/pic" + maType);
			try {
				photo.write(photoFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "上传失败，请检查网络！");
				return;
			}
		}

		if (!userPass1.equals(userPass2)) {
			request.setAttribute("message", "两次密码不一致！");
			request.setAttribute("teacher", teacher);
			adminTea(request, response);
			return;
		}
		if (userEmail != null && !userEmail.contains("@")) {
			request.setAttribute("message", "邮箱格式错误！");
			request.setAttribute("teacher", teacher);
			adminTea(request, response);
			return;
		}
		if (userPass1 != null && !userPass1.equals("")) {
			String userPass = Encrypt.getSecretCode(userNumber, userPass1);
			teacher.setPassword(userPass);
		} else {
			teacher.setPassword(null);
		}
		teacher.setPhoto(photoURL);
		teacher.setCollege(userCollege);
		teacher.setSpecialty(userSpecialty);
		TeacherDao tDao = new TeacherDaoImpl();
		tDao.update(teacher);
		adminTea(request, response);
	}

	private void modStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		int userID = 0;
		String userName = "";
		String userSex = "";
		String userNumber = "";
		String userCollege = "";
		String userSpecialty = "";
		String userPass1 = "";
		String userPass2 = "";
		String userPhone = "";
		String userEmail = "";
		String signature = "";
		FileItem photo = null;
		String photoURL = null;
		for (FileItem item : list) {
			if (!item.isFormField()) {// 如果是文件
				if (item.getName() != null && item.getName() != "") {
					photo = item;
				}
			} else if ("userID".equals(item.getFieldName())) {
				userID = Integer.parseInt(new String(item.getString().getBytes("iso-8859-1"), "utf-8"));
			} else if ("userName".equals(item.getFieldName())) {
				userName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userSex".equals(item.getFieldName())) {
				userSex = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userNumber".equals(item.getFieldName())) {
				userNumber = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if("userCollege".equals(item.getFieldName())) {
				userCollege = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if("userSpecialty".equals(item.getFieldName())) {
				userSpecialty = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass1".equals(item.getFieldName())) {
				userPass1 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass2".equals(item.getFieldName())) {
				userPass2 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPhone".equals(item.getFieldName())) {
				userPhone = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userEmail".equals(item.getFieldName())) {
				userEmail = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("signature".equals(item.getFieldName())) {
				signature = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			}
		}
		Student student = new Student(userID, userNumber, "", userName, userSex, userPhone, userEmail, "", signature,
				"");
		String maType = "";
		if (photo != null) {
			String fileName = photo.getName();// 这是上传的文件名，包括后缀
			int point_index = fileName.lastIndexOf(".");
			maType = fileName.substring(point_index, fileName.length());// 文件格式
			if (!maType.equals(".png") && !maType.equals(".jpg") && !maType.equals(".gif") && !maType.equals(".jpeg")) {
				request.setAttribute("student", student);
				request.setAttribute("msg", "文件格式不对！");
				adminStu(request, response);
				return;
			}
			String photoDir = this.getServletContext().getRealPath("/") + "res/"; // 文件目录
			photoDir += "user/" + userNumber;
			photoURL = "res/user/" + userNumber + "/pic" + maType;
			photoDir = photoDir.replaceAll("\\\\", "/");
			File dir = new File(photoDir);
			if (!dir.exists()) {// 如果目录不存在就创建目录
				System.out.println(photoDir + "目录不存在");
				dir.mkdirs();
				System.out.println(photoDir + "目录创建成功");
			}
			File photoFile = new File(photoDir + "/pic" + maType);
			try {
				photo.write(photoFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "上传失败，请检查网络！");
				return;
			}
		}

		if (!userPass1.equals(userPass2)) {
			request.setAttribute("message", "两次密码不一致！");
			request.setAttribute("student", student);
			request.setAttribute("msg", "文件格式不对！");
			adminStu(request, response);
			return;
		}
		if (userEmail != null && !userEmail.contains("@")) {
			request.setAttribute("message", "邮箱格式错误！");
			request.setAttribute("student", student);
			request.setAttribute("msg", "文件格式不对！");
			adminStu(request, response);
			return;
		}
		UserDao uDao = new UserDaoImpl();
		if (userPass1 != null && !userPass1.equals("")) {
			String userPass = Encrypt.getSecretCode(userNumber, userPass1);
			student.setPassword(userPass);
		} else {
			student.setPassword(null);
		}
		student.setPhoto(photoURL);
		student.setCollege(userCollege);
		student.setSpecialty(userSpecialty);
		uDao.update(student);
		adminStu(request, response);
	}

	private void editTea(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userID = Integer.parseInt(request.getParameter("userID"));
		TeacherDao tDao = new TeacherDaoImpl();
		Teacher teacher = tDao.findById(userID);
		teacher.setLogintime(teacher.getLogintime().substring(0, 19));

		request.setAttribute("teacher", teacher);
		request.getRequestDispatcher("/WEB-INF/admin/userManage/teacher/modTea.jsp").forward(request, response);
	}

	private void editStu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userID = Integer.parseInt(request.getParameter("userID"));
		UserDao uDao = new UserDaoImpl();
		Student student = uDao.findById(userID);
		student.setLogintime(student.getLogintime().substring(0, 19));

		request.setAttribute("student", student);
		request.getRequestDispatcher("/WEB-INF/admin/userManage/student/modStu.jsp").forward(request, response);
	}

	private void adminStu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao uDao = new UserDaoImpl();
		List<Student> sList = uDao.findAll();

		request.setAttribute("sList", sList);
		request.getRequestDispatcher("/WEB-INF/admin/userManage/student/studentList.jsp").forward(request, response);
	}

	private void adminTea(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TeacherDao tDao = new TeacherDaoImpl();
		List<Teacher> tList = tDao.findAll();

		System.out.println(tList);
		request.setAttribute("tList", tList);
		request.getRequestDispatcher("/WEB-INF/admin/userManage/teacher/teacherList.jsp").forward(request, response);
	}

	private void delStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userID = Integer.parseInt(request.getParameter("userID"));
		UserDao uDao = new UserDaoImpl();
		uDao.del(userID);
		List<Student> sList = uDao.findAll();

		request.setAttribute("sList", sList);
		request.getRequestDispatcher("/WEB-INF/admin/userManage/student/studentList.jsp").forward(request, response);
	}

	private void delTea(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userID = Integer.parseInt(request.getParameter("userID"));
		TeacherDao tDao = new TeacherDaoImpl();
		tDao.del(userID);
		List<Teacher> tList = tDao.findAll();

		request.setAttribute("tList", tList);
		request.getRequestDispatcher("/WEB-INF/admin/userManage/teacher/teacherList.jsp").forward(request, response);
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long currentTimeMillis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(currentTimeMillis); // 只有年月日
																	// 与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒 与MySQL中的TIME相对应
		String loginTime = date.toString() + " " + time.toString();// 中间要加空格才行

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		String userType = request.getParameter("userType");
		String userName = request.getParameter("userName");
		String userSex = request.getParameter("userSex");
		String userNumber = request.getParameter("userNumber");
		String userCollege = request.getParameter("userCollege");
		String userSpecialty = request.getParameter("userSpecialty");
		String userPass1 = request.getParameter("userPass1");
		String userPass2 = request.getParameter("userPass2");
		String userPhone = request.getParameter("userPhone");
		String userEmail = request.getParameter("userEmail");
		FileItem photo = null;
		String photoURL = null;
		for (FileItem item : list) {
			if (!item.isFormField()) {// 如果是文件
				if (item.getName() != null && item.getName() != "") {
					photo = item;
				}
			} else if ("userType".equals(item.getFieldName())) {
				userType = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userName".equals(item.getFieldName())) {
				userName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userSex".equals(item.getFieldName())) {
				userSex = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userNumber".equals(item.getFieldName())) {
				userNumber = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if("userCollege".equals(item.getFieldName())) {
				userCollege = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if("userSpecialty".equals(item.getFieldName())) {
				userSpecialty = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass1".equals(item.getFieldName())) {
				userPass1 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass2".equals(item.getFieldName())) {
				userPass2 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPhone".equals(item.getFieldName())) {
				userPhone = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userEmail".equals(item.getFieldName())) {
				userEmail = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			}
		}
		String maType = "";
		if (photo != null) {
			String fileName = photo.getName();// 这是上传的文件名，包括后缀
			int point_index = fileName.lastIndexOf(".");
			maType = fileName.substring(point_index, fileName.length());// 文件格式
			if (!maType.equals(".png") && !maType.equals(".jpg") && !maType.equals(".gif") && !maType.equals(".jpeg")) {
				System.out.println("文件格式不对！");
				request.setAttribute("msg", "文件格式不对！");
				register(request, response);
				return;
			}
			String photoDir = this.getServletContext().getRealPath("/") + "res/"; // 文件目录
			if (userType.equals("教师")) {
				photoDir += "teacher/" + userNumber;
				photoURL = "res/teacher/" + userNumber + "/pic" + maType;
			} else if (userType.equals("学生")) {
				photoDir += "user/" + userNumber;
				photoURL = "res/user/" + userNumber + "/pic" + maType;
			}
			photoDir = photoDir.replaceAll("\\\\", "/");
			File dir = new File(photoDir);
			if (!dir.exists()) {// 如果目录不存在就创建目录
				System.out.println(photoDir + "目录不存在");
				dir.mkdirs();
				System.out.println(photoDir + "目录创建成功");
			}
			File photoFile = new File(photoDir + "/pic" + maType);
			try {
				photo.write(photoFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "上传失败，请检查网络！");
				return;
			}
		}

		if (!userPass1.equals(userPass2)) {
			System.out.println("两次密码不一致！");
			request.setAttribute("message", "两次密码不一致！");
			request.getRequestDispatcher("/WEB-INF/admin/userManage/register/register.jsp").forward(request, response);
			return;
		}
		if (userEmail != null && !userEmail.contains("@")) {
			System.out.println("邮箱格式错误！");
			request.setAttribute("message", "邮箱格式错误！");
			request.getRequestDispatcher("/WEB-INF/admin/userManage/register/register.jsp").forward(request, response);
			return;
		}
		if (userType.equals("老师")) {
			TeacherDao tDao = new TeacherDaoImpl();
			if (tDao.findByNumber(userNumber) != null) {
				request.setAttribute("message", "用户已存在！");
				request.getRequestDispatcher("/WEB-INF/admin/userManage/register/register.jsp").forward(request,
						response);
				return;
			}
			if (photo != null) {
				photoURL = "res/teacher/" + userNumber + "/pic" + maType;
			}
			String userPass = Encrypt.getSecretCode(userNumber, userPass1);
			Teacher teacher = new Teacher(0, userNumber, userPass, userName, userSex, userPhone, userEmail, photoURL,
					loginTime);
			teacher.setCollege(userCollege);
			teacher.setSpecialty(userSpecialty);
			tDao.add(teacher);
		} else if (userType.equals("学生")) {
			UserDao uDao = new UserDaoImpl();
			if (uDao.findByNumber(userNumber) != null) {
				request.setAttribute("message", "用户已存在！");
				request.getRequestDispatcher("/WEB-INF/admin/userManage/register/register.jsp").forward(request,
						response);
				return;
			}
			if (photo != null) {
				photoURL = "res/user/" + userNumber + "/pic" + maType;
			}
			String userPass = Encrypt.getSecretCode(userNumber, userPass1);
			Student student = new Student(0, userNumber, userPass, userName, userSex, userPhone, userEmail, photoURL,
					"", loginTime);
			student.setCollege(userCollege);
			student.setSpecialty(userSpecialty);
			uDao.add(student);
		}
		if (userType.equals("教师")) {
			adminTea(request, response);
		} else if (userType.equals("学生")) {
			adminStu(request, response);
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/admin/userManage/register/register.jsp").forward(request, response);
	}

}
