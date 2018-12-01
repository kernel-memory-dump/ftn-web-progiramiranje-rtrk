package isidor.rimski.string;

public class GlavnaKlasaString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "abc";
		test = test.replace("a", "1");
		test += "X";

		String test2 = "A;B;C;D";
		String[] rasparcano = test2.split(";");
		for (int i = 0; i < rasparcano.length; i++) {
			System.out.println(rasparcano[i]);
		}

		String prvi = "abc";
		String drugi = new String("abc");

		if (prvi == drugi) {
			System.out.println("JESU JEDNAKI");
		} else {
			System.out.println("NISU JEDNKAI");
		}

		if (prvi.equals(drugi)) {
			System.out.println("JESU JEDNAKI");
		} else {
			System.out.println("NISU JEDNKAI");
		}
		
		int broj = Integer.parseInt("10");
		System.out.println(broj);
	}

}
