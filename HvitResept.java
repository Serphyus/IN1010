
//lager klassen Hvitreseptresept som arver fra Resept
public class HvitResept extends Resept {

    //lager konsrtuktør for Hvitresepten som arver alle parameterene fra Resept
    public HvitResept(Legemiddel legemiddel, Lege utskrivendLege, int pasientId, int reit) {
        super(legemiddel, utskrivendLege, pasientId, reit);
    }

    //overskriver farge metoden fra Resept med addere return "Hvit"
    @Override
    public String farge() {
        return "hvit";
    }

    //overskriver prisAabetale med å returnere pris
    @Override
    public int prisAaBetale(int pris) {
        return pris;
    }
    
}
