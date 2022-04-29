import java.util.Scanner;
import java.util.ArrayList;


public class Oblig6 {
    public static void main (String[] args) {
        // skjekker om bruker har gitt ett fil argument
        if (args.length < 1) {
            System.err.println("ingen fil argument gitt");
            System.exit(1);
        }

        else if (args.length > 1) {
            System.err.println("for mange fil argumenter");
            System.exit(1);
        }

        // lager en ny labyrint med fil argumentet
        // og printer ut den nye labyrinten
        Labyrint labyrint = new Labyrint(args[0]);
        System.out.println(labyrint);

        // lag en standard input stream
        Scanner stdin = new Scanner(System.in);

        // loop helt til brukeren skriver inn -1
        String choice = "";
        while (true) {
            System.out.print("Skriv inn koordinater <rad> <kolonne> ('-1' for aa avslutte) ");
            
            // les bruker inptu
            choice = stdin.nextLine();

            // skjekk om inputtet gitt er -1 for da å stoppe programmet
            if (choice.equals("-1")) {
                stdin.close();
                System.exit(0);
            } 

            // spitt bruker input ved mellomrom
            String[] line_split = choice.split(" ");
            
            // sørg for at brukeren ga 2 verdier som input 
            if (line_split.length != 2) {
                System.err.println("input krever ha 2 kordinater");
                continue;
            }

            // prøv å parse string dataen til int verdier som blir
            // brukt som startspunktet for pathfindinen i labyrinten
            try {
                int y = Integer.parseInt(line_split[0]);
                int x = Integer.parseInt(line_split[1]);

                ArrayList<Tuppel> path = labyrint.finnUtveiFra(y, x);
                
                // hvis path er null så fant det ingen vei ut fra (y,x) startspunktet
                if (path == null) {
                    System.out.println(String.format("klarte ikke finne en vei fra (%s,%s)", y, x));
                }
                else {
                    // marker hver rute i arraylisten path
                    labyrint.markPath(path);

                    // output labyrinten etter å ha markert den
                    System.out.println(labyrint);
                }
            }

            catch (NumberFormatException e) {
                System.err.println("input krever ha 2 kordinater");
            }

            catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("kordinat finnes ikke i labyrinten");
            }
        }
    }
}
