public class TestLegemiddel {
    public static void main(String[] args){ 

        Narkotisk narkotisk = new Narkotisk("melk", 10, 1, 100);
        Vanedannende vanedannende = new Vanedannende("vann", 0, 0, 0);
        Vanlig vanlig = new Vanlig("snø", 32, 42);

        System.out.println("Narkotisk Styrke: "+narkotisk.hentNarkotiskStyrke());
        System.out.println("Navn: "+narkotisk.hentNavn());
        System.out.println("Pris: "+narkotisk.hentPris());
        System.out.println("Virkestoff: "+narkotisk.hentVirkestoff());
        System.out.println("ID: "+narkotisk.hentid());
        System.out.println("Pris: "+narkotisk.settNyPris(100000000));
        System.out.println();

        System.out.println("Vanedannede Styrke: "+vanedannende.hentVanedannendekStyrke());
        System.out.println("Navn: "+vanedannende.hentNavn());
        System.out.println("Pris: "+vanedannende.hentPris());
        System.out.println("Virkestoff: "+vanedannende.hentVirkestoff());
        System.out.println("ID: "+vanedannende.hentid());
        System.out.println("Pris: "+vanedannende.settNyPris(100000000));
        System.out.println();

        
        System.out.println("Navn: "+vanlig.hentNavn());
        System.out.println("Pris: "+vanlig.hentPris());
        System.out.println("Virkestoff: "+vanlig.hentVirkestoff());
        System.out.println("ID: "+vanlig.hentid());
        System.out.println("Pris: "+vanlig.settNyPris(100000000));
    }

}
