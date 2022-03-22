public class Lege {
    private String navn;

    public Lege(String navn) {
        this.navn = navn;
    }

    public String hentNavn() {
        return navn;
    }

    public String toString() {
        return String.format("navn: %s", hentNavn());
    }
}
