package marko.gostovic.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import marko.gostovic.model.User;



/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username == null || password == null)
		{
			response.setStatus(400);
			return;
		}
		
		if(username.equals("admin") && password.equals("admin"))
		{
			//Ako je uspeo da se uloguje
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			
			HttpSession session = request.getSession();
			session.setAttribute("ulogovani", user);

			response.sendRedirect("index.html?random=" + System.currentTimeMillis());
		}
		else
		{
			PrintWriter printer = response.getWriter();
			printer.print("<html><body><h1>NEISPRAVNI PODATCI!</h1></body></html>");
			printer.close();
		}
	}

}
