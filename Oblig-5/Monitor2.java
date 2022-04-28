import java.util.HashMap;

public class Monitor2 extends Monitor1 {
    public void mergeHashMaps() {
        // lås thread locken
        this.thread_lock.lock();
        
        HashMap<String, Subsekvens> map1 = null;
        HashMap<String, Subsekvens> map2 = null;

        try {
            // hvis antall hashmaps er minder enn 2 vent på signal
            if (this.antallHashMaps() < 2) {
                this.thread_lock.unlock();
                return;
            }
            
            // ta ut 2 hashmaps fra registeret for å merges
            map1 = this.taUtHashMap(0);
            map2 = this.taUtHashMap(0);
        }
        
        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }

        // merge sammen hashmapsene etter å ha hentet
        // den ut. dette gjøres utenfår låsen siden
        // dataen ikke lenger ligger i registeret.
        HashMap<String, Subsekvens> merged = SubsekvensRegister.mergeHashMaps(map1, map2);
        
        // legg inn det ferdig mergede hashmappet
        this.settInnHashMap(merged);
    }
}