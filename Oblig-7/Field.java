import java.util.ArrayList;
import java.awt.GridLayout;
import javax.swing.JPanel;


public class Field extends JPanel {
    public final Square[][] map;
    public final int height;
    public final int width;

    public Field (int height, int width) {
        // sett høyde og bredde til feltet
        this.height = height;
        this.width = width;

        // lag en 2dimensjonal array for feltet
        this.map = new Square[this.height][this.width];

        // lag et grid layout til den extendede JPanel klassen
        this.setLayout(new GridLayout(this.height, this.width));

        // lag alle square instansene for hvert kordinat
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                // lag en ny square til feltet
                Square square = new Square(x, y);
                
                // lagre squaren i en 2 dimensjonal array
                this.map[y][x] = square;

                // legg squaren til grid layoutet
                this.add(square);
            }
        }
    }

    // hent square fra feltet
    public Square getSquare(int x, int y) {
        return this.map[y][x];
    }

    // hent alle squares som en 1-dimensjonal array
    public ArrayList<Square> getAll() {
        // hent alle squares og samle den i en arraylist
        ArrayList<Square> squares = new ArrayList<>();
        for (Square[] row : this.map) {
            for (Square square : row) {
                squares.add(square);
            }
        }
        return squares;
    }
}