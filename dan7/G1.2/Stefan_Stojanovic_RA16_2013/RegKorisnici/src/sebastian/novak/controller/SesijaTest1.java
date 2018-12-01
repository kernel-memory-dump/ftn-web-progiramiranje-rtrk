package sebastian.novak.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sebastian.novak.model.Brojac;

/**
 * Servlet implementation class SesijaTest1
 */
public class SesijaTest1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SesijaTest1() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Brojac b = new Brojac();
		session.setAttribute("brojacc", b);
		PrintWriter printer = response.getWriter();
		printer.print("<html><body><h1>" + b.getX() + "</h1></body></html>");
		printer.close();
	}

}
