package rs.ac.uns.ftn.rtrk.controller;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.rtrk.model.PolniOrgan;

public class Main {

	public static void main(String[] args){
		PolniOrgan p1 = new PolniOrgan();
		PolniOrgan p2 = new PolniOrgan();
		
		System.out.println("p1==p2" + (p1==p2));
		System.out.println("p1==p1" + (p1==p1));
		System.out.println("p1.equals(p2)" + p1.equals(p2));
		
		List<PolniOrgan> polniOrgani = new ArrayList<>();
		polniOrgani.add(p1);
		//polniOrgani.add(p2);
		
		System.out.println("Da li sadrzi p1?" + polniOrgani.contains(p2));
	}
}
