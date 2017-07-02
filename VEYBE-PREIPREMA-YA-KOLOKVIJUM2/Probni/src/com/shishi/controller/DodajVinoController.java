package com.shishi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shishi.model.Vino;

/**
 * Servlet implementation class DodajVinoController
 */
public class DodajVinoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DodajVinoController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	super.init(config);
    	
    	List<Vino> vina = new ArrayList<>();
    	// Vino v1 = new Vino();
    	// ....
    	// vina.add(v1);
    	getServletContext().setAttribute("baya-svih-vina", vina);
    	
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String naziv = request.getParameter("naziv");
		String proizvodjac = request.getParameter("proizvodjac");
		
		
		String vrstaStr = request.getParameter("vrsta"); // "0"
		int vrstaInt = Integer.parseInt(vrstaStr);  // 0
		
		String pravaVrsta = Vino.MOGUCE_VRSTE[vrstaInt]; //  Rosé
		
		String region = request.getParameter("region");
		String berba = request.getParameter("berba");
		String cena = request.getParameter("cena");
		
		int berbaInt = Integer.parseInt(berba);
		int cenaInt = Integer.parseInt(cena);
		
		if(berbaInt <= 1970) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		Vino vino = new Vino(naziv, proizvodjac, pravaVrsta, region, berbaInt, cenaInt);
		
		List<Vino> svaVina = (List<Vino>) getServletContext().getAttribute("baya-svih-vina");
		
		//double x = 4.4;
		//int y = (int) x;
		svaVina.add(vino);
		
		HttpSession session = request.getSession();
		
		// PRAVIM NOVU LISTU KOJU CU SKRACIVATI PRI FILTRIRANJU!!!111
		List<Vino> vinaZaStranicu = new ArrayList<>();
		//DODACU SVA VINA....pa cu neka izbacivati pri filriranju.........!!!11111
		vinaZaStranicu.addAll(svaVina);
		
		session.setAttribute("vinaZaStranicu", vinaZaStranicu);
		
		response.sendRedirect("pregledVina.jsp");

		
	}

}
