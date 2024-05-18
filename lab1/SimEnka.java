

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimEnka{	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        StringBuilder sb = new StringBuilder();
        while (s != null) {
            sb.append(s + "\n");
            s = reader.readLine();
        }
		System.out.println(simulator(sb.toString()));     
	}
	public static String simulator(String tekst) {
        ArrayList<String[]> ulazNiz = new ArrayList<>();
    	List<Stanje> svaStanja = new ArrayList<>();
    	Set<String> simboliAbc = new HashSet<>();
    	Stanje pocetnoStanje = new Stanje("");
    	
    	String[] line = tekst.split("\n");
		
		for(String a: line[0].split("\\|")) {
			ulazNiz.add(a.split(","));
		}
		
		for(String b : line[1].split(",")) {
			svaStanja.add(new Stanje(b));
		}
		svaStanja.add(new Stanje("#"));
				
		for(String c : line[2].split(",")) {
			simboliAbc.add(c);
		}
		simboliAbc.add("$");
		
		for(Stanje stanje : svaStanja) {
			if(stanje.name.equals(line[4])) {
				pocetnoStanje = stanje;
				break;
			}
		}
				
		for(int i = 5; i < line.length; i++) {
			Stanje trenStanje = new Stanje(line[i].split("->")[0].split(",")[0]);
			String trenUlaz = line[i].split("->")[0].split(",")[1];
			for(String iduceStanje : line[i].split("->")[1].split(",")) {
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
		}
		StringBuilder sb = new StringBuilder();
		for(String[] ulaz : ulazNiz) {
			Automat automat = new Automat(pocetnoStanje, svaStanja);
			sb.append(automat.obradi(ulaz) + "\n");
		}
		return sb.toString();
	}
}
