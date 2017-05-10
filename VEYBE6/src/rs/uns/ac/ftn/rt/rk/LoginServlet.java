package rs.uns.ac.ftn.rt.rk;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ime = request.getParameter("ime");
		String lozinka = request.getParameter("lozinka");
		
		
		List<User> users = (List<User>) getServletContext().getAttribute("users");
		for(User user : users) {
			if(user.getUsername().equals(ime) && user.getPassword().equals(lozinka)) {
				response.sendRedirect("ulogovan.jsp");
				return;
			}
		}
		response.sendRedirect("greska.jsp");
		
		/*
		if(ime != null && lozinka != null && lozinka.equals("admin") && ime.equals("admin")){
			response.sendRedirect("ulogovan.jsp");
		} else {
			response.sendRedirect("greska.jsp");
		}*/
		
		
		
		
	}

}
