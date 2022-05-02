public class Tuppel {
    public final int y;
    public final int x;

    public Tuppel (int y, int x) {
        this.y = y;
        this.x = x;
    }

    @Override
    public String toString () {
        return String.format("Kordinat: [%s,%s]", this.y, this.x);
    }
}