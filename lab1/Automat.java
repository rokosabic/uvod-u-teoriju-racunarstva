
import java.util.*;

public class Automat {
	private List<Stanje> stanja;
	private Set<Stanje> trenutnaStanja;
	
	public Automat(Stanje pocetnoStanje, List<Stanje> stanja2) {
		stanja = stanja2;
		trenutnaStanja = new HashSet<>();
		trenutnaStanja.add(pocetnoStanje);
	}
	
	public String obradi(String[] input) {
		
		StringBuilder sb = new StringBuilder();
		Set<Stanje> iducaStanja = new HashSet<>();
		iducaStanja.addAll(trenutnaStanja);
		
		int elementiPrije, elementiPoslije;
		
		do {
			elementiPrije = trenutnaStanja.size();
			for(Stanje stanje : iducaStanja) {
				if(stanje.transition("$") != null) trenutnaStanja.addAll(stanje.transition("$"));
			}
			elementiPoslije = trenutnaStanja.size();
			iducaStanja.addAll(trenutnaStanja);
//			trenutnaStanja.clear();
		} while(elementiPrije != elementiPoslije);
		
		trenutnaStanja.addAll(iducaStanja);
		iducaStanja.clear();
		dodajUOutput(sb, trenutnaStanja);
		sb.append("|");
		
		for(String simbol: input) {
			iducaStanja.clear();
			for(Stanje stanje : trenutnaStanja) {
				if(stanje.transition(simbol) != null) iducaStanja.addAll(stanje.transition(simbol));
			}
			trenutnaStanja.clear();
			trenutnaStanja.addAll(iducaStanja);
			
			do {
				elementiPrije = trenutnaStanja.size();
				for(Stanje stanje : iducaStanja) {
					if(stanje.transition("$") != null) trenutnaStanja.addAll(stanje.transition("$"));
				}
				
				elementiPoslije = trenutnaStanja.size();
				iducaStanja.addAll(trenutnaStanja);
			} while(elementiPrije != elementiPoslije);
			
			if(trenutnaStanja.size() == 0) trenutnaStanja.add(new Stanje("#"));
			dodajUOutput(sb, trenutnaStanja);
			sb.append("|");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public void dodajUOutput(StringBuilder sb, Set<Stanje> setStanja) {
		setStanja.stream().sorted(Comparator.comparing(Stanje::getName)).forEach(stanje -> sb.append(stanje.name + ","));
		sb.deleteCharAt(sb.length() - 1);
	}
	
}
