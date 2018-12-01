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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jednostavan web server
 */
public class httpd {
	static Map<String, User> users = new HashMap<String, User>();

	public static void main(String args[]) throws IOException {
		int port = 80;

		@SuppressWarnings("resource")
		ServerSocket srvr = new ServerSocket(port);

		System.out.println("httpd running on port: " + port);
		System.out.println("document root is: " + new File(".").getAbsolutePath() + "\n");

		Socket skt = null;

		while (true) {
			try {
				skt = srvr.accept();
				InetAddress addr = skt.getInetAddress();

				String resource = getResource(skt.getInputStream());

				if (resource.equals(""))
					resource = "index.html";

				System.out.println("Request from " + addr.getHostName() + ": " + resource);

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

	static void sendResponse(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os);
		// zamenimo web separator sistemskim separatorom
		resource = resource.replace('/', File.separatorChar);

		if (resource.endsWith(".html")) {
			File file = new File(resource);

			if (!file.exists()) {
				// ako datoteka ne postoji, vratimo kod za gresku
				ps.print("HTTP/1.0 404 File not found\r\n"
						+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Нисам нашао:" + file.getName()
						+ "</b>");
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
		} else if (resource.startsWith("DodajKorisnika")) {
			// DodajKorisnika?ime=Pera
			int startIndex = resource.indexOf("ime=") + "ime=".length();// nacin1
			int endIndex = resource.indexOf("&");
			String ime = resource.substring(startIndex, endIndex);
			startIndex = resource.indexOf("tip=") + "tip=".length();
			String tip = resource.substring(startIndex);
			ime = URLDecoder.decode(ime, "UTF-8");
			//
			// String[] delovi1 = resource.split("\\?"); // nacin2
			// String[] delovi2 = delovi1[1].split("\\=");
			// String ime2 = delovi2[1];
			// ime2 = URLDecoder.decode(ime2, "UTF-8");
			users.put(ime, new User(ime, tip));
			ps.println(browserResponse("SVI"));
		} else if (resource.startsWith("BrisiKorisnika")) {
			// BrisiKorisnika?ime=Pera
			String[] delovi1 = resource.split("\\?");
			String[] delovi2 = delovi1[1].split("\\=");
			String ime2 = delovi2[1];
			ime2 = URLDecoder.decode(ime2, "UTF-8");
			users.remove(ime2);
			ps.println(browserResponse("SVI"));
		} else if (resource.startsWith("filter")) {
			int startIndex = resource.indexOf("tip=") + "tip=".length();

			String tip = resource.substring(startIndex);
			ps.println(browserResponse(tip));
		}
	}

	/**
	 * Generisanje odgovora za web browser
	 */
	static private String browserResponse(String tipFilter) {
		String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
		retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
		retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
		for (String i : users.keySet()) {
			User user = users.get(i);
			if (tipFilter.equals("SVI") || user.getType().equals(tipFilter))
				retVal += "<li>" + user.getName() + " " + user.getType() + "</li>\n";
			
		}
		retVal += "</ol></body></html>\n";
		return retVal;
	}

}
