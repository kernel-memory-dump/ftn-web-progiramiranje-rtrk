package com.shishi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shishi.model.Vino;

/**
 * Servlet implementation class FiltrirajVinaController
 */
public class FiltrirajVinaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FiltrirajVinaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String vrstaStr = request.getParameter("vrsta");
		int vrstaInt = Integer.parseInt(vrstaStr);

		List<Vino> svaVina = (List<Vino>) getServletContext().getAttribute("baya-svih-vina");
		List<Vino> vinaZaStranicu = new ArrayList<>();
		// DODACU SVA VINA....pa cu neka izbacivati pri
		// filriranju.........!!!11111
		vinaZaStranicu.addAll(svaVina);

		if (vrstaInt == 4) {
			HttpSession session = request.getSession();
			session.setAttribute("vinaZaStranicu", vinaZaStranicu);
		} else {
			String izabranaVrsta = Vino.MOGUCE_VRSTE[vrstaInt]; //.
			
			List<Vino> filrirano = new ArrayList<>();
			
			for (Vino vino : svaVina) {
				if (vino.getVrsta().equals(izabranaVrsta)) {
					filrirano.add(vino);
				}
			}
			HttpSession session = request.getSession();
			session.setAttribute("izabranaVrsta", izabranaVrsta);
			session.setAttribute("vinaZaStranicu", filrirano);
		}
		
		
		response.sendRedirect("pregledVina.jsp");

	}

}
