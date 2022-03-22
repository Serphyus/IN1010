public class HvitResept extends Resept {
    //lager klassen Hvitreseptresept som arver fra Resept
    public HvitResept(Legemiddel middel, Lege utskrivendeLege, Pasient pasient, int reit) {
        //lager konsrtuktør for Hvitresepten som arver alle parameterene fra Resept
        super(middel, utskrivendeLege, pasient, reit);
    }

    @Override
    public String farge() {
        return "hvit";
    }

    @Override
    public int prisAaBetale() {
        return this.hentLegemiddel().hentPris();
    }
}
