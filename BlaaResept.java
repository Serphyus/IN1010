
//lager klassen Blaaresept som arver fra Resept
public class BlaaResept extends Resept{

    //lager konsrtuktør for Blaaresepten som arver alle parameterene fra Resept
    public BlaaResept(Legemiddel legemiddel, Lege utskrivendLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendLege, pasient, reit);
    }

    //overskriver farge metoden fra Resept med addere return "blaa"
    @Override
    public String farge() {
        return "blaa";
    }

    //overskriver prisAabetale med å returnere 25% av pris
    @Override
    public int prisAaBetale(int pris) {
        return (int) (pris *0.25);
    }
    
}