package sandra.ivanovic.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import sandra.ivanovic.dto.OsobaDTO;

/**
 * Servlet implementation class JSONListServlet
 */
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
		ObjectMapper mapper = new ObjectMapper();
		List<OsobaDTO> osobe = new ArrayList<>();
		OsobaDTO osoba1 = new OsobaDTO();
		osoba1.setIme("PERO");
		osoba1.setStarost(15);
		
		OsobaDTO osoba2 = new OsobaDTO();
		osoba2.setIme("Suki");
		osoba2.setStarost(23);
		
		osobe.add(osoba1);
		osobe.add(osoba2);
		response.setContentType("application/json");
		String odgovor = mapper.writeValueAsString(osobe);
		PrintWriter printer = response.getWriter();
		printer.print(odgovor);
		printer.flush();
		printer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
