package andrej.popovic.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import andrej.popovic.dao.UserDao;
import andrej.popovic.model.User;

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
		if (ime==null || tip == null)
		{
			response.setStatus(400);
			return;
		}
		
		System.out.println("Ime: " + ime + "Tip: " + tip);
		User novi = new User(ime, tip);
		PrintWriter writer = response.getWriter();
		List<User> users = null;
		try
		{
			UserDao.addUser(novi);
			users = UserDao.getAllUsers();
		} catch (FileNotFoundException e)
		{
			writer.print("<html><body><h1>Greska pri dodavanju korisnika! </h1></body></html>");
			e.printStackTrace();
			writer.flush();
			writer.close();
			return;
		}
		
		String htmlZaKlijenta = browserResponse(users);

		writer.write(htmlZaKlijenta);
	}
	
	/**
	 * Generisanje odgovora za klijenta, spisak korisnika
	 * @param users - lista korisnika za ispis unutar <ol> na klijentu
	 * @return - String koji predstavlja HTML sadrzaj stranice
	 */
    private String browserResponse(List<User> users)
    {
        String retVal = "";
        retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
        retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
        for (int i = 0; i < users.size(); i++)
        {
            retVal += "<li>" + users.get(i).ime + " [" + users.get(i).type + "]</li>\n";
        }
        retVal += "</ol></body></html>\n";
        return retVal;
    }

}
