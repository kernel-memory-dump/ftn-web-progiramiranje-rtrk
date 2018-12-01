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
import java.util.ArrayList;
import java.util.List;

/**
 * Jednostavan web server
 */
public class httpd {
	
	private static List<String> usersList = new ArrayList<>();
	
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

				if (resource.equals("")){
					resource = "index.html";
					System.out.println("Request from " + addr.getHostName() + ": "
						+ resource);

					sendResponse(resource, skt.getOutputStream());
				
				}else if(resource.equals("dodaj.html")){
					resource = "dodaj.html";
					System.out.println("Request from " + addr.getHostName() + ": "
							+ resource);

					sendResponse(resource, skt.getOutputStream());
				}
				else if(resource.contains("dodaj?ime=")){
					String username =resource.split("=")[1];
					System.out.println("Parsed username is: " + username );
					if(addTolist(username) == null){
						sendSpecificResponse("Korisnik vec postoji", skt.getOutputStream(), false);
					}
					else{
						sendSpecificResponse("Korisnik uspeno dodat", skt.getOutputStream(), true);
					}
				}
				else if(resource.equals("brisi.html")){
					resource = "brisi.html";
					System.out.println("Request from " + addr.getHostName() + ": "
							+ resource);

					sendResponse(resource, skt.getOutputStream());
				}
				else if(resource.contains("brisi?ime=")){
					String username = resource.split("=")[1];
					System.out.println("Parsed username is: " + username );
					if(removeFromlist(username) == null){
						sendSpecificResponse("Korisnik ne postoji", skt.getOutputStream(), false);
					} else{
						sendSpecificResponse("Korisnik uspeno uklonjen", skt.getOutputStream(), true);
					}
				}

				
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
	}
	
	private static String addTolist(String username){
		
		if(usersList.contains(username)){
			return null;
		}
		usersList.add(username);
	
		return username;
	}
	
	
private static String removeFromlist(String username){
	int i = 0;
	
	for(i=0; i < usersList.size(); i++){
		if(usersList.get(i).equals(username)){
			usersList.remove(i);
			return username;
		}
	}
	
	return null;
	}
	
	
	static void sendSpecificResponse(String msg, OutputStream os, boolean successful){
			PrintStream ps = new PrintStream(os);
			// zamenimo web separator sistemskim separatorom
			if(!successful){
				ps.print("HTTP/1.0 404 File not found\r\n"
					+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 " + msg 
					+ "</b>");
				// ps.flush();
				System.out.println("ERROR: " + msg);
			}else {
				ps.print("HTTP/1.0 200 OK\r\n"
						+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>200 " + msg 
						+ "</b>");
					// ps.flush();
				System.out.println("CLEAN: " + msg);
			}
	}
	
}
