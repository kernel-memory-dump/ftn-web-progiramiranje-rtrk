package rs.ac.uns.ftn.rtrk.model;

import java.util.ArrayList;

public class Novcanik {

	private ArrayList<String> sadrzaj = new ArrayList<>();
	
	public boolean hasItem(String item){
		return sadrzaj.contains(item);
	}
}
