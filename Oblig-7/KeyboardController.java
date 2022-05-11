import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyboardController extends KeyAdapter {
    private Game game;
    
    public KeyboardController (Game game) {
        this.game = game;
    }
    
    public void keyPressed (KeyEvent event) {
        // skjekk key koden til kanppen som ble
        // trykket og bruk switch cases for å
        // skjekke om den er en av piltastene
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.game.setDirection(Game.LEFT);
                break;
            
            case KeyEvent.VK_UP:
                this.game.setDirection(Game.UP);
                break;
            
            case KeyEvent.VK_RIGHT:
                this.game.setDirection(Game.RIGHT);
                break;

            case KeyEvent.VK_DOWN:
                this.game.setDirection(Game.DOWN);
                break;

            default:
                break;
        }
    }
}