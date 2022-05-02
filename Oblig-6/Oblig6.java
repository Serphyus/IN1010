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
        String position_choice = "";
        while (!position_choice.equals("-1")) {
            System.out.print("Gi posisjon <y:rad> <x:kolonne> ('-1' for aa avslutte) ");
            
            // les bruker input
            position_choice = stdin.nextLine();

            // skjekk om inputtet gitt er -1 for da å stoppe programmet
            if (position_choice.equals("-1")) {
                continue;
            } 
            
            // spitt bruker input ved mellomrom
            String[] line_split = position_choice.split(" ");
            
            // sørg for at brukeren ga 2 verdier som input 
            if (line_split.length != 2) {
                System.err.println("input krever ha 2 kordinater");
                continue;
            }
            
            try {
                // prøv å parse string dataen til int verdier som blir
                // brukt som startspunktet for pathfindinen i labyrinten
                int y = Integer.parseInt(line_split[0]);
                int x = Integer.parseInt(line_split[1]);
                
                // finn alle veier ut fra kordinatet (y,x)
                ArrayList<ArrayList<Tuppel>> paths = labyrint.finnUtveiFra(y, x);
                
                // hvis lengden på paths er 0 ble det ikke funnet noen ingen vei ut fra (y,x)
                if (paths.size() == 0) {
                    System.out.println(String.format("klarte ikke finne en vei fra (%s,%s)", y, x));
                }
                else {
                    String display_choice = "";
                    while (!display_choice.equals(String.valueOf(paths.size()+1))) {
                        // print ut alle menyen av utveier
                        System.out.println("\033[H\033[2J\033[3JPaths funnet:\n=============");
                        int num = 1;
                        for (ArrayList<Tuppel> path : paths) {
                            System.out.println(String.format(" %s - %s", num, path.get(path.size()-1)));
                            num++;
                        }
                        System.out.println(String.format(" %s - %s", num, "Tilbake"));
                        System.out.print("\nChoice: ");

                        // la brukeren velge hvilken path som skal vises
                        display_choice = stdin.nextLine();
                        
                        try {
                            // marker hver rute til den valgte pathen
                            labyrint.markPath(paths.get(Integer.parseInt(display_choice)-1));
                        
                            // output labyrinten etter å ha markert den
                            System.out.println(labyrint);

                            System.out.println("[Press Enter To Return]");
                            stdin.nextLine();
                        }

                        // i tilfelle brukeren gir et ugyldig nummer
                        catch (NumberFormatException | IndexOutOfBoundsException e) {}
                    }
                }
            }
            
            catch (NumberFormatException e) {
                System.err.println("input krever ha 2 kordinater");
            }
            
            catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("kordinat finnes ikke i labyrinten");
            }
        }

        // lukk input stream
        stdin.close();
    }
}
