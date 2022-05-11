import java.util.ArrayList;
import java.awt.Color;


public class Snake {
    private ArrayList<Square> body;

    public Snake (Square start) {
        // lager en body til slangen
        this.body = new ArrayList<>();
        
        // legger til sstarts square
        this.addSquare(start);
    }

    public boolean collideWith (Square square) {
        // skjekker om square er del av slange
        return this.body.contains(square);
    }

    public void addSquare (Square square) {
        // legg til en ny square og gjør den grønn
        this.body.add(square);
        square.setColor(Color.green);
    }

    public Square getHead () {
        // hent hodet til slangen
        return this.body.get(this.body.size()-1);
    }

    public void removeTail () {
        // fjern det siste feltet i slangen
        // og reset fargen til feltet
        Square tail = this.body.remove(0);
        tail.resetColor();
    }

    public void gameOver () {
        // sett en rød fage på hodet til slangen
        // når spilleren har kræsjet inn i noe
        this.getHead().setColor(Color.red);
    }
}