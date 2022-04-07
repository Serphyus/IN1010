import java.util.HashMap;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Oblig5Del1 {
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
        
        // lag et nytt subsekvens register
        SubsekvensRegister register = new SubsekvensRegister();
        
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
                
                // les hashmappet fra en filnavnet som
                // ble hentet ut av metadata filen
                HashMap<String, Subsekvens> file_map = SubsekvensRegister.LesFil(file_path);
                
                // legg til det leste hashmappet til registeret
                register.settInnHashMap(file_map);
            }

            // print subsekvens registeret
            System.out.println(register);
    
            // lag en merged hashmap som skal brukes til å merge
            // hvert hashmap i registeret sammen til å finne antall
            // forekomster av hver sekvens
            HashMap<String, Subsekvens> merged = new HashMap<>();
            
            // loop gjennom hver hashmap og merge det med merged hashmappet
            for (int i = 0; i < register.antallHashMaps(); i++) {
                merged = register.mergeHashMaps(merged, register.hentHashMap(i));
            }
            
            // hold en variabel av maks_sekvens 
            Subsekvens max_sekvens = null;
            
            for (Subsekvens sekvens: merged.values()) {
                // hvis maks_sekvens ikke er null og den nye sekvens
                // variablen ikke har et større antall en max_sekvens
                // sitt antall så hopper loopen til neste element
                if (max_sekvens != null) {
                    if (sekvens.hentAntall() < max_sekvens.hentAntall()) {
                        continue;
                    }
                }
                
                // endre maks_sekvens til sekvens hvis sekvens
                // har et større antall en maks_sekvens
                max_sekvens = sekvens;
            }

            // print ut sekvensen som hadde høyest antall
            System.out.println(max_sekvens);
            
            // lukk scanner objektet
            meta_scanner.close();
        }

        // gi feilmelding hvis metadata filen ikke ble funnet og exit programmet
        catch (FileNotFoundException e) {
            System.err.println(String.format("Error: unable to locate %s/metadata.csv", folder));
            System.exit(1);
        }
    }
}