package isidor.rimski;

public class MojaKlasa {
	
	public void nekaMetoda() throws EdukativniException {
		throw new EdukativniException("Poruka, greska se desila");
	}

	public void pozitivniInt(int i) {
		// TODO Auto-generated method stub
		if(i<=0) {
			throw new IllegalArgumentException("Argument mora biti veci od 0");
		}
		
	}
}
