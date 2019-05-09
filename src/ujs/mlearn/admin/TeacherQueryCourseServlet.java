package ujs.mlearn.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.entity.Course;

/**
 * Servlet implementation class TeacherQueryCourse
 */
@WebServlet("/TeacherQueryCourseServlet")
public class TeacherQueryCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherQueryCourseServlet() {
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
		String teacher = request.getParameter("teacher");
		String teacherNumber = teacher.substring(0, teacher.lastIndexOf(":"));

		CourseDao cDao = new CourseDaoImpl();
		List<Course> cList = cDao.findMyCourse(teacherNumber);
		JSONArray jsonData = JSONArray.fromObject(cList);
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(jsonData);
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
