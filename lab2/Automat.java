import java.util.*;
import java.util.stream.Collectors;

public class Automat {
	private Stanje pocetnostanje;
	
	public Automat(Stanje pocetnoStanje) {
		this.pocetnostanje = pocetnoStanje;
	}
	
	public HashSet<Stanje> dohvatljivaStanja(List<String> simboli){
		HashSet<Stanje> dohvatljivaStanja = new HashSet<>();
		Queue<Stanje> queue = new LinkedList<>();
		HashSet<Stanje> stanja = new HashSet<>();
		stanja.add(pocetnostanje);
		queue.add(pocetnostanje);
		
		
		while(!queue.isEmpty()) {
			Stanje trenStanje = queue.poll();
			dohvatljivaStanja.add(trenStanje);
			
			for(String simbol : simboli) {
				if(!dohvatljivaStanja.contains(trenStanje.transition(simbol))) {
					if(stanja.add(trenStanje.transition(simbol))) {
						queue.add(trenStanje.transition(simbol));
					}
				}	
			}
		}
		return dohvatljivaStanja;
	}
	 
	public HashSet<Stanje> istovjetna(HashSet<Stanje> svaStanja, List<String> simboli, HashSet<Stanje> prihStanja){
		HashSet<HashSet<Stanje>> grupe = new HashSet<>();
		HashSet<HashSet<Stanje>> novegrupe = new HashSet<>();
		HashSet<Stanje> g1 = new HashSet<>();
		HashSet<Stanje> g2 = new HashSet<>();
		
		for(Stanje stanje : svaStanja) {
			if(prihStanja.contains(stanje)) g1.add(stanje);
			else g2.add(stanje);
		}
		if(!g1.isEmpty())grupe.add(g1);
		if(!g2.isEmpty())grupe.add(g2);
		int velicina;
		do {
			novegrupe.clear();
			for(HashSet<Stanje> grupa : grupe) {
				Stanje nekoStanje = grupa.stream().sorted(Comparator.comparing(Stanje::getName)).collect(Collectors.toList()).get(0);
				HashSet<Stanje> novaGrupa = new HashSet<>();
				HashSet<Stanje> staraGrupa = new HashSet<>();
				for(Stanje stanje : grupa) {
					if(!provjera(nekoStanje, stanje, grupe, simboli)) {
						novaGrupa.add(stanje);
					} else staraGrupa.add(stanje);
				}
				if(!novaGrupa.isEmpty()) {
					novegrupe.add(novaGrupa);
				}
				novegrupe.add(staraGrupa);
			}			
			velicina = grupe.size();
			grupe.clear();
			grupe.addAll(novegrupe);
		} while(novegrupe.size() != velicina);
//		for(HashSet<Stanje> grupa : grupe) {
//			for(Stanje stanje : grupa) {
//				System.out.print(stanje + " ");
//			}
//			System.out.println();
//		}
		HashSet<Stanje> rjesenje = new HashSet<>();
		grupe.forEach(grupa -> {
			Stanje stanje = grupa.stream().sorted(Comparator.comparing(Stanje::getName)).collect(Collectors.toList()).get(0);
			rjesenje.add(stanje);	
			if(grupa.contains(pocetnostanje)) {
				pocetnostanje = grupa.stream().sorted(Comparator.comparing(Stanje::getName)).collect(Collectors.toList()).get(0);
			}
		});
		for(Stanje stanje : rjesenje) {
			for(String simbol : simboli) {
				if(!rjesenje.contains(stanje.transition(simbol))) {
					stanje.addTransition(simbol, grupe.stream().filter(grupa -> grupa.contains(stanje.transition(simbol))).collect(Collectors.toList()).get(0)
							.stream().sorted(Comparator.comparing(Stanje::getName)).collect(Collectors.toList()).get(0));			
				}
			}
		}
		return rjesenje;
	}
	
	public boolean provjera(Stanje prvo, Stanje drugo, HashSet<HashSet<Stanje>> grupe, List<String> simboli) {
		for(String simbol : simboli) {
			for(HashSet<Stanje> grupa : grupe) {
				if((!grupa.contains(prvo.transition(simbol)) 
						&& grupa.contains(drugo.transition(simbol))) 
						|| (grupa.contains(prvo.transition(simbol)) 
								&& !grupa.contains(drugo.transition(simbol)))){
					return false;
				}
			}
		}
		return true;
	}

	public Stanje getPocetnostanje() {
		return pocetnostanje;
	}
	
	
}




