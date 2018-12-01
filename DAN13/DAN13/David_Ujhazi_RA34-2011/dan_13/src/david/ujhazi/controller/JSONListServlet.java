package david.ujhazi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import david.ujhazi.dto.OsobaDTO;

/**
 * Servlet implementation class JSONListServlet
 */
@WebServlet("/JSONListServlet")
public class JSONListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<OsobaDTO> osobe = new ArrayList<>();
		osobe.add(new OsobaDTO("Pera",23));
		osobe.add(new OsobaDTO("Mika",15));
		
		String odgovor = mapper.writeValueAsString(osobe);
				
		response.getWriter().println(odgovor);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
