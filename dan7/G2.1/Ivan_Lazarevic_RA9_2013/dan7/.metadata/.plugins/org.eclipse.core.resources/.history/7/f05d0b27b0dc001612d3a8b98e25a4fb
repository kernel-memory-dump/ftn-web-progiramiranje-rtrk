package ivan.lazarevic.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ivan.lazarevic.dao.UserDao;
import ivan.lazarevic.model.User;

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
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setStatus(400);
	}

	
	

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		String ime = request.getParameter("ime");
		String tip = request.getParameter("tip");
		// sanity check
		if (ime == null || tip == null) {
			response.setStatus(400);
			return;
		}
		System.out.println("Ime: " + ime + "tip: " + tip);
		User novi = new User(ime, tip);
		PrintWriter printer = response.getWriter();
		List<User> users = null;
		try {
			UserDao.addUser(novi);
			users = UserDao.getAllUsers();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			printer.print("<html><body><h1>Greska</h1></body></html>");
			e.printStackTrace();
			printer.flush();
			printer.close();
			return;
		}
		String htmlZaKlijenta = browserResponse(users);
		printer.write(htmlZaKlijenta);
		
	}
	
	/**
	 * Generisanje odgovora za web browser
	 */
	private static  String browserResponse(List<User> users) {
		String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
		retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
		retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
		
		for (int i = 0; i < users.size(); i++) {
			
			User user = users.get(i);
			//String tip = users.get(i).getTip();		
			//if(tip2.equals("svi") || tip2.equals(tip)){
			retVal += "<li>" + user.getIme() +  "</li>\n";
		}
			
				
		
		retVal += "</ol></body></html>\n";
		return retVal;
	}	

}
