package una.radosavac.model;

public class User {

	private String tip;
	private String ime;

	public User() {

	}

	public User(String ime, String tip) {
		this.ime = ime;
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
}
