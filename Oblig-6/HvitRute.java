import java.util.ArrayList;


public class HvitRute extends Rute {
    private boolean used = false;
    private boolean mark = false;

    public HvitRute (int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    @Override
    public void finn (Rute fra, ArrayList<Tuppel> path, ArrayList<ArrayList<Tuppel>> completed) {
        // skjekk om rute allerede er brukt
        if (this.used) {
            return;
        }

        // sett used flag til true
        this.used = true;

        // lag en ny tuppel og legg den til path
        Tuppel current = new Tuppel(this.y, this.x);
        path.add(current);

        // lag en array av naboer som skal skjekkes for mulige veier
        Rute[] available = {this.nord, this.syd, this.vest, this.oest};

        // loop gjennom hver eneste nabo og skjekk om de er hvite
        // og om de legger seg selv til etter å kalle finn metoden
        for (Rute neste : available) {
            if (neste != fra && neste != null) {
                neste.finn(this, path, completed);
            }
        }

        // hvis metoden når hit så var ingen av naboene en
        // gyldig vei til utgangen og da fjerner ruten seg
        // selv fra pathen slik at vi kan gå tilbake uten å
        // beholde feilede forsøk i path arrayen
        path.remove(current);
    }

    @Override
    public void reset () {
        // resetter used og mark verdiene til false
        this.used = false;
        this.mark = false;
    }

    @Override
    public void mark () {
        // marker ruten slik at den vil printes med farge
        this.mark = true;
    }


    @Override
    public String toString () {
        if (this.mark) {
            // hvis ruten er markert så blir den grønn
            return "\033[42m  \033[0m";
        }
        return "  ";
    }
}