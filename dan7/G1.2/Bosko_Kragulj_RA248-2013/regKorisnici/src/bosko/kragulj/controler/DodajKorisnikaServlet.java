package bosko.kragulj.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bosko.kragulj.dao.UserDao;
import bosko.kragulj.model.User;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		if(ime == null || tip == null){
			response.setStatus(400);
			return;
		}
		User user = new User(ime, tip);
		List<User> users;
		try{
			UserDao.addUser(user);
			users = UserDao.getAllUsers();
		} catch(IOException e) {
			PrintWriter printer = null;
			printer.println("<html><body><h1>Neuspesno dodavanje korisnika, desila se greska </h1></body></html>");
			printer.flush();
			printer.close();
			return;
		}
		UserDao.addUser(user);
		String responseHTML = browserResponse(users);
		PrintWriter printer = response.getWriter();
		printer.print(responseHTML);
		printer.close();
	}
	
	
	private static String browserResponse(List<User> users) {
		//String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
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
