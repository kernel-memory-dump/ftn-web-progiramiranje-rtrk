package rs.ac.uns.ftn.rtrk.controller;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.rtrk.model.Ispisivo;
import rs.ac.uns.ftn.rtrk.model.Mesec;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mesec p1 = new Mesec();
		Mesec p2 = new Mesec();
		
		System.out.println("p1 == p2" + (p1 == p2));
		System.out.println("p1 == p2" + (p1 == p1));
		System.out.println("p1 == p2" + p1.equals(p2));
		
		
		
		List<Mesec> mesec = new ArrayList<>();
		mesec.add(p1);
		mesec.add(p2);
		
		System.out.println("DA LI SADRZI " + mesec.contains(p2));
		
		Ispisivo i = new Ispisivo() {
			
			@Override
			public void IspisIme() {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
