import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Legesystem{
    private Prioritetskoe<Lege> leger = new Prioritetskoe<>();
    private Koe<Pasient> pasienter = new Koe<>();
    private Koe<Resept> resepter = new Koe<>();
    private Koe<Legemiddel> legemidler = new Koe<>();

    // interne variabler for å holde styr
    // på hvilken type som leses fra filen
    // og hvilken linjenummer man er på
    private String type_gen = null;
    private int line_num = 0;

    // liste av gyldige type generatorer
    private String[] gyldige_gens = {"pasienter", "legemidler", "leger", "resepter"};

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

    public void leggTilPasient(String navn, String foodselsdato) {
        this.pasienter.leggTil(new Pasient(navn, foodselsdato));
        this.debugMsg(String.format("Lagt til ny pasient lagt til: %s", navn));
    }

    public void leggTilLegemiddel(String navn, String type, int pris, double virkestoff, int styrke) {
        // lag Legemiddel basert på middel_type
        // og legg det til i legemidler koe.

        // (String.equals(String) vs String == String)
        // her må vi bruke equals metoden istedenfor
        // == operatoren fordi equals slkeller innholdet
        // til String mens == operatoren vil skjekke om
        // objektenes refererer til det samme. bruket av
        // == operatoren er heller designet for primitive
        // typer i java.

        // [!] dette prinsippet vil gjentas i de flere metoder

        if (type.equals("vanlig")) {
            this.legemidler.leggTil(
                new Vanlig(navn, pris, virkestoff)
            );
        }
        
        else if (type.equals("narkotisk")) {
            this.legemidler.leggTil(
                new Narkotisk(navn, pris, virkestoff, styrke)
            );
        }
        
        else if (type.equals("vanedannende")) {
            this.legemidler.leggTil(
                new Vanedannende(navn, pris, virkestoff, styrke)
            );
        }

        else {
            // hvis kode når hit er det gitt en ugyldig
            // middel type og derfor gir en feil melding
            this.errorMsg(String.format("invalid middel type: \"%s\"", type));
            return;
        }

        this.debugMsg(String.format("Lagt til nytt legemiddel: %s", navn));
    }

    public void leggTilLege(String navn, String kontrollId) {
        // skjekk om noen navnet til den nye
        // legen allerede hører til en lege
        // i leger prioritetskoen 
        for (Lege lege: this.leger) {
            if (lege.hentNavn().equals(navn)) {
                errorMsg(String.format("Lege med navn %s eksisterer allerede", navn));
                return;
            }
        }

        // hvis første argument er null så skal
        // legen være en standard lege mens hvis
        // første argument er en annen verdi så
        // skal den brukes til å lage en spesialist
        if (kontrollId.equals("0")) {
            this.leger.leggTil(new Lege(navn));
        } else {
            this.leger.leggTil(new Spesialist(navn, kontrollId));
        }

        this.debugMsg(String.format("Lagt til ny lege: %s", navn));
    }


    public void leggTilResept(int middel_id, String lege_navn, int pasient_id, String type, int reit) {
        // loop gjennom leger til man finner en lege som har
        // et navn som  matcher lege_navn parameteret ellers
        // sendes det en feilmelding.
        Lege lege = null;
        for (Lege node: this.leger) {
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

        // loop gjennom legemidler til man finner en legemiddel
        // som har en id som matcher middel_id parameteret ellers
        // sendes det en feilmelding.
        Legemiddel middel = null;
        for (Legemiddel node: this.legemidler) {
            if (node.hentId() == middel_id) {
                middel = node;
                break;
            }
        }

        // hvis middel fortsatt er null fant vi
        // ingen middel med en matchende id
        if (middel == null) {
            this.errorMsg(String.format("no middel found with id: %s", middel_id));
            return;
        }

        // loop gjennom pasienter til man finner en pasient
        // som har en id som matcher pasient_id parameteret
        // ellers sendes det en feilmelding.
        Pasient pasient = null;
        for (Pasient node: this.pasienter) {
            if (node.hentId() == pasient_id) {
                pasient = node;
                break;
            }
        }
        
        // hvis pasient er null fant vi ingen
        // pasient med en matchende id
        if (pasient == null) {
            this.errorMsg(String.format("no pasient found with id: %s", pasient_id));
            return;
        }

        // sett reit før if statements for å unngå
        // udefinert lokal variabel error
        // lag resept basert på type med legen
        try {
            Resept resept = null;
            if (type.equals("hvit")) {
                resept = lege.skrivHvitResept(middel, pasient, reit);
            }
            else if (type.equals("blaa")) {
                resept = lege.skrivBlaaResept(middel, pasient, reit);
            }
            else if (type.equals("militaer")) {
                resept = lege.skrivMilResept(middel, pasient);
            }
            else if (type.equals("p")) {
                resept = lege.skrivPResept(middel, pasient, reit);
            }
            else {
                this.errorMsg(String.format("Ugyldig resept type: %s", type));
                return;
            }

            this.resepter.leggTil(resept);
            this.debugMsg(String.format("Lagt til ny resept for legemiddel: %s", middel.hentNavn()));
        }

        // hvis en ulovlig utskrift oppstår ta den imot
        catch (UlovligUtskrift e) {
            this.errorMsg(e.toString());
        }
    }


    public void lesFraFil(String filename){
        // reset error counter og linje nummer
        this.err_count = 0;
        this.line_num = 0;

        try {
            // inkrementer linje nummer
            this.line_num++;
            
            this.debugMsg(String.format("Reading file: %s\n", filename));

            // lag et fil objekt og en scanner for å lese filen
            File data_fil = new File(filename);
            Scanner fil_leser = new Scanner(data_fil);

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
                    // gjør ny type om til lowercase
                    String new_type_gen = line.split(" ")[1].toLowerCase();

                    // skjekk om den nye typen er en gyldig type
                    boolean gyldig_type = false;
                    for (String gen_type: this.gyldige_gens) {
                        if (new_type_gen.equals(gen_type)) {
                            gyldig_type = true;
                            break;
                        }
                    }

                    if (gyldig_type) {
                        this.debugMsg(String.format("generator type updated: %s", new_type_gen));    
                        this.type_gen = new_type_gen;
                    } else {
                        this.errorMsg(String.format("ugyldig generator gitt: %s", new_type_gen));
                    }
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
                try {
                    if (this.type_gen == null) {
                        this.errorMsg("ingen generator type valgt");
                        System.exit(1);
                    }

                    // bruker ikke switch case her for å unngå
                    // "orphaned case error" som oppstod av å
                    // putte if statements inni switch casene
                    else if (this.type_gen.equals("pasienter")) {
                        // alle parameterne er strings så de
                        // kan gis direkte til metoden uten
                        // type casting.

                        // legg til ny pasient i legesystemet
                        this.leggTilPasient(args[0], args[1]);
                    }
                    
                    else if (this.type_gen.equals("legemidler")) {
                        // henter data fra linjen
                        String navn = args[0];
                        String type = args[1];
                        
                        // runder av pris verdien
                        int pris = (int) Math.round(Double.parseDouble(args[2]));
                        
                        // typen konverteres fra String til double
                        double virkestoff = Double.parseDouble(args[3]);

                        // hvis typen er vanlig gir vi en 0 som styrke
                        // som ikke vil bli brukt av funskjonen for vi
                        // gidder ikke overloade parameterene mens hvis
                        // typen ikke er vanlig vil det kreve en styrke
                        int styrke = 0;
                        if (!type.equals("vanlig")) {
                            styrke = Integer.parseInt(args[4]);
                        }

                        // legg til ny legemiddel i legesystemet
                        this.leggTilLegemiddel(navn, type, pris, virkestoff, styrke);
                    }
                    
                    else if (this.type_gen.equals("leger")) {
                        // alle parameterne er strings så de
                        // kan gis direkte til metoden uten
                        // type casting.
                        // legg til ny lege i legesystemet
                        this.leggTilLege(args[0], args[1]);
                    }

                    else if (this.type_gen.equals("resepter")) {
                        int middel_id = Integer.parseInt(args[0]);
                        String lege_navn = args[1];
                        int pasient_id = Integer.parseInt(args[2]);
                        String type = args[3];
                        
                        int reit = 0;
                        if (!type.equals("militaer")) {
                            reit = Integer.parseInt(args[4]);
                        }
                        
                        this.leggTilResept(middel_id, lege_navn, pasient_id, type, reit);
                    }
                }
                
                // hvis en index exception skjer så er det feil
                // når det leses av argumentene og kan blir dekket
                // med en feil melding ved å bruke line_num uten
                // å stoppe programmet for å kunne kjøre den store
                // filen som skal inneholde noen feil
                catch (IndexOutOfBoundsException e) {
                    System.out.println(e);
                    this.errorMsg("data mangler argumenter");
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
            this.errorMsg(String.format("klarer ikke finne filen: %s", filename));
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
            this.warningMsg(String.format("det oppstod %s feil i lesing og parsing av fil", this.err_count));
        }
    }

    public void lagreSystem(String filename) {
        File fil = new File(filename);
        
        try {
            // prøv å lage filen
            fil.createNewFile();
            
            // lag et FileWriter objekt
            FileWriter fil_skriver = new FileWriter(fil);

            // lagre pasient seksjon indikator
            fil_skriver.write("# Pasienter\n");

            // lagre hver pasient i legesystemet
            for (Pasient pasient: this.pasienter) {
                // hent data
                String navn = pasient.hentNavn();
                String dato = pasient.hentFoodselsdato();

                // lagre data
                fil_skriver.write(String.format("%s,%s\n", navn, dato));
            }


            // lagre legemiddel seksjon indikator
            fil_skriver.write("# Legemidler\n");
            
            // lagre hver legemiddel i legesystemet
            for (Legemiddel middel: this.legemidler) {
                // hent data
                String navn = middel.hentNavn();
                String type = middel.getClass().getSimpleName().toLowerCase();
                String pris = String.valueOf(middel.hentPris());
                String virkestoff = String.valueOf(middel.hentVirkestoff());
                
                String data = String.format("%s,%s,%s,%s", navn, type, pris, virkestoff);
                
                // legg til styrke hvis middel er narkotisk
                if (middel instanceof Narkotisk) {
                    data += String.format(",%s", String.valueOf(((Narkotisk) middel).hentStyrke()));
                }
                
                // legg til styrke hvis middel er vanedannende
                else if (middel instanceof Vanedannende) {
                    data += String.format(",%s", String.valueOf(((Vanedannende) middel).hentStyrke()));
                }

                // skriv data
                fil_skriver.write(data + "\n");
            }


            // lagre legemiddel seksjon indikator
            fil_skriver.write("# Leger\n");

            // lagre hver lege i legesystemet
            for (Lege lege: this.leger) {
                // hent data
                String navn = lege.hentNavn();
                
                // hent kontroll id som er 0 for vanlige leger
                String id = "0";
                if (lege instanceof Spesialist) {
                    id = ((Spesialist) lege).hentKontrollID();
                }

                // skriv data
                fil_skriver.write(String.format("%s,%s\n", navn, id));
            }


            // lagre legemiddel seksjon indikator
            fil_skriver.write("# Resepter\n");

            // lagre hver resept i legesystemet
            for (Resept resept: this.resepter) {
                // hent data
                String middel_nummer = String.valueOf(resept.hentLegemiddel().hentId());
                String lege_navn = String.valueOf(resept.hentLege().hentNavn());
                String pasient_id = String.valueOf(resept.hentPasient().hentId());
                
                // hent type
                String type = "";
                
                if (resept instanceof MilResept) {
                    type = "militaer";
                }

                else if (resept instanceof PResept) {
                    type = "p";
                }
                
                else if (resept instanceof HvitResept) {
                    type = "hvit";
                }

                else if (resept instanceof BlaaResept) {
                    type = "blaa";
                }

                String data = String.format("%s,%s,%s,%s", middel_nummer, lege_navn, pasient_id, type);
                
                // lagre resepter som ikke er milresept med reit
                if (!(resept instanceof MilResept)) {
                    data += String.format(",%s", resept.hentReit());
                }

                // skriv data
                fil_skriver.write(data + "\n");
            }

            this.debugMsg(String.format("data er lagret til fil: %s", filename));

            // lukk filen
            fil_skriver.close();
        }
        
        catch (IOException e) {
            this.errorMsg(String.format("Klarer ikke lagre data til %s", filename));
            return;
        }

    }
    
    public Pasient[] hentPasienter() {
        Pasient[] pasient_array = new Pasient[this.pasienter.stoerrelse()];

        int indeks = 0;
        for (Pasient pasient: this.pasienter) {
            pasient_array[indeks] = pasient;
            indeks++;
        }

        return pasient_array;
    }

    public Legemiddel[] hentLegemiddler() {
        Legemiddel[] middle_array = new Legemiddel[this.legemidler.stoerrelse()];
        
        int indeks = 0;
        for (Legemiddel middel: this.legemidler) {
            middle_array[indeks] = middel;
            indeks++;
        }

        return middle_array;
    }

    public Lege[] hentLeger() {
        Lege[] lege_array = new Lege[this.leger.stoerrelse()];

        int indeks = 0;
        for (Lege lege: this.leger) {
            lege_array[indeks] = lege;
            indeks++;
        }

        return lege_array;
    }

    public Resept[] hentResepter() {
        Resept[] resept_array = new Resept[this.resepter.stoerrelse()];

        int indeks = 0;
        for (Resept resept: this.resepter) {
            resept_array[indeks] = resept;
            indeks++;
        }

        return resept_array;
    }

    public void tabellerData(String[] keys, String[][] data) {
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
        String string_format = " ";
        for (int padding: paddings) {
            string_format += "%-"+ padding + "s  ";
        }

        // (Object[]keys) er en type casting som gjør
        // at String.format ikke gir en advarsel på
        // kompilatoren når du gir argumentene som en
        // array 
        System.out.println(String.format(string_format, (Object[])keys));
        
        // print splitter
        String split_line = " ";
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
        String[] keys = {"Lege Navn", "Resepter", "Type", "KontrollID"};

        // lag en 2 dimensjonal liste som skal holde på hver
        // eneste lege i lege_data[n] og i lege_data[n][0-4]
        // skal den holde data om lege
        String[][] lege_data = new String[this.leger.stoerrelse()][5];

        // start på indeks 0 og bruk den for å spesifisere
        // legen som dataen skal lagres til og puttets
        // i en tabell senere
        int index = 0;
        for (Lege lege: this.leger) {
            // hent diverse data om legen og lagre det i
            // lege_data hos denne legen sin indeks
            lege_data[index][0] = lege.hentNavn();
            lege_data[index][1] = String.valueOf(lege.utskrevneResepter().antall);
            lege_data[index][2] = lege.getClass().getSimpleName();
            
            // hvis legen er en spesialist så må vi
            // type caste det slik at java compileren
            // skjønner at det finnes en hentKontrollID
            // metode å kalle på 
            if (lege instanceof Spesialist) {
                Spesialist spesialist = (Spesialist)(lege);
                lege_data[index][3] = spesialist.hentKontrollID();
            } 
            
            // hvis ikke legen er en spesialist setter
            // vi kontroll id til en blank verdi siden
            // vanlige leger har ikke kontroll id 
            else {
                lege_data[index][3] = "";
            }
            index++;
        }

        // print en tabell av lege dataen
        tabellerData(keys, lege_data);
    }

    public void pasientTabell() {
        // keys for å lage data områder på toppen av tabellen
        String[] keys = {"Pasient Navn", "ID", "Foodselsdato"};

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
            pasient_data[index][1] = String.valueOf(pasient.hentId());
            pasient_data[index][2] = pasient.hentFoodselsdato();
            index++;
        }

        // print en tabell av pasient dataen
        tabellerData(keys, pasient_data);
    }

    public void middlerTabell() {
        // keys for å lage data områder på toppen av tabellen
        String[] keys = {"Legemiddel Navn", "Type", "ID", "Pris", "Virkestoff", "Styrke"};

        // lag en 2 dimensjonal liste som skal holde på
        // hver eneste legemiddel i middel_data[n] og
        // i middel_data[n][0-4] skal den holde data om
        // legemiddelet
        String[][] middel_data = new String[this.legemidler.stoerrelse()][6];

        // start på indeks 0 og bruk den for å spesifisere
        // legemiddelet som dataen skal lagres til og puttets
        // i en tabell senere
        int index = 0;
        for (Legemiddel middel: this.legemidler) {
            // hent diverse data om legemiddelet og lagre det
            // i middel_data hos denne middelet sin indeks
            middel_data[index][0] = middel.hentNavn();
            middel_data[index][1] = middel.getClass().getSimpleName();
            middel_data[index][2] = String.valueOf(middel.hentId());
            middel_data[index][3] = String.valueOf(middel.hentPris());
            middel_data[index][4] = String.valueOf(middel.hentVirkestoff());
            
            // hvis legemiddelet er narkotisk så må vi
            // type caste det slik at java compileren
            // skjønner at det finnes en hentNarkotiskStyrke
            // metode å kalle på 
            if (middel instanceof Narkotisk) {
                Narkotisk narkotisk = (Narkotisk)middel;
                middel_data[index][5] = String.valueOf(narkotisk.hentStyrke());
            }
            
            // samme prinsipp som i narkotisk mens her
            // heter metoden som dataen skal hentes fra
            // hentVanedannendeStyrke
            else if (middel instanceof Vanedannende) {
                Vanedannende vanedannende = (Vanedannende)middel;
                middel_data[index][5] = String.valueOf(vanedannende.hentStyrke());
            }

            // dette tilfelle er for vanlig legemidler
            // og de har ingen styrke så vi setter en
            // tom string som styrke data
            else {
                middel_data[index][5] = "";
            }
            
            // inkrementer til neste indeks
            index++;
        }

        // print en tabell av middel dataen
        tabellerData(keys, middel_data);
    }

    public void reseptTabell() {
        // keys for å lage data områder på toppen av tabellen
        String[] keys = {"Resept ID", "Lege Navn", "PasientId", "Type", "Reit"};

        // lag en 2 dimensjonal liste som skal holde på
        // hver eneste resept i resept_data[n] og
        // i resept_data[n][0-4] skal den holde data om
        // reseptet
        String[][] resept_data = new String[this.resepter.stoerrelse()][5];

        // start på indeks 0 og bruk den for å spesifisere
        // reseptet som dataen skal lagres til og puttets
        // i en tabell senere
        int index = 0;
        for (Resept resept: this.resepter) {
            // hent diverse data om reseptet og lagre det
            // i resept_data hos denne reseptet sin indeks
            resept_data[index][0] = String.valueOf(resept.hentId());
            resept_data[index][1] = resept.hentLege().hentNavn();
            resept_data[index][2] = String.valueOf(resept.hentPasient().hentId());
            resept_data[index][3] = String.valueOf(resept.getClass());
            resept_data[index][4] = String.valueOf(resept.hentReit());
            // inkrementer til neste indeks
            index++;
        }

        // print en tabell av resept dataen
        tabellerData(keys, resept_data);
    }
}