public class BlaaResept extends Resept {
    public BlaaResept(Legemiddel middel, Lege utskrivendeLege, int pasientId, int reit) {
        super(middel, utskrivendeLege, pasientId, reit);
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
