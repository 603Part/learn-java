package ujs.mlearn.servlet;

import java.io.File;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.HomeworkDao;
import ujs.mlearn.dao.StuHomeworkDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.HomeworkDaoImpl;
import ujs.mlearn.dao.impl.StuHomeworkDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.entity.Homework;
import ujs.mlearn.entity.SentMessage;
import ujs.mlearn.entity.StuHomework;

/**
 * Servlet implementation class HomeworkServlet
 */
public class HomeworkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeworkServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op = request.getParameter("operation");
		System.out.println(op);
		if (op.equals("findCourseHw")) {
			findCourseHw(request, response);// 找一门课的所有作业
		} else if (op.equals("uploadHomework")) {
			uploadHomework(request, response);// 学生上传作业
		} else if (op.equals("findMyHw")) {
			findMyHw(request, response);// 查看学生提交的作业
		} else if (op.equals("delMyHw")) {
			delMyHw(request, response);// 删除上传的作业
		}
	}

	private void uploadHomework(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		System.out.println("学生开始上传作业");
		request.setCharacterEncoding("utf-8");
		int studentID = Integer.parseInt((request.getParameter("studentID")));
		int hwID = Integer.parseInt((request.getParameter("hwID")));
		String stuWorkTitle = request.getParameter("stuWorkTitle");
		
		StuHomeworkDao shDao = new StuHomeworkDaoImpl();
		StuHomework preHw = shDao.findHomework(hwID, studentID);
		if (preHw != null) {
			String shwPath = this.getServletContext().getRealPath("/")+preHw.getHwUrl();
			shwPath = shwPath.replaceAll("\\\\", "/");
			File file = new File(shwPath);
			if(file.exists()) {
				file.delete();
			}
			shwPath = shwPath.substring(0, shwPath.lastIndexOf("/"));
			File dir = new File(shwPath);
			if(dir.exists()) {
				dir.delete();
			}
		}
		HomeworkDao hDao = new HomeworkDaoImpl();
		int courseID = hDao.findHwByHwID(hwID).getCourseID();
		String hwUrl = "";
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月日 与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒 与MySQL中的TIME相对应
		String subTime = date.toString() + " " + time.toString();// 中间要加空格才行

		long size = 0;
		String fileDir = "";// 完整的文件目录
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
			for (FileItem item : list) {
				if (!item.isFormField()) {// 如果是文件
					if (!stuWorkTitle.equals("")) {
						hwUrl = "res/course/"+courseID+"/homework/" + hwID + "/" + studentID + "/" + stuWorkTitle;
						fileDir = this.getServletContext().getRealPath("/") + "/res/course/"+courseID+"/homework/" + hwID + "/"
								+ studentID;
						fileDir = fileDir.replaceAll("\\\\", "/");// 这就是最后的目录路径
					}
					File dir = new File(fileDir);
					if (!dir.exists()) {// 如果目录不存在就创建目录
						dir.mkdirs();
						System.out.println("目录不存在，现已创建成功");
					}
					size = item.getSize();
					String url = fileDir + "/" + stuWorkTitle;
					System.out.println("文件存放路径" + url);
					File serverFile = new File(url);
					try {
						item.write(serverFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("成功上传作业");
				}
			}
		} catch (Exception e) {
			SentMessage message = new SentMessage(0, "上传作业失败");
			CommonUtil.renderJson(response, message);
			e.printStackTrace();
		}
		StuHomework sHomework = new StuHomework(0, 0, hwID, subTime, hwUrl, stuWorkTitle, studentID, size);
		if (preHw == null) {
			shDao.uploadHomework(sHomework);
		} else {
			shDao.updateHomework(sHomework, preHw.getShwID());
		}
		SentMessage message = new SentMessage(1, "上传作业成功");
		CommonUtil.renderJson(response, message);
	}

	private void findCourseHw(HttpServletRequest request, HttpServletResponse response) {
		int courseID = Integer.parseInt((request.getParameter("courseID")));
		HomeworkDao hDao = new HomeworkDaoImpl();
		List<Homework> hList = hDao.findHwByCourseID(courseID);
		if(hList != null) {
			CourseDao cDao = new CourseDaoImpl();
			TeacherDao tDao = new TeacherDaoImpl();
			for(int i = 0; i < hList.size(); i++) {
				hList.get(i).setCourseName(cDao.findById(hList.get(i).getCourseID()).getCourseName());
				hList.get(i).setTeacherName(tDao.findByNumber(hList.get(i).getTeacherNumber()).getName());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hList", hList);
		CommonUtil.renderJson(response, map);
	}
	
	private void findMyHw(HttpServletRequest request, HttpServletResponse response) {
		int userID = Integer.parseInt((request.getParameter("userID")));
		int courseID = Integer.parseInt((request.getParameter("courseID")));
		StuHomeworkDao shDao = new StuHomeworkDaoImpl();
		List<StuHomework> shList = shDao.findMyCourseHomework(userID, courseID);
		if(shList != null) {
			HomeworkDao hDao = new HomeworkDaoImpl();
			UserDao uDao = new UserDaoImpl();
			CourseDao cDao = new CourseDaoImpl();
			for(int i = 0; i < shList.size(); i++) {
				shList.get(i).setCourseID(courseID);
				shList.get(i).setCourseName(cDao.findById(courseID).getCourseName());
				shList.get(i).setHwTitle(hDao.findHwByHwID(shList.get(i).getHwID()).getHwTitle());
				shList.get(i).setUserID(userID);
				shList.get(i).setSid(uDao.findById(shList.get(i).getUserID()).getsId());
				shList.get(i).setStudentName(uDao.findById(shList.get(i).getUserID()).getName());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shList", shList);
		CommonUtil.renderJson(response, map);
	}
	
	private void delMyHw(HttpServletRequest request, HttpServletResponse response) {
		int shwID = Integer.parseInt((request.getParameter("shwID")));
		StuHomeworkDao shDao = new StuHomeworkDaoImpl();
		StuHomework shw = shDao.findOneMyHomework(shwID);
		String shwPath = this.getServletContext().getRealPath("/")+shw.getHwUrl();
		shwPath = shwPath.replaceAll("\\\\", "/");
		File file = new File(shwPath);
		if(file.exists()) {
			file.delete();
		}
		shwPath = shwPath.substring(0, shwPath.lastIndexOf("/"));
		File dir = new File(shwPath);
		if(dir.exists()) {
			dir.delete();
		}
		shDao.delStuHomework(shwID);
		
		SentMessage message = new SentMessage(1, "删除作业成功");
		CommonUtil.renderJson(response, message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
