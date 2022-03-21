public class PResept extends HvitResept {
    public PResept(Legemiddel middel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(middel, utskrivendeLege, pasient, reit);
    }
    
    @Override
    public int prisAaBetale() {
        int ny_pris = this.hentLegemiddel().hentPris() - 108;

        if (ny_pris < 0) {
            ny_pris = 0;
        }

	    return ny_pris;
    }
}
