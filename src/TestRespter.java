import java.util.ArrayList;

public class TestRespter {
    public static void main(String[] args) {
        
        Vanlig legemiddel = new Vanlig("paracet", 100, 2);
        
        Lege lege = new Lege(null);


        BlaaResept blaaResept = new BlaaResept(legemiddel, lege, 1, 2);
        HvitResept hvitResept = new HvitResept(legemiddel, lege, 2, 1);
        MilResept mili = new MilResept(legemiddel, lege, 3);
        PResept pResept = new PResept(legemiddel, lege, 4, 1);

        ArrayList<Resept> liste = new ArrayList<>();
        liste.add(blaaResept);
        liste.add(hvitResept);
        liste.add(mili);
        liste.add(pResept);

        for( Resept resp: liste){
        

        System.out.println("pris å betale: "+resp.prisAaBetale(1000));
        System.out.println("Farge: "+resp.farge());
        System.out.println("Antall Reit: "+resp.hentReit());
        System.out.println(resp.bruk());System.out.println("Antall Reit: "+resp.hentReit());
        System.out.println(resp.bruk());System.out.println("Antall Reit: "+resp.hentReit());
        System.out.println(resp.bruk());System.out.println("Antall Reit: "+resp.hentReit());
        System.out.println(resp.bruk());System.out.println("Antall Reit: "+resp.hentReit());
        System.out.println(resp.bruk());System.out.println("Antall Reit: "+resp.hentReit());
        System.out.println();
        System.out.println("Pasient ID: "+resp.hentPasientId());
        System.out.println("Lege: "+resp.hentLege());
        System.out.println("Legemiddel: "+resp.hentLegemiddel());
        System.out.println("ID: "+resp.hentId());
        System.out.println();

        }
        
    }
}
