package ujs.mlearn.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import ujs.mlearn.dao.CourseMaterialDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.CourseMaterialDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.CourseMaterial;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebMaterialServlet
 */
public class WebMaterialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebMaterialServlet() {
		super();
	}

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
		} else if (op.equals("addResource")) {
			addResource(request, response);
		} else if (op.equals("findMyCourseList")) {
			findMyCourseList(request, response);
		} else if (op.equals("findCourseResourceList")) {
			findCourseResourceList(request, response);
		} else if (op.equals("downloadResource")) {
			downloadResource(request, response);
		} else if (op.equals("delResource")) {
			delResource(request, response);
		} else if (op.equals("editResource")) {
			editResource(request, response);
		} else if (op.equals("updateResource")) {
			updateResource(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cDao = new CourseDaoImpl();
		List<Course> courses = cDao.findMyCourse(teacherNumber);
		request.setAttribute("courses", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/resource/addResource.jsp").forward(request, response);
	}

	private void addResource(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		String resName = "";
		String fileURL = "";
		long currentTimeMillis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(currentTimeMillis); // 只有年月日
																	// 与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒 与MySQL中的TIME相对应
		String uploadTime = date.toString() + " " + time.toString();// 中间要加空格才行

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		List<FileItem> files = new ArrayList<FileItem>();
		String courseName = null;
		for (FileItem item : list) {
			if (!item.isFormField()) {// 如果是文件
				if (item.getName() != null && item.getName() != "") {
					files.add(item);
				}
			} else if ("course".equals(item.getFieldName())) {
				courseName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
			}
		}
		if (files.size() == 0) {
			System.out.println("未选择文件！");
			request.setAttribute("msg", "请选择文件！");
			goShowPage(request, response);
			return;
		}
		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findIdByName(courseName, teacherNumber);
		for (int i = 0; i < files.size(); i++) {
			FileItem item = files.get(i);

			resName = item.getName();// 这是上传的文件名，包括后缀
			int point_index = resName.lastIndexOf(".");
			String maType = resName.substring(point_index, resName.length());// 文件格式
			if (!maType.equals(".zip")) {
				System.out.println("文件格式不对！");
				request.setAttribute("msg", "文件格式不对！");
				goShowPage(request, response);
				return;
			}
			String resDir = this.getServletContext().getRealPath("/") + "res/course/" + course.getCourseID()
					+ "/material";// 文件目录
			/*
			 * "\\" 被正则表达式引擎解释为 \，所以在正则表达式中需要使用四个反斜杠。
			 * 也就是说，前两个反斜杠在字符串中被解释为一个反斜杠，后两个也被解释为一个反斜杠，
			 * 这时解释完毕后变成两个反斜杠，再由正则表达式解释两个反斜杠，就又解释成了一个反斜杠，
			 * 所以，在正则表达式中要匹配一个反斜杠时，需要四个反斜杠。
			 */
			resDir = resDir.replaceAll("\\\\", "/");
			File dir = new File(resDir);
			if (!dir.exists()) {// 如果目录不存在就创建目录
				System.out.println(resDir + "目录不存在");
				dir.mkdirs();
				System.out.println(resDir + "目录创建成功");
			}
			long cTime = System.currentTimeMillis();
			Date upDate = new Date(cTime);
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String serverFileName = fmt.format(upDate);
			serverFileName = serverFileName + maType;

			fileURL = "res/course/" + course.getCourseID() + "/material/" + serverFileName;

			long size = item.getSize();
			int dir_index = resName.lastIndexOf("\\");
			resName = resName.substring(dir_index + 1, resName.length());// 文件名

			String serverFileURL = resDir + "/" + serverFileName;
			File serverFile = new File(serverFileURL);
			System.out.println(serverFileURL);
			try {
				item.write(serverFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "上传失败，请检查网络！");
				return;
			}
			CourseMaterial courseMaterial = new CourseMaterial();
			courseMaterial.setCourseID(course.getCourseID());
			courseMaterial.setTeacherNumber(teacherNumber);
			courseMaterial.setResTitle(resName);
			courseMaterial.setResUrl(fileURL);
			courseMaterial.setPublishTime(uploadTime);
			courseMaterial.setSize(size);
			CourseMaterialDao cmDao = new CourseMaterialDaoImpl();
			cmDao.addRes(courseMaterial);
		}
		request.setAttribute("msg", "上传成功，请点击[管理文件]查看！");
		request.getRequestDispatcher(
				"/WebMaterialServlet?action=findCourseResourceList&courseID=" + course.getCourseID())
				.forward(request, response);
	}

	private void findMyCourseList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String teacherNumber = ((Teacher) request.getSession().getAttribute("teacher")).gettId();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherNumber);
		request.setAttribute("cList", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/resource/courselist.jsp").forward(request, response);
	}

	private void findCourseResourceList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		CourseMaterialDao mDao = new CourseMaterialDaoImpl();
		List<CourseMaterial> mList = mDao.findCourseMaterial(courseID);
		if (mList != null) {
			CourseDao cDao = new CourseDaoImpl();
			for (int i = 0; i < mList.size(); i++) {
				mList.get(i).setCourseName(cDao.findById(courseID).getCourseName());
			}
		}
		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findById(courseID);
		request.setAttribute("courseName", course.getCourseName());
		request.setAttribute("mList", mList);
		request.getRequestDispatcher("/WEB-INF/teacher/resource/resourceList.jsp").forward(request, response);
	}

	private void downloadResource(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int resID = Integer.parseInt(request.getParameter("resID"));
		CourseMaterialDao mDao = new CourseMaterialDaoImpl();
		CourseMaterial courseMaterial = mDao.findMaterialByID(resID);
		String resName = courseMaterial.getResTitle();
		response.setContentType(getServletContext().getMimeType(resName));
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(resName, "UTF-8"));
		String path = getServletContext().getRealPath("/") + courseMaterial.getResUrl();
		path = path.replaceAll("\\\\", "/");
		File file = new File(path);
		response.setHeader("Content-Length", String.valueOf(file.length()));// 需要这句才能获取文件大小
		InputStream in = new BufferedInputStream(new FileInputStream(path));

		OutputStream out = response.getOutputStream();
		byte[] buff = new byte[1024];
		int len = 0;
		while ((len = in.read(buff)) != -1) {
			for(int i = 0; i < len; i++) {
				System.out.print(buff[i]);
			}
			System.out.println();
			out.write(buff, 0, len);
		}
		in.close();
	}

	private void delResource(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int resID = Integer.parseInt(request.getParameter("resID"));
		CourseMaterialDao mDao = new CourseMaterialDaoImpl();
		CourseMaterial resource = mDao.findMaterialByID(resID);
		String resName = resource.getResTitle();
		String path = getServletContext().getRealPath("/") + resource.getResUrl();
		path = path.replaceAll("\\\\", "/");
		File file = new File(path);
		System.out.println(path);
		if (file.exists()) {
			file.delete();
			System.out.println("[" + resName + "]删除成功！");
		} else {
			System.out.println("[" + resName + "]不存在,无法删除！");
		}
		mDao.delMaterialByID(resID);
		request.getRequestDispatcher(
				"/WebMaterialServlet?action=findCourseResourceList&courseID=" + resource.getCourseID())
				.forward(request, response);
	}

	private void editResource(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int resID = Integer.parseInt(request.getParameter("resID"));
		CourseMaterialDao mDao = new CourseMaterialDaoImpl();
		CourseMaterial resource = mDao.findMaterialByID(resID);
		CourseDao cDao = new CourseDaoImpl();
		List<Course> courses = cDao.findMyCourse(resource.getTeacherNumber());
		resource.setCourseName(cDao.findById(resource.getCourseID()).getCourseName());
		request.setAttribute("courses", courses);
		request.setAttribute("resource", resource);
		request.getRequestDispatcher("/WEB-INF/teacher/resource/modResource.jsp").forward(request, response);
	}

	private void updateResource(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int resID = Integer.parseInt(request.getParameter("resID"));
		String teacherNumber = ((Teacher)request.getSession().getAttribute("teacher")).gettId();
		String courseName = request.getParameter("course");
		String resTitle = request.getParameter("resTitle");
		String type = request.getParameter("type");
		CourseDao cDao = new CourseDaoImpl();
		Course course = cDao.findIdByName(courseName, teacherNumber);
		CourseMaterial resource = new CourseMaterial();
		resource.setResID(resID);
		resource.setTeacherNumber(teacherNumber);
		resource.setCourseID(course.getCourseID());
		resource.setResTitle(resTitle+type);
		resource.setPublishTime(null);
		CourseMaterialDao mDao = new CourseMaterialDaoImpl();
		mDao.updateMaterial(resource);
		
		request.getRequestDispatcher(
				"/WebMaterialServlet?action=findCourseResourceList&courseID=" + course.getCourseID())
				.forward(request, response);
	}

}
