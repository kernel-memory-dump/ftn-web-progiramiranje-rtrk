package stefan.stojanovic.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import stefan.stojanovic.dto.OsobaDTO;

/**
 * Servlet implementation class JDONservlet
 */
@WebServlet("/JDONservlet")
public class JDONservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDONservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter printer = response.getWriter();
		printer.print("{ \"ime\": \"Pera\", \"starost\":21}");
		response.setContentType("application/json");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.readValue(request.getInputStream(), OsobaDTO.class);
		OsobaDTO isparsirano = mapper.readValue(request.getInputStream(), OsobaDTO.class);
		System.out.println("PRIMILI OSOBU");
		System.out.println("IME: " + isparsirano.getIme());
		isparsirano.setStarost(isparsirano.getStarost() * (-1));
		System.out.println("Starost:  " + isparsirano.getStarost());
		
		String odgovor = mapper.writeValueAsString(isparsirano);
		PrintWriter printer = response.getWriter();
		printer.print(odgovor);
		printer.flush();
		printer.close();

	
	}

}
