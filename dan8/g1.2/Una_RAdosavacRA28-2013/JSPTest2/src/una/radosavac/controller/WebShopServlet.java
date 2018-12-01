package una.radosavac.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import una.radosavac.model.Products;

/**
 * Servlet implementation class WebShopServlet
 */
public class WebShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	private Products products;

	/*
	 * Obratiti paznju da se metod init() zove samo jednom, prilikom prvo
	 * pokretanja (inicijalziacije) servleta. => Ukoliko bismo u products.txt
	 * dodali novi proizvod, bez restartovanja web servera, a prethodno je
	 * servlet vec bio pokrenut, novi proizvod se nece biti procitan.
	 */
	@Override
	public void init(ServletConfig cfg) {
		try {
			// obavezan poziv super metode, kako bi se korektno izvrsila
			// inicijalizacija
			super.init(cfg);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		ServletContext ctx = getServletContext();
		products = new Products(ctx.getRealPath(""));
		ctx.setAttribute("products", products);
	}

	public WebShopServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher disp = request
				.getRequestDispatcher("Webshop.jsp");
		// redirektovacemo na shopping cart stranicu
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
