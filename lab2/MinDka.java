
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class MinDka {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        List<String> line = new ArrayList<>();
        while (s != null) {
            line.add(s);
            s = reader.readLine();
        }
		System.out.print(simulator(line));
	}
	
	public static String simulator(List<String> line) {
		HashSet<Stanje> svaStanja = new HashSet<>();
		List<String> simboli = new ArrayList<>();
		HashSet<Stanje> prihStanja = new HashSet<>();
		Stanje pocetnoStanje = new Stanje("");
		
		for(String s : line.get(0).split(",")) {
			svaStanja.add(new Stanje(s));
		}
		
		for(String a : line.get(1).split(",")) {
			simboli.add(a);
		}
		
		for(String b : line.get(2).split(",")) {
			prihStanja.add(new Stanje(b));
		}
		
		for(Stanje stanje : svaStanja) {
			if(stanje.name.equals(line.get(3))) {
				pocetnoStanje = stanje;
				break;
			}
		}
		
		for(int i = 4; i < line.size(); i++) {
			Stanje trenStanje = new Stanje(line.get(i).split("->")[0].split(",")[0]);
			String trenUlaz = line.get(i).split("->")[0].split(",")[1];
			String iduceStanje = line.get(i).split("->")[1];
			Stanje stanjeTemp = new Stanje("");
			for(Stanje stanje : svaStanja) {
				if(stanje.name.equals(iduceStanje)) {
					stanjeTemp = stanje;
					break;
				}
			}
			for(Stanje stanje : svaStanja) {
				if(stanje.name.equals(trenStanje.name)) {
					stanje.addTransition(trenUlaz, stanjeTemp);
				}
			}
		}
		
		Automat automat = new Automat(pocetnoStanje);
		HashSet<Stanje> dohvatljiva = new HashSet<>();
		dohvatljiva = automat.dohvatljivaStanja(simboli);
		svaStanja.retainAll(dohvatljiva);
		prihStanja.retainAll(dohvatljiva);
		HashSet<Stanje> novaStanja = automat.istovjetna(svaStanja, simboli, prihStanja);
		prihStanja.retainAll(novaStanja);
		
		StringBuilder sb = new StringBuilder();
		
		for(Stanje stanje : novaStanja) {
			sb.append(stanje.toString() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\n");
		
		
		for(String simbol : simboli) {
			sb.append(simbol + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\n");
		
		
		if(!prihStanja.isEmpty()) {
			for(Stanje prihStanje : prihStanja) {
				sb.append(prihStanje + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("\n");
		
		sb.append(automat.getPocetnostanje() + "\n");
		
		novaStanja.stream().sorted(Comparator.comparing(Stanje::getName)).collect(Collectors.toList()).forEach(stanje -> {
			for(String simbol : simboli) {
				sb.append(stanje.getName() + "," + simbol + "->" + stanje.transition(simbol).getName() + "\n");
			}
		});
		
		
		return sb.toString();		
	}
}
