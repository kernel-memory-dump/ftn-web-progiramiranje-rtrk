package tatjana.eric.model;

public class User {

	public String ime;
	public String tip;
	
	public User(){
		
	}
	
	public User(String ime, String tip) {
		this.ime=ime;
		this.tip=tip;
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

}
