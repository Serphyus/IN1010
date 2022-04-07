import java.util.HashMap;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor1 {
    // lag et internt register
    private SubsekvensRegister register = new SubsekvensRegister();
    
    // lag en thread lock slik data ikke blir endret samtidig
    // hvor en thread leser en minne posisjon samtidig som en
    // annen thread skriver til minne posisjonen. denne locken
    // vil putte alle som ønsker selv å låse dem inn i en kø
    // og vente på sin tur til å selv låse den.
    private Lock thread_lock = new ReentrantLock();

    public static HashMap<String, Subsekvens> LesFil(String filnavn) {
        return SubsekvensRegister.LesFil(filnavn);
    }
    
    public void settInnHashMap(HashMap<String, Subsekvens> map){
        // lås thread locken
        this.thread_lock.lock();

        // legg til den nye hashmappen
        try {
            this.register.settInnHashMap(map);
        }

        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }
    }
    
    
    public HashMap<String, Subsekvens> hentHashMap(int indeks) {
        // låst thread locken
        this.thread_lock.lock();
        
        // lag en undefined hashmap som skal holde
        // på hashmappen som hentes fra registeret
        HashMap<String, Subsekvens> map;

        // hent hashmappet
        try {
            map = this.register.hentHashMap(indeks);
        }

        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }

        return map;
    }
    
    public int antallHashMaps(){
        // lag en undefined integer som skal holde
        // på antall hashmaps som registeret holder
        int antall;
        
        // lås thread locken
        this.thread_lock.lock();

        // hent antall hashmapper
        try {
            antall = this.register.antallHashMaps();
        }

        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }

        return antall;
    }
    
    public HashMap<String, Subsekvens> mergeHashMaps(HashMap<String, Subsekvens> map1, HashMap<String, Subsekvens> map2) {
        // grunnen til at denne må låses er at selv om
        // den bare merger parameterene gitt så vil de
        // hashmappene gitt i parameterene kunne blitt
        // endret av en annen thread mens mergingen
        // skjer som kan skape problemer.
        
        // lås thread locken
        this.thread_lock.lock();

        // lag en undefined hashmap som skal holde
        // på en merget hashmap laget fra parameterene
        HashMap<String, Subsekvens> merged;
        
        // legg til den nye hashmappen
        try {
            merged = this.register.mergeHashMaps(map1, map2);
        }

        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }
        
        return merged;
    }

    public String toString() {
        // lag en undefined String som skal holde
        // på toString resultatet fra registeret
        String output;
        
        // lås thread locken
        this.thread_lock.lock();

        // hent registeret sin toString
        try {
            output = this.register.toString();
        }

        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }

        return output;
    }
}