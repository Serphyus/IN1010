public class Narkotisk extends Legemiddel {

    int styrke;
    

    //Narkotisk legemiddel har et heltall som sier hvor sterkt narkotisk det er
    //men har også en til parameter styrke

    public Narkotisk(String navn, int pris, double virkestoff, int styrke){
        super(navn, pris, virkestoff);
        this.styrke = styrke;
    }

    //metodet som retunerer styrken til den narkotiske legemiddelet
    public int hentNarkotiskStyrke(){
        return styrke;
    }

    @Override
    public String toString() {
    return super.toString() + "\n"+"Styrke: "+styrke;
}
}
