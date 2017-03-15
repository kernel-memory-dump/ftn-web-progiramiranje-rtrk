package rs.ac.uns.ftn.krt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Torba implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> sminka = new ArrayList<>();
	
	public boolean hasNesto(String nesto){
		return sminka.contains(nesto);
	}

	public boolean dodaj(String nesto){
		return sminka.add(nesto);
	}
	
	public List<String> getSminka() {
		return sminka;
	}

	public void setSminka(List<String> sminka) {
		this.sminka = sminka;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sminka == null) ? 0 : sminka.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Torba other = (Torba) obj;
		if (sminka == null) {
			if (other.sminka != null)
				return false;
		} else if (!sminka.equals(other.sminka))
			return false;
		return true;
	}
}
