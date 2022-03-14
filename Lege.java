
//lager superklassen Lege
public class Lege implements Comparable<Lege>{
    
    //innehoder kun en String variabel som heter lege
    private String navn;

    //lager konstuktør for Lege med parametrene navn
    public Lege(String lege){

        // impliserer hva parameterene er i den nåværende istansene
        this.navn = lege;
    }

    //retunerer lege
    public String hentNavn() {
        return this.navn;
    }
    
    public int compareTo(Lege x) {
        return this.navn.compareTo(x.hentNavn());
    }

    ////returnerer all av informasjon for lege
    public String toString(){
        return String.format("Objekt av Lege:\nNavn: %s", navn);
    }
}