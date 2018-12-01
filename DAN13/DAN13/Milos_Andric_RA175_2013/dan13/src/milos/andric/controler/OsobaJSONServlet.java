package milos.andric.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import milos.andric.dto.OsobaDTO;

/**
 * Servlet implementation class OsobaJSONServlet
 */
public class OsobaJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public OsobaJSONServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ObjectMapper mapper = new ObjectMapper();
		OsobaDTO value = new OsobaDTO();
		value.setIme("PERA");
		value.setStarost(21);
		
		response.setContentType("application/json");
		
		PrintWriter printer = response.getWriter();
		String kaoJSON = mapper.writeValueAsString(value);
		printer.print(kaoJSON);
		printer.close();
		
		OsobaDTO valueIzJSON = mapper.readValue(kaoJSON, OsobaDTO.class);
		
//		Class dtoPrototip = OsobaDTO.class;
//		Object instanceDTO = dtoPrototip.newInstance();
//		
//		String vrednost = "PERA";
//		Method metoda = dtoPrototip.getMethod("setIme", new Object[] {String.class});
//		metoda.invoke(instanceDTO, "PERA");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
