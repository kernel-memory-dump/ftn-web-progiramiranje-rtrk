package isidor.rimski.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import isidor.rimski.dto.OsobaDTO;

/**
 * Servlet implementation class JASONlistServlet
 */
public class JASONlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JASONlistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<OsobaDTO> osobe = new ArrayList<OsobaDTO>();
		
		OsobaDTO osoba1 = new OsobaDTO();
		osoba1.setIme("PERA");
		osoba1.setStarost(21);
		OsobaDTO osoba2 = new OsobaDTO();
		osoba2.setIme("ZIKA");
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
