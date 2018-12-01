package sebastian.novak.security1;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sebastian.novak.model.User;

/**
 * Servlet Filter implementation class SecurityFillter
 */
public class SecurityFillter implements Filter {

    /**
     * Default constructor. 
     */
    public SecurityFillter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;
		String url=httpRequest.getRequestURI();
		
		if(url.contains("logovanje.html") || url.contains("/UlogujSe")){
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session=httpRequest.getSession();
		User user=(User) session.getAttribute("logged");
		if(user==null){
			/* Nije ulogovan */
			httpResponse.sendRedirect("logovanje.html?random="+ System.currentTimeMillis());
			return;
		}
		
		chain.doFilter(request, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
