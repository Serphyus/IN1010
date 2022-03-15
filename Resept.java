public abstract class Resept {
    // gjør alle instanse variablene protected for
    // å bare tillate at metoder brukes for å hente
    // dem og private på id_counter fordi den skal
    // bare has tilgang til her slik at ingen subklasser
    // kan endre på den

    protected Legemiddel legemiddel;
    protected Lege utskrivende_lege;
    protected Pasient pasient;
    protected int reit;

    protected int id;
    private static int id_counter = 1;

    // lager konstruktør med legemiddel, utskrivende_lege, pasientID og reit
    public Resept(Legemiddel legemiddel, Lege utskrivendLege, Pasient pasient, int reit){

        // impliserer hva parameterene er i den nåværende istansene 
        this.legemiddel = legemiddel;
        this.utskrivende_lege = utskrivendLege;
        this.pasient = pasient;
        this.reit = reit;

        // øker id med en for hver gang det blir laget et Resept objekt
        this.id = id_counter;
        id_counter++;

    }

    // returnerer IDen til Resepten
    public int hentId(){
        return this.id;
    }

    // returnerer legemiddelt til Resepten 
    public  Legemiddel hentLegemiddel(){
        return this.legemiddel;
    }

    // returnerer utskrivende_lege til Resepten 
    public Lege hentLege(){
        return this.utskrivende_lege;
    }

    // returnerer pasient til Resepten 
    public int hentPasientId(){
        return this.pasient.hentId();
    }

    // returnerer antall reit som er igjen til Resepten 
    public int hentReit(){
        return this.reit;
    }

    // metode som bruker reiten til en gang 
    // hvis det er ikke noe reit lenger returnerer den false
    // Og hvis den har noe igjen returnerer den true
    public boolean bruk(){
        if (this.reit <= 0){
            return false;
        } else {
            this.reit--;
            return true;
        }
    }

    //returnerer all av informasjon for Resepten i en rydig måte
    public String toString(){
        // legg til bedre toString med tanke på at pasient og legemiddel har egne tostrings
        return String.format("Legemiddel: %s\nId: %s\nPasient: %s\nReit: %s\nLege: %s", this.legemiddel, this.id, this.pasient, this.reit, this.utskrivende_lege);
    }
        
    // lager an abstract metode som heter farge for Resept objekter
    abstract public String farge();

    // lager an abstract metode som tar imot en pris
    abstract public int prisAaBetale(int pris);

}
