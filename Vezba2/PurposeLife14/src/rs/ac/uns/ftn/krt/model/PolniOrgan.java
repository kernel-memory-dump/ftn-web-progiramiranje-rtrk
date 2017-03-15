package rs.ac.uns.ftn.krt.model;

public class PolniOrgan {
	public boolean isIspravan() {
		return ispravan;
	}

	public void setIspravan(boolean ispravan) {
		this.ispravan = ispravan;
	}

	public PolniOrgan(boolean ispravan) {
		super();
		this.ispravan = ispravan;
	}

	private boolean ispravan;

	public PolniOrgan() {
		super();
		// TODO Auto-generated constructor stub
	}
}
