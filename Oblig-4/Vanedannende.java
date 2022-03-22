public class Vanedannende extends Legemiddel {
    private int styrke;
    
    public Vanedannende(String navn, int pris, double virkestoff, int styrke){
        super(navn, pris, virkestoff);
        this.styrke = styrke;
    }

    public int hentStyrke() {
        return styrke;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\nVanedannende Styrke: %s", this.styrke);
    }
}
