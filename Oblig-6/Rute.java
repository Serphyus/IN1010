import java.util.ArrayList;


public abstract class Rute {
    protected int y;
    protected int x;
    protected Labyrint labyrint;
    
    public Rute nord = null;
    public Rute syd = null;
    public Rute oest = null;
    public Rute vest = null;

    public Rute (int y, int x, Labyrint labyrint) {
        this.y = y;
        this.x = x;
        this.labyrint = labyrint;
    }

    abstract public void finn (Rute fra, ArrayList<Tuppel> path);
    abstract public void reset ();
    abstract public String toString ();
}
