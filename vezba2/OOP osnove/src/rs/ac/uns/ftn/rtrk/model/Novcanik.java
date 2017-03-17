package rs.ac.uns.ftn.rtrk.model;

import java.util.ArrayList;
import java.util.List;

public class Novcanik {

	List<String> sadrzaj = new ArrayList<>();
	
	public boolean hasItem(String item){
		return sadrzaj.contains(item);
	}
}
