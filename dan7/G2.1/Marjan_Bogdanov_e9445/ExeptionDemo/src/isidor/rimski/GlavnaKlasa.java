package isidor.rimski;

public class GlavnaKlasa {

	public static void main(String[] args) {
		MojaKlasa mojObj = new MojaKlasa();
		
		mojObj.pozitivniInt(-1);
		
/*//bilo bi u C-u
		if(f2() != 0){
			
			System.err.println("negde se desila greeska");
		}
*/

		
		try {
			mojObj.nekaMetoda();
		} catch (EdukativniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


/*//bilo bi u C-u
	public static int f1() {
		int rezultat;
		int errorKod =f2();
		if(errorKod != 0){
			return -1;
		}
		return 0;
	}
	public static int f2() {
		int rezultat;
	
		return -1;
	}
*/
}
