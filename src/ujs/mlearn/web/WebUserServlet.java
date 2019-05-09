package ujs.mlearn.web;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
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
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.encrypt.Encrypt;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebUserServlet
 */
public class WebUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebUserServlet() {
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
		String op = request.getParameter("action");
		System.out.println(op);
		if (op.equals("login_register")) {
			login_register(request, response);
		} else if (op.equals("logout")) {
			logout(request, response);
		} else if (op.equals("reLogin")) {
			reLogin(request, response);
		} else if (op.equals("register")) {
			register(request, response);
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

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		String teacherNumber = teacher.gettId();
		try {
			request.getSession().invalidate();
			TeacherDao teacherDao = new TeacherDaoImpl();
			teacherDao.changeLoginState(teacherNumber, Teacher.LOGOUT);
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login_register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		System.out.println(login);
		String register = request.getParameter("register");
		System.out.println(register);
		if (login != null && !login.equals("")) {
			login(request, response);
		} else if (register != null && !register.equals("")) {
			goToRegister(request, response);
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String teacherNumber = request.getParameter("teacherNumber");
		String password = request.getParameter("password");
		TeacherDao teacherDao = new TeacherDaoImpl();
		String pass = Encrypt.getSecretCode(teacherNumber, password);
		Teacher teacher = teacherDao.login(teacherNumber, pass);
		if (teacher == null) {
			request.setAttribute("error", "用户名或密码错误");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			System.out.println("失败");
			return;
		} else {
			long currentTimeMillis = System.currentTimeMillis();
			Date date = new Date(currentTimeMillis); // 只有年月日与MySQL中的DATE相对应
			Time time = new Time(currentTimeMillis); // 只有时分秒与MySQL中的TIME相对应
			String logintime = date.toString() + " " + time.toString();// 中间要加空格才行
			teacherDao.updateUser("logintime", logintime, teacher.getUserId());
			teacherDao.changeLoginState(teacherNumber, Teacher.LOGIN);
			request.getSession().setAttribute("teacher", teacher);
			request.getSession().setAttribute("login", "1");
			System.out.println("登录成功 ");
			request.getRequestDispatcher("/index/index.jsp").forward(request, response);
		}
	}

	private void reLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	private void goToRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/teacher/register/register.jsp").forward(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月日
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

		String userName = request.getParameter("userName");
		String userSex = request.getParameter("userSex");
		String userNumber = request.getParameter("userNumber");
		String userPass1 = request.getParameter("userPass1");
		String userPass2 = request.getParameter("userPass2");
		String userCollege = request.getParameter("userCollege");
		String userSpecialty =  request.getParameter("userSpecialty");
		String userPhone = request.getParameter("userPhone");
		String userEmail = request.getParameter("userEmail");
		FileItem photo = null;
		String photoURL = null;
		for (FileItem item : list) {
			if (!item.isFormField()) {// 如果是文件
				if (item.getName() != null && item.getName() != "") {
					photo = item;
				}
			} else if ("userName".equals(item.getFieldName())) {
				userName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userSex".equals(item.getFieldName())) {
				userSex = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userNumber".equals(item.getFieldName())) {
				userNumber = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass1".equals(item.getFieldName())) {
				userPass1 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPass2".equals(item.getFieldName())) {
				userPass2 = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userPhone".equals(item.getFieldName())) {
				userPhone = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			} else if ("userEmail".equals(item.getFieldName())) {
				userEmail = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			}else if ("userCollege".equals(item.getFieldName())) {
				userCollege = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			}else if ("userSpecialty".equals(item.getFieldName())) {
				userSpecialty = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
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
		TeacherDao tDao = new TeacherDaoImpl();
		if (tDao.findByNumber(userNumber) != null) {
			request.setAttribute("message", "用户已存在！");
			request.getRequestDispatcher("/WEB-INF/admin/userManage/register/register.jsp").forward(request, response);
			return;
		}
		if (photo != null) {
			photoURL = "res/teacher/" + userNumber + "/pic" + maType;
		}
		String userPass = Encrypt.getSecretCode(userNumber, userPass1);
		Teacher teacher = new Teacher(userNumber, userName, userPass, userSex, userEmail,userPhone, loginTime, photoURL,userCollege,userSpecialty,0);
		tDao.add(teacher);

		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

}
