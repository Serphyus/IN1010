import java.util.ArrayList;


public abstract class Rute {
    protected int y;
    protected int x;
    protected Labyrint labyrint;
    
    // sett nabo noder | kanskje endre til final
    public Rute nord = null;
    public Rute syd = null;
    public Rute oest = null;
    public Rute vest = null;

    public Rute (int y, int x, Labyrint labyrint) {
        this.y = y;
        this.x = x;
        this.labyrint = labyrint;
    }

    // lag abstrakte metoder som må overskrives
    abstract public void finn (Rute fra, ArrayList<Tuppel> path);
    abstract public void reset ();
    abstract public String toString ();
}
