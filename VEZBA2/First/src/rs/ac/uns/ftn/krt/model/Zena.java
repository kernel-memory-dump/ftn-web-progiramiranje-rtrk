package rs.ac.uns.ftn.krt.model;

public class Zena implements Ispisivo {

	private String ime;
	private PolniOrgan polniOrgan = new PolniOrgan();
	private Torba torba;
	
	public Zena(String ime, PolniOrgan polniOrgan) {
		super();
		this.ime = ime;
		this.polniOrgan = polniOrgan;
	}
	
	public Zena() {
		super();
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public PolniOrgan getPolniOrgan() {
		return polniOrgan;
	}
	
	public void setPolniOrgan(PolniOrgan polniOrgan) {
		this.polniOrgan = polniOrgan;
	}

	public Torba getTorba() {
		return torba;
	}

	public void setTorba(Torba torba) {
		this.torba = torba;
	}

	@Override
	public void ispisiSe() {
		System.out.println(ime);
		
	}
	
	
}
