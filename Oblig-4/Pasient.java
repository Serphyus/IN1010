public class Pasient {
    private static int id_counter = 1;

    private int id;
    private String navn;
    private String foodselsdato;
    private Koe<Resept> resepter = new Koe<>();

    public Pasient(String navn, String foodselsdato) {
        this.navn = navn;
        this.foodselsdato = foodselsdato;

        this.id = id_counter;
        id_counter++;
    }

    public String hentNavn() {
        return this.navn;
    }

    public String hentFoodselsdato() {
        return this.foodselsdato;
    }

    public int hentId() {
        return this.id;
    }

    public Koe<Resept> hentResepter() {
        return this.resepter;
    }

    public void leggTilResept(Resept resept) {
        this.resepter.leggTil(resept);
    }

    public String toString() {
        return String.format("Navn: %s\nId: %s\nFoodselsdato: %s\nResepter: %s",
            this.navn, this.id, this.foodselsdato, this.resepter.stoerrelse()
        );
    }
}