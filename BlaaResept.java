public class BlaaResept extends Resept {
    public BlaaResept(Legemiddel middel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(middel, utskrivendeLege, pasient, reit);
    }

    @Override
    public String farge() {
        return "blaa";
    }
    
    @Override
    public int prisAaBetale() {
        return (hentLegemiddel().hentPris() / 4);
    }
}
