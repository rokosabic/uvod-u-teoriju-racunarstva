import java.util.HashMap;

public class Stanje {
    private String name;
    HashMap<Pair, Transition> mapa;
    public Stanje(String name){
        this.name = name;
        mapa = new HashMap<>();
    }

    public void addTransition(Pair pair, Transition transition){
        mapa.put(pair, transition);
    }

    public Transition getTransition(Pair pair){
        return mapa.get(pair);
    }

    public void getKeys(){
        for(Pair par : mapa.keySet()){
            System.out.println(par.getSimbol() + " " + par.getZnak() + "\n");
        }
    }

    public void getValues(){
        for(Transition trans : mapa.values()){
            System.out.println(trans.getIduceStanje().getName() + " " + trans.getNoviStack() + "\n");
        }
    }

    public String getName() {
        return name;
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
