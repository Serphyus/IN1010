import java.util.ArrayList;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Oblig5Del2B {
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
        
        // lag et nytt monitor objekt
        Monitor2 monitor = new Monitor2();
        
        // lag en array list som skal holde på alle threads som blir
        // laget til å lese filer slik at man kan vente på at alle
        // blir ferdig etter at de er startet
        ArrayList<Thread> read_thread_pool = new ArrayList<>();
        
        try {
            // lag en path til metadata filen ved å bruke mappe navnet
            String meta_file_path = String.format("%s/metadata.csv", folder);
            
            // lag en scanner for å lese fra metadata.csv filen
            Scanner meta_scanner = new Scanner(new File(meta_file_path));
            
            // looper så lenge metadata filen har en neste linje å lese av
            while (meta_scanner.hasNextLine()) {
                // les neste linje i metadata.csv filen som inneholder
                // det neste filnavnet og lag en path til filen
                String file_name = meta_scanner.nextLine();
                String file_path = String.format("%s/%s", folder, file_name);
                
                // lag en ny thread som bruker en instanse av LeseTrad klassen
                Thread new_thread = new Thread(new LeseTrad(file_path, monitor));
                
                // legg til den nye threaden i read_thread_pool
                read_thread_pool.add(new_thread);

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

        // start 8 thread for som skal merge sammen alle hashmaps
        // i en monitor sitt register til den bare har 1 hashmap.
        for (int i = 0; i < 8; i++) {
            Thread merge_thread = new Thread(new FletteTrad(monitor));
            merge_thread_pool.add(merge_thread);
            merge_thread.start();
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

        // print subsekvens monitoret
        System.out.println(monitor);
    }
}