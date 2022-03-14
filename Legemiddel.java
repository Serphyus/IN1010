
//Lager superklassen Legemiddel 
public abstract class Legemiddel {
    
    //med variablene navn, pris og virkestoff
    String navn; 
    int pris;
    double virkestoff; 

    //lager id med en statisk id teller
    int id;
    static int id_counter = 1;
    

    //lager konstuktør for legemiddler med parametrene navn, pris og virkestoff
    public Legemiddel(String navn, int pris, double virkestoff){

        // impliserer hva parameterene er i den nåværende istansene 
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;

        //øker id med en for hver gang det blir laget et legemiddel objekt
        id = id_counter;
        id_counter ++;
    }

    //returnerer IDen til legemiddelet
    public int hentId(){
        return id;
    }

    //returnerer navnet til legemiddelet
    public String hentNavn(){
        return navn;
    }

    //returnerer prisen til legemiddelet
    public int hentPris(){
        return pris;
    }

    //returnerer mengden av virkestoffet til legemiddelet
    public double hentVirkestoff(){
        return virkestoff;
    }
    //returnerer Den nye priset til legemiddelet
    public int settNyPris(int nyPris){
        return pris = nyPris;
    }
    
    //returnerer all av informasjon for legemiddelet i en rydig måte
    public String toString(){
        return "Navn: "+navn +"\n"+ "ID: "+ id+"\n"+ "Pris: "+ pris+"\n"+ "Virkestoff: "+virkestoff;
    }
}
