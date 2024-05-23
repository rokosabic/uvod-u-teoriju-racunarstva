import java.util.*;

public class Automat {
    private Stanje pocetnoStanje;
    private String pocetniZnak;
    HashSet<Stanje> prihStanja;

    public Automat(Stanje pocetnoStanje, String pocetniZnak, HashSet<Stanje> prihStanja) {
        this.pocetnoStanje = pocetnoStanje;
        this.pocetniZnak = pocetniZnak;
        this.prihStanja = prihStanja;
    }

    public String getStack(Stack<String> stack){
        String[] arej = new String[stack.size()];
        stack.copyInto(arej);
        StringBuilder sb = new StringBuilder();
        List<String> lista = Arrays.asList(arej);
        Collections.reverse(lista);
        for(String linija : lista){
            if(!linija.equals("$"))
                sb.append(linija);
        }
        if(sb.length() == 0) sb.append("$");
        return sb.toString();
    }

    public String obradi(ArrayList<String> simboli){
        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack<>();

        Stanje trenStanje = pocetnoStanje;
        stack.push("$");
        stack.push(pocetniZnak);
        sb.append(trenStanje.getName() + "#" + pocetniZnak + "|");
        int j;
        for(j = 0; j < simboli.size(); j++){
            String ulaz = simboli.get(j);
            String stackEl = stack.pop();
            Pair trenPair = new Pair(ulaz, stackEl);

            if(trenStanje.getTransition(trenPair) == null){
                Pair noviPair = new Pair("$", stackEl);
                if(trenStanje.getTransition(noviPair) == null){
                    sb.append("fail|0");
                    return sb.toString();
                }
                Stanje iduceStanje = trenStanje.getTransition(noviPair).getIduceStanje();
                String noviStack = trenStanje.getTransition(noviPair).getNoviStack();
                for (int i = noviStack.length() - 1; i >= 0; i--) {
                    String stackElem = String.valueOf(noviStack.charAt(i));
                    if (!stackElem.equals("$")) {
                        stack.push(stackElem);
                    }
                }
                trenStanje = iduceStanje;
                sb.append(trenStanje.getName() + "#" + getStack(stack) + "|");
                j--;
                continue;
            }
            Stanje iduceStanje = trenStanje.getTransition(trenPair).getIduceStanje();
            String noviStack = trenStanje.getTransition(trenPair).getNoviStack();
            for (int i = noviStack.length() - 1; i >= 0; i--) {
                String stackElem = String.valueOf(noviStack.charAt(i));
                if (!stackElem.equals("$")) {
                    stack.push(stackElem);
                }
            }
            trenStanje = iduceStanje;
            sb.append(trenStanje.getName() + "#" + getStack(stack) + "|");
        }
        if(prihStanja.contains(trenStanje)){
            sb.append("1");
            return sb.toString();
        }
        Pair trenPair;
        do{
            String stackEl = stack.pop();
            trenPair = new Pair("$", stackEl);
            if(trenStanje.getTransition(trenPair) == null){
                sb.append("0");
                return sb.toString();
            }
            Stanje iduceStanje = trenStanje.getTransition(trenPair).getIduceStanje();
            String noviStack = trenStanje.getTransition(trenPair).getNoviStack();
            for (int i = noviStack.length() - 1; i >= 0; i--) {
                String stackElem = String.valueOf(noviStack.charAt(i));
                if (!stackElem.equals("$")) {
                    stack.push(stackElem);
                }
            }
            trenStanje = iduceStanje;
            sb.append(trenStanje.getName() + "#" + getStack(stack) + "|");
        } while(!prihStanja.contains(trenStanje));
        sb.append("1");
        return sb.toString();
    }
}
