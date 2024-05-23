import java.util.Objects;

public class Pair {
    String simbol;
    String znak;

    Pair(String simbol, String znak){
        this.simbol = simbol;
        this.znak = znak;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getZnak() {
        return znak;
    }

    public void setZnak(String znak) {
        this.znak = znak;
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the object is compared with itself
        if (this == obj) {
            return true;
        }

        // Check if obj is an instance of Pair
        if (obj instanceof Pair) {
            Pair other = (Pair) obj;
            // Compare the fields for equality
            return this.simbol.equals(other.simbol) && this.znak.equals(other.znak);
        }

        // The objects are not equal if they are not of the same type
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(simbol, znak);
    }
}
