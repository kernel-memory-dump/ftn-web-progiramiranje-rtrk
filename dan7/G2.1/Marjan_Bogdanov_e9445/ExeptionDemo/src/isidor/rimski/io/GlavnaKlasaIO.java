package isidor.rimski.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GlavnaKlasaIO {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File mojFajl = new File("mojFajl.txt");

		try {
			FileOutputStream fajlStream = new FileOutputStream(mojFajl);
			BufferedOutputStream bafer = new BufferedOutputStream(fajlStream);
			String mojString = "test upis";
			bafer.write(mojString.getBytes());
			bafer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File mojFajl2 = new File("mojFajl2.txt");
		FileOutputStream fajlStream = new FileOutputStream(mojFajl2);
		PrintWriter printer = new PrintWriter(fajlStream);
		printer.println("moj neki ispis");
		printer.close();

		Scanner skener = new Scanner(System.in);
		String unosKorisknika = skener.nextLine();
		System.out.println("Ucitano::" + unosKorisknika);
		skener.close();

		FileInputStream ulazniStream = new FileInputStream(mojFajl2);
		Scanner skenerZaFajl = new Scanner(ulazniStream);
		while (skenerZaFajl.hasNext()) {
			String linija = skenerZaFajl.nextLine();
			System.out.println(linija);
		}
		skenerZaFajl.close();

	}

}
