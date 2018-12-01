package primer04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

/**
 * Jednostavan web server
 */


public class httpd {
	
	static List<Osoba> users = new ArrayList<>();

	public static void main(String args[]) throws IOException {
		int port = 80;
		

		
		
		@SuppressWarnings("resource")
		ServerSocket srvr = new ServerSocket(port);

		System.out.println("httpd running on port: " + port);
		System.out.println("document root is: "
				+ new File(".").getAbsolutePath() + "\n");

		Socket skt = null;

		while (true) {
			try {
				skt = srvr.accept();
				InetAddress addr = skt.getInetAddress();

				String resource = getResource(skt.getInputStream());

				if (resource.equals(""))
					resource = "index.html";

				System.out.println("Request from " + addr.getHostName() + ": "
						+ resource);

				sendResponse(resource, skt.getOutputStream());
				skt.close();
				skt = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	static String getResource(InputStream is) throws IOException {
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		String s = dis.readLine();
		System.out.println(s);

		String[] tokens = s.split(" ");

		// prva linija HTTP zahteva: METOD /resurs HTTP/verzija
		// obradjujemo samo GET metodu
		String method = tokens[0];
		if (!method.equals("GET")) {
			return null;
		}

		String rsrc = tokens[1];

		// izbacimo znak '/' sa pocetka
		rsrc = rsrc.substring(1);

		// ignorisemo ostatak zaglavlja
		String s1;
		while (!(s1 = dis.readLine()).equals(""))
			System.out.println(s1);

		return rsrc;
	}

	static void sendResponse(String resource, OutputStream os)
			throws IOException {
		PrintStream ps = new PrintStream(os);
		// zamenimo web separator sistemskim separatorom
		resource = resource.replace('/', File.separatorChar);
		
		if(resource.endsWith(".html")){
			File file = new File(resource);

			if (!file.exists()) {
				// ako datoteka ne postoji, vratimo kod za gresku
				ps.print("HTTP/1.0 404 File not found\r\n"
						+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Нисам нашао:"
						+ file.getName() + "</b>");
				// ps.flush();
				System.out.println("Could not find resource: " + file);
				return;
			}

			// ispisemo zaglavlje HTTP odgovora
			ps.print("HTTP/1.0 200 OK\r\n\r\n");

			// a, zatim datoteku
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[8192];
			int len;

			while ((len = fis.read(data)) != -1) {
				ps.write(data, 0, len);
			}

			ps.flush();
			fis.close();
		} else if (resource.startsWith("DodajKorisnika")){
			int startIndex = resource.indexOf("ime=") + "ime=".length();
			int endIndex = resource.indexOf("&");
			String ime = resource.substring(startIndex, endIndex);

			startIndex= resource.indexOf("tip=", startIndex + 1) + "tip=".length() ;
			String tip = resource.substring(startIndex);
			//drugi nacin
			//DodajKorisnika?ime=pera
//			String[] delovi1 = resource.split("\\?");
//			String parametar= delovi1[1];
//			String[] delovi2=parametar.split("\\=");
			ime = URLDecoder.decode(ime, "UTF-8");
			Osoba osoba = new Osoba(ime, tip);
			
			users.add(osoba);
			ps.print(browserResponse("svi"));
		} else if (resource.startsWith("BrisiKorisnika")){
			String[] delovi1 = resource.split("\\?");
			String parametar= delovi1[1];
			String[] delovi2=parametar.split("\\=");
			String ime2 = delovi2[1];
			
			ime2 = URLDecoder.decode(ime2, "UTF-8");
			
			Osoba osoba = new Osoba(ime2, null);
		//drugi nacin	
//			for(int i = 0; i<users.size(); i++)
//			{
//				Osoba temp = users.get(i);
//				if (temp.equals(osoba)){
//					users.remove(i);
//				}
//			}
			
			users.remove(osoba);
			ps.print(browserResponse("svi"));
		} else if (resource.startsWith("Filtiraj")){

			
			int startIndex= resource.indexOf("tip=") + "tip=".length() ;
			String tip = resource.substring(startIndex);

			ps.print(browserResponse(tip));
		}
	}
		
		/**
		 * Generisanje odgovora za web browser
		 */
		private static  String browserResponse(String tip2) {
			String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
			retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
			retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
			
			for (int i = 0; i < users.size(); i++) {
				
				String ime = users.get(i).getIme();
				String tip = users.get(i).getTip();		
				if(tip2.equals("svi") || tip2.equals(tip)){
					retVal += "<li>" + ime + " "+ tip + "</li>\n";
				}
				
					
			}
			retVal += "</ol></body></html>\n";
			return retVal;
		}	
}
		
		



