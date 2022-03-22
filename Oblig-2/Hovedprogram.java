public class Hovedprogram {
    public static void main(String[] args) {
        Vanlig middel1 = new Vanlig("Hoste Tabletter", 50, 3);
        Lege lege1 = new Lege("John Karl");
        MilResept resept1 = new MilResept(middel1, lege1, 43253);

        Narkotisk middel2 = new Narkotisk("Weed", 130, 2.2, 4);
        Lege lege2 = new Spesialist("Peter Almon", "sxh4vn");
        PResept resept2 = new PResept(middel2, lege2, 76521, 2);

        Vanedannende middel3 = new Vanedannende("Ritalin", 200, 5, 4);
        Lege lege3 = new Spesialist("Karl Johan", "f73msb");
        BlaaResept resept3 = new BlaaResept(middel3, lege3, 23546, 4);

        System.out.println("Vanlig Legemiddel med MilResept");
        System.out.println("===============================");
        System.out.println(middel1+"\n");
        System.out.println(lege1+"\n");
        System.out.println(resept1+"\n\n");
        
        System.out.println("Narkotisk Legemiddel med PResept");
        System.out.println("================================");
        System.out.println(middel2+"\n");
        System.out.println(lege2+"\n");
        System.out.println(resept2+"\n\n");
        
        System.out.println("Vabedabbebde Legemiddel med BlaaResept");
        System.out.println("======================================");
        System.out.println(middel3+"\n");
        System.out.println(lege3+"\n");
        System.out.println(resept3+"\n\n");
    }
}