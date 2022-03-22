abstract public class Legemiddel {
    // internal id counter
    static private int id_count;
    
    // instance variables set at construction
    private int Id;
    private String navn;
    private int pris;
    private double virkestoff;

    public Legemiddel(String navn, int pris, double virkestoff) {
        // add set current id count as instance id and
        // increment it by one for the next instance
        this.Id = id_count;
        id_count++;
        
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;
    }

    public int hentId() {
        return Id;
    }

    public String hentNavn() {
        return navn;
    }

    public int hentPris() {
        return pris;
    }

    public double hentVirkestoff() {
        return virkestoff;
    }

    public void settNyPris(int pris) {
        this.pris = pris;
    }

    public String toString() {
        return String.format("Id: %s\nNavn: %s\nPris: %s\nVirkestoff: %s", Id, navn, pris, virkestoff);
    }
}