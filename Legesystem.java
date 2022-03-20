import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Legesystem{
    private Prioritetskoe<Lege> leger = new Prioritetskoe<>();
    private Koe<Pasient> pasienter = new Koe<>();
    private Koe<Legemiddel> legemiddler = new Koe<>();

    // interne variabler for å holde styr
    // på hvilken type som leses fra filen
    // og hvilken linjenummer man er på
    private String type_gen = null;
    private int line_num = 0;

    // error counter
    private int err_count = 0;


    // denne funksjonen er bare til å legge
    // til linje nummeret hvis meldingen skjer
    // mens en fil leses
    public String formatMsg(String msg) {
        if (line_num > 0) {
            // hvis linje nummer ikke er på null
            // så legger vi til linje nummeret i
            // meldingen
            msg = String.format("line: %3s | %s", this.line_num, msg);
        }

        return msg;
    }


    public void debugMsg(String msg) {
        // bruker ansi escape codes til å gjøre tekst
        // output farget ved farge 34m som er blå og 0m
        // som resetter fargen til konsollens default
        System.out.println(String.format("\033[34m[Debug]\033[0m   | %s", formatMsg(msg)));
    }

    public void warningMsg(String msg) {
        System.out.println(String.format("\033[33m[Warning]\033[0m | %s", formatMsg(msg)));
    }

    public void errorMsg(String msg) {
        // bruker ansi escape codes til å gjøre tekst
        // output farget ved farge 33m som er rød og 0m
        // som resetter fargen til konsollens default

        System.out.println(String.format("\033[31m[Error]\033[0m   | %s", formatMsg(msg)));
        this.err_count++;
    }

    public String[] hentLegeNavn() {
        String[] navn = new String[this.leger.stoerrelse()];
        
        int indeks = 0;
        for (Lege lege: this.leger) {
            navn[indeks] = lege.hentNavn();
            indeks++;
        }

        return navn;
    }


    public void leggTilPasient(String[] args) {
        this.pasienter.leggTil(new Pasient(args[0], args[1]));
    }

    public void leggTilLegemiddel(String[] args) {
        // henter middel_type fra andre linje argument
        String middel_type = args[1];

        String navn = args[0];
        int pris = (int) Double.parseDouble(args[2]);
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
        // i minne som en char[] så hvis vi prøver å bruke
        // == operatoren blir det som å gjøre skjekke om
        // String == char[] som ikke vil gi true uansett
        // char[] sitt innhold.

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

    public void leggTilLege(String[] args) {
        // skjekk om noen navnet til den nye
        // legen allerede hører til en lege
        // i leger prioritetskoen 
        for (Lege lege: this.leger) {
            if (lege.hentNavn().equals(args[0])) {
                errorMsg(String.format("Lege med navn %s eksisterer allerede", args[0]));
                return;
            }
        }

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


    public void leggTilResept(String[] args) {
        // hent lege navn som vi skal finne i listen
        String lege_navn = args[1];

        // loop gjennom listen til man finner en lege som matcher
        // vår lege navn eller ikke og gi en feil for så å returnere.
        
        Lege lege = null;
        for (Lege node: this.leger) {
            // String.equals() og ikke == operatoren
            if (node.hentNavn().equals(lege_navn)) {
                lege = node;
                break;
            }
        }

        // hvis lege fortsatt er null fant vi ingen
        // lege med et matchende navn
        if (lege == null) {
            this.errorMsg(String.format("no lege found with name: %s", lege_navn));
            return;
        }
        

        // hent middel id som vi skal finne i listen
        int middel_id = Integer.parseInt(args[0]);

        // loop gjennom listen til man finner en legemiddel som matcher
        // vår middel id eller ikke og gi en feil for så å returnere.
        Legemiddel middel = null;
        for (Legemiddel node: this.legemiddler) {
            if (node.hentId() == middel_id) {
                middel = node;
                break;
            }
        }

        // hvis middel er null fant vi ingen
        // middel med en matchende id og kan
        // gi en feilmelding
        if (middel == null) {
            this.errorMsg(String.format("no middel found with id: %s", middel_id));
            return;
        }

        // hent paisent id som vi skal finne i listen
        int pasient_id = Integer.parseInt(args[2]);
        
        // loop gjennom listen til man finner en pasient som matcher
        // vår pasient id eller ikke og gi en feil for så å returnere.
        // vi kan bruke == operatoren her fordi vi sjekker int verdier.
        Pasient pasient = null;
        for (Pasient node: this.pasienter) {
            if (node.hentId() == pasient_id) {
                pasient = node;
                break;
            }
        }
        
        // hvis pasient er null fant vi ingen
        // pasient med en matchende id og kan
        // gi en feilmelding
        if (pasient == null) {
            this.errorMsg(String.format("no pasient found with id: %s", pasient_id));
            return;
        }

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
        // reset error counter
        this.err_count = 0;

        try {
            this.debugMsg(String.format("Reading file: %s\n", filename));

            // lag et fil objekt og en scanner for å lese filen
            File data_fil = new File(filename);
            Scanner fil_leser = new Scanner(data_fil);

            // lag en linje nummer int for å vite hvilken linje
            // feil oppstår på
            this.line_num = 1;
            
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

                // strip each argument to remove extra spaces
                int i = 0;
                for (String arg: args) {
                    args[i] = arg.strip();
                    i++;
                }

                // gjør en switch case for å spesifisere hvordan
                // vi skal hontere argumentene på linjen basert
                // på vår nåværende type gitt av forrige linje
                // med '#' tegn

                if (this.type_gen == null) {
                    this.errorMsg("no generator type selected");
                    System.exit(1);
                }

                try {
                    switch(this.type_gen) {
                        case "Pasienter":
                            this.leggTilPasient(args);
                            break;

                        case "Legemidler":
                            this.leggTilLegemiddel(args);
                            break;

                        case "Leger":
                            this.leggTilLege(args);
                            break;

                        case "Resepter":
                            this.leggTilResept(args);
                            break;
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

                // midlertidlig løsningfor generic exceptions
                catch (Exception e) {
                    this.errorMsg(e.toString());
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

        // reset linje nummer til 0 slik
        // at konsoll meldinger ikke legger
        // til linjenummer i meldingen
        this.line_num = 0;

        // hvis error count er mer enn 0 gi
        // en advarsel melding til brukeren
        if (this.err_count > 0) {
            System.out.println();
            this.warningMsg(String.format("Errors occurred when reading file: %s", this.err_count));
        }
    }

    private void tabellerData(String[] keys, String[][] data) {
        int[] paddings = new int[keys.length];

        // regn ut mellomromet mellom hver av
        // keys string verdiene for å skape et
        // gjevnt mellomrom mellom dataen
        for (int i = 0; i < keys.length; i++) {
            // default mellomromer lengden på hver
            // string i keys argumentet
            paddings[i] = keys[i].length();

            // skjekk om lengden på hver string verdi
            // i den 2 dimensjonale arrayen er større 
            // enn lengden på keys String verdiene og
            // hvis den er det sett den nye lengde 
            // verdien til dataen sin string lengde 
            for (String[] section: data) {
                int length = section[i].length();
                if (paddings[i] < length) {
                    paddings[i] = length;
                }
            }
        }

        // lag en string formatter til som bruker
        // paddings verdiene til å sette mellomrom
        // mellom hvert argument som blir formattert
        // inn med String.format("...", arg1, arg2, ...)
        String string_format = "";
        for (int padding: paddings) {
            string_format += "%-"+ padding + "s  ";
        }

        // (Object[]keys) er en type casting som gjør
        // at String.format ikke gir en advarsel på
        // kompilatoren når du gir argumentene som en
        // array 
        System.out.println(String.format(string_format, (Object[])keys));
        
        // print splitter
        String split_line = "";
        for (int p: paddings) {
            split_line += new String(new char[p+2]).replace("\0", "=");
        }

        // print tabell splitteren som består
        // av mange '=' bokstaver
        System.out.println(split_line);

        // print hver seksjon med data gitt fra data
        // argumentet som da er en 2 dimensjonal array
        for (String[] section: data) {
            // (Object[]keys) er en type casting som gjør
            // at String.format ikke gir en advarsel på
            // kompilatoren når du gir argumentene som en
            // array
            System.out.println(String.format(string_format, (Object[])section));
        }

        System.out.println();
    }

    public void legeTabell() {
        // keys for å lage data områder på toppen av tabellen
        String[] keys = {"Lege Navn", "Resepter", "ID"};

        // lag en 2 dimensjonal liste som skal holde på hver
        // eneste lege i lege_data[n] og i lege_data[n][0-4]
        // skal den holde data om lege
        String[][] lege_data = new String[this.leger.stoerrelse()][3];

        // start på indeks 0 og bruk den for å spesifisere
        // legen som dataen skal lagres til og puttets
        // i en tabell senere
        int index = 0;
        for (Lege lege: this.leger) {
            // hent diverse data om legen og lagre det i
            // lege_data hos denne legen sin indeks
            lege_data[index][0] = lege.hentNavn();
            lege_data[index][1] = String.format("%s", lege.utskrevneResepter().antall);
            
            // hvis legen er en spesialist så må vi
            // type caste det slik at java compileren
            // skjønner at det finnes en hentKontrollID
            // metode å kalle på 
            if (lege instanceof Spesialist) {
                Spesialist spesialist = (Spesialist)(lege);
                lege_data[index][2] = spesialist.hentKontrollID();
            } 
            
            // hvis ikke legen er en spesialist setter
            // vi kontroll id til en blank verdi siden
            // vanlige leger har ikke kontroll id 
            else {
                lege_data[index][2] = "";
            }
            index++;
        }

        // print en tabell av lege dataen
        tabellerData(keys, lege_data);
    }

    public void pasientTabell() {
        // keys for å lage data områder på toppen av tabellen
        String[] keys = {"Pasient Navn", "Foodselsdato", "ID"};

        // lag en 2 dimensjonal liste som skal holde på
        // hver eneste pasient i pasient_data[n] og i
        // pasient_data[n][0-4] skal den holde data om
        // pasient
        String[][] pasient_data = new String[this.pasienter.stoerrelse()][3];

        // start på indeks 0 og bruk den for å spesifisere
        // pasienten som dataen skal lagres til og puttets
        // i en tabell senere
        int index = 0;
        for (Pasient pasient: this.pasienter) {
            // hent diverse data om pasienten og lagre det i
            // pasient_data hos denne pasienten sin indeks
            pasient_data[index][0] = pasient.hentNavn();
            pasient_data[index][1] = pasient.hentFoodselsdato();
            pasient_data[index][2] = String.format("%s", pasient.hentId());
            index++;
        }

        // print en tabell av pasient dataen
        tabellerData(keys, pasient_data);
    }

    public void middlerTabell() {
        // keys for å lage data områder på toppen av tabellen
        String[] keys = {"Legemiddel Navn", "ID", "Pris", "Virkestoff", "Styrke"};

        // lag en 2 dimensjonal liste som skal holde på
        // hver eneste legemiddel i middel_data[n] og
        // i middel_data[n][0-4] skal den holde data om
        // legemiddelet
        String[][] middel_data = new String[this.legemiddler.stoerrelse()][5];

        // start på indeks 0 og bruk den for å spesifisere
        // legemiddelet som dataen skal lagres til og puttets
        // i en tabell senere
        int index = 0;
        for (Legemiddel middel: this.legemiddler) {
            // hent diverse data om legemiddelet og lagre det
            // i middel_data hos denne middelet sin indeks
            middel_data[index][0] = middel.hentNavn();
            middel_data[index][1] = String.format("%s", middel.hentId());
            middel_data[index][2] = String.format("%s", middel.hentPris());
            middel_data[index][3] = String.format("%s", middel.hentVirkestoff());
            
            // hvis legemiddelet er narkotisk så må vi
            // type caste det slik at java compileren
            // skjønner at det finnes en hentNarkotiskStyrke
            // metode å kalle på 
            if (middel instanceof Narkotisk) {
                Narkotisk narkotisk = (Narkotisk)middel;
                middel_data[index][4] = String.format("%s", narkotisk.hentNarkotiskStyrke());
            }
            
            // samme prinsipp som i narkotisk mens her
            // heter metoden som dataen skal hentes fra
            // hentVanedannendeStyrke
            else if (middel instanceof Vanedannende) {
                Vanedannende vanedannende = (Vanedannende)middel;
                middel_data[index][4] = String.format("%s", vanedannende.hentVanedannendeStyrke());
            }

            // dette tilfelle er for vanlig legemiddler
            // og de har ingen styrke så vi setter en
            // tom string som styrke data
            else {
                middel_data[index][4] = "";
            }
            
            // inkrementer til neste indeks
            index++;
        }

        // print en tabell av middel dataen
        tabellerData(keys, middel_data);
    }
}