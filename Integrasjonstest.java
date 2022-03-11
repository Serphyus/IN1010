public class Integrasjonstest{
    public static void main(String[] args) {

        Lege drlove = new Lege("Dr. Love");
        Lege drhate = new Spesialist("Dr. Spesialist"," 12345"); 

        Legemiddel narkotisk = new Narkotisk("Some drug", 1000,  21.1, 2);
        Legemiddel vanedanne = new Vanedannende("Noe avhengig", 22, 0.2, 3);
        Legemiddel vanlig = new Vanlig("Paracet", 50, 25);
        
        Resept blaa = new BlaaResept(vanlig, drlove, 1, 25);
        Resept hvit = new HvitResept(vanlig, drhate,  1, 1);
        Resept pr = new PResept(vanedanne, drlove, 2, 100);
        Resept milli = new MilResept(narkotisk, drhate, 3);

       System.out.println(); 
       System.out.println(drhate.toString());
       System.out.println();

       System.out.println(drlove.toString());
       System.out.println();

       System.out.println(narkotisk.toString());
       System.out.println(vanlig.toString());
       System.out.println(vanedanne.toString());
       System.out.println();
       System.out.println(blaa.toString());
       System.out.println(hvit.toString());
       System.out.println(milli.toString());
       System.out.println(pr.toString());


    }
}