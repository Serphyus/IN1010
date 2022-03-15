public class Vanedannende extends Legemiddel{
    private int styrke;

    // Vanedannende arver alle parameterne fra superklassen
    // Legemiddel men har også en til parameter styrke
    public Vanedannende(String navn, int pris, double virkestoff, int styrke){
        super(navn, pris, virkestoff);
        this.styrke = styrke;
    }

    //metodet som retunerer styrken til den vanedannende legemiddelet
    public int hentVanedannendeStyrke(){
        return styrke;
    }

    //overskriver toString metoden med en ny en med at den returner også styrken sammen alt annet
    @Override
    public String toString() {
        return super.toString() + String.format("\nStyrke: %s", this.hentVanedannendeStyrke());
    }
}