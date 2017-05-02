
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Jednostavan web server
 */
public class httpd {

	

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
				// zastita od praznih zahteve od browsera 
				if (resource == null) {
					continue;
				}
				// localhost /pocetna
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
		// zastita od praznih zahteve od browsera 
		if (s == null) {
			return null;
		}

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

	
	/***
	 * Funkcija koja parsira HTTP parametre
	 * Povratna vrednost je Map objekat u kom je kljuc naziv parametra, a vrednost pod tim kljucem, uneta vrednost sa forme
	 * 
	 * Primer upotrebe:
	 * Ako je resource="index.html?ime=Pera&prezime=Peric&operacija=dodaj
	 * Map<String, String> parametri = getParams(resource);
	 * String ime = parametri.get("ime");  // ovo ce biti "Pera"
	 * String prezime = parametri.get("prezime"); // ovo ce biti "Peric"
	 * 
	 * @param resource
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> getParams(String resource) throws UnsupportedEncodingException {
		  final Map<String, String> queryPairs = new LinkedHashMap<String,String>();
		  // nadji ?
		  int queryStartIndex = resource.indexOf("?");
		  // ako nema ? , vrati praznu mapu
		  if(queryStartIndex == -1) {
			  return queryPairs;
		  }
		  
		  // u suprotnom parsiraj resource nakon ?
		  // odseci nakon ? ceo stalo
		  String query = resource.substring(queryStartIndex + 1);
		  
		  // cepaj po &
		  final String[] pairs = query.split("&");
		  
		  // napravi parove gde ce kljuc biti naziv parama, a vrednost pod tim kljucem ce biti vrednost parametra iz URL-a
		  for (String pair : pairs) {
		    final int idx = pair.indexOf("=");
		    final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
		    final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
		    queryPairs.put(key, value);
		  }
		  return queryPairs;
		}
	
	static void sendResponse(String resource, OutputStream os)
			throws IOException {
		PrintStream ps = new PrintStream(os);
		File file = null;
		// sadrzaj HTTP odgovora za klijenta
		String retVal = "";
		
		Map<String, String> parametri = getParams(resource);
		// DA LI VRACAMO FAJL ILI PARSIRAMO HTTP ZAHTEV SA FORME?
			if (resource.startsWith("index.html")) {
				retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
				retVal += "<html><head><link href='style.css' rel='stylesheet' type='text/css'></head>\n";
				retVal += "<body>";
				/////////////////////////
				// PRAZNA STRANICA
				
				// koja je operacija itd?
		
				retVal += "</body>";
				retVal += "</html>";
				// print u output stream da bi se poslalo...
				ps.print(retVal);
				
				

			}
			
			// else if mozemo dodati ako je neki drugi URL, neka druga forma, neka druga stranica
			//else if()  {
			//	
			//}
				
			
			else {
				// NIJE SE POKLOPILO NI SA JEDNIM URLom za prihvatanje podataka sa forme
				// zamenimo web separator sistemskim separatorom
				resource = resource.replace('/', File.separatorChar);
				file = new File(resource);
				
				// AKO NIJE ZAHTEV SA FORME I NIJE FAJL , ONDA VRACAMO 404, NEMA NICEG NA TOM URL
				// POYYY
				if(!file.exists()) {
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
				fis.close();
			} 
	

		

		ps.flush();
		
	}

}
