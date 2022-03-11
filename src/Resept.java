
//lager super klassen Resept som skal være abstrakt (du kan ikke lage objekter av denne klassen)
public abstract class Resept {
    
    //lager variabelene som trengs for Resept klassen
    Legemiddel legemiddel;
    Lege utskrivendeLege;
    int pasientId;
    int reit;

    //lager id med en statisk id teller
    int id;
    static int id_counter = 1;

    //lager konstruktør med legemiddel, utskrivendeLege, pasientID og reit
    public Resept(Legemiddel legemiddel, Lege utskrivendLege, int pasientId, int reit){

        // impliserer hva parameterene er i den nåværende istansene 
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendLege;
        this.pasientId = pasientId;
        this.reit = reit;

        //øker id med en for hver gang det blir laget et Resept objekt
        id = id_counter;
        id_counter ++;

    }

    //returnerer IDen til Resepten
    public int hentId(){
        return id;
    }

    //returnerer legemiddelt til Resepten 
    public  Legemiddel hentLegemiddel(){
        return legemiddel;
    }

    //returnerer utskrivendeLege til Resepten 
    public Lege hentLege(){
        return utskrivendeLege;
    }

    //returnerer pasientID til Resepten 
    public int hentPasientId(){
        return pasientId;
    }

    //returnerer antall reit som er igjen til Resepten 
    public int hentReit(){
        return reit;
    }

    //metode som bruker reiten til en gang 
    //hvis det er ikke noe reit lenger returnerer den false
    //Og hvis den har noe igjen returnerer den true
    public boolean bruk(){
        
        if (reit <= 0){
            return false;
        } else {
            reit--;
            return true;
        }
    }

    //returnerer all av informasjon for Resepten i en rydig måte
    public String toString(){
        return "Legemiddel: "+legemiddel +"\n"+ "ID: "+ id+"\n"+ "Pasientid: "+ pasientId+"\n"+ "Reit: "+reit+
        "\n"+"Utskrivendelege: "+ utskrivendeLege;
    }

        
    //lager an abstract metode som heter farge for Resept objekter
    abstract public String farge();

    //lager an abstract metode som tar imot en pris
    abstract public int prisAaBetale(int pris);

}
