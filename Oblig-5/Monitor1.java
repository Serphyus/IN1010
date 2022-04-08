import java.util.HashMap;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor1 {
    // lag et internt register
    protected SubsekvensRegister register = new SubsekvensRegister();
    
    // lag en thread lock slik data ikke blir endret samtidig
    // hvor en thread leser en minne posisjon samtidig som en
    // annen thread skriver til minne posisjonen. denne locken
    // vil putte alle som ønsker selv å låse dem inn i en kø
    // og vente på sin tur til å selv låse den.
    protected Lock thread_lock = new ReentrantLock(true);

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

    public void mergeInternals() {
        this.register.mergeInternals();
    }

    public Subsekvens hentStoerste() {
        // lag en undefined subsekvens som skal holde
        // på den største subsekvensen fra registeret
        Subsekvens stoerste;
        
        // lås thread locken
        this.thread_lock.lock();

        // hent registeret sin største subsekvens
        try {
            stoerste = this.register.hentStoerste();
        }

        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }

        return stoerste;
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