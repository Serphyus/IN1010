import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Window extends JFrame {
    public Window (Field field) {
        // legg til field i vinduet
        this.add(field);
    }

    public void displayScore (int score) {        
        // lag en score melding
        String msg = String.format("Score: %s", score);

        // lag et panel på midten av skjermen
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // lag en label med score meldinen
        JLabel label = new JLabel(msg);
        label.setFont(new Font("Verdana", 1, 70));

        // legg label inn i panel og panel i vindu
        panel.add(label);
        this.add(panel);
        
        // oppdater skjermens innhold
        this.validate();
    }

    public void startListener (Game game) {
        // lag en keyboard listener til dette vinduet.
        // dette funker ikke med flere paneler på grunn
        // av hvordan java swing fokuserer på paneler.
        this.addKeyListener(new Controller(game));
    }
}