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
        this.body.add(square);
        square.setColor(Color.green);
    }

    public Square getHead () {
        return this.body.get(this.body.size()-1);
    }

    public void removeTail () {
        Square tail = this.body.remove(0);
        tail.resetColor();
    }

    public void gameOver () {
        this.getHead().setColor(Color.red);
    }
}