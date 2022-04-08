import java.util.HashMap;
import java.util.concurrent.locks.Condition;

public class Monitor2 extends Monitor1 {
    public Condition flettebar = this.thread_lock.newCondition();
    public Boolean aktiv_lesing = false;

    @Override
    public void settInnHashMap(HashMap<String, Subsekvens> map){
        this.thread_lock.lock();

        // legg til den nye hashmappen
        try {
            this.register.settInnHashMap(map);

            // signaliser flettbar kondisjonen hvis
            // antallet hashmaps er mer eller lik 2
            if (this.register.antallHashMaps() >= 2) {
                this.flettebar.signal();
            }
        }

        // lås opp thread locken selv om noe går galt
        finally {
            // this.flettebar.signal();
            this.thread_lock.unlock();
        }
    }

    // jeg gjorde dette i en metode for å gjøre prosessen
    // enklere fordi her inne i Monitor2 har jeg direkte
    // tilgang til registeret og thread locken
    public void mergeHashMaps() {
        // lås thread locken
        this.thread_lock.lock();
        
        try {
            if (this.antallHashMaps() > 2) {
                // System.out.println(this.antallHashMaps());
                this.flettebar.await();
            }

            HashMap<String, Subsekvens> map1 = this.hentHashMap(0);
            HashMap<String, Subsekvens> map2 = this.hentHashMap(1);

            this.register.fjernHashMap(0);
            this.register.fjernHashMap(0);
            
            // legg til det mergede hashmappet til registeret
            this.register.settInnHashMap(SubsekvensRegister.mergeHashMaps(map1, map2));
        }

        catch (InterruptedException e) {
            System.err.println("Error: thread contition await interrupted by user");
            System.exit(1);
        }

        // lås opp thread locken selv om noe går galt
        finally {
            this.thread_lock.unlock();
        }
    }
}