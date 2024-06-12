import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Parser {
    public String input;
    public int position;
    public boolean success;
    public StringBuilder output;

    public Parser(String input){ //konstruktor za parser
        this.input = input;
        this.position = 0;
        this.success = true;
        this.output = new StringBuilder();
    }

    public String parse() {
        S();
        if (success && position == input.length()) {
            output.append("\nDA");
            return output.toString();
        } else {
        	output.append("\nNE");
            return output.toString();
        }
    }

    private void match(char token) {
        if (position < input.length() && input.charAt(position) == token) {
            position++;
        } else {
            success = false;
        }
    }

    private void S() {
        if (!success) return;
        output.append("S");
        if (position < input.length() && input.charAt(position) == 'a') {
            match('a');
            A();
            B();
        } else if (position < input.length() && input.charAt(position) == 'b') {
            match('b');
            B();
            A();
        } else {
            success = false;
        }
    }

    private void A() {
        if (!success) return;
        output.append("A");
        if (position < input.length() && input.charAt(position) == 'b') {
            match('b');
            C();
        } else if (position < input.length() && input.charAt(position) == 'a') {
            match('a');
        } else {
            success = false;
        }
    }

    private void B() {
        if (!success) return;
        output.append("B");
        if (position < input.length() - 1 && input.charAt(position) == 'c' && input.charAt(position + 1) == 'c') {
            match('c');
            match('c');
            S();
            if (success) {
                match('b');
                match('c');
            }
        }
    }

    private void C() {
        if (!success) return;
        output.append("C");
        A();
        A();
    }

    public static void main(String[] args) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        Parser p = new Parser(s.split("\n")[0]);
        System.out.println(p.parse());
    }
}
