//lager klassen PResept som arver fra Hvitresept
public class PResept extends HvitResept {

    //lager konsrtuktør for PResept som arver alle parameterene fra Hvitresept
    public PResept(Legemiddel legemiddel, Lege utskrivendLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendLege, pasient, reit);
        
    }

    //overskriver prisAabetale med å returnere 0 som pris hvis prisen er under eller lik 108
    //ellers returnerer den  prisen som er nedsatt med 108
    @Override
    public int prisAaBetale(int pris) {
        if (pris < 108){
            return 0;
        } else{
            return pris-108;
        }
    }
}
