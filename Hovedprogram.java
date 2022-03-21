import java.util.Scanner;


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

    private static int brukerMeny(String title, String[] choices) {
        // lag en divider for tittelen og menyen og formater det
        String split = new String(new char[title.length()]).replace("\0", "=");
        title = String.format(" %s\n %s\n", title, split);

        // sett valget utenfor rekkeviden av mulige valg for
        // å loope programmet til man gir et gyldig valg
        int choice = -1;
        while (choice < 0 || choice > choices.length) {
            // clear skjermen hver gang en meny skal vises
            clearScreen();
            
            System.out.println(title);
            for (int i = 0; i < choices.length; i++) {
                // hent ut string verdien fra choices arrayen
                String option = choices[i];

                // gi hvert valg sin string i menyen en stor
                // forbokstav hvis valget ikke er en tom string
                if (!option.isEmpty()) {
                    option = option.substring(0, 1).toUpperCase() + option.substring(1);
                }
                
                // output menyen sitt nummer og option
                System.out.println(String.format("  %s) %s", i+1, option));
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
                System.out.print("\033[2A");
            }
        }

        return bruker_input;
    }

    private static void listLegesystem() {
        clearScreen();

        legesystem.legeTabell();
        legesystem.pasientTabell();
        legesystem.middlerTabell();
        legesystem.reseptTabell();

        returnPrompt();
    }

    private static void leggTilNyLege() {
        String[] meny_valg = {"Ja", "Nei"};
        int lege_type = brukerMeny("Er legen spesialist?", meny_valg);

        // sett kontrollId og hvis
        // meny_valg var 1(ja) så
        // gir bruker en kontrollId
        String kontrollId = "";
        if (lege_type == 1) {
            kontrollId = brukerInput("KontrollId: ");
        }

        // få legens navn av bruker
        String navn = brukerInput("Navn: ");

        // legg til ny lege
        legesystem.leggTilLege(navn, kontrollId);
    }
    
    private static void leggTilNyPasient() {
        // få pasient navn av bruker
        String navn = brukerInput("Pasientens navn: ");
        
        // få foodselsdato av bruker
        String foodselsdato = brukerInput("Pasientens foodselsdato: ");
        
        // legg til ny pasient
        legesystem.leggTilPasient(navn, foodselsdato);
    }

    private static void leggTilNyResept() {
        // hent alle legemiddler fra legesystemet
        Legemiddel[] middler = legesystem.hentLegemiddler();
        
        // hent alle legemiddel navnene
        String[] alle_middel_navn = new String[middler.length];
        for (int i = 0; i < middler.length; i++) {
            alle_middel_navn[i] = middler[i].hentNavn();
        }

        // velg et legemiddel og hent den ut fra middler arrayen
        Legemiddel valgt_middel = middler[brukerMeny("Velg legemiddel", alle_middel_navn)-1];

        
        // hent alle leger fra legesystemet
        Lege[] leger = legesystem.hentLeger();

        // hent alle lege navnene
        String[] alle_lege_navn = new String[leger.length];
        for (int i = 0; i < leger.length; i++) {
            alle_lege_navn[i] = leger[i].hentNavn();
        }

        // velg en lege og hent den ut fra pasienter lege
        Lege valgt_lege = leger[brukerMeny("Velg lege", alle_lege_navn)-1];

        
        // hent alle pasienter fra legesystemet
        Pasient[] pasienter = legesystem.hentPasienter();
        
        // hent alle pasient navnene
        String[] alle_pasient_navn = new String[pasienter.length];
        for (int i = 0; i < pasienter.length; i++) {
            alle_pasient_navn[i] = pasienter[i].hentNavn();
        }

        // velg en pasient og hent den ut fra pasienter arrayen
        Pasient valgt_pasient = pasienter[brukerMeny("Velg pasient", alle_pasient_navn)];
 
        // lag en valg meny for å velge resept type
        String[] meny_valg = {"hvit", "blaa", "militaer", "p"};
        int valg_indeks = brukerMeny("Hva slags legemiddel?", meny_valg)-1;
        String middel_type = meny_valg[valg_indeks];
        
        int middel_id = valgt_middel.hentId();
        String lege_navn = valgt_lege.hentNavn();
        int pasient_id = valgt_pasient.hentId();

        int reit = 0;
        if (!middel_type.equals("militaer")) {
            try {
                reit = Integer.parseInt(brukerInput("Reit: "));
            }
            
            catch (NumberFormatException e) {
                legesystem.errorMsg(String.format("Ugyldig data format til bruker input"));
            }
        }

        legesystem.leggTilResept(middel_id, lege_navn, pasient_id, middel_type, reit);
    }

    private static void leggTilNyLegemiddel() {
        // velg legemiddel type
        String[] meny_valg = {"vanlig", "vanedannende", "narkotisk"};
        int middel_type_indeks = brukerMeny("Hva slags legemiddel?", meny_valg);
        String middel_type = meny_valg[middel_type_indeks-1];
        
        try {
            String navn = brukerInput("Navn: ");
            
            int pris = Integer.parseInt(brukerInput("Pris: "));
            double virkestoff = Double.parseDouble(brukerInput("Virkestoff: "));
            
            int styrke = 0;
            if (!middel_type.equals("vanlig")) {
                // hvis middel type er en vanedannende eller
                // narkotisk spør om en styrke verdi og gjør
                // den om til en integer
                if (middel_type.equals("vanedannende") || middel_type.equals("narkotisk")) {
                    styrke = Integer.parseInt(brukerInput("Styrke: "));
                }
            }

            // legg til det nye legemiddelet
            clearScreen();
            legesystem.leggTilLegemiddel(navn, middel_type, pris, virkestoff, styrke);
        }
        
        // hvis en av type konverteringene
        // feilet vil det gi en feilmelding
        catch (NumberFormatException e) {
            System.out.println();
            legesystem.errorMsg(String.format("Ugyldig data format til bruker input"));
        }

        returnPrompt();
    }

    private static void leggTil() {
        // definer tilgjengelige valg for menyen
        String[] meny_valg = {
            "Lege",
            "Pasient",
            "Resept",
            "Legemiddel",
            "Tilbake"
        };
    
        // få et valg av brukeren
        int choice = brukerMeny("Hva skal du legge til?", meny_valg);

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

    private static void brukResept() {
        // hent alle resepter i legesystem
        Resept[] resepter = legesystem.hentResepter();

        // lag en meny_valg array for å vise tilgjengelige
        // valg i menyen og legg til 1 i lengde for å ha
        // en tilbake valg
        String[] meny_valg = new String[resepter.length + 1];
        
        // les av hver resept i resepter
        for (int i = 0; i < resepter.length; i++) {
            int resept_id = resepter[i].hentId();
            String middel_navn = resepter[i].hentLegemiddel().hentNavn();
            int reit = resepter[i].hentReit();
            
            // legg til string i menyen
            meny_valg[i] = String.format("ID: %-5s | Reit: %-5s | Legemiddel: %s", resept_id, reit, middel_navn);
        }

        // sett et tilbake valg på siste indeks
        meny_valg[resepter.length] = "tilbake";

        int choice = brukerMeny("Velg resept", meny_valg)-1;

        // hvis brukeren velger tilbake
        if (choice == resepter.length) {
            return;
        }

        // velg resept ved å få en indeks fra meny
        Resept valgt_resept = resepter[choice];

        // skjekk om reit er tom og da gi
        // feildmelding mens hvis den ikke
        // er tom brukes resepten en gang
        System.out.println();
        if (valgt_resept.hentReit() == 0) {
            legesystem.errorMsg("Resepten kan ikke bli brukt siden reiten er 0");
        }

        else {
            valgt_resept.bruk();
            legesystem.debugMsg(String.format("Bruker resept id %s og endrer reit til %s", valgt_resept.hentId(), valgt_resept.hentReit()));
        }

        returnPrompt();
    }


    private static void reseptStatistikk(String middel_type) {
        // navnet på hver data type i tabellen
        String[] keys = {"Utskrevne", "Total Reit", "Total Pris"};
        
        // lag statistikk verdier
        int utskrevne = 0;
        int total_reit = 0;
        int total_pris = 0;

        // loop gjennom hver resept i legesystemet
        for (Resept resept: legesystem.hentResepter()) {
            Legemiddel middel = resept.hentLegemiddel();
            if (middel.getClass().getSimpleName().equals(middel_type)) {
                utskrevne++;
                total_reit += resept.hentReit();
                total_pris += middel.hentPris();
            }
        }

        // lag en 2 dimensjoal tabell for å holde data
        String[][] statistikk_data = new String[1][3];
        statistikk_data[0][0] = String.valueOf(utskrevne);
        statistikk_data[0][1] = String.valueOf(total_reit);
        statistikk_data[0][2] = String.valueOf(total_pris);

        clearScreen();
        legesystem.tabellerData(keys, statistikk_data);
        returnPrompt();
    }


    private static void vanedannendeStatistikk() {
        reseptStatistikk("Vanedannende");
    }

    private static void narkotiskeStatistikk() {
        reseptStatistikk("Narkotisk");
    }

    private static void narkotikaMisbruk() {
        // ................................
    }


    private static void visStatistikk() {
        String[] meny_valg = {
            "Vanedannende legemidler",
            "Narkotiske legemidler",
            "Mulig misbruk av Narkotika",
            "Tilbake"
        };

        int choice = brukerMeny("Vis statistikk", meny_valg);

        switch (choice) {
            case 1:
                vanedannendeStatistikk();
                break;
            case 2:
                narkotiskeStatistikk();
                break;
            case 3:
                narkotikaMisbruk();
                break;
            
        }
    }

    private static void MainMenu() {
        while (true) {
            clearScreen();
            
            String[] meny_valg = {
                "Print Legesystem",
                "Legg til nytt element",
                "Bruk resept",
                "Statistikk",
                "Exit"
            };
            
            int choice = brukerMeny("Hovedmeny", meny_valg);

            switch (choice) {
                case 1:
                    listLegesystem();
                    break;
                case 2:
                    leggTil();
                    break;
                case 3:
                    brukResept();
                    break;
                case 4:
                    visStatistikk();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        clearScreen();        
        for (String arg: args) {
            legesystem.lesFraFil(arg);
            returnPrompt();
            clearScreen();
        }

        MainMenu();
    }
}

// Opprette og legge til nye elementer i systemet (deloppgave E4).

// Bruke en gitt resept fra listen til en pasient (deloppgave E5).

// Skrive ut forskjellige former for statistikk (deloppgave E6).

// Skrive alle data til fil (deloppgave E7)