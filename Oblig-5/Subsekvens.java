public class Subsekvens {
    // sett sekvens til en undefined konstant som
    // aldri kan endres etter å bli defined en gang
    public final String sekvens;
    
    // antall forekomster av sekvensen
    private int antall;

    public Subsekvens(String sekvens, int antall) {
        this.sekvens = sekvens;
        this.antall = antall;
    }

    // hent antall instansevariabel
    public int hentAntall() {
        return this.antall;
    }

    // sett en ny verdi på antall
    public void settAntall(int antall) {
        this.antall = antall;
    }

    @Override
    public String toString() {
        return String.format("Sekvens: %s | Antall: %s\n", this.sekvens, this.antall);
    }
}