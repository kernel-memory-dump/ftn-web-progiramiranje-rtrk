package isidor.rimski.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jndi.ldap.sasl.SaslInputStream;

import isidor.rimski.model.User;


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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String pasword = request.getParameter("pasword");
		if(username == null || pasword == null){
			System.out.println("null oba\n");	
			response.setStatus(400);
			return;
		}
		HttpSession session = request.getSession();
		if(username.equals("admin") && pasword.equals("admin")){
			
			User user = new User();
			user.setPasword("admin");
			user.setUsername("admin");
			
			session.setAttribute("loggedInUser", user);
			System.out.println(session.getAttribute("loggedInUser"));
			response.sendRedirect("index.html");
		}else{
			//session.setAttribute("loggedInUser", null);
			response.sendRedirect("login.html");
		}
			
	}

}
