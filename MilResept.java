
//lager klassen MilResept som arver fra Hvitresept
public class MilResept extends HvitResept{
    
    //lager konsrtuktør for MilResept som arver alle parameterene fra Hvitresept
    // hoved forskjellen er at reit i en MilResept er alltid 3 når den blir laget 
    public MilResept(Legemiddel legemiddel, Lege utskrivendLege, Pasient pasient) {
        super(legemiddel, utskrivendLege, pasient, 3);
    }

    //overskriver prisAabetale med å returnere 0 som pris
    @Override
    public int prisAaBetale(int pris) {
        return 0;
    }
}
