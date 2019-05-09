package ujs.mlearn.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminUserServlet
 */
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUserServlet() {
		super();
		// TODO Auto-generated c onstructor stub
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
		if (op.equals("login")) {
			login(request, response);
		} else if (op.equals("logout")) {
			logout(request, response);
		}
		// else if(op.equals("user")) {
		// gotoUserManagePage(request, response);
		// } else if(op.equals("class")) {
		// gotoClassManagePage(request, response);
		// } else if(op.equals("resource")) {
		// gotoResourceManagePage(request, response);
		// } else if(op.equals("forum")) {
		// gotoForumManagePage(request, response);
		// }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath() + "/adminLogin.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("管理员登录出成功！");
	}

	// private void gotoClassManagePage(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	// request.getSession().invalidate();
	// request.getRequestDispatcher("/adIndex/classManage/classManageMain.jsp").forward(request,
	// response);
	// System.out.println("课程管理");
	// }
	//
	// private void gotoResourceManagePage(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	// request.getSession().invalidate();
	// request.getRequestDispatcher("/adIndex/resourceManage/resourceManageMain.jsp").forward(request,
	// response);
	// System.out.println("资源管理");
	// }
	//
	// private void gotoForumManagePage(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	// request.getSession().invalidate();
	// request.getRequestDispatcher("/adIndex/forumManage/forumManageMain.jsp").forward(request,
	// response);
	// System.out.println("论坛管理");
	// }
	//
	// private void gotoUserManagePage(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	// request.getSession().invalidate();
	// request.getRequestDispatcher("/adIndex/userManage/userManageMain.jsp").forward(request,
	// response);
	// System.out.println("用户管理");
	// }

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		if (account.equals("root") && password.equals("123")) {
			request.getSession().setAttribute("admin", "1");
			request.getRequestDispatcher("/adIndex/adIndex.jsp").forward(request, response);
			System.out.println("管理员登录成功！");
		} else {
			request.setAttribute("error", "账号或密码错误！");
			request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
		}
	}

}
