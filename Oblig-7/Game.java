import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JFrame;


public class Game {
    // spill komponenter
    private Window window;
    private Field field;
    private Snake snake;
    
    // spill tilstand
    private int score = 0;
    private double speed = 2.5;
    private boolean running = false;
    private int snake_direction = RIGHT;
    private ArrayList<Square> all_food;
    
    // retnings verdier slik at det er lettere å si hvor slangen skal
    public static final int LEFT  = 1;
    public static final int UP    = 2;
    public static final int RIGHT = 3;
    public static final int DOWN  = 4;

    public Game (int height, int width) {
        // lag gui komponenter
        this.field = new Field(height, width);
        this.window = new Window(this.field);
        
        this.window.setTitle("Snake Game");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setVisible(true);
        this.window.setSize(600, 600);
    }
    
    public void start () {
        this.window.startListener(this);
        
        // slangen starter i midten av feltet
        int start_x = (int) (this.field.width / 2);
        int start_y = (int) (this.field.height / 2);

        Square snake_start = this.field.getSquare(start_x, start_y);

        // lag en snake instanse
        this.snake = new Snake(snake_start);
        this.all_food = new ArrayList<>();

        // sett game running til true
        this.running = true;

        // lag 3 mat til starten
        for (int i = 0; i < 3; i++) {
            this.createFood();
        }

        // så lenge spille kjører vent en delay og beveg slangen
        while (this.running) {
            this.timeDelay();
            this.moveSnake();
        }
    }

    public void moveSnake () {
        // hend hode til slangen og definer en neste square
        Square head = this.snake.getHead();
        Square next = null;

        // hent den neste posisjonen til slangen
        try {
            switch (this.snake_direction) {
                case LEFT:
                    next = this.field.getSquare(head.x-1, head.y);
                    break;

                case UP:
                    next = this.field.getSquare(head.x, head.y-1);
                    break;

                case RIGHT:
                    next = this.field.getSquare(head.x+1, head.y);
                    break;

                case DOWN:
                    next = this.field.getSquare(head.x, head.y+1);
                    break;
            }
        }

        catch (ArrayIndexOutOfBoundsException e) {
            this.gameOver();
            return;
        }
        
        // skjekk om slangen traff mat
        if (this.all_food.contains(next)) {
            // fjern mat som slangen traff og lag ny
            this.all_food.remove(next);
            this.createFood();

            // legg til 1 poeng i score
            this.score++;
    
            // speed up slangen helt til den er på 2.5 i speed
            if (this.speed < 2.5) {
                this.speed += 0.1;
            }
        }

        // hvis slangen ikke spiste fjernes bare halen
        else {
            this.snake.removeTail();
        }

        // skjekk om slangen kolliderer med seg selv og da slutt spillet
        if (this.snake.collideWith(next)) {
            this.gameOver();
        }

        // legg den neste posisjonen til slangen
        this.snake.addSquare(next);
    }

    public void setDirection (int direction) {
        this.snake_direction = direction;
    }

    public void createFood () {
        ArrayList<Square> all_squares = new ArrayList<>();

        // finn alle ledige plasser for en ny mat
        for (Square square : this.field.getAll()) {
            if (!this.snake.collideWith(square) && !this.all_food.contains(square)) {
                all_squares.add(square);
            }
        }

        if (all_squares.size() != 0) {
            // tilfeldig nummer innenfor antall tilgjengelige squares
            int diff = all_squares.size()-1;
            int rand = (int) (Math.random() * (diff));
            
            // velg en tilfeldig av de ledige feltene til å bli mat
            Square new_food = all_squares.get(rand);

            // legg den til mat og gjør den gul
            this.all_food.add(new_food);
            new_food.setColor(Color.yellow);
        }
    }

    public void gameOver () {
        // sett running til false for å stoppe loopen i run
        this.running = false;
        
        // visuel indikator for at snaken dør
        this.snake.gameOver();
        
        // sleep 0.5 sekunder for å vise at snaken døde
        this.sleep(500);

        // fjern feltet og vis scoren til spilleren
        this.window.remove(this.field);
        this.window.displayScore(this.score);
    }

    private void timeDelay () {
        // delay mellom hver slange bevegelse
        this.sleep((int)(1000.0 / this.speed));
    }

    // denne metoden er bare for å mimalisere exception handling
    private void sleep (int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.err.println("sleep interrupted");
        }
    }
}