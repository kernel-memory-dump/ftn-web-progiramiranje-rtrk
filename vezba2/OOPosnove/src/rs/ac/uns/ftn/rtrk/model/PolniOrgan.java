package rs.ac.uns.ftn.rtrk.model;

/**
 * Klasa koja modeluje polni organ
 * @author student
 *
 */

public class PolniOrgan implements Ispisivo {
   private boolean ispravan;
   private int duzina;
   private int sirina;
   
   public boolean isIspravan() {
	return ispravan;
   }
   public void setIspravan(boolean ispravan) {
	 this.ispravan = ispravan;
   }
   public int getDuzina() {
	return duzina;
   }
   public void setDuzina(int duzina) {
	this.duzina = duzina;
   }
   public int getSirina() {
	return sirina;
   }
   public void setSirina(int sirina) {
	this.sirina = sirina;
   }
@Override
   public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + duzina;
	result = prime * result + (ispravan ? 1231 : 1237);
	result = prime * result + sirina;
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
	PolniOrgan other = (PolniOrgan) obj;
	if (duzina != other.duzina)
		return false;
	if (ispravan != other.ispravan)
		return false;
	if (sirina != other.sirina)
		return false;
	return true;
   }
@Override
   public void ispisiSe() {
	// TODO Auto-generated method stub
	System.out.println("Ispisao se");
   }
	

}
