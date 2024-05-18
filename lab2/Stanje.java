


import java.util.*;

public class Stanje {
	public String name;
	private HashMap<String, Stanje> mapaPrijelaza;
	
	public Stanje(String name) {
		this.name = name;
		mapaPrijelaza = new HashMap<>();
	}
		
	public void addTransition(String simbol, Stanje stanje) {
		mapaPrijelaza.put(simbol, stanje);
	}
	
	public Stanje transition(String simbol){
		return mapaPrijelaza.get(simbol);
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Stanje && ((Stanje) obj).name.equals(this.name)) return true;
		else return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	
	
	
}
