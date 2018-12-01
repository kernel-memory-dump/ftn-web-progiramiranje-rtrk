package david.ujhazi.model;

public class User {

	private String ime;
	private String tip;
	private String username;
	private String password;

	public User() {

	}

	public User(String ime, String tip) {
		super();
		this.ime = ime;
		this.tip = tip;
	}

	public User(String ime, String tip, String username, String password) {
		super();
		this.ime = ime;
		this.tip = tip;
		this.username = username;
		this.password = password;
	}

	public User(User user) {
		this.ime = user.ime;
		this.tip = user.tip;
		this.username = user.username;
		this.password = user.password;
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

	@Override
	public String toString() {
		return "Ime: " + ime + " Tip korisnika: " + tip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		return true;
	}

}
