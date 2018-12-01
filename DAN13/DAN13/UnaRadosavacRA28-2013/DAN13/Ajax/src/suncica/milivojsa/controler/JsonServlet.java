package suncica.milivojsa.controler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import suncica.milivojsa.dto.OsobaDto;

/**
 * Servlet implementation class JsonServlet
 */
public class JsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter printer = response.getWriter();
		printer.print("{ \"ime\":\"pera\" ,  \"starost\":23 }");
		response.setContentType("application/json");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//telo jsona koji stize se pretvara u objekat
		ObjectMapper mapper = new ObjectMapper();
		OsobaDto isparsirano = mapper.readValue(request.getInputStream(), OsobaDto.class);
		System.out.println("primili osobu " + isparsirano.getIme() + " " + isparsirano.getStarost());
		isparsirano.setStarost(isparsirano.getStarost() * (-1));
		//vracamo ovaj objekat kao json string
		String odgovor = mapper.writeValueAsString(isparsirano);
		PrintWriter printer = response.getWriter();
		printer.print(odgovor);
		printer.flush();
		printer.close();
	}

}
