import java.awt.Color;
import javax.swing.JPanel;


public class Square extends JPanel {
    public final int x;
    public final int y;

    public Square (int x, int y) {
        // lagre kordinat
        this.x = x;
        this.y = y;

        // reset farge
        this.resetColor();
    }

    public void resetColor () {
        // sett farge til grå ved reset
        this.setColor(Color.gray);
    }

    public void setColor (Color color) {
        // sett en ny bakgrunns farge til JPanel
        this.setBackground(color);
        this.repaint();
    }
}