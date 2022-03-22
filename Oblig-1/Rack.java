public class Rack {
    // lag en statisk array med 12 tilgjengelige node
    // plasser som lagres som den private instans
    // variablen node_array.
    private Node[] node_array = new Node[12];

    // sett inn en ny node i rack arrayen ved å skjekke
    // hver indeks om den holder en null verdi som betyr
    // at den er tom deretter legg noden der.
    public void settInn(Node node) {
        for (int i = 0; i < 12; i++) {
            if (node_array[i] == null) {
                node_array[i] = node;
                return;
            }
        }
    }

    // loop gjennom node_array og inkrementer ant_noder
    // verdien hvis noden ikke er en null verdi
    public int antNoder() {
        int ant_noder = 0;
        for (Node node: node_array) {
            if (node != null) {
                ant_noder++;
            }
        }
        return ant_noder;
    }

    // loop gjennom node_array og legg til verdien returnert
    // fra Node klassens antProsessorer hvis noden ikke er null
    // til ant_prosessorer for så å returnere verdien.
    public int antProssesorer() {
        int ant_prossesorer = 0;
        for (Node node: node_array) {
            if (node != null) {
                ant_prossesorer += node.antProsessorer();
            }
        }
        return ant_prossesorer;
    }

    // loop gjennom node_array og inkrementer ant_noder verdien
    // hvis den nåværende noden ikke er null og oppfyller verdien
    // gitt som paakrevdMinne argumentet.
    public int noderMedNokMinne(int paakrevdMinne) {
        int ant_noder = 0;
        for (Node node: node_array) {
            if (node != null) {
                if (node.nokMinne(paakrevdMinne)) {
                    ant_noder++;
                }
            }
        }
        return ant_noder;
    }
}