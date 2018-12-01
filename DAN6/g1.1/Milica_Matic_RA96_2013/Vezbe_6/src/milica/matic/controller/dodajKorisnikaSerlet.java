package milica.matic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import milica.matic.dao.UserDao;
import milica.matic.model.User;

/**
 * Servlet implementation class dodajKorisnikaSerlet
 */
public class dodajKorisnikaSerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dodajKorisnikaSerlet() {
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
		// TODO Auto-generated method stub
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		List<User> users = null;
		
		if (ime == null || tip == null)
		{
			response.setStatus(400);
			return;
		}
		PrintWriter printer = response.getWriter();
		User user = new User(ime, tip);
		try {
			UserDao.addUser(user);
			users = UserDao.getAllUsers();
		} catch(IOException e)
		{
			printer.println("<html><body><h1>Desila se greska pri upisu korisnika</h1></body></html>");
			printer.flush();
			printer.close();
			return;
		}
		String responseHTML = browserResponse(users);
		printer.print(responseHTML);
		printer.close();
	}
	
	/**
	 * Generates page for client, including a list of all users as <ol>
	 * @param users - List of currently registered users fetched from file
	 * @return HTML response to be sent to client
	 */
	private static String browserResponse(List<User> users) {
		String retVal = "";/*HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";*/
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
