package david.ujhazi.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import david.ujhazi.model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username =  request.getParameter("username");
		String password =  request.getParameter("password");

		if (username == null || password == null) {
			response.setStatus(400);
			return;
		}

		if (username.equals("admin") && password.equals("admin")) {
			HttpSession session = request.getSession();
			User user = new User();
			user.setPassword("admin");
			user.setUsername("admin");

			session.setAttribute("loggedUser", user);
			response.sendRedirect("/dan06/index.html?random=" + System.currentTimeMillis());

		} else {
			response.setStatus(403);
		}

	}

}
