package Boris.Tesic.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Boris.Tesic.dao.UserDao;
import Boris.Tesic.model.User;


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
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setStatus(400);
	}
	
	//List<User> users = new ArrayList<>(); //- ovo bi bilo zivo dok god je server ziv
	
	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		//saniti check
		if(ime == null || tip == null){
			response.setStatus(400);
			return;
		}
		System.out.println("Ime osobe je:" + ime + " tip osobe je:" + tip);
		User novi = new User(ime, tip);
		PrintWriter printer = response.getWriter();
		List<User> users = null;
		try {
			UserDao.addUser(novi);
			users = UserDao.getAllUsers();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			printer.print("<html><body><h1>Greska pri dodavanju korisnika! </h1></body></html>");
			e.printStackTrace();
			printer.flush();
			printer.close();
			return;
		}
		String htmlZaKlijenta = browserResponse(users);
		printer.write(htmlZaKlijenta);
	}
	
	/**
	 * Generise odgovor za klijenta, spisak korisnika
	 * @param users - lista korisnika za ispis unutar <ol> na lijentu
	 * @return String koji predstavlja HTML sadrzaj stranice
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
