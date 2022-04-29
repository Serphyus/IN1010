public class Tuppel {
    public final int y;
    public final int x;
    public final boolean finish;

    public Tuppel(int y, int x, boolean finish) {
        this.y = y;
        this.x = x;

        // finish brukes som en indikator for å si om
        // denne posisjonen er en slutt posisjon.
        this.finish = finish;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", this.y, this.x);
    }
}