package rs.ac.uns.ftn.rtrk.model;

/**
 * Klasa koja modeluje prosecnu stoku u Srbiji, poyyy
 * @author student
 *
 */
public class Muskarac {
	private static int x;
	private PolniOrgan polniOrgan;
	private Novcanik novcanik;
	
	{
		polniOrgan = new PolniOrgan();
	}
	
	public Muskarac() {
		super();
		x++;
		// TODO Auto-generated constructor stub
	}

	public PolniOrgan getPolniOrgan() {
		return polniOrgan;
	}

	public void setPolniOrgan(PolniOrgan polniOrgan) {
		this.polniOrgan = polniOrgan;
	}

	public Novcanik getNovcanik() {
		return novcanik;
	}

	public void setNovcanik(Novcanik novcanik) {
		this.novcanik = novcanik;
	}
	
	Ispisivo i = new Ispisivo() {
		
		@Override
		public void ispisiSe() {
			// TODO Auto-generated method stub
			
		}
	};
}
