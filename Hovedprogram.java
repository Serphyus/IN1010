import java.util.Scanner;


public class Hovedprogram {
    private static Scanner console_input = new Scanner(System.in);
    private static Legesystem legesystem = new Legesystem();

    private static void listInternals() {
        System.out.println("\nLeger:");
        legesystem.legeTabell();
        System.out.println();

        System.out.println("\nPasienter:");
        legesystem.pasientTabell();
        System.out.println();

        System.out.println("\nLegemiddler:");
        legesystem.middlerTabell();
        System.out.println();
    }

    private static void leggTil() {

    }

    private static void returnPrompt() {
        System.out.println("\n[Press Enter To Return]\n");
        console_input.nextLine();
    }

    private static void clearScreen() {
        System.out.print("\033[2J\033[H");
    }

    private static void MainMenu() {
        while (true) {
            clearScreen();
            System.out.println("Main menu:\n==========\n");

            System.out.println(" 1) Print legesystem innhold");
            System.out.println(" 2) Legg til nytt element");
            System.out.println(" 3) ");
            System.out.println(" 4) ");
            System.out.println(" 5) Exit");
            
            System.out.print("\n Choice: ");
            String choice = console_input.nextLine();

            switch (choice) {
                case "1":
                    listInternals();
                case "2":
                    leggTil();
                    break;
                case "3":
                    System.out.println();
                    break;
                case "4":
                    System.out.println();
                    break;
                case "5":
                    System.exit(0);
            }

            returnPrompt();
        }
    }


    public static void main(String[] args) {
        //Legesystem legesystem = new Legesystem();

        clearScreen();
        for (String arg: args) {
            legesystem.lesFraFil(arg);
        }

        returnPrompt();
        MainMenu();
    }
}

// Skrive ut en fullstendig oversikt over pasienter, leger, legemidler og resepter (deloppgave E3).

// Opprette og legge til nye elementer i systemet (deloppgave E4).

// Bruke en gitt resept fra listen til en pasient (deloppgave E5).

// Skrive ut forskjellige former for statistikk (deloppgave E6).

// Skrive alle data til fil (deloppgave E7)