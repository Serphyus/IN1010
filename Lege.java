
//lager superklassen Lege
public class Lege {
    
    //innehoder kun en String variabel som heter lege
    String lege;

    //lager konstuktør for Lege med parametrene navn
    public Lege(String lege){

        // impliserer hva parameterene er i den nåværende istansene
        this.lege = lege;
    }

    //retunerer lege
    public String hentLege(String lege) {
        return lege;
    }
    
    ////returnerer all av informasjon for lege
    public String toString(){
        return "Lege: "+ lege;
    }

}
