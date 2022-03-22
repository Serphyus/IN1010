public class HviteResept extends Resept {
    public HviteResept(Legemiddel middel, Lege utskrivendeLege, int pasientId, int reit) {
        super(middel, utskrivendeLege, pasientId, reit);
    }

    @Override
    public String farge() {
        return "hvit";
    }

    @Override
    public int prisAaBetale() {
        return hentLegemiddel().hentPris();
    }
}
