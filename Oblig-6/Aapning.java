import java.util.ArrayList;


public class Aapning extends HvitRute {
    public Aapning (int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    @Override
    public void finn (Rute fra, ArrayList<Tuppel> path, ArrayList<ArrayList<Tuppel>> completed) {
        // lag en tuppel for denne åpnings ruten
        Tuppel current = new Tuppel(this.y, this.x);

        // legg til denne åpningen til path
        path.add(current);

        // kopier over alle tuplene til en new_completed
        // array list og legg den til completed arraylisten
        ArrayList<Tuppel> new_completed = new ArrayList<>();
        new_completed.addAll(path);

        completed.add(new_completed);

        // fjern åpningen fra pathen slik at vi kan
        // gjenbruke den til å finne de neste utveiene
        path.remove(current);
    }
}
