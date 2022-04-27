import java.util.Scanner;
import java.util.ArrayList;


public class Oblig6 {
    public static void main (String[] args) {
        if (args.length < 1) {
            System.err.println("ingen fil argument gitt");
            System.exit(1);
        }

        else if (args.length > 1) {
            System.err.println("for mange fil argumenter");
            System.exit(1);
        }

        Labyrint labyrint = new Labyrint(args[0]);
        System.out.println(labyrint);

        // lag en standard input stream
        Scanner stdin = new Scanner(System.in);

        String choice = "";
        while (!choice.equals("-1")) {
            System.out.print("Skriv inn koordinater <rad> <kolonne> ('-1' for aa avslutte) ");
            
            choice = stdin.nextLine();

            if (choice.equals("-1")) {
                System.exit(0);
            } 

            String[] line_split = choice.split(" ");
            
            if (line_split.length < 2 || line_split.length > 2) {
                System.err.println("input maa ha 2 kordinater");
            }

            try {
                int y = Integer.parseInt(line_split[0]);
                int x = Integer.parseInt(line_split[1]);

                ArrayList<Tuppel> path = labyrint.finnUtveiFra(y, x);
                if (path == null) {
                    System.out.println(String.format("klarte ikke finne en vei fra (%s,%s)", y, x));
                }
                else {
                    for (Tuppel pos : path) {
                        System.out.println(pos);
                    }
                }
            }

            catch (NumberFormatException e) {
                System.err.println("ugyldige nummere i input");
            }
        }
    }
}
