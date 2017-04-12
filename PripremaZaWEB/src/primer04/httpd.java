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
	
	private static List<User> users = new ArrayList<User>();
	
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
				if(resource == null) {
					continue;
				}
				
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
		if(s == null) {
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

	@SuppressWarnings("deprecation")
	static void sendResponse(String resource, OutputStream os)
			throws IOException {
		PrintStream ps = new PrintStream(os);
		File file = null;
			
		// DA LI VRACAMO FAJL ILI PARSIRAMO HTTP ZAHTEV SA FORME?
			
			if (resource.startsWith("index.html")) {
				String retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
				retVal += "<html><head><link href='style.css' rel='stylesheet' type='text/css'> </head>\n";
				retVal += "<body>";
				
				retVal+="<form action='/index.html'>";
				retVal+= "<table border='1'>";
				retVal+= "<tr>";
				retVal+="<td>Ime:</td>";
				retVal+="<td> <input type='text' name='ime' required></td>";
				retVal+= "</tr>";
				
				retVal+= "<tr>";
				retVal+="<td>Prezime:</td>";
				retVal+="<td> <input type='text' name='prezime'required></td>";
				retVal+= "</tr>";
				
				retVal+= "<tr>";
				retVal+="<td>Email:</td>";
				retVal+="<td> <input type='email' name='email'required></td>";
				
				retVal+= "<tr>";
				retVal+="<td>Grad:";
				retVal+="<td> <select name='grad'required>";
				retVal+="<option value='NS'>Novi Sad</option>";
				retVal+="<option value='BG'>Beograd</option>";
				retVal+= "</select></td>";
				retVal+= "</tr>";
				
				retVal+= "<tr>";
				retVal+="<td>Kredit:</td>";
				retVal+="<td> <input type='number' name='kredit'required></td>";
				retVal+= "</tr>";
				
				
				retVal +="<tr >";
				retVal += "<td colspan='2'><input type='submit' name='operacija' value='dodaj'>"
						+ "<input type='submit' name='operacija' value='snimi'>"
						+ "<input type='submit' name='operacija' value='obrisi'>"
						+ "<input type='submit' name='operacija' value='odustani'>"
						+ "</td>";
				retVal += "</tr>";
				retVal += "</table>";
				
				retVal += "</form>";
				
				Map<String,String> parametri = new HashMap<String,String>();
				String operacija=parametri.get("operacija");
				
				if(resource.contains("operacija")){
					String[] firstPart=resource.split("\\?"); 
					String params = firstPart[1];
					String [] paramParts = params.split("&");
					for(int i=0;i<paramParts.length;i++){
						String part=paramParts[i];
						String[] keyValue = part.split("=");
						System.out.println("SPLIT: "+keyValue);
						parametri.put(keyValue[0], URLDecoder.decode(keyValue[1]));
					}
					System.out.println(parametri.toString());
					switch(parametri.get("operacija")){
					case "dodaj":
						User u = new User();
						u.setIme(parametri.get("ime"));
						u.setPrezime(parametri.get("prezime"));
						u.setEmail(parametri.get("email"));
						u.setGrad(parametri.get("grad"));
						u.setKredit(Integer.parseInt(parametri.get("kredit")));
						users.add(u);
					}
				}
				
				if(resource.contains("operacija=dodaj")){
					
				}
				
				retVal += "<h1> Trenutno registrovani korisnici </h1>";
				if (users.isEmpty()){
					retVal += "<h2>Nema trenutno registrovanih</h2>";
				}else{
					retVal += "<table border='1'>";
					for(User user:users){
						retVal+="<tr>";
						retVal+="<td>"+user.getIme()+"</td>";
						retVal+="<td>"+user.getPrezime()+"</td>";
						retVal+="<td>"+user.getEmail()+"</td>";
						switch(user.getGrad()){
						case "NS":
							retVal+="<td><selsct>"
									+ "<option value='NS' selected>Novi Sad</option>"
									+ "<option value='BG'>Beograd</option>"
									+ "</select></td>";
							break;
						case "BG":
							retVal+="<td><selsct>"
									+ "<option value='BG' selected>Beograd</option>"
									+ "<option value='NS'>Novi Sad</option>"
									+ "</select></td>";
							break;
							default:
								break;
						}
						
						retVal+="<td>"+user.getKredit()+"</td>";
						retVal+="</tr>";
					}
							
				}
				
				retVal += "</body>";
				retVal += "</html>";
				
				ps.print(retVal);
				
				
			
			}  else {
				// NIJE SE POKLOPILO NI SA JEDNIM URLom za prihvatanje podataka sa forme
				// zamenimo web separator sistemskim separatorom
				resource = resource.replace('/', File.separatorChar);
				file = new File(resource);
				
				// AKO NIJE ZAHTEV SA FORME I NIJE FAJL , ONDA VRACAMO 404, NEMA NICEG NA TOM URL
				// POYYY
				if(!file.exists()) {
					// ako datoteka ne postoji, vratimo kod za gresku
					ps.print("HTTP/1.0 404 File not found\r\n"
							+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 ĐťĐ¸Ń�Đ°ĐĽ Đ˝Đ°Ń�Đ°Đľ:"
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
	
	/**
	 * Generisanje odgovora za web browser
	 */

}
