package damir.becirbasic.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import damir.becirbasic.dao.UserDao;
import damir.becirbasic.model.User;
import sun.print.PrinterJobWrapper;

/**
 * Servlet implementation class DodajKorisnikaServlet
 */
public class DodajKorisnikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DodajKorisnikaServlet() {
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
	
	//List<User> users=new ArrayList<>();
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ime=request.getParameter("ime");
		String tip=request.getParameter("tip");
		
		if(ime==null || tip==null)
		{
			response.setStatus(400);
			return;
		}
		
		PrintWriter printer=response.getWriter();
		
		User user=new User(ime, tip);
		List<User> users;

		try{
			UserDao.addUser(user);
			users=UserDao.getAllUsers();
		} catch(IOException e){
			printer.println("<html>"
					+ "<body>"
					+ "<h1>Neuspesno otvaranje korisnika, desila se greska pri upisu novog korisnika"
					+ "</h1></body></html>");
			printer.flush();
			printer.close();
			return;
		}
			
			
		String responseHTML=browserResponse(users);
		
		//PrintWriter printer=response.getWriter();
		printer.print(responseHTML);
		printer.close();
		
		
		
	}

	
/**
 * Generate page for client including a list of all users as <ol>
 * @param users - List of currently registred users fetched from file
 * @return	HTML response to be sent to the client
 */
	private static String browserResponse(List<User> users) {
		String retVal = "";
		retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
		retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			retVal += "<li>" + user.getIme() + "</li>\n";
		}
		retVal += "</ol></body></html>\n";
		return retVal;
	}
	
}
