import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Oblig5Hele {
    public static void main(String[] args) {
        // skjekker om antall argumenter gitt etter kommandoen 0:
        // java Oblig5Del1 [argumenter]
        if (args.length < 1) {
            System.err.println("Error: mangler filnavn parameter");
            System.exit(1);
        }
        
        // skjekker om antall argumenter gitt etter kommandoen er mer enn 1
        else if (args.length > 1) {
            System.err.println("Error: programmet tillater bare en parameter");
            System.exit(1);
        }

        // hent det første kommando argumentet og lag en
        // folder variable for å spesifisere test mappen
        String folder = args[0];
        
        // lag en array list som skal holde på alle threads som blir
        // laget til å lese filer slik at man kan vente på at alle
        // blir ferdig etter at de er startet
        ArrayList<Thread> read_thread_pool = new ArrayList<>();
        
        // lag en monitor for friske og en monitor for syke
        Monitor2 monitor_f = new Monitor2();
        Monitor2 monitor_s = new Monitor2();
        
        try {
            // lag en path til metadata filen ved å bruke mappe navnet
            String meta_file_path = String.format("%s/metadata.csv", folder);
            
            // lag en scanner for å lese fra metadata.csv filen
            Scanner meta_scanner = new Scanner(new File(meta_file_path));
            
            // looper så lenge metadata filen har en neste linje å lese av
            while (meta_scanner.hasNextLine()) {
                // les neste linje i metadata.csv filen som inneholder
                // det neste filnavnet og lag en path til filen
                String[] line_data = meta_scanner.nextLine().split(",");

                String file_name = line_data[0];
                String syk_status = line_data[1];

                String file_path = String.format("%s/%s", folder, file_name);
                
                LeseTrad lese_thread;
                if (syk_status.equals("True")) {
                    lese_thread = new LeseTrad(file_path, monitor_s);
                }
                
                else {
                    lese_thread = new LeseTrad(file_path, monitor_f);
                }
                // lag en ny thread som bruker en instanse av LeseTrad klassen
                Thread new_thread = new Thread(lese_thread);
                
                // legg til den nye threaden i thread_pool
                read_thread_pool.add(new_thread);

                // start den nye threaden og la den kjøre i bakgrunnen
                new_thread.start();
            }
            
            // lukk scanner objektet
            meta_scanner.close();
        }
        
        // gi feilmelding hvis metadata filen ikke ble funnet og exit programmet
        catch (FileNotFoundException e) {
            System.err.println(String.format("Error: unable to locate %s/metadata.csv", folder));
            System.exit(1);
        }

        try {
            // bruk Thread.join() metoden for å vente på
            // at alle theads blir ferdig med å lese filene
            for (Thread thread : read_thread_pool) {
                thread.join();
            }
        }

        // gi feilmelding hvis brukeren velger å avbryte
        // programmet med ctrl+c mens det kjører
        catch (InterruptedException e) {
            System.err.print("Error: program was interrupted by user");
        }

        // lag en array list som skal holde på alle threads som blir
        // laget til å merge sammen hashmapsene i monitorer slik at
        // man kan vente på at alle blir ferdig etter at de er startet
        ArrayList<Thread> merge_thread_pool = new ArrayList<>();

        // start 8 thread for hver monitor som merger
        // deres interne hashmaps til de bare har 1.
        for (int i = 0; i < 8; i++) {
            Thread merge_thread_f = new Thread(new FletteTrad(monitor_f));
            Thread merge_thread_s = new Thread(new FletteTrad(monitor_s));

            merge_thread_pool.add(merge_thread_f);
            merge_thread_pool.add(merge_thread_s);

            merge_thread_f.start();
            merge_thread_s.start();
        }

        // vent på at mergingen blir ferdig
        try {
            for (Thread thread : merge_thread_pool) {
                thread.join();
            }
        }
        
        // gi feilmelding hvis brukeren velger å avbryte
        // programmet med ctrl+c mens det kjører
        catch (InterruptedException e) {
            System.err.print("Error: program was interrupted by user");
        }

        // ---------------------------------------------------------------- //
        //                ADVARSEL FOR ANDRE SOM LESER DETTE                //
        // Her brukte jeg den lette løsningen for oppgave 12 istedenfor     //
        // å bruke en binomial test for å lage bedre statistikk resultater  //
        // ---------------------------------------------------------------- //

        // lag en liste som skal holde på alle subsekvenser som
        // oppstår oftere hos smittede personer enn friske
        ArrayList<Subsekvens> vanlig_smitte = new ArrayList<>();
        
        // finn alle sekvenser med høyere antall i syke enn friske
        HashMap<String, Subsekvens> frisk_hashmap = monitor_f.hentHashMap(0);
        HashMap<String, Subsekvens> syk_hashmap = monitor_s.hentHashMap(0);
        
        // loop gjennom alle subsekvenser hos syke personer
        for (String sekvens: syk_hashmap.keySet()) {
            // skjekk om subsekvensen eksisterer hos friske personer
            if (frisk_hashmap.containsKey(sekvens)) {

                // hent ut objektet med spesefik DNA-sekvens
                // fra friske og syke personer hashmappene
                Subsekvens frisk_sekvens = frisk_hashmap.get(sekvens);
                Subsekvens syk_sekvens = syk_hashmap.get(sekvens);

                // hvis subsekvensen oppstår mer hos syke personer
                // enn hos friske personer legges den til en array
                // av subsekvenser som er dominante hos syke personer
                if (syk_sekvens.hentAntall() > frisk_sekvens.hentAntall()) {
                    vanlig_smitte.add(syk_sekvens);
                }
            }
        }

        // printer ut overskrift
        System.out.println("\nDominante sekvenser hos syke personer");
        System.out.println("=====================================");
        
        // looper gjennom alle array listen av dominante
        // subsekvenser som ble funnet og printer dem ut
        for (Subsekvens sub: vanlig_smitte) {
            System.out.print(sub);
        }
    }
}