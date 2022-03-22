abstract public class Resept {
    private static int id_count = 0;
    
    private int Id;
    private Legemiddel middel;
    private Lege lege;
    private int pasientId;
    private int reit;

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
        // add set current id count as instance id and
        // increment it by one for the next instance
        this.Id = id_count;
        id_count++;

        this.middel = legemiddel;
        this.lege = utskrivendeLege;
        this.pasientId = pasientId;
        this.reit = reit;
    }

    public int hentId() {
        return Id;
    }
    
    public Legemiddel hentLegemiddel() {
        return middel;
    }

    public Lege hentLege() {
        return lege;
    }

    public int hentPasientId() {
        return pasientId;
    }

    public int hentReit() {
        return reit;
    }

    public boolean bruk() {
        if (reit > 0) {
            reit--;
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return String.format("Id: %s\nlegemiddel: %s\nlege: %s\npasientId: %s\nreit: %s\nfarge: %s\nlegemiddel pris: %s\npris aa betale: %s", Id, middel.hentNavn(), lege.hentNavn(), pasientId, reit, farge(), middel.hentPris(), prisAaBetale());
    }

    abstract public String farge();
    abstract public int prisAaBetale();
}
