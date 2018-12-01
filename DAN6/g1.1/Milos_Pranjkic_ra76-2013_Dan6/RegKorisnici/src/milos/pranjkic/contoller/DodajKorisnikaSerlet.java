package milos.pranjkic.contoller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import milos.pranjkic.dao.UserDao;
import milos.pranjkic.model.User;


public class DodajKorisnikaSerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    
    public DodajKorisnikaSerlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		
		if(ime==null || tip == null){
			response.setStatus(400);
			return;
		}
		
		PrintWriter printer = response.getWriter();
		User user = new User(ime, tip);
		List<User> users;
		try{
			UserDao.addUser(user);
			users = UserDao.getAllUsers();
		}catch(IOException e){
			printer.println("<html><body><h1>Desila se greska prilikom dodavanja korisnika</h1></body></html>");
			printer.flush();
			printer.close();
			return;
		}
		
		String respondsetHtml = browserResponse(users);
		
		printer.print(respondsetHtml);
		printer.close();
		
	}
	
	
	/**
	 * Generisanje odgovora za web browser
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
