package tatjana.eric.controler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import tatjana.eric.dto.OsobaDTO;

/**
 * Servlet implementation class JSONSevlet
 */
public class JSONSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONSevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter printer=response.getWriter();
		printer.print("{ \"ime\":  \"Pera\", \"starost\": 21 }");
		response.setContentType("application/json");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper=new ObjectMapper();
		OsobaDTO isparsirano=mapper.readValue(request.getInputStream(),OsobaDTO.class);
		
		System.out.println("Primili osobu: ");
		System.out.print("IME: " + isparsirano.getIme());
		System.out.print(" STAROST: " + isparsirano.getStarost());
		isparsirano.setStarost(isparsirano.getStarost() * (-1));
		
		String odgovor= mapper.writeValueAsString(isparsirano);
		PrintWriter printer=response.getWriter();
		printer.print(odgovor);
		printer.flush();
		printer.close();
		
	}

}
