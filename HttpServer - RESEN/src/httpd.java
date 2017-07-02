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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
	 * Funkcija koja parsira HTTP parametre Povratna vrednost je Map objekat u
	 * kom je kljuc naziv parametra, a vrednost pod tim kljucem, uneta vrednost
	 * sa forme
	 * 
	 * Primer upotrebe: Ako je
	 * resource="index.html?ime=Pera&prezime=Peric&operacija=dodaj Map<String,
	 * String> parametri = getParams(resource); String ime =
	 * parametri.get("ime"); // ovo ce biti "Pera" String prezime =
	 * parametri.get("prezime"); // ovo ce biti "Peric"
	 * 
	 * @param resource
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> getParams(String resource)
			throws UnsupportedEncodingException {
		final Map<String, String> queryPairs = new LinkedHashMap<String, String>();
		// nadji ?
		int queryStartIndex = resource.indexOf("?");
		// ako nema ? , vrati praznu mapu
		if (queryStartIndex == -1) {
			return queryPairs;
		}

		// u suprotnom parsiraj resource nakon ?
		// odseci nakon ? ceo stalo
		String query = resource.substring(queryStartIndex + 1);

		// cepaj po &
		final String[] pairs = query.split("&");

		// napravi parove gde ce kljuc biti naziv parama, a vrednost pod tim
		// kljucem ce biti vrednost parametra iz URL-a
		for (String pair : pairs) {
			final int idx = pair.indexOf("=");
			final String key = idx > 0 ? URLDecoder.decode(
					pair.substring(0, idx), "UTF-8") : pair;
			final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder
					.decode(pair.substring(idx + 1), "UTF-8") : null;
			queryPairs.put(key, value);
		}
		return queryPairs;
	}

	/**
	 * Forma na pocetnoj gadja sledeci URL: index.html?operacija=X Gde X moze
	 * biti: dodaj, izmeni , obrisi
	 * ----------------------------------------------------------- Kada se
	 * klikne na dugme IZMENI iz tabele ispod gadja se URL: popunjena.html, gde
	 * se ispisuje SKORO isti HTML kao za index.html jedina razlike je sto su
	 * polja na formi popunjena unapred i "email" je readonly, da bi se sprecila
	 * izmena
	 * -------------------------------------------------------------------- Kada
	 * se klikne na dugme BRISI iz tabele, gadja se URL brisi, gde se samo
	 * prosledi mejl korisnika koga treba izbrisati u odgovoru se ispise sadrzaj
	 * stranice identican index.html
	 * -------------------------------------------------------------------- Kao
	 * poslednji red tabele je forma za filtiranje, koja gadja: filtrirano.html
	 * Ova stranica prikazuje samo korisnike sa kreditom vecim od 1000, ako
	 * takvih nema, nijedan korisnik se ne prikazuje
	 * 
	 * 
	 * 
	 * @param resource
	 * @param os
	 * @throws IOException
	 */
	static void sendResponse(String resource, OutputStream os)
			throws IOException {
		PrintStream ps = new PrintStream(os);
		File file = null;
		// sadrzaj HTTP odgovora za klijenta
		String retVal = "";

		Map<String, String> parametri = getParams(resource);

		// IMAMO U SUSTINI par stranica
		// ===>, pocetnu sa praznom formom
		// ===> POCETNU sa POPUNJENOM FORMOM, kojoj se pristupa ako se klikne na
		// izmeni u tabeli
		// ===> FILTIRANU stranicu gde su prikazani svi sa kreditom > 1000
		if (resource.startsWith("index.html")) {
			retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
			retVal += "<html><head><link href='style.css' rel='stylesheet' type='text/css'></head>\n";
			retVal += "<body>";
			// ISPISIVANJE PROKLETE FORME JE u funkciji
			// AKO JOJ PROLEDIMO null, nece popuniti formu
			// AKO JOJ PROSLEDIMO USERA-a KOJI NIJE NULL, popunice formu sa
			// njegovim podacima
			retVal += ispisiFormuProkletu(null);

			//
			// SVE OPERACIJE ODRADIMO PRE ISPISA LISTE
			// DODAMO ILI IZBACIMO ILI ISFILTIRAMO LISTU,
			// NAKON TOGA KOD ZA ISPIS TABELE SA SPISKOM KORISNIKA MOZE DA SE
			// ISKORISTI ZA SVE OPERACIJE

			retVal += obradiOperaciju(resource, parametri);

			// //////////////////
			// RUKOVANJE AKCIJONM ....
			// //////////////////////

			// STAMPANJE TABELE SA KORISNICIMA JE UVEK ISTO, MOZE SE
			// IZDVOJITI U METODU
			// PA SE POZVATI NPR NAKON FILTRIRANJA....ILI NAKON NEKE AKCIJE
			// ITD
			// += to sto vrati funkcija
			retVal += ispisiTabeluProkletu(users);

			retVal += "</body>";
			retVal += "</html>";

			ps.print(retVal);

			// else if mozemo dodati ako je neki drugi URL, neka druga forma,
			// neka
			// druga stranica
			// else if() {
			//
			// }

		} else if (resource.startsWith("izmeniKorisnika")) {
			retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
			retVal += "<html><head><link href='style.css' rel='stylesheet' type='text/css'></head>\n";
			retVal += "<body>";

			// nadjemo korisnika
			// popunimo formu sa njegovim podacima, prikazemo sve kao na
			// index.html
			// zabranimo izmenu mejla

			User zaIzmenu = null;
			// nadjemo korisnika koga menjamo
			for (User u : users) {
				if (u.getEmail().equals(parametri.get("kogaMenjati"))) {
					zaIzmenu = u;
					break;
				}
			}

			retVal += ispisiFormuProkletu(zaIzmenu);

			// STAMPANJE TABELE SA KORISNICIMA JE UVEK ISTO, MOZE SE
			// IZDVOJITI U METODU
			// PA SE POZVATI NPR NAKON FILTRIRANJA....ILI NAKON NEKE AKCIJE
			// ITD
			// += to sto vrati funkcija
			retVal += ispisiTabeluProkletu(users);

			retVal += "</body>";
			retVal += "</html>";

			ps.print(retVal);

		} else if (resource.startsWith("izbrisiKorisnika")) {
			retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
			retVal += "<html><head><link href='style.css' rel='stylesheet' type='text/css'></head>\n";
			retVal += "<body>";

			// stampamo praznu formu
			retVal += ispisiFormuProkletu(null);

			// izbrisemo korisnika i ispisemo sve isto kao na index.html

			for (int i = 0; i < users.size(); i++) {
				User zaBrisanje = users.get(i);
				if (zaBrisanje.getEmail().equals(parametri.get("kogaBrisati"))) {
					users.remove(i);
					break; // kraj for petlje
				}
			}

			// STAMPANJE TABELE SA KORISNICIMA JE UVEK ISTO, MOZE SE
			// IZDVOJITI U METODU
			// PA SE POZVATI NPR NAKON FILTRIRANJA....ILI NAKON NEKE AKCIJE
			// ITD
			// += to sto vrati funkcija
			retVal += ispisiTabeluProkletu(users);
			retVal += "</body>";
			retVal += "</html>";

			ps.print(retVal);

		} else if (resource.startsWith("filtirajKredit")) {
			retVal = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=UTF-8\r\n\r\n";
			retVal += "<html><head><link href='style.css' rel='stylesheet' type='text/css'></head>\n";
			retVal += "<body>";

			// stampamo praznu formu
			retVal += ispisiFormuProkletu(null);

			List<User> filtrirano = new ArrayList<User>();

			// PO CEMU FITLRIRAMO?????
			String kriterijum = parametri.get("kriterijum");
			switch (kriterijum) {
			case "iznad1000": {
				for (User u : users) {
					if (u.getKredit() > 1000) {
						filtrirano.add(u);
					}
				}
			}
				break;

			case "ispod1000": {
				for (User u : users) {
					if (u.getKredit() < 1000) {
						filtrirano.add(u);
					}
				}
				break;
			}
			default: // SVI , bez filtera
				filtrirano = users;
				break;
			}

			// procistimo listu korisnika na osnovu kriterijuma izabranog
			// sve ostalo isto kao za index.html

			// STAMPANJE TABELE SA KORISNICIMA JE UVEK ISTO, MOZE SE
			// IZDVOJITI U METODU
			// PA SE POZVATI NPR NAKON FILTRIRANJA....ILI NAKON NEKE AKCIJE
			// ITD
			// += to sto vrati funkcija
			retVal += ispisiTabeluProkletu(filtrirano);

			retVal += "</body>";
			retVal += "</html>";

			ps.print(retVal);

		}

		else {
			// NIJE SE POKLOPILO NI SA JEDNIM URLom za prihvatanje podataka
			// sa
			// forme
			// zamenimo web separator sistemskim separatorom
			resource = resource.replace('/', File.separatorChar);
			file = new File(resource);

			// AKO NIJE ZAHTEV SA FORME I NIJE FAJL , ONDA VRACAMO 404, NEMA
			// NICEG NA TOM URL
			// POYYY
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
			fis.close();
		}

		ps.flush();

	}

	private static String obradiOperaciju(String resource,
			Map<String, String> parametri) {
		String retVal = "";


		if (resource.contains("operacija")) {
			// index.hmtl?ime=&prezime=&email=&grad=NS&kredit=&operacija=dodaj
			String[] parts = resource.split("\\?");
			// fetch the other part
			// ime=x&prezime=&email=&grad=NS&kredit=&operacija=dodaj
			String params = parts[1];
			String[] paramParts = params.split("&");
			// ["ime=pera", "prezime=peric" ]
			for (int i = 0; i < paramParts.length; i++) {
				// ime=Pera
				String part = paramParts[i];
				String[] keyValue = part.split("=");
				System.out.println("SPLIT:" + keyValue);
				// "ime" "Pera"
				parametri.put(keyValue[0], URLDecoder.decode(keyValue[1]));
			}

			System.out.println(parametri.toString());

			switch (parametri.get("operacija")) {
			case "dodaj": {
				// NIJE LOSE STAVITI CASE unutar { } , da bi mogli da
				// koristimo
				// lokalne varijable sa istim imenima u svakom case-u, inace
				// bi imali problem

				User u = new User();
				u.setIme(parametri.get("ime"));
				u.setPrezime(parametri.get("prezime"));
				u.setEmail(parametri.get("email"));
				u.setGrad(parametri.get("grad"));
				u.setKredit(Integer.parseInt(parametri.get("kredit")));

				users.add(u);

				break;
			}
			case "snimi": { /*
							 * snimi salje podatke sa forme za izmenu
							 * postojeceg korisnika
							 */

				// nadjemo korisnika koga menjamo
				for (User zaIzmenu : users) {
					if (zaIzmenu.getEmail().equals(parametri.get("email"))) {
						zaIzmenu.setIme(parametri.get("ime"));
						zaIzmenu.setPrezime(parametri.get("prezime"));
						zaIzmenu.setGrad(parametri.get("grad"));
						zaIzmenu.setKredit(Integer.parseInt(parametri
								.get("kredit")));
					}
				}

				break;
			}
			/*
			 * obrisi sa prve forme, ne iz tabele, klik na dugme iz tabele se
			 * rukuje dole u else if
			 */
			case "obrisi": {
				// nadjemo korisnika koga brisemo

				for (int i = 0; i < users.size(); i++) {
					User zaBrisanje = users.get(i);
					if (zaBrisanje.getEmail().equals(parametri.get("email"))) {
						users.remove(i);
						break; // kraj for petlje
					}
				}

				break;
			}

			default: {
				// odustani bi ovde islo, ne radimo nista svakako
				break;
			}
			}
		}

		return retVal;

	}

	/**
	 * ISPISUJE formu , treba dodati ovo sto ova funkcija vrati na ukupan HTML
	 * kod
	 * 
	 * AKO JOJ SE PROSLEDI null, forma ce biti prazna, AKO JOJ SE PROSLEDI USER
	 * KOJI NIJE NULL, popunice se forma sa njegovim podacima
	 * 
	 * @return
	 */
	private static String ispisiFormuProkletu(User popuniSaOvim) {

		String retVal = "";

		// AKO JE NULL, PRAZNO:
		if (popuniSaOvim == null) {

			retVal += "<form action='/index.html' >";
			retVal += "<table border='1'>";

			retVal += "<tr>";
			retVal += "<td>Ime:</td>";
			retVal += "<td><input type='text' name='ime' required></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td>Prezime:</td>";
			retVal += "<td><input type='text' name='prezime' required></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td>Email:</td>";
			retVal += "<td><input type='email' name='email' required></td>";
			retVal += "</tr>";
			retVal += "<tr>";
			retVal += "<td>Grad:</td>";
			retVal += "<td><select  name='grad' required>";
			retVal += "<option value='NS'>Novi Sad</option>";
			retVal += "<option value='BG'>Beograd</option>";
			retVal += "</select></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td>Kredit:</td>";
			retVal += "<td><input type='number' name='kredit' required></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td  colspan='2'     ><input type='submit' name='operacija' value='dodaj'>"
					+ "<input type='submit' name='operacija' value='snimi'>"
					+ "<input type='submit' name='operacija' value='obrisi'>"
					+ "<input type='submit' name='operacija' value='odustani'>"
					+ "<a href='/index.html'> ODUSTANI KAO LINK </a>"
					+ "</td>";
			retVal += "</tr>";
			retVal += "</table>";
			retVal += "</form>";
		} else {

			// popuni SA PODACIMA IZ OBJEKTA USER~!!
			// MEJL CE BITI READONLY~!

			retVal += "<form action='/index.html' >";
			retVal += "<table border='1'>";

			retVal += "<tr>";
			retVal += "<td>Ime:</td>";
			retVal += "<td><input type='text' name='ime' required  value='"
					+ popuniSaOvim.getIme() + "'></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td>Prezime:</td>";
			retVal += "<td><input type='text' name='prezime' required value='"
					+ popuniSaOvim.getPrezime() + "'></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td>Email:</td>";
			retVal += "<td><input type='email' name='email' required readonly value='"
					+ popuniSaOvim.getEmail() + "'></td>";
			retVal += "</tr>";
			retVal += "<tr>";
			retVal += "<td>Grad:</td>";
			retVal += "<td><select  name='grad' required>";
			// AKO SE GRAD POKLAPA, TREBA MU DODATI selected
			// AKO SE NE POKLAPA, NISTA moze ovako, moze i preko switch case kao
			// u tabeli

			retVal += "<option value='NS' "
					+ (popuniSaOvim.getGrad().equals("NS") ? "selected" : "")
					+ ">Novi Sad</option>";
			retVal += "<option value='BG' "
					+ (popuniSaOvim.getGrad().equals("BG") ? "selected" : "")
					+ ">Beograd</option>";
			retVal += "</select></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td>Kredit:</td>";
			retVal += "<td><input type='number' name='kredit' required  value='"
					+ popuniSaOvim.getKredit() + "'></td>";
			retVal += "</tr>";

			retVal += "<tr>";
			retVal += "<td  colspan='2'     ><input type='submit' name='operacija' value='dodaj'>"
					+ "<input type='submit' name='operacija' value='snimi'>"
					+ "<input type='submit' name='operacija' value='obrisi'>"
					+ "<input type='submit' name='operacija' value='odustani'>"
					+ "<a href='/index.html'> ODUSTANI KAO LINK </a>"
					+ "</td>";
			retVal += "</tr>";
			retVal += "</table>";
			retVal += "</form>";

		}

		return retVal;
	}

	/**
	 * Vraca HTML kod proklete tabele, da mozemo da ga nalepimo , umesto da
	 * copy/paste ovih 100 linija koda tamo vamo ZAHTEVA LISTU USERA KAO
	 * PARAMETAR, OVO NAM TREBA ZBOG FILTRIRANJA...kada ispisujemo filtiranu
	 * listu
	 * 
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String ispisiTabeluProkletu(List<User> users)
			throws UnsupportedEncodingException {

		String retVal = "";

		retVal += "<h1> TrenutnUTRNO REGISTROVANI KORISNICI</h1>";

		if (users.isEmpty()) {
			retVal += "<h2> Nema trenutno registrovanih korisnika</h2>";
		} else {

			// IPAK IH IMA>...
			retVal += "<table border='1'>";
			for (User user : users) {
				// ako treba filtirati neki if...
				retVal += "<tr>";
				retVal += "<td>" + user.getIme() + "</td>";
				retVal += "<td>" + user.getPrezime() + "</td>";
				retVal += "<td>" + user.getEmail() + "</td>";

				// OVO NIJE NEOPHODNO
				// OVO JE FENSI OVERKILL da imamo u tabeli kao <select>
				// izabran grad svakokg korisnika
				// DOVOLJNO JE I SAMO <td>Novi Sad</td>
				switch (user.getGrad()) {
				case "NS":
					retVal += "<td><select>";
					retVal += "<option value='NS' selected>Novi Sad </option>";
					retVal += "<option value='BG'>Beograd </option>";
					retVal += "</select></td>";
					break;

				case "BG":
					retVal += "<td><select>";
					retVal += "<option value='NS' >Novi Sad </option>";
					retVal += "<option value='BG' selected>Beograd </option>";
					retVal += "</select></td>";
					break;

				default:

					break;
				}
				// //////////////////////////////////////////////////////////////////////////////

				// PREKO LINKA MOZEMO OKINUTI IZMENU KORISNIKA, ISTO BI
				// URADILI I DA IMAMO FORMU + HIDDEN POLJE + SUBMIT
				// AKO IDEMO PREKO LINKA, MORAMO URADITI URLENCODE @
				// postaje %40, razmak %20 itd

				retVal += "<td>" + user.getKredit() + "</td>";
				retVal += "<td>" + "<a href='izmeniKorisnika?kogaMenjati="
						+ URLEncoder.encode(user.getEmail(), "UTF-8")
						+ "'>IZmeni</a>";
				retVal += "</td>";

				// PRIMER PREKO FORME:
				// retVal +=
				// "<form action=\"izbrisiKorisnika\">\r\n<input type='hidden' name='kogaBrisati' value='"
				// + user.getEmail() +
				// "'>\r\n<input type='submit' value='izmeni'>\r\n\r\n</form>";

				retVal += "<td>";
				retVal += "<a href='izbrisiKorisnika?kogaBrisati="
						+ URLEncoder.encode(user.getEmail(), "UTF-8")
						+ "'>Izbrisi</a>";
				retVal += "</td>";

				retVal += "</tr>";
			}

			// PSOLEDNJI RED JE <select> za FILTRIRANJE , POSEBNA FORMA SA ILI
			// NPR POSEBAN LINK ZA SVAKI KRITERIJUM

			retVal += "<tr> <td colspan='7'> <form action='filtirajKredit' >"
					+ "<select name='kriterijum'>"
					+ "<option value='iznad1000' >Prikazi sve korisnike sa kreditom preko 1000</option>"
					+ "<option value='ispod1000' >Prikazi sve korisnike sa kreditom ispod 1000</option>"
					+ "<option value='svi' >Prikazi sve korisnike </option>"
					+ "</select>"
					+ "<input type='submit' value='PRIMENI FILTER'>"
					+ "</form>   </td></tr>";

			retVal += "</table>";

		}

		return retVal;
	}

}
