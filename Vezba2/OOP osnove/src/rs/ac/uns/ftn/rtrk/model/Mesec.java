package rs.ac.uns.ftn.rtrk.model;

/**
 * Klasa koja modeliju prosecnu velicinu
 * @author student
 *
 */
public class Mesec implements Ispisivo {
	private Satelit satelit = new Satelit();
	private static int x;
	private Novcanik novcanik;
	
	{
		satelit = new Satelit();
	}
	
	static {
		
	}
	public Mesec() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Satelit getSatelit() {
		return satelit;
	}
	public void setSatelit(Satelit satelit) {
		this.satelit = satelit;
	}
	public Novcanik getNovcanik() {
		return novcanik;
	}
	public void setNovcanik(Novcanik novcanik) {
		this.novcanik = novcanik;
	}

	public void IspisIme() {
		// TODO Auto-generated method stub
		
	}
	
}
