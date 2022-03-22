import java.util.ArrayList;


public class Dataklynge {
    // lag en dynamisk beholder som en vektor for å beholde
    // ett ikke statisk antall av racks.
    private ArrayList<Rack> dataklynge = new ArrayList<Rack>();

    // returner antall racks
    public int antRacks() {
        return dataklynge.size();
    }

    // legg til ny node
    public void settInn(Node node) {
        // hvis dataklyngen er tom kan man legge til en ny rack
        if (dataklynge.size() == 0) {
            dataklynge.add(new Rack());
        }

        // hvis den siste racken er full kan man legge til en ny rack
        else if (dataklynge.get(dataklynge.size()-1).antNoder() == 12) { // sadkopasdk as
            dataklynge.add(new Rack());
        }
        
        // etter de siste operasjonene vet vi at den siste racken i
        // dataklyngen har plass så vi setter inn noden der.
        dataklynge.get(dataklynge.size()-1).settInn(node);
    }

    public int antProsessorer() {
        // loop gjennom hver rack og hent antall prosessorer
        // nodene deres inneholder og returner dem
        int ant_prosessorer = 0;
        for (Rack rack: dataklynge) {
            ant_prosessorer += rack.antProssesorer();
        }
        return ant_prosessorer;
    }

    public int noderMedNokMinne(int paakrevdMinne) {
        // loop gjennom hver rack og kall noderMedNokMinne for
        // å skjekke om de har nok minne med påkrevd minne
        // argumentet
        int ant_noder = 0;
        for (Rack rack: dataklynge) {
            ant_noder += rack.noderMedNokMinne(paakrevdMinne);
        }
        return ant_noder;
    }
}