package marjan.bogdanov.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import marjan.bogdanov.dao.UserDao;
import marjan.bogdanov.model.User;

/**
 * Servlet implementation class DodajKorisnikaServlet
 */
public class DodajKorisnikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServ
	 * let()
	 */
	public DodajKorisnikaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer brojac = (Integer) session.getAttribute("brojac");
		if (brojac == null || user == null){
			brojac = 1;
			user = new User ("ime1", "tip1");
			session.setAttribute("brojac", brojac);
			session.setAttribute("user", user);
		}
		brojac++;
		//jedan te isti projekat
		user.setIme("ime" + brojac);
		// novi objekat, nova referenca na sesiju
		session.setAttribute("brojac", brojac);
		System.out.println(user.getIme());
		
		PrintWriter printer = response.getWriter();
		printer.println("<html><body>" + "<h1>" + brojac + "</h1> </body> </html>");
		printer.close();
//		String ime = request.getParameter("ime");
//		String tip = request.getParameter("tip");
//		if (ime == null || tip == null) {
//			response.setStatus(400);
//			return;
//		}
//		PrintWriter printer = response.getWriter();
//		User user = new User(ime, tip);
//		List<User> users = null;
//		try {
//			UserDao.addUser(user);
//			users = UserDao.getAllUsers();
//		} catch (Exception e) {
//			printer.println("<html><body>"
//					+ "<h1>Neospesno dodavanje korisnika, desila se greska"
//					+ "pri upisu korisnika</h1></body></html>");
//			printer.flush();
//			printer.close();
//			return;
//		}
//		String responseHTML = browserResponse(users);
//
//		printer.print(responseHTML);
//		printer.close();

	}

	/**
	 * Generates page for lcient including a list of all users as
	 * <ol>
	 * 
	 * @param users
	 *            - List of currently registered users fetched from file
	 * @return HTML response to be sent to the client
	 */
	private static String browserResponse(List<User> users) {
		//String retVal= "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
		//retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
		String retVal = "<html><head><title>Prijavljeni korisnici</title></head>\n";
		retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			retVal += "<li>" + user.getIme() + "</li>\n";
		}
		retVal += "</ol></body></html>\n";
		return retVal;
	}

}
