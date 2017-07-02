package com.shishi.model;

public class Vino {

	/**
	 * Napisati Web aplikaciju za dodavanje i
	 *  pregledanje vina u vinoteci. 
	 *  Vino je opisano nazivom, proizvoðaèem, vrstom 
	 *  
	 *   regionom, berbom i cenom. 
	 *   
	 *   Berba mora da bude pozitivan broj veæi od 1970.
	 *   
	 *    Validaciju forme za unos realizovati pomoæu JavaScript-a i putem servleta.
	 *  
	 */
	private String naziv;
	private String proizvodjac;
	public static final String[] MOGUCE_VRSTE = 
			new String[] {"Rosé", "Cabernet sauvignon",  "Sauvignon Blanc", "Porto"};
	
	private String vrsta;
	private String region;
	
	private int berba;
	private int cena;
	
	
	
	
	public Vino() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Vino(String naziv, String proizvodjac, String vrsta, String region, int berba, int cena) {
		super();
		this.naziv = naziv;
		this.proizvodjac = proizvodjac;
		this.vrsta = vrsta;
		this.region = region;
		this.berba = berba;
		this.cena = cena;
	}



	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getProizvodjac() {
		return proizvodjac;
	}
	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}
	public String getVrsta() {
		return vrsta;
	}
	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getBerba() {
		return berba;
	}
	public void setBerba(int berba) {
		this.berba = berba;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	
	
	
	
}
