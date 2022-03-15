public class Pasient {
    private static int id_teller = 0;

    public int id;
    public String navn;
    public String foodselsdato;
    public Koe<Resept> resepter = new Koe<>();

    public Pasient(String navn, String foodselsdato) {
        this.navn = navn;
        this.foodselsdato = foodselsdato;

        this.id = id_teller;
        id_teller++;
    }

    public int hentId() {
        return this.id;
    }

    public void leggTilResept(Resept resept) {
        this.resepter.leggTil(resept);
    }

    public String toString() {
        return String.format("Navn: %s\nId: %s\nFoodselsdato: %s\nAntall: %s\n", this.navn, this.id, this.foodselsdato, this.resepter.stoerrelse());
    }
}