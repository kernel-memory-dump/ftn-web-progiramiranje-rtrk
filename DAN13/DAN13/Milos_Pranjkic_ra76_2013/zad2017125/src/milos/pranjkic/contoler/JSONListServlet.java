package milos.pranjkic.contoler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import milos.pranjkic.dto.OsobaDto;

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
		
		List<OsobaDto> osobe = new ArrayList();
		
		OsobaDto osoba1 = new OsobaDto();
		osoba1.setIme("Pera");
		osoba1.setStarost(21);
		
		OsobaDto osoba2 = new OsobaDto();
		osoba2.setIme("Mika");
		osoba2.setStarost(18);
		
		osobe.add(osoba1);
		osobe.add(osoba2);		
		
		response.setContentType("application/json");
		String odgovor = mapper.writeValueAsString(osobe);
		PrintWriter printer = response.getWriter();
		printer.print(odgovor);
		printer.flush();
		printer.close();
		System.out.println("STIGO DO KRAJA");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
