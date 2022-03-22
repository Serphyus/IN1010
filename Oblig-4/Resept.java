abstract public class Resept {
    private static int id_counter = 1;
    
    private int Id;
    private Legemiddel middel;
    private Lege lege;
    private Pasient pasient;
    private int reit;

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        this.middel = legemiddel;
        this.lege = utskrivendeLege;
        this.pasient = pasient;
        this.reit = reit;

        this.Id = id_counter;
        id_counter++;
    }

    public int hentId() {
        return this.Id;
    }
    
    public Legemiddel hentLegemiddel() {
        return this.middel;
    }

    public Lege hentLege() {
        return this.lege;
    }

    public Pasient hentPasient() {
        return this.pasient;
    }

    public int hentReit() {
        return this.reit;
    }

    public boolean bruk() {
        if (this.reit > 0) {
            this.reit--;
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return String.format("Id: %s\nLegemiddel: %s\nLege: %s\nPasient: %s\nReit: %s\nFarge: %s\nLegemiddel pris: %s\nPris aa betale: %s",
            this.Id, this.middel.hentNavn(), this.lege.hentNavn(), this.pasient, this.reit, farge(), this.middel.hentPris(), this.prisAaBetale()
        );
    }

    abstract public String farge();
    abstract public int prisAaBetale();
}
