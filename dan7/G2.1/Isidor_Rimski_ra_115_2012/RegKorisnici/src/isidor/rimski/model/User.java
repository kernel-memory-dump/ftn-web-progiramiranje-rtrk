package isidor.rimski.model;

/**
 * Data model for all users in the system
 * @author student
 *
 */
public class User {
	private String ime;
	private String tip;
	private String username;
	private String pasword;
	
	
	public User() {
		super();
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public User(String ime, String tip) {
		this.ime = ime;
		this.tip = tip;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
