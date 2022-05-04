import java.util.ArrayList;


public class SortRute extends Rute {
    public SortRute (int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    // siden sort rute er en vegg skal ikke
    // finn eller reset metode gjøre noe

    @Override
    public void finn (Rute fra, ArrayList<Tuppel> path, ArrayList<ArrayList<Tuppel>> completed) {}

    @Override
    public void reset () {}

    @Override
    public void mark() {}

    @Override
    public String toString () {
        return String.format("##");
    }
}