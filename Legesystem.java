import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Legesystem{
    Prioritetskoe<Lege> leger;
    Koe<Pasient> pasienter;
    Koe<Legemiddel> legemiddler;

    String type_gen = null;


    private void lesPasient(String[] args) {
        this.pasienter.leggTil(new Pasient(args[0], args[1]));
    }

    public void lesLegemiddel(String[] args) {
        // henter middel_type fra andre linje argument
        String middel_type = args[1];
        
        // sett middel før if statements for å
        // unngå udefinert lokal variabel error 
        Legemiddel middel = null;

        // lag Legemiddel basert på middel_type
        if (middel_type == "vanlig") {
            middel = new Vanlig(args[0], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        else if (middel_type == "narkotisk") {
            middel = new Narkotisk(args[0],  Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        }
        else if (middel_type == "vanedannende") {
            middel = new Vanedannende(args[0], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        }

        // hvis middel er null så var ikke en gyldig
        // middel_type gitt som på linjen og gir en
        // error message
        if (middel == null) {
            System.out.println(String.format("[Error] invalid middel type: %s", middel_type));
            return;
        }

        // legg til det nye middelet til legemiddler
        this.legemiddler.leggTil(middel);
    }

    public void lesLege(String[] args) {
        // hvis første argument er null så skal
        // legen være en standard lege mens hvis
        // første argument er en annen verdi så
        // skal den brukes til å lage en spesialist
        if (args[1] == "0") {
            this.leger.leggTil(new Lege(args[0]));
        } else {
            this.leger.leggTil(new Spesialist(args[0], args[1]));
        }
    }


    public void lesResept(String[] args) {
        // hent lege navn som vi skal finne i listen
        String lege_navn = args[1];
        Lenkeliste<Lege>.Node lege_node = this.leger.start;

        // loop gjennom listen til man finner en lege som matcher
        // vår lege navn eller ikke og gi en feil for så å returnere
        while(lege_node.data.hentNavn() != lege_navn) {
            if (lege_node.neste == null) {
                System.out.println(String.format("[Error] No lege found with name: %s", lege_navn));
                return;
            }
            lege_node = lege_node.neste;

        }
        Lege lege = lege_node.data;
        
        // hent middel id som vi skal finne i listen
        int middel_id = Integer.parseInt(args[0]);
        Lenkeliste<Legemiddel>.Node middel_node = this.legemiddler.start;

        // loop gjennom listen til man finner en legemiddel som matcher
        // vår middel id eller ikke og gi en feil for så å returnere
        while (middel_node.data.hentId() != middel_id) {
            if (middel_node.neste == null) {
                System.out.println(String.format("[Error] No middel found with id: %s", middel_id));
                return;
            }
            middel_node = middel_node.neste;
        }
        Legemiddel middel = middel_node.data;


        // hent paisent id som vi skal finne i listen
        int pasient_id = Integer.parseInt(args[2]);
        Lenkeliste<Pasient>.Node pasient_node = this.pasienter.start;
        
        // loop gjennom listen til man finner en pasient som matcher
        // vår pasient id eller ikke og gi en feil for så å returnere
        while (pasient_node.data.hentId() != pasient_id) {
            if (pasient_node.neste == null) {
                System.out.println(String.format("[Error] No lege found with name: %s", pasient_id));
                return;
            }
            pasient_node = pasient_node.neste;
        }

        // hent pasient fra pasient_noden
        Pasient pasient = pasient_node.data;

        // hent resept typen
        String resept_type = args[3];

        // sett reit før if statements for å unngå
        // udefinert lokal variabel error
        int reit;
        if (resept_type != "militaer") {
            reit = Integer.parseInt(args[4]);
        } else {
            reit = 0;
        }

        // lag resept basert på resept_type med legen
        try {
            if (resept_type == "hvit") {
                lege.skrivHvitResept(middel, pasient, reit);
            }
            else if (resept_type == "blaa"){
                lege.skrivBlaaResept(middel, pasient, reit);
            }
            else if (resept_type == "militaer") {
                lege.skrivMilResept(middel, pasient);
            }
            else if (resept_type == "p") {
                lege.skrivPResept(middel, pasient, reit);
            }                            
        }

        // hvis en ulovlig utskrift oppstår ta den imot
        catch (UlovligUtskrift e) {
            System.out.println(e);
            System.exit(1);
        }
    }


    public void lesFraFil(String filename){
        try {
            // lag et fil objekt og en scanner for å lese filen
            File data_fil = new File(filename);
            Scanner fil_leser = new Scanner(data_fil);

            // lag en linje nummer int for å vite hvilken linje
            // feil oppstår på
            int line_num = 1;
            
            // loop gjennom linjene så lenge det filen har en neste linje
            while (fil_leser.hasNextLine()) {
                // les nåværende linje fra filen
                String line = fil_leser.nextLine();

                // fjern ekstra mellomrom og skjekk om det gjør
                // linjen tom og da hopp over linjen
                if (line.strip() == "") {
                    continue;
                }

                // hvis linjen starter med et # så endrer den
                // typen vi skal bruke og så hopper vi til neste
                // linje. typen står på index 1 etter å splitte
                // linjen -> {"#", "TYPE", ... }
                if (line.charAt(0) == '#') {
                    this.type_gen = line.split(" ")[1];
                    continue;
                }

                // splitt linjen til argumenter på komma tegnet
                String[] args = line.split(",");

                // gjør en switch case for å spesifisere hvordan
                // vi skal hontere argumentene på linjen basert
                // på vår nåværende type gitt av forrige linje
                // med # tegn
                try {
                    switch(this.type_gen) {
                        case "Pasienter":
                            this.lesPasient(args);

                        case "Legemidler":
                            this.lesLegemiddel(args);

                        case "Leger":
                            this.lesLege(args);

                        case "Resepter":
                            this.lesResept(args);

                        default:
                            // hvis type_gen er null så ble det aldri gitt
                            // en type å bruke og programmet må stoppe
                            System.out.println("[Error] no generator type selected");
                            System.exit(1);
                    }
                }
                
                // hvis en index exception skjer så er det feil
                // når det leses av argumentene og kan blir dekket
                // med en feil melding ved å bruke line_num uten
                // å stoppe programmet for å kunne kjøre den store
                // filen som skal inneholde noen feil
                catch (IndexOutOfBoundsException e) {
                    System.out.print(String.format("[Error] invalid arguments at line: %s", line_num));
                }

                // inkrementer linje nummer
                line_num++;
            }

            // lukk filen
            fil_leser.close();
        }

        // hvis filen ikke eksisterer gi en feil melding
        catch (FileNotFoundException e) {
            System.out.println(String.format("[Error] unable to locate file: %s", filename));
            System.exit(1);
        }
    }
}