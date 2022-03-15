public class Vanlig extends Legemiddel {
    // Arver all Legemiddel funksjonalitet uten endring for
    // å kunne lage objekter siden Legemiddel er abstrakt

    public Vanlig(String navn, int pris, float virkestoff){
        super(navn, pris, virkestoff);
    }
}
