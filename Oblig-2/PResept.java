public class PResept extends HviteResept {
    public PResept(Legemiddel middel, Lege utskrivendeLege, int pasientId, int reit) {
        super(middel, utskrivendeLege, pasientId, reit);
    }

    @Override
    public int prisAaBetale() {
        int ny_pris = hentLegemiddel().hentPris() - 108;

        if (ny_pris < 0) {
            ny_pris = 0;
        }

	    return ny_pris;
    }
}
