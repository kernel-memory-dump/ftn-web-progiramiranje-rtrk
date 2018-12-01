package nemanja.djekic.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import nemanja.djekic.dto.OsobaDTO;

/**
 * Servlet implementation class JSONServlet
 */
public class JSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter printer = response.getWriter();
		printer.print("{ \"ime\": \"Pera\", \"starost\": 21 }");
		response.setContentType("application/json");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		OsobaDTO isparsirano = 
				mapper.readValue(request.getInputStream(),
								 OsobaDTO.class);
		System.out.println("PRIMILI OSOBU:");
		System.out.println("IME:" + isparsirano.getIme());
		System.out.println("STAROST:" + isparsirano.getStarost());
		isparsirano.setStarost(isparsirano.getStarost() * (-1));
		
		String odgovor = 
				mapper.writeValueAsString(isparsirano);
		PrintWriter printer = response.getWriter();
		printer.print(odgovor);
		printer.flush();
		printer.flush();
		
		/**
		String poljeJSON = "ime";
		Class osobaDTOKlasa = OsobaDTO.class;
		Method m = osobaDTOKlasa.getMethod("setTime", [String.class]);
		OsobaDTO isparsiranDTO = new OsobaDTO();
		m.invoke(isparsiranDTO, "Pera");
		*/
	}

}
