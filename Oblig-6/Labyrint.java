import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Labyrint {
    private Rute[][] field;
    private int height;
    private int width;

    public Labyrint (String filename) {
        try {
            // lag en scanner for filename parameteret
            Scanner file_reader = new Scanner(new File(filename));

            // hent den første linjen og splitt den på mellomrom
            String meta_line = file_reader.nextLine();
            String[] labyrint_size = meta_line.split(" ");

            // hent og konverted høyde og bredde verdiene
            this.height = Integer.parseInt(labyrint_size[0]);
            this.width = Integer.parseInt(labyrint_size[1]);

            // lag en 2-dimensjonal array basert på høyde og
            // bredde verdiene fra den første linjen
            this.field = new Rute[this.height][this.width];

            // definer rad og kolonne
            int y = 0;
            int x = 0;
    
            while (file_reader.hasNextLine()) {
                String line = file_reader.nextLine();

                // reset kolonne 
                x = 0;
                
                for (char ch : line.toCharArray()) {
                    // sett rute til null
                    Rute rute = null;

                    // skjekk om ruten er en vegg
                    if (ch == '#') {
                        rute = new SortRute(y, x, this);
                    }

                    // skjekk om ruten er en åpen
                    else if (ch == '.') {

                        // hvis ruten befinner seg på en av endene i labyrinten
                        // så er den en Aapning, ellers er den bare en hvit rute
                        if ((x == 0 || x == width-1) || (y == 0 || y == height-1)) {
                            rute = new Aapning(y, x, this);
                        }
                        else {
                            rute = new HvitRute(y, x, this);
                        }
                    }

                    // sett ruten inn i labyrint fieldet
                    this.field[y][x] = rute;
                    
                    // gå til neste kolonne
                    x++;
                }
                
                // gå til neste rad 
                y++;
            }
        
            // close file scanner
            file_reader.close();
            
        }

        catch (FileNotFoundException e) {
            System.err.println(String.format("unable to locate file: %s", filename));
            System.exit(1);
        }
        
        // sett alle naboer etter å ha funnet alle rutene, slik kan
        // vi lettere loope gjennom hele labyrint feltet.
        this.settNaboer();
    }

    public void settNaboer () {
        // ####################################
        // ####################################
        // ####################################
        // ####################################
        for (int y = 1; y < this.height-1; y++) {
            for (int x = 1; x < this.width-1; x++) {
                Rute rute = this.field[y][x];
                
                Rute n_rute = this.field[y-1][x];
                Rute v_rute = this.field[y][x-1];
                
                rute.nord = n_rute;
                rute.vest = v_rute;
                
                n_rute.syd = rute;
                v_rute.oest = rute;
            }
        }
    }

    public ArrayList<Tuppel> finnUtveiFra (int y, int x) {
        this.reset_field();

        if ((x > 0 || x < this.width) || (y > 0 || y < this.height)) {
            ArrayList<Tuppel> path = new ArrayList<>();
            
            Rute start = this.field[y][x];
            start.finn(null, path);
            
            if (path.size() > 0) {
                Tuppel end = path.get(path.size()-1);
                if (end.finish) {
                    return path;
                }
            }
        }
        return null;
    }

    public void reset_field () {
        for (Rute[] rad : this.field) {
            for (Rute rute : rad) {
                rute.reset();
            }
        }
    }

    public String toString () {
        String output = "";
        for (Rute[] rad : this.field) {
            for (Rute rute : rad) {
                output += rute;
            }
            output += "\n";
        }
        return output;
    }
}