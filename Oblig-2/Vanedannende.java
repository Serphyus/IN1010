public class Vanedannende extends Legemiddel {
    public int vanedannende;
    
    public Vanedannende(String navn, int pris, double virkestoff, int vanedannende) {
        super(navn, pris, virkestoff);
        this.vanedannende = vanedannende;
    }

    public int hentVanedannendeStyrke() {
        return vanedannende;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\nvanedannende: %s", vanedannende);
    }
}
