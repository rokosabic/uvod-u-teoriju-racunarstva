
import java.util.*;

public class Stanje {
	public String name;
	private HashMap<String, List<Stanje>> mapaPrijelaza;
	
	public Stanje(String name) {
		this.name = name;
		mapaPrijelaza = new HashMap<>();
	}
	
	
	public void addTransition(String simbol, Stanje stanje) {
		if(stanje.name.equals("#")) return;
		if(mapaPrijelaza.get(simbol) == null) {
			List<Stanje> prijelazi = new ArrayList<>();
			prijelazi.add(stanje);
			mapaPrijelaza.put(simbol, prijelazi);
		} else {
			mapaPrijelaza.get(simbol).add(stanje);
		}
	}
	
	public List<Stanje> transition(String simbol){
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

	
	
	
}
