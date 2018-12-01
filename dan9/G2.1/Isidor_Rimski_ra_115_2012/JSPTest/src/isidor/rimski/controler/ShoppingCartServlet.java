package isidor.rimski.controler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import isidor.rimski.model.Products;
import isidor.rimski.model.ShoppingCart;

/**
 * Servlet implementation class ShoppingCartServlet
 */
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SHOPPING_CART_KEY = "ShoppingCart";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public ShoppingCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // zasto ovako ne valja?
    		// obratiti paznju na prirodu http protokola, koji je stateless
    		// private ShoppingCart sc = new ShoppingCart();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// zasto ovako ne valja?
		// obratiti paznju na prirodu http protokola, koji je stateless
		// ShoppingCart sc = new ShoppingCart();

		// pogledamo da li u tekucoj sesiji postoji objekat ShoppingCart
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute(SHOPPING_CART_KEY);
		if ( sc == null ) {
			// ako ne postoji, kreiramo ga i dodelimo tekucoj sesiji. Na ovaj
			// nacin, objekat klase ShoppingCart ce pratiti sesiju.
			sc = new ShoppingCart();
			session.setAttribute(SHOPPING_CART_KEY, sc);
		}

		response.setContentType("text/html");
		/*
		PrintWriter pout = response.getWriter();
		
		pout.println("<html>");
		pout.println("<head>");
		pout.println("</head>");
		pout.println("<body>");
		*/
		if ( request.getParameter("itemId") != null ) {
			// ako smo pozvali ovaj servlet sa parametrima za dodavanje proizvoda u korpu
			try {
				Products products = (Products) getServletContext().getAttribute("products");
				// probamo da ga dodamo
				sc.addItem(products.getProduct(request.getParameter("itemId")),
						Integer.parseInt(request.getParameter("itemCount")));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		RequestDispatcher disp = request.getRequestDispatcher("ShoppingCart.jsp");
		try {
			disp.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
