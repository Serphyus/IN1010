import java.util.Scanner;
import java.util.InputMismatchException;


public class Hovedprogram {
    private static Scanner console_input = new Scanner(System.in);
    private static Legesystem legesystem = new Legesystem();

    private static void clearScreen() {
        System.out.print("\033[H\033[2J\033[3J");
    }

    private static void returnPrompt() {
        System.out.println("\n[Press Enter To Return]\n");
        console_input.nextLine();
    }

    private static void resetLines(int lines) {
        System.out.print(String.format("\033[%sA", lines));
    }

    private static int brukerMeny(String title, String[] choices) {
        title += "\n" + new String(new char[title.length()]).replace("\0", "=") + "\n";

        int choice = -1;
        while (choice < 0 || choice > choices.length) {
            clearScreen();
            
            System.out.println(title);
            for (int i = 0; i < choices.length; i++) {
                System.out.println(String.format(" %s) %s", i+1, choices[i]));
            }

            System.out.print("\n Valg: ");
            String user_input = console_input.nextLine();

            try {
                choice = Integer.parseInt(user_input);
            }
            catch (NumberFormatException e) {
            }
        }

        return choice;
    }

    private static String brukerInput(String prompt) {
        String bruker_input = "";
        while (bruker_input == "") {
            System.out.print("\n " + prompt);
            bruker_input = console_input.nextLine();

            if (bruker_input == "") {
                System.out.print("\033[%sA", lines);
            }
        }

        return bruker_input;
    }

    private static void listLegesystem() {
        clearScreen();

        legesystem.legeTabell();
        legesystem.pasientTabell();
        legesystem.middlerTabell();

        returnPrompt();
    }

    private static void leggTilNyLege() {
        String[] meny_valg = {"Ja", "Nei"};
        int lege_type = brukerMeny("Er legen spesialist", meny_valg);

        String kontrollId = "";
        switch (lege_type) {
            case 1:
                kontrollId = brukerInput("KontrollId: ");
                break;
            case 2:
                kontrollId = "";
                break;
        }

        String lege_navn = brukerInput("Navn: ");

        String[] args = {lege_navn, kontrollId};
        legesystem.leggTilLege(args);
    }
    
    private static void leggTilNyPasient() {
        String navn = brukerInput("Pasientens navn: ");
        String foodselsdato = brukerInput("Pasientens foodselsdato: ");
        String[] args = {navn, foodselsdato};
        legesystem.leggTilPasient(args);
    }

    private static void leggTilNyResept() {
        
        Sring lege_valg = "";

        while (lege_navn == "") {
            String[] meny_valg = {"Legg til ny", "Bruk eksisterende"};
            int valg_av_lege = brukerMeny("Legg til ny eller bruk eksisterende lege", meny_valg);
            
            switch(valg_av_lege) {
                case 1:
                    // legg til ny lege
                    leggTilNyLege();
                    break;
                case 2:
                    // hent en liste av navn på alle leger i legensystemet
                    String[] navn_liste = legesystem.hentLegeNavn();
                    
                    // få en valg verdi og ta minus 1 for å få indeks
                    // istedenfor nummer på valget for å kunne hente
                    // ut navnet fra navn_liste
                    int lege_indeks = brukerMeny("Velg lege", navn_liste)-1;
                    
                    // hent lege_navn med lege_indeks
                    String lege_valg = navn_liste[lege_indeks];
                    break
            }
        }

        
        String[] meny_valg = {"Hvitresept", "Blaaresept","MilResept", "PResept"};
        int valg_av_resept = brukerMeny("Hva slags resept?", meny_valg);
    }

    private static void leggTilNyLegemiddel() {
        String[] meny_valg = {"Vanlig", "","Vanedannede", "Narkotisk"};
        int valg_av_legemiddel = brukerMeny("Hva slags legemiddel?", meny_valg);
        
        String navn = brukerInput("Navn: ");
        String pris = brukerInput("Pris: ");
        String virkestoff = bruker_input("Virkestoff: ");

        String styrke = "";
        switch(valg_av_resept){
            case 2:
                String styrke = bruker_input("Vanedannende Styrke: ");
                break;
            case 3:
                String styrke = bruker_input("Narkotisk styrke: ");
                break;
        }

        String[] args = {navn, pris, virkestoff, styrke}
        legesystem.leggTilLegemiddel(args);
    }

    private static void leggTil() {
        String[] meny_valg = {
            "Legg til en ny lege",
            "Legg til en ny pasient",
            "Legg til en ny resept",
            "Legg til et nytt legemiddel",
            "Tilbake"
        };
    
        int choice = brukerMeny("Legg til element", meny_valg);
        switch (choice) {
            case 1:
                leggTilNyLege();
                break;
            case 2:
                leggTilNyPasient();
                break;
            case 3:
                leggTilNyResept();
                break;
            case 4:
                leggTilNyLegemiddel();
                break;
            case 5:
                return;
        }
    }

    private static void MainMenu() {
        while (true) {
            clearScreen();
            
            String[] meny_valg = {"Print Legesystem", "Legg til nytt element", "", "", "Exit"};
            int choice = brukerMeny("Hovedmeny", meny_valg);

            switch (choice) {
                case 1:
                    listLegesystem();
                    break;
                case 2:
                    leggTil();
                    break;
                case 3:
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }


    public static void main(String[] args) {
        //Legesystem legesystem = new Legesystem();

        clearScreen();
        
        for (String arg: args) {
            legesystem.lesFraFil(arg);
            returnPrompt();
            clearScreen();
        }

        MainMenu();
    }
}

// Skrive ut en fullstendig oversikt over pasienter, leger, legemidler og resepter (deloppgave E3).

// Opprette og legge til nye elementer i systemet (deloppgave E4).

// Bruke en gitt resept fra listen til en pasient (deloppgave E5).

// Skrive ut forskjellige former for statistikk (deloppgave E6).

// Skrive alle data til fil (deloppgave E7)