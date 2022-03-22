abstract public class Legemiddel {
    static private int id_counter = 1;
    
    private int Id;
    private String navn;
    private int pris;
    private double virkestoff;

    public Legemiddel(String navn, int pris, double virkestoff) {
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;

        this.Id = id_counter;
        id_counter++;
    }

    public int hentId() {
        return this.Id;
    }

    public String hentNavn() {
        return this.navn;
    }

    public int hentPris() {
        return this.pris;
    }

    public double hentVirkestoff() {
        return this.virkestoff;
    }

    public void settNyPris(int ny_pris) {
        this.pris = ny_pris;
    }

    public String toString() {
        return String.format(
            "Id: %s\nNavn: %s\nPris: %s\nVirkestoff: %s",
            this.Id, this.navn, this.pris, this.virkestoff
        );
    }
}