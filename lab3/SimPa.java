import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SimPa {
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

    public static String simulator(String tekst){
        ArrayList<ArrayList<String>> ulazNiz = new ArrayList<>();
        HashSet<Stanje> svaStanja = new HashSet<Stanje>();
        HashSet<String> simboli = new HashSet<>();
        HashSet<String> znakovi = new HashSet<>();
        HashSet<Stanje> prihStanja = new HashSet<Stanje>();
        Stanje pocetnoStanje;
        String pocetniZnak;
        HashSet<Pair> parovi = new HashSet<>();

        String[] line = tekst.split("\n");

        for(String a : line[0].split("\\|")){
            ArrayList<String> ulaz = new ArrayList<>();
            ulaz.addAll(Arrays.asList(a.split(",")));
            ulazNiz.add(ulaz);
        }
        for(String b : line[1].split(",")) svaStanja.add(new Stanje(b));
        for(String c : line[2].split(",")) simboli.add(c);
        simboli.add("$");
        for(String d : line[3].split(",")) znakovi.add(d);
        znakovi.add("$");
        for(String e : line[4].split(",")) prihStanja.add(new Stanje(e));
        pocetnoStanje = new Stanje(line[5]);
        for(Stanje stanje : svaStanja){
            if(stanje.equals(pocetnoStanje)) pocetnoStanje = stanje;
        }
        pocetniZnak = line[6];


        for(int i = 7; i < line.length; i++){
            Stanje trenStanje = new Stanje(line[i].split("->")[0].split(",")[0]);
            String trenSimbol = line[i].split("->")[0].split(",")[1];
            String trenZnak = line[i].split("->")[0].split(",")[2];
            Stanje iduceStanje = new Stanje(line[i].split("->")[1].split(",")[0]);
            String noviStack = line[i].split("->")[1].split(",")[1];

            for(Stanje stanje : svaStanja){
                if(trenStanje.equals(stanje)) trenStanje = stanje;
            }

            for(Stanje stanje : svaStanja){
                if(iduceStanje.equals(stanje)) iduceStanje = stanje;
            }

            Pair pair = new Pair(trenSimbol, trenZnak);
            parovi.add(pair);
            Transition trans = new Transition(iduceStanje, noviStack);

            trenStanje.addTransition(pair, trans);
        }


        StringBuilder sb = new StringBuilder();
        for(ArrayList<String> ulaz : ulazNiz){
            Automat automat = new Automat(pocetnoStanje, pocetniZnak, prihStanja);
            sb.append(automat.obradi(ulaz) + "\n");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
}
