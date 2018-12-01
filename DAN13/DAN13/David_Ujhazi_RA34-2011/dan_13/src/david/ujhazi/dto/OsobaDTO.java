package david.ujhazi.dto;

public class OsobaDTO {

	private String ime;

	private int starost;

	public OsobaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OsobaDTO(String ime, int starost) {
		super();
		this.ime = ime;
		this.starost = starost;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public int getStarost() {
		return starost;
	}

	public void setStarost(int starost) {
		this.starost = starost;
	}

}
