package andrej.popovic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import andrej.popovic.dto.OsobaDTO;

/**
 * Servlet implementation class OsobeJSONServlet
 */
public class OsobeJSONServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OsobeJSONServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		OsobaDTO dto = mapper.readValue(request.getInputStream(), OsobaDTO.class);
		
		List<OsobaDTO> lista = new ArrayList<>();
		System.out.println("CLIENT SENT: ");
		System.out.println("NAME: " + dto.getName());
		System.out.println("AGE: " + dto.getAge());
		
		for(int i = 0; i < dto.getAge(); ++i)
		{
			OsobaDTO odgovor = new OsobaDTO();
			odgovor.setName(dto.getName() + i);
			odgovor.setAge(i);
			lista.add(odgovor);
		}
		
		String listaStr = mapper.writeValueAsString(lista);
		PrintWriter writer = response.getWriter();
		writer.print(listaStr);
		writer.close();
	}

}
