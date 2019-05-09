package ujs.mlearn.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
 * Servlet implementation class WebInfoServlet
 */
@WebServlet("/WebInfoServlet")
public class WebInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebInfoServlet() {
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
		} else if (op.equals("updateInfo")) {
			try {
				updateInfo(request, response);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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

	private void goShowPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if (teacher == null) {
			System.out.println("用户未登录");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		request.getSession().removeAttribute("teacher");
		int userID = teacher.getUserId();
		TeacherDao tDao = new TeacherDaoImpl();
		teacher = tDao.findById(userID);
		request.getSession().setAttribute("teacher", teacher);
		request.setAttribute("teacher", teacher);
		request.getRequestDispatcher("/WEB-INF/teacher/info/modTea.jsp").forward(request, response);
	}

	private void updateInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InterruptedException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
			System.out.println(list.get(0).getName());
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
				goShowPage(request, response);
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
			goShowPage(request, response);
			return;
		}
		if (userEmail != null && !userEmail.contains("@")) {
			request.setAttribute("message", "邮箱格式错误！");
			goShowPage(request, response);
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
		System.out.println(teacher);
		tDao.update(teacher);
		if (userPass1 != null && !userPass1.equals("")) {
			request.setAttribute("message", "修改成功, 请重新登录！");
			request.getSession().removeAttribute("teacher");
			goShowPage(request, response);
		} else {
			request.setAttribute("message", "修改成功!");
			goShowPage(request, response);
		}
	}
}
