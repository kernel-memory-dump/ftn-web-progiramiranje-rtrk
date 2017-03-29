package com.rt.rk.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static final int TCP_PORT = 9000;
	private final static String address ="10.81.36.48";
	static InetAddress addr;
	static Socket sock;
	static BufferedReader in;
	static PrintWriter out;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);  // Reading from System.in
			
		try {
			addr = InetAddress.getByName(address);
			
			sock = new Socket(addr, TCP_PORT);

			// inicijalizuj ulazni stream
			in = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));

			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sock.getOutputStream())), true);
		}
		catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	
		while(true) {
			System.out.println("Enter a number: ");
			String s = reader.nextLine(); // Scans the next token of the input as an int.
			
			// posalji zahtev
			System.out.println("Sending mesage...");
			out.println(s);

			if(s == "exit") {
				break;
			}
		}
		
		// zatvori konekciju
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
