package milan.novakovic.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import milan.novakovic.dao.UserDao;
import milan.novakovic.model.User;


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
		response.setStatus(400);
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		//sanity check
		if (ime == null || tip == null) {
			response.setStatus(400);
			return;
		}
		User tempUser = new User(ime,tip);
		PrintWriter out = response.getWriter();
		List<User> users = null;
		try {
			UserDao.addUser(tempUser);
			users = UserDao.getAllUsers();
		} catch (FileNotFoundException e) {
			
			out.print("<html><body><h1>Greska pri dodavanju korisnika!</h1><body></html>");
			e.printStackTrace();
			out.flush();
			out.close();
			return;
		}
		String htmlZaKlijenta = browserResponse(users);
		
		out.write(htmlZaKlijenta);
		
	}
	
	/**
	 * Generisanje odgovora za web browser
	 */
	private static String browserResponse(List<User> users) {
		String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
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
