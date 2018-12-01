package eleonora.nan.model;

/**
 * Data model for all users in the system
 * 
 * @author student
 *
 */
public class User {

	public String ime;
	public String tip;

	public User() {

	}

	public User(String ime, String tip) {
		super();
		this.ime = ime;
		this.tip = tip;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getIme() {
		return this.ime;
	}

	public String getTip() {
		return this.ime;
	}

}
