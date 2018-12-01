package isidor.rimski.nizovi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlavnaKlasaNiz {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] x = new int[5];
		String[] x2 = new String[5];
		int[][] nizNizova = new int[5][];

		for (int i = 0; i < nizNizova.length; i++) {
			nizNizova[i] = new int[5];
			for (int j = 0; j < nizNizova[i].length; j++) {
				System.out.println("niz:" + nizNizova[i][j]);
			}
		}

		for (int i = 0; i < x.length; i++) {
			System.out.println("niz[" + i + "]=" + x[i]);
		}

		for (int i = 0; i < x2.length; i++) {
			x2[i] = "neki string" + i;
			x2[i] = new String("neki string" + i);
			System.out.println("niz[" + i + "]=" + x2[i]);
		}

		List<String> lista = new ArrayList<String>();

		lista.add("PRVI");
		lista.add("DRUGI");
		// oba for-a su ista
		for (int i = 0; i < lista.size(); i++) {
			String element = lista.get(i);
			System.out.println(element);
		}

		for (String element : lista) {
			System.out.println(element);
		}

		Map<String, Integer> mapa = new HashMap<>();
		mapa.put("BELO", 255);
		mapa.put("CRNO", 0);

		System.out.println(mapa.get("BELO"));
		System.out.println(mapa.get("CRNO"));

		if (mapa.containsKey("NEMA")) {
			System.out.println("IMA GA");
		} else {
			System.out.println("NEMA GA");
		}

		if (mapa.get("CRNO") != null) {
			System.out.println("NEMA GA");
		} else {
			System.out.println("IMA GA");
		}

		for (String kljuc : mapa.keySet()) {
			System.out.println("Kljuc je:" + kljuc);
			System.out.println("Vrednost je:" + mapa.get(kljuc));
		}
	}

}