package marko.gostovic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import marko.gostovic.dao.UserDao;
import marko.gostovic.model.User;

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
	
	//List<User> users = new ArrayList<>();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		
		if(ime == null || tip == null)
		{
			response.setStatus(400);
			return;
		}
		
		/*
		User user = new User(ime, tip);
		//users.add(user);
		UserDao.addUser(user);
		String responseHTML = browserResponse(users);
		PrintWriter printer = response.getWriter();
		printer.print(responseHTML);
		printer.close();
		*/
		
		PrintWriter printer = response.getWriter();
		User user = new User(ime, tip);
		List<User> users;
		
		try
		{
			UserDao.addUser(user);
			users = UserDao.getAllUser();
		}
		catch(IOException e)
		{
			/* Send some HTML to client so that the client knows that the error has ocurred. */
			
			printer.println("<html><body>"
						  + "<h1>Neuspesno dodavanje korisnika, desila se greska"
					      + " pri upisu novog korisnika.</h1></body></html>");
			
			printer.flush();
			printer.close();
			return;
		}
		
		String responseHTML = browserResponse(users);
		printer.print(responseHTML);
		printer.close();
	}
	
	/**
	 * Generates page for client including a list of all users as <ol>
	 * @param users - List of currently registered users fetched from file
	 * @return HTML response to be sent to the client
	 */
	private static String browserResponse(List<User> users) 
	{
		String retVal = "";//"HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
		retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
		retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
		
		for (int i = 0; i < users.size(); i++) 
		{
			User user = users.get(i);
			retVal += "<li>" + user.getIme() + "</li>\n";
		}
		
		retVal += "</ol></body></html>\n";
		return retVal;
	}

}
