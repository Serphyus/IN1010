import java.util.HashMap;
import java.util.ArrayList;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class SubsekvensRegister {
    private ArrayList<HashMap<String, Subsekvens>> hashBeholder = new ArrayList<>();

    
    public static HashMap<String, Subsekvens> LesFil(String filnavn) {
        // lag en tom hashmap til å holde subsekvenser
        HashMap<String, Subsekvens> fil_map = new HashMap<>();
        
        try {
            // lag en scanner objekt av filenavn argumentet
            Scanner reader = new Scanner(new File(filnavn));

            // sett linje nummer for feilmeldinger
            int line_num = 1;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                // hvis linje lengde er mer enn 
                if (line.length() < 3) {
                    System.err.println(String.format("Error: line %s in %s: subsekvens is not 3 characters or longer", line_num, filnavn));
                    System.exit(1);
                }

                // del opp stringen i alle muldige sekvenser av 3
                // ved å loope gjennom lengden-2 og hent en substring
                // som er stringen fra indeks x til y.
                for (int i = 0; i < line.length()-2; i++) {
                    String sekvens = line.substring(i, i+3);

                    // hvis sekvensen ikke eksisterer i hashmappet legg til
                    // et nytt sekvens objekt med antall 1 fordi antall skal
                    // bare brukes til å telle når man merger hashmaps.
                    if (!fil_map.containsKey(sekvens)) {
                        fil_map.put(sekvens, new Subsekvens(sekvens, 1));
                    }
                }
            }
            
            // lukk scanner objekt
            reader.close();
        }
        
        // hvis filen som er gitt som parameteret ikke eksisterer
        // gi en feildmelding for så å stoppe programmet
        catch (FileNotFoundException e) {
            System.err.println(String.format("Error: Unable to locate: %s", filnavn));
            System.exit(1);
        }

        // returner hashmappen
        return fil_map;
    }


    public void settInnHashMap(HashMap<String, Subsekvens> map){
        this.hashBeholder.add(map);
    }


    public HashMap<String, Subsekvens> hentHashMap(int indeks) {
        return this.hashBeholder.get(indeks);
    }
    

    public int antallHashMaps(){
        // returner størrelsen på array listen som inneholder hashmaps
        return this.hashBeholder.size();
    }


    public HashMap<String, Subsekvens> mergeHashMaps(HashMap<String, Subsekvens> map1, HashMap<String, Subsekvens> map2) {
        // vi lager et tredje hashmap for å ikke påvirke
        // hashmap parameterene siden de er en referanse
        // til minne posisjoner og vil da endres for alt
        // annet som peker på dem
        HashMap<String, Subsekvens> merged = new HashMap<>();

        // legg til alle key og valuesene til den første hashmappen
        merged.putAll(map1);

        for (String key: map2.keySet()) {
            // hvis sekvens key ikke eksisterer i merged
            // hashmap så legges den bare til
            if (!merged.containsKey(key)) {
                merged.put(key, map2.get(key));
            }
            
            // hvis sekvens key allerede eksisterer i
            // merged hashmap så legger man til 1 i antall
            else {
                Subsekvens subsekvens = merged.get(key);
                subsekvens.settAntall(subsekvens.hentAntall() + 1);
            }
        }

        // returner det hashmappet som inneholder merged key og values
        return merged;
    }


    public String toString() {
        // lag en output string som skal inneholde
        // infomasjonen om hver hashmap i arraylisten
        // og hver subsekvens i hashmapsene 
        String output = "";
        
        // bruk en indeks for å representere fil numer
        // og loop gjennom hver hashmap i array listen
        int indeks = 1;
        for (HashMap<String, Subsekvens> map: this.hashBeholder) {
            
            // loop gjennom hver sekvens String og subsekvens objekt
            // i hashmapen og bruk subsekvens klassens toString metode
            output += String.format("File %s\n", indeks);
            
            // legg til hver subsekvens i hashmpappet
            // til stringen output
            for (String sekvens: map.keySet()) {
                output += map.get(sekvens);
            }

            output += "\n";
            indeks++;
        }
        
        // returner kombinert output av alle hashmappene
        return output;
    }
}