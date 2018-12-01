package primer05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class UserServerThread extends Thread
{

	private Socket sock;
	private ArrayList<String> users;
	private BufferedReader in;
	private PrintWriter out;
	
	public UserServerThread(Socket sock, ArrayList<String> users)
	{
		this.sock = sock;
		this.users = users;
		
		try
		{
			// inicijalizuj ulazni stream
			in = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));

			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					sock.getOutputStream())), true);
			// pokreni thread
			start();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void run()
	{
		String response = "";
		try
		{
			// procitaj zahtev
			String request = in.readLine();

			// dodaj u listu
			response = clientResponse(request);

			// posalji odgovor
			out.println(response);

			// zatvori konekciju
			in.close();
			out.close();
			sock.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Generisanje odgovora za klijenta
	 */
	private synchronized String clientResponse(String request)
	{
		if(request.startsWith("ADD"))
		{
			// smemo da koristimo za projekat samo " " i ";"
			String[] delovi = request.split(" ");
			// 0
			// [ "ADD", "pera"]
			if(delovi.length != 2)
			{
				return "BAD REQUEST!!!\nEND";
			}
			
			users.add(delovi[1]);
			return "ALL OK\nEND";
		}
		else if(request.equals("LIST"))
		{
			StringBuilder builder = new StringBuilder();
			
			for (int i = 0; i < users.size(); i++)
			{
				String user = users.get(i);
				builder.append(user);
				builder.append("\n");
			}
			
			builder.append("\nEND");
			return builder.toString();
		}
		else
		{
			return "BAD REQUEST!!!\nEND";
		}
	}
}
