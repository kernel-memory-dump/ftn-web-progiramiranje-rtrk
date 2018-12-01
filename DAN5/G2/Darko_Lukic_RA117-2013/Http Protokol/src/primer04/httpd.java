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
import java.util.HashMap;
import java.util.Map;


enum UserType { PREMIUM, FREE, STANDARD, UNDEFINED };

class User {
	private String username;
	private UserType type;
	
	User(String username, UserType type) {
		this.username = username;
		this.type = type;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
}

/**
 * Jednostavan web server
 */
public class httpd {
	private static ArrayList<User> users = new ArrayList<User>();
	
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
		
		if (resource.indexOf("?") >= 0) {
			onQueryRequest(resource, os);
		} else {
			onFileRequest(resource, os);
		}
	}
	
	static void onQueryRequest(String resource, OutputStream os) {
		// Parse query
		HashMap<String, String> values = new HashMap<String, String>();
		String action = resource.substring(0, resource.indexOf("?"));
		
		String queryString = resource.substring(resource.indexOf("?") + 1);
		String queryStringPairs[] = queryString.split("&");
		for (String queryStringPair : queryStringPairs) {
			String queryPair[] = queryStringPair.split("=");
			values.put(queryPair[0], queryPair.length == 2 ? queryPair[1] : "");
		}
		
		// Take an action
		switch (action) {
		case "dodaj":
			users.add(new User(values.get("ime"), UserType.valueOf(values.get("tip"))));
			createUsersListResponse(resource, os, "", UserType.UNDEFINED);
			break;
			
		case "pronadji":
			createUsersListResponse(resource, os, values.get("ime"), UserType.valueOf(values.get("tip")));
			break;
			
		case "obrisi":
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getUsername().equals(values.get("ime"))) {
					users.remove(i);
				}
			}
			createUsersListResponse(resource, os, "", UserType.UNDEFINED);
			break;
		}
	}
	
	static void createUsersListResponse(String resource, OutputStream os, String filterUsername, UserType filterType) {
		// Create a response
		PrintStream ps = new PrintStream(os);
		ps.print("HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n");
		
		ps.println("<ul>");
		for (User user : users) {
			if (user.getUsername().indexOf(filterUsername) >= 0 && (filterType == user.getType() || filterType == UserType.UNDEFINED)) {
				ps.println("<li>" + user.getUsername() + " | " + user.getType().toString());
				ps.println("<a href=\"obrisi?ime=" + user.getUsername() + "\">[x]</a>");
				ps.println("</li>");
			}
		}
		ps.println("</ul>");
		ps.println("<a href=\"index.html\">Nazad</a>");
	}
	
	static void onFileRequest(String resource, OutputStream os) throws IOException {
		PrintStream ps = new PrintStream(os);
		
		System.out.println("Resource = " + resource);
		
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
}
