
//lager superklassen Lege
public class Lege {
    
    //innehoder kun en String variabel som heter lege
    String navn;

    //lager konstuktør for Lege med parametrene navn
    public Lege(String lege){

        // impliserer hva parameterene er i den nåværende istansene
        this.navn = lege;
    }

    //retunerer lege
    public String hentNavn() {
        return this.navn;
    }
    
    ////returnerer all av informasjon for lege
    public String toString(){
        return String.format("Objekt av Lege:\nNavn: %s", navn);
    }
}