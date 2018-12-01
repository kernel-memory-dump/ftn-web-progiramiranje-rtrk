package eleonora.nan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import eleonora.nan.dto.OsobaDto;

/**
 * Servlet implementation class JSONListServle
 */
public class JSONListServle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONListServle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		List<OsobaDto> osobe = new ArrayList<>();
		
		OsobaDto osoba1 = new OsobaDto();
		OsobaDto osoba2 = new OsobaDto();
		OsobaDto osoba3 = new OsobaDto();
		OsobaDto osoba4 = new OsobaDto();
		OsobaDto osoba5 = new OsobaDto();
		OsobaDto osoba6 = new OsobaDto();
		
		osoba1.setIme("PERA");
		osoba1.setStarost(21);
		osoba2.setIme("ZIKA");
		osoba2.setStarost(101);
		osoba4.setIme("PERA");
		osoba4.setStarost(21);
		osoba3.setIme("ZIKA");
		osoba3.setStarost(101);
		osoba5.setIme("PERA");
		osoba5.setStarost(21);
		osoba6.setIme("ZIKA");
		osoba6.setStarost(101);
		osobe.add(osoba1);
		osobe.add(osoba2);
		osobe.add(osoba3);
		osobe.add(osoba4);
		osobe.add(osoba5);
		osobe.add(osoba6);
		
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
