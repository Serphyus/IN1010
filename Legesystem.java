import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Legesystem{
    Prioritetskoe<Lege> leger;
    Koe<Pasient> pasienter;
    Koe<Legemiddel> legemiddler;

    String type_gen = null;

    public void lesFraFil(String filename){
        try {
            File data_fil = new File(filename);
            Scanner fil_leser = new Scanner(data_fil);

            while (fil_leser.hasNextLine()) {
                String line = fil_leser.nextLine();

                if (line.strip() == "") {
                    continue;
                }

                if (line.charAt(0) == '#') {
                    this.type_gen = line.split(" ")[1];
                    continue;
                }

                String[] args = line.split(",");

                try {
                    switch(this.type_gen) {
                        case "Pasienter":
                            this.pasienter.leggTil(new Pasient(args[0], args[1]));
                            System.out.println();

                        case "Legemidler":
                            String middel_type = args[1];
                
                            if (middel_type == "vanlig") {
                                // args[2] = int
                                // args[3] = float
                                Vanlig middel = new Vanlig(args[0], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                                this.legemiddler.leggTil(middel);
                            }
                            else if (middel_type == "narkotisk") {
                                // args[2] = int
                                // args[3] = float
                                Narkotisk middel = new Narkotisk(args[0],  Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                                this.legemiddler.leggTil(middel);
                            }
                            else if (middel_type == "vanedannende") {
                                // args[2] = int
                                // args[3] = float
                                Vanedannende middel = new Vanedannende(args[0], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                                this.legemiddler.leggTil(middel);
                            }

                        case "Leger":
                            if (args[1] == "0") {
                                this.leger.leggTil(new Lege(args[0]));
                            } else {
                                this.leger.leggTil(new Spesialist(args[0], args[1]));
                            }

                        case "Resepter":
                            String resept_type = args[3];

                            String lege_navn = args[1];
                            
                            Lenkeliste<Lege>.Node lege_node = this.leger.start;
                            while(lege_node.data.hentNavn() != lege_navn) {
                                if (lege_node.neste == null) {
                                    System.out.println(String.format("No lege found with name: %s", lege_navn));
                                    System.exit(1);
                                }
                                lege_node = lege_node.neste;

                            }
                            Lege lege = lege_node.data;
                            
                            
                            int middel_id = Integer.parseInt(args[0]);
                            Lenkeliste<Legemiddel>.Node middel_node = this.legemiddler.start;

                            while (middel_node.data.hentId() != middel_id) {
                                if (middel_node.neste == null) {
                                    System.out.println(String.format("No middel found with id: %s", middel_id));
                                    System.exit(1);
                                }
                                middel_node = middel_node.neste;
                            }
                            Legemiddel middel = middel_node.data;


                            int pasient_id = Integer.parseInt(args[2]);
                            Lenkeliste<Pasient>.Node pasient_node = this.pasienter.start;
                            
                            while (pasient_node.data.hentId() != pasient_id) {
                                if (pasient_node.neste == null) {
                                    System.out.println(String.format("No lege found with name: %s", pasient_id));
                                    System.exit(1);
                                }
                                pasient_node = pasient_node.neste;
                            }

                            Pasient pasient = pasient_node.data;

                            int reit;
                            if (resept_type != "militaer") {
                                reit = Integer.parseInt(args[4]);
                            }
                            else {
                                // militær node med reit error
                                reit = 0;
                                System.out.println("militer no reit");
                                System.exit(1);
                            }

                            try {
                                if (resept_type == "hvit") {
                                    lege.skrivHvitResept(middel, pasient, reit);
                                }
                                else if (resept_type == "blaa"){
                                    lege.skrivBlaaResept(middel, pasient, reit);
                                }
                                else if (resept_type == "militaer") {
                                    lege.skrivMilResept(middel, pasient);
                                }
                                else if (resept_type == "p") {
                                    lege.skrivPResept(middel, pasient, reit);
                                }                            
                            }

                            catch (UlovligUtskrift e) {
                                System.out.println(e);
                                System.exit(1);
                            }

                        default:
                            System.out.println("No generator type selected");
                            System.exit(1);
                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }

            fil_leser.close();
        }

        catch (FileNotFoundException e) {
            System.out.println(String.format("Unable to locate file: %s", filename));
            System.exit(1);
        }
    }
}