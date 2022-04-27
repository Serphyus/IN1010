import java.util.ArrayList;


public class Aapning extends HvitRute {
    public Aapning (int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    @Override
    public void finn (Rute fra, ArrayList<Tuppel> path) {
        path.add(new Tuppel(this.y, this.x, true));
    }
}
