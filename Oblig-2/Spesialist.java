public class Spesialist extends Lege implements Godkjenningsfritak {
    private String kontrollId;

    public Spesialist(String navn, String kontrollId) {
        super(navn);
        this.kontrollId = kontrollId;
    }

    @Override
    public String hentKontrollId() {
        return kontrollId;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\nkontroll Id: %s", hentKontrollId());
    }
}
