import java.util.Objects;

public class Transition {
    private Stanje iduceStanje;
    private String noviStack;

    Transition(Stanje iduceStanje, String noviStack){
        this.iduceStanje = iduceStanje;
        this.noviStack = noviStack;
    }

    public Stanje getIduceStanje() {
        return iduceStanje;
    }

    public String getNoviStack() {
        return noviStack;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Transition && ((Transition) obj).getIduceStanje().equals(this.iduceStanje) &&
                ((Transition) obj).getNoviStack().equals(this.getNoviStack())) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iduceStanje, noviStack);
    }
}
