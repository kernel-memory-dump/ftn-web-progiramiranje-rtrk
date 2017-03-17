package rs.ac.uns.ftn.rtrk.controler;

import rs.ac.uns.ftn.rtrk.model.Ispisivo;
import rs.ac.uns.ftn.rtrk.model.PolniOrgan;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void f1(PolniOrgan p1)
	{
		System.out.println(p1.getDuzina());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PolniOrgan p1 = new PolniOrgan();
		PolniOrgan p2 = new PolniOrgan();
		
		System.out.println("p1 == p2" + (p1 == p2));
		System.out.println("p1 == p1" + (p1 == p1));
		System.out.println("p1.equals(p2)" + (p1.equals(p2)));
		
		 List<PolniOrgan> polniOrgan = new ArrayList<>();
		 polniOrgan.add(p1);
		 //polniOrgani.add(p2);
		 
		 System.out.println("DA LI SADRZI P1?" + polniOrgan.contains(p1)); 
		 f1(p1);
		 
		 Ispisivo i = new Ispisivo() {
			
			@Override
			public void ispisiSe() {
				// TODO Auto-generated method stub
				
			}
		};
		
		Ispisivo cao = new Ispisivo() {
			
			@Override
			public void ispisiSe() {
				// TODO Auto-generated method stub
				
			}
		};
		String s1 = "pas";
		String s2 = "pas";
		System.out.println(s1.equals(s2)); 
	}
}
