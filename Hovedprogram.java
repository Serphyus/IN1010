public class Hovedprogram {
    public static void main(String[] args) {
        Legesystem legesystem = new Legesystem();
        
        for (String arg: args) {
            legesystem.lesFraFil(arg);
        }

        System.out.println(legesystem);
    }
}