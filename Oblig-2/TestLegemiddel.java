public class TestLegemiddel {
    public static boolean testLegemiddelId(Legemiddel middel, int forventet) {
        return middel.hentId() == forventet;
    }

    public static boolean testLegemiddelNavn(Legemiddel middel, String forventet) {
        return middel.hentNavn() == forventet;
    }

    public static boolean testLegemiddelPris(Legemiddel middel, int forventet) {
        return middel.hentPris() == forventet;
    }

    public static boolean testLegemiddelVirkestoff(Legemiddel middel, double forventet) {
        return middel.hentVirkestoff() == forventet;
    }

    public static boolean testLegemiddelStyrke(Narkotisk middel, int forventet) {
        return middel.hentNarkotiskStyrke() == forventet;
    }

    public static boolean testLegemiddelVanedannende(Vanedannende middel, int forventet) {
        return middel.hentVanedannendeStyrke() == forventet;
    }

    public static void main(String[] args) {
        Legemiddel middel1 = new Vanlig("Hoste Tabletter", 50, 3);
        Narkotisk middel2 = new Narkotisk("Weed", 130, 2.2, 4);
        Vanedannende middel3 = new Vanedannende("Ritalin", 200, 5, 4);
        
        System.out.println("Vanlig Legemiddel Test");
        System.out.println("======================");
        System.out.println(String.format("tester Id: %s", testLegemiddelId(middel1, 0)));
        System.out.println(String.format("tester navn: %s", testLegemiddelNavn(middel1, "Hoste Tabletter")));
        System.out.println(String.format("tester pris: %s", testLegemiddelPris(middel1, 50)));
        System.out.println(String.format("tester virkestoff: %s", testLegemiddelVirkestoff(middel1, 3)));
        System.out.println(String.format("\n%s\n\n", middel1));

        System.out.println("Narkotisk Legemiddel Test");
        System.out.println("=========================");
        System.out.println(String.format("tester Id: %s", testLegemiddelId(middel2, 1)));
        System.out.println(String.format("tester navn: %s", testLegemiddelNavn(middel2, "Weed")));
        System.out.println(String.format("tester pris: %s", testLegemiddelPris(middel2, 130)));
        System.out.println(String.format("tester virkestoff: %s", testLegemiddelVirkestoff(middel2, 2.2)));
        System.out.println(String.format("tester styrke: %s", testLegemiddelStyrke(middel2, 4)));
        System.out.println(String.format("\n%s\n\n", middel2));

        System.out.println("Vanedannende Legemiddel Test");
        System.out.println("============================");
        System.out.println(String.format("tester Id: %s", testLegemiddelId(middel3, 2)));
        System.out.println(String.format("tester navn: %s", testLegemiddelNavn(middel3, "Ritalin")));
        System.out.println(String.format("tester pris: %s", testLegemiddelPris(middel3, 200)));
        System.out.println(String.format("tester virkestoff: %s", testLegemiddelVirkestoff(middel3, 2.2)));
        System.out.println(String.format("tester vanedannende: %s", testLegemiddelVanedannende(middel3, 4)));
        System.out.println(String.format("\n%s\n\n", middel3));
    }
}