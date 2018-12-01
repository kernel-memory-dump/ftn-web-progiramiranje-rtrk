package uros.visekruna.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uros.visekruna.model.Products;
import uros.visekruna.model.ShoppingCart;

/**
 * Servlet implementation class ShopppingCartServlet
 */
public class ShopppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// ShoppingCart
	private static final String SHOPPING_CART_KEY = "ShoppingCart";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopppingCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
				// redirektovacemo na login stranicu
				disp.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
