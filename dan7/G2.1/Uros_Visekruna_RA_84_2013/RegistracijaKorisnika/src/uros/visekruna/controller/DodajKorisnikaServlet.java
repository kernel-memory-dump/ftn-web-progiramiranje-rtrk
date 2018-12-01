package uros.visekruna.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uros.visekruna.dao.UserDao;
import uros.visekruna.model.User;

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
		
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Integer brojac = (Integer) session.getAttribute("brojac");
		if(brojac==null){
			
			brojac=1;
			user = new User("ime1","tip1");
			session.setAttribute("brojac", brojac);
			session.setAttribute("user", user);
			
		}
		brojac++;
		user.setIme("Ime" + brojac);
		session.setAttribute("brojac", brojac);
		
		System.out.println(user.getIme());
		
		PrintWriter printer = response.getWriter();
		printer.println("<html><body>" + "<h1>" + brojac + "</h1></body></html>" );
		printer.close();
		
		/*
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		
		if(ime == null || tip == null){
			response.setStatus(400);
			return;			
		}
		
		System.out.println("ime osobe je: " + ime + " tip osobe je: " + tip);
		User novi = new User(ime, tip);
		PrintWriter printer = response.getWriter(); 
		List<User> users = null;
		try {
			
			UserDao.addUser(novi);
			users = UserDao.getAllUsers();
		} catch (FileNotFoundException e) {
			printer.print("<html><body><h1>Greska pri dodavanju korisnika</h1></body></html>");
			e.printStackTrace();
			printer.flush();
			printer.close();
			return;
		}
		String htmlZaKlijenta = browserResponse(users);
		//PrintWriter printer = response.getWriter();
		printer.write(htmlZaKlijenta);
		*/
	}
	/**
	 * Generise odgovor za klijenta, spisak korisnika
	 * @param users - lista korisnika za ispis unutar <ol> na klijentu
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
