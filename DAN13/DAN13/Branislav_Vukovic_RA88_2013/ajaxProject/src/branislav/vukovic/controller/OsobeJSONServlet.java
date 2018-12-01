package branislav.vukovic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import branislav.vukovic.dto.OsobaDTO;

/**
 * Servlet implementation class OsobeJSONServlet
 */
public class OsobeJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OsobeJSONServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		OsobaDTO dto = mapper.readValue(request.getInputStream(), OsobaDTO.class);
		List<OsobaDTO> lista = new ArrayList<OsobaDTO>();
		System.out.println("Klijent poslao:");
		System.out.println("IME: "+dto.getIme());
		System.out.println("STAROST: "+dto.getStarost());
		for(int i= 0; i<dto.getStarost(); i++){
		OsobaDTO odgovor = new OsobaDTO();
		odgovor.setIme("ime"+i);
		odgovor.setStarost(i);
		lista.add(odgovor);
		}
		String listaStr = mapper.writeValueAsString(lista);
		PrintWriter printer = response.getWriter();
		printer.print(listaStr);
		printer.close();
		
		
	}

}
