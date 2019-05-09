package ujs.mlearn.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.CourseMaterialDao;
import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.CourseMaterialDaoImpl;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.entity.CourseMaterial;

/**
 * Servlet implementation class ResServlet
 */
public class ResServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResServlet() {
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
		System.out.println(op);
		if (op.equals("findResByCourseID")) {
			findResByCourseID(request, response);
		}
	}

	private void findResByCourseID(HttpServletRequest request, HttpServletResponse response) {
		int courseID = Integer.parseInt(request.getParameter("courseID"));
		CourseMaterialDao mDao = new CourseMaterialDaoImpl();
		List<CourseMaterial> resList = mDao.findCourseMaterial(courseID);
		if(resList != null) {
			TeacherDao tDao = new TeacherDaoImpl();
			CourseDao cDao = new CourseDaoImpl();
			for(int i = 0; i < resList.size(); i++) {
				resList.get(i).setTeacherName(tDao.findByNumber(resList.get(i).getTeacherNumber()).getName());
				resList.get(i).setCourseName(cDao.findById(resList.get(i).getCourseID()).getCourseName());
			}
		}
		System.out.println(request.getContextPath());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resList", resList);
		CommonUtil.renderJson(response, map);
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
