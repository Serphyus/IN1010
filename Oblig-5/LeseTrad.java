import java.util.HashMap;

public class LeseTrad implements Runnable {
    // filnavn for å vite hvilken fil som skal leses
    private String filnavn;
    // en referanse til monitor for å kunne kalle dens metoder
    private Monitor1 monitor;

    public LeseTrad(String filnavn, Monitor1 monitor) {
        this.filnavn = filnavn;
        this.monitor = monitor;
    }
    
    @Override
    public void run() {
        // les fil og lag et hashmap av Strings og Subsekvenser
        HashMap<String, Subsekvens> fil_map = Monitor1.lesFil(this.filnavn);

        // legg til hashmappet som ble laget fra
        // filen til i monitoren sitt register
        this.monitor.settInnHashMap(fil_map);
    }
}
