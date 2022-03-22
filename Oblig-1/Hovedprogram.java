import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Hovedprogram {
    public static void main(String[] args) {
        Dataklynge saga = new Dataklynge();

        // deklarer line_num før try and catch fordi try bruker
        // lokalvariabler som frigjøres inne i try kode blocken
        // for å unngå memory problemns, defor deklareres int
        // variablen line_num før try for å kunne inkrementere
        // den og fortsatt beholde verdien den til catch.
        int line_num = 0;

        try {
            // lager en File instanse av det første argumentet gitt
            // når Hovedprogram ble kjørt
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);

            // looper så lenge Scanner instansen har en neste linje
            while (myReader.hasNextLine()) {
                // leser og splitter dataen på linjen inn i en array
                String line_data = myReader.nextLine();
                String[] split_data = line_data.split(" ");

                // lager en ny array til å holde int verdiene fra split_data
                int[] parsed_data = new int[3];

                // konverter hver verdi til en int verdi
                for (int i = 0; i < 3; i++) {
                    parsed_data[i] = Integer.valueOf(split_data[i]);
                }

                // lag noder ifølge fil instruksjonenen
                for (int i = 0; i < parsed_data[0]; i++) {
                    saga.settInn(new Node(parsed_data[1], parsed_data[2]));
                }

                // inkrementer linje nummer for å kunne peke til linjen
                // feilen oppsto på i lesingen av filen
                line_num++;
            }
            myReader.close();

        // catch exception som oppstår hvis ingen argumenter
        // er gitt når hovedprogrammet kjøres
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No file argument provided");
            System.exit(1);

        // catch exception som oppstår hvis filen i argumentet ikke finnes
        } catch (FileNotFoundException e) {
            System.out.println("Unable to locate file" + args[0]);
            System.exit(1);
        
        // catch exception som blir throwet i node klassen
        // hvis verdien gitt er ugyldig
        } catch (IllegalArgumentException e) {
            System.out.println(String.format("Error in %s at line: %s", args[0], line_num));
            System.out.println(e.getMessage());
            System.exit(1);
        }

        // lag en array av minne krav som skal skjekkes
        int[] minne_krav = {128, 512, 1024};

        // loop gjennom hvert minne krav og skjekk antall noder som godkjennes
        for (int krav: minne_krav) {
            System.out.println(String.format("Noder med minst %s GB: %s", krav, saga.noderMedNokMinne(krav)));
        }
        
        // print ut antall prossessorer og antall racks
        System.out.println();
        System.out.println(String.format("Antall prosessorer: %s", saga.antProsessorer()));
        System.out.println(String.format("Antall rack: %s", saga.antRacks()));
    }
}