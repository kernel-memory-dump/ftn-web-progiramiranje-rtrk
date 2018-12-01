package nebojsa.rablov.controler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nebojsa.rablov.model.Brojac;

/**
 * Servlet implementation class SesijaTest1
 */
public class SesijaTest1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SesijaTest1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
 		if(session.getAttribute("brojac") == null){
 			response.setStatus(400);
 			return;
 		}
 		
 		Brojac b = (Brojac) session.getAttribute("brojac");
 		b.setX(b.getX() + 1);
 		
 		PrintWriter printer = response.getWriter();
		printer.print("<html><body><h1>" + b.getX() + "</h1></body></html>");
		printer.close();
	}

}
