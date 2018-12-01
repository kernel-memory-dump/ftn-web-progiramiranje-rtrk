package isidor.rimski.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GlavnaKlasaKlient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner skener = new Scanner(System.in);
		String unosKorisnika = skener.nextLine();
		System.out.println("UCITANO::" + unosKorisnika);
		/*
		String parMBteksta = null; StringBuilder bilder = new
		StringBuilder();
		
		for (int i = 0; i < 2000; i++) { bilder.append("A"); }
		bilder.append("\n"); bilder.append(unosKorisnika); parMBteksta =
		bilder.toString();
		 */
		try {
			Socket vezaKaServeru = new Socket("10.81.35.60", 8080);
			OutputStream izlaz = vezaKaServeru.getOutputStream();
			PrintWriter printer = new PrintWriter(izlaz);
			/*
			printer.println(parMBteksta); 
			//*/ printer.println(unosKorisnika);
			printer.flush();

			InputStream ulaz = vezaKaServeru.getInputStream();
			Scanner ulazniSkener = new Scanner(ulaz);
			System.out.println("OD SERVERA: " + ulazniSkener.nextLine());
			ulazniSkener.close();

			vezaKaServeru.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
