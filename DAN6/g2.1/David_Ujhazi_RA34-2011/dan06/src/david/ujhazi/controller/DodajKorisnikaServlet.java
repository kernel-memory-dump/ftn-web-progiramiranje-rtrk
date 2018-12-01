package david.ujhazi.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import david.ujhazi.dao.UserDao;
import david.ujhazi.model.User;

/**
 * Servlet implementation class DodajKorisnikaServlet
 */
public class DodajKorisnikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// List<User> users = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
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
		response.setStatus(400);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("");
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		PrintWriter printer = response.getWriter();
		if (ime != null && tip != null) {
			System.out.println("Ime: " + ime + " Tip: " + tip);
			User novi = new User(ime, tip);
			List<User> users = null;
			try {
				UserDao.addUser(novi);
				users = UserDao.getAllUsers();
			} catch (FileNotFoundException e) {
				printer.print("<html><body><h1>Greska pri dodavanju korisnika!</h1></body></html>");
				e.printStackTrace();
				printer.flush();
				printer.close();
				return;
			}
			String htmlZaKlijenta = browserResponse(users);
			printer.write(htmlZaKlijenta);
		} else {
			response.setStatus(400);
		}
	}

	/**
	 * Generisanje odgovora za klijenta, spisak korisnika
	 * 
	 * @param users
	 *            - lista korisnika za ispis unutar
	 *            <ol>
	 *            na klijentu
	 * @return String koji predstavlja HTML sadrzaj stranice
	 */
	private static String browserResponse(List<User> users) {
		String retVal = "";
		retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
		retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			retVal += "<li>" + user + "</li>\n";
		}
		retVal += "</ol></body></html>\n";
		return retVal;
	}

}