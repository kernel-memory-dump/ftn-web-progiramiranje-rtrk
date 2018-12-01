package zadatak4;

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
import java.util.ArrayList;
import java.util.List;


	public class Zadatak4 {
		private static  List<String> users = new ArrayList<>();
		
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
						resource = "dodaj.html";

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
			//File file = new File(resource);
			
			if(resource.contains(".html")){
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
			}else if(resource.contains("dodajKorisnika")){
				String[] tokens = resource.split("\\=");
				users.add(tokens[1]);
				String response = browserResponse();
				ps.println(response);
				ps.flush();
			}else if(resource.contains("brisiKorisnika")){
				String[] tokens = resource.split("\\=");
				users.remove(tokens[1]);
				String response = browserResponse();
				ps.println(response);
				ps.flush();
			}
		}
		
		private static String browserResponse() {
			String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
			retVal += "<html><head><title>Prijavljeni korisnici</title></head>\n";
			retVal += "<body><h1>Prijavljeni korisnici</h1><ol>\n";
			for (int i = 0; i < users.size(); i++) {
				String user = users.get(i);
				retVal += "<li>" + user + "</li>\n";
			}
			retVal += "</ol></body></html>\n";
			return retVal;
		}
}
