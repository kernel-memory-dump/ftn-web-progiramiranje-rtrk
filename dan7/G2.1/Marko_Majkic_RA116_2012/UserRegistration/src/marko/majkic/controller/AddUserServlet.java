package marko.majkic.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import marko.majkic.dao.UserDao;
import marko.majkic.model.User;

/**
 * Servlet implementation class AddUserServlet
 */
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AddUserServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(400);
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter printer = response.getWriter();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer brojac = (Integer) session.getAttribute("brojac");
		if(brojac == null || user == null) {
			brojac = 1;
			user = new User("ime1", "tip1");
			session.setAttribute("brojac", brojac);
			session.setAttribute("user", user);
		}
		
		brojac++;
		user.setName("ime" + brojac);
		
		session.setAttribute("brojac", brojac);
		
		printer.print("<html><body><h1>" + user.getName() + ", " + brojac +"</h1></body></html>");
		
		List<User> users = null;
		
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		if(name == null || type == null) {
			response.setStatus(400);
			return;
		}
		System.out.println("Name: " + name + ", Type: " + type);
		
		User novi = new User(name, type);
		
		
		try {
			UserDao.addUser(novi);
			users = UserDao.getAllUsers();
		} catch (FileNotFoundException e) {
			printer.print("<html><body><h1>Error: [X]Add User</h1></body></html>");
			e.printStackTrace();
			printer.flush();
			printer.close();
			return;
		}
		
		String htmlForClient = browserResponse(users);
		printer.write(htmlForClient);
	}
	
	/**
	 * Generise odgovor za klijenta, spisak korisnika
	 * @param users lista korisnika za ispis unutar <ol> na klijentu
	 * @return String koji predstavlja HTML sadrzaj stranice
	 */
	private String browserResponse(List<User> users) {
		String retVal = "";
		retVal += "<html><head><title></title></head>\n";
		retVal += "<body><h1>Registered users</h1><ol>\n";
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			retVal += "<li>" + user.getName() + " <b>" + user.getType() + "</b></li>\n";
		}
		retVal += "</ol></body></html>\n";
		return retVal;
	}
}
