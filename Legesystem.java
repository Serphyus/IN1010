import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Legesystem{
    Prioritetskoe<Lege> leger = new Prioritetskoe<>();
    Koe<Pasient> pasienter = new Koe<>();
    Koe<Legemiddel> legemiddler = new Koe<>();

    String type_gen = null;
    private int line_num = 0;


    public void debugMsg(String msg) {
        // bruker ascii escape codes til å gjøre tekst
        // output farget ved farge 34m som er blå og 0m
        // som resetter fargen til konsollens default
        System.out.println(String.format("\033[34m[Debug]\033[0m line: %3s | %s", this.line_num, msg));
    }

    public void errorMsg(String msg) {
        // bruker ascii escape codes til å gjøre tekst
        // output farget ved farge 33m som er rød og 0m
        // som resetter fargen til konsollens default
        System.out.println(String.format("\033[31m[Error]\033[0m line: %3s | %s", this.line_num, msg));
    }


    private void lesPasient(String[] args) {
        this.pasienter.leggTil(new Pasient(args[0], args[1]));
    }

    public void lesLegemiddel(String[] args) {
        // henter middel_type fra andre linje argument
        String middel_type = args[1];

        String navn = args[0];
        int pris = Integer.parseInt(args[2]);
        double virkestoff = Double.parseDouble(args[3]);

        // lag Legemiddel basert på middel_type
        // og legg det til i legemiddler koe.

        // (String.equals(String) vs String == String)
        // her må vi bruke equals metoden istedenfor
        // == operatoren fordi String er ikke en primitiv
        // type som char/int/float/boolean... og vil bare
        // tillate == operatoren hvis begge objektene er
        // en laget som en String men her bruker vi args
        // som er en String[] array og hvert element ligger
        // i minne som en char[] så hvis vi prøver å bruke
        // == operatoren blir det som å gjøre skjekke om
        // String == char[] som ikke vil gi true uansett
        // char[] sitt innhold.

        // [!] dette prinsippet vil gjentas i de neste metodene

        if (middel_type.equals("vanlig")) {
            this.legemiddler.leggTil(
                new Vanlig(navn, pris, virkestoff)
            );
        }
        
        else if (middel_type.equals("narkotisk")) {
            int styrke = Integer.parseInt(args[4]);
            this.legemiddler.leggTil(
                new Narkotisk(navn, pris, virkestoff, styrke)
            );
        }
        
        else if (middel_type.equals("vanedannende")) {
            int styrke = Integer.parseInt(args[4]);
            this.legemiddler.leggTil(
                new Vanedannende(navn, pris, virkestoff, styrke)
            );
        }

        else {
            // hvis kode når hit er det gitt en ugyldig
            // middel type og derfor gir en feil melding
            this.errorMsg(String.format("invalid middel type: \"%s\"", middel_type));
        }
    }

    public void lesLege(String[] args) {
        // hvis første argument er null så skal
        // legen være en standard lege mens hvis
        // første argument er en annen verdi så
        // skal den brukes til å lage en spesialist

        // String.equals() og ikke == operatoren
        if (args[1].equals("0")) {
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
        // vår lege navn eller ikke og gi en feil for så å returnere.
        
        // String.equals() og ikke == operatoren
        while (lege_node.data.hentNavn().equals(lege_navn) != true) {
            if (lege_node.neste == null) {
                this.errorMsg(String.format("no lege found with name: %s", lege_navn));
                return;
            }
            lege_node = lege_node.neste;

        }
        Lege lege = lege_node.data;
        
        // hent middel id som vi skal finne i listen
        int middel_id = Integer.parseInt(args[0]);
        Lenkeliste<Legemiddel>.Node middel_node = this.legemiddler.start;

        // loop gjennom listen til man finner en legemiddel som matcher
        // vår middel id eller ikke og gi en feil for så å returnere.
        while (middel_node.data.hentId() != middel_id) {
            if (middel_node.neste == null) {
                this.errorMsg(String.format("no middel found with id: %s", middel_id));
                return;
            }
            middel_node = middel_node.neste;
        }
        Legemiddel middel = middel_node.data;


        // hent paisent id som vi skal finne i listen
        int pasient_id = Integer.parseInt(args[2]);
        Lenkeliste<Pasient>.Node pasient_node = this.pasienter.start;
        
        // loop gjennom listen til man finner en pasient som matcher
        // vår pasient id eller ikke og gi en feil for så å returnere.
        // vi kan bruke == operatoren her fordi vi sjekker int verdier.
        while (pasient_node.data.hentId() != pasient_id) {
            if (pasient_node.neste == null) {
                this.errorMsg(String.format("no lege found with name: %s", pasient_id));
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
        
        // String.equals() og ikke == operatoren
        int reit;
        if (resept_type.equals("militaer")) {
            reit = 0;
        }
        else {
            reit = Integer.parseInt(args[4]);
        }

        // lag resept basert på resept_type med legen

        // String.equals() og ikke == operatoren
        
        try {
            if (resept_type.equals("hvit")) {
                lege.skrivHvitResept(middel, pasient, reit);
            }
            else if (resept_type.equals("blaa")) {
                lege.skrivBlaaResept(middel, pasient, reit);
            }
            else if (resept_type.equals("militaer")) {
                lege.skrivMilResept(middel, pasient);
            }
            else if (resept_type.equals("p")) {
                lege.skrivPResept(middel, pasient, reit);
            }                            
        }

        // hvis en ulovlig utskrift oppstår ta den imot
        catch (UlovligUtskrift e) {
            this.errorMsg(e.toString());
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
                
                // inkrementer linje nummer
                this.line_num++;

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
                    this.debugMsg(String.format("generator type updated: %s", line.split(" ")[1]));
                    this.type_gen = line.split(" ")[1];
                    continue;
                }

                // splitt linjen til argumenter på komma tegnet
                String[] args = line.split(",");

                // gjør en switch case for å spesifisere hvordan
                // vi skal hontere argumentene på linjen basert
                // på vår nåværende type gitt av forrige linje
                // med # tegn

                if (this.type_gen == null) {
                    this.errorMsg("no generator type selected");
                    System.exit(1);
                }

                try {
                    switch(this.type_gen) {
                        case "Pasienter":
                            this.lesPasient(args);
                            break;

                        case "Legemidler":
                            this.lesLegemiddel(args);
                            break;

                        case "Leger":
                            this.lesLege(args);
                            break;

                        case "Resepter":
                            this.lesResept(args);
                            break;

                        //default:
                            // hvis type_gen er null så ble det aldri gitt
                            // en type å bruke og programmet må stoppe
                        //    this.errorMsg("no generator type selected");
                        //    System.exit(1);
                    }
                }
                
                // hvis en index exception skjer så er det feil
                // når det leses av argumentene og kan blir dekket
                // med en feil melding ved å bruke line_num uten
                // å stoppe programmet for å kunne kjøre den store
                // filen som skal inneholde noen feil
                catch (IndexOutOfBoundsException e) {
                    this.errorMsg("missing arguments");
                }
            }

            // lukk filen
            fil_leser.close();
        }

        // hvis filen ikke eksisterer gi en feil melding
        catch (FileNotFoundException e) {
            this.errorMsg(String.format("unable to locate file: %s", filename));
            System.exit(1);
        }
    }
}