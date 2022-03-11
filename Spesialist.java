
//lager subklassen Spesialist som arver fra Lege
// og implimenetere fra en interfacen Godkjenningsfritak
public class Spesialist extends Lege implements Godkjenningsfritak {

    //innehoder  String variabel som heter kontrollid
    String kontrollid;

    //lager konstuktør for Spesialist med parametrene lege som den arver fra lege + kontrollID
    public Spesialist(String lege, String kontrollid) {
        super(lege);
        this.kontrollid = kontrollid;
    }
    //overskriver hentKontrollID med å returnere kontrollID
    @Override
    public String hentKontrollID() {
        return kontrollid;
    }
    
    //returnerer all av informasjon for spesialist
    @Override
    public String toString(){
        return super.toString() + String.format("\nKontrollId: %s", kontrollid);
    }
    
    
}
