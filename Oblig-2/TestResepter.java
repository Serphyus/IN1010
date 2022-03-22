public class TestResepter {
    public static boolean testReseptId(Resept resept, int forventet) {
        return resept.hentId() == forventet;
    }

    public static boolean testReseptMiddel(Resept resept, Legemiddel forventet) {
        return resept.hentLegemiddel() == forventet;
    }

    public static boolean testReseptPasientId(Resept resept, int forventet) {
        return resept.hentPasientId() == forventet;
    }

    public static boolean testReseptReit(Resept resept, int forventet) {
        return resept.hentReit() == forventet;
    }

    public static boolean testReseptFarge(Resept resept, String forventet) {
        return resept.farge() == forventet;
    }

    public static boolean testReseptPris(Resept resept, int forventet) {
        return resept.prisAaBetale() == forventet;
    }

    public static void main(String[] args) {
        Vanlig middel1 = new Vanlig("ritalin", 220, 5.2);
        Lege lege1 = new Lege("John Karl");
        MilResept resept1 = new MilResept(middel1, lege1, 43253);

        Vanlig middel2 = new Vanlig("pencilin", 200, 4.3);
        Lege lege2 = new Lege("Peter Almon");
        PResept resept2 = new PResept(middel2, lege2, 76521, 2);

        Vanlig middel3 = new Vanlig("pencilin", 500, 4.3);
        Lege lege3 = new Lege("Karl Johan");
        BlaaResept resept3 = new BlaaResept(middel3, lege3, 23546, 4);
        
        System.out.println("MilResept Resept Test");
        System.out.println("=====================");
        System.out.println(String.format("tester Id: %s", testReseptId(resept1, 0)));
        System.out.println(String.format("tester middel: %s", testReseptMiddel(resept1, middel1)));
        System.out.println(String.format("tester paisentId: %s", testReseptPasientId(resept1, 43253)));
        System.out.println(String.format("tester reit: %s", testReseptReit(resept1, 3)));
        System.out.println(String.format("tester farge: %s", testReseptFarge(resept1, "hvit")));
        System.out.println(String.format("tester pris: %s", testReseptPris(resept1, 0)));
        
        System.out.println(String.format("\n%s\n\n", resept1));

        System.out.println("PResept Resept Test");
        System.out.println("===================");
        System.out.println(String.format("tester Id: %s", testReseptId(resept2, 1)));
        System.out.println(String.format("tester middel: %s", testReseptMiddel(resept2, middel2)));
        System.out.println(String.format("tester paisentId: %s", testReseptPasientId(resept2, 76521)));
        System.out.println(String.format("tester reit: %s", testReseptReit(resept2, 2)));
        System.out.println(String.format("tester farge: %s", testReseptFarge(resept2, "hvit")));
        System.out.println(String.format("tester pris: %s", testReseptPris(resept2, 92)));
        System.out.println(String.format("\n%s\n\n", resept2));

        System.out.println("BlaaResept Resept Test");
        System.out.println("======================");
        System.out.println(String.format("tester Id: %s", testReseptId(resept3, 2)));
        System.out.println(String.format("tester middel: %s", testReseptMiddel(resept3, middel3)));
        System.out.println(String.format("tester paisentId: %s", testReseptPasientId(resept3, 23546)));
        System.out.println(String.format("tester reit: %s", testReseptReit(resept3, 4)));
        System.out.println(String.format("tester farge: %s", testReseptFarge(resept3, "blaa")));
        System.out.println(String.format("tester pris: %s", testReseptPris(resept3, 125)));
        System.out.println(String.format("\n%s\n\n", resept3));
    }
}