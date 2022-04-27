import java.util.ArrayList;


public class HvitRute extends Rute {
    private boolean used = false;

    public HvitRute (int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    @Override
    public void finn (Rute fra, ArrayList<Tuppel> path) {
        if (this.used) {
            return;
        }

        this.used = true;

        Tuppel current = new Tuppel(this.y, this.x, false);
        path.add(current);

        Rute[] available = {this.nord, this.syd, this.vest, this.oest};

        for (Rute neste : available) {
            if (neste != fra && neste != null) {
                neste.finn(this, path);
                if (path.get(path.size()-1).finish) {
                    return;
                }
            }
        }

        path.remove(current);
    }

    @Override
    public void reset () {
        this.used = false;
    }

    @Override
    public String toString () {
        return "  ";
    }
}
