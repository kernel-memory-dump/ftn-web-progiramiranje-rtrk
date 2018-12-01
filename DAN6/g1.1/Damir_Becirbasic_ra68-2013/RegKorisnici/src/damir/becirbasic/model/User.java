package damir.becirbasic.model;

public class User {

	
	private String ime;
	private String tip;
	
	public User()
	{
	
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
		this.ime=ime;
		this.tip=tip;
		
		// TODO Auto-generated constructor stub
	}

}
