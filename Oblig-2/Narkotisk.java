public class Narkotisk extends Legemiddel {
    public int styrke;
    
    public Narkotisk(String navn, int pris, double virkestoff, int styrke) {
        super(navn, pris, virkestoff);
        this.styrke = styrke;
    }

    public int hentNarkotiskStyrke() {
        return styrke;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\nstyrke: %s", styrke);
    }
}
