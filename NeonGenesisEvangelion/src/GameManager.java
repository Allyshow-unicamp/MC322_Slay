import java.util.Scanner;

public class GameManager {
    
    Hero hero;
    Enemy angel;
    int syncRate; // works identical to energy attribute
    Scanner scanner;

    public void initialScreen() {
        System.out.println("image");

        scanner.nextLine();
    }

    public void start() {
        this.hero = new Hero("", 40, 10);
        this.angel = new Enemy("", 200, 200);
        this.syncRate = 4;
        this.scanner = new Scanner(System.in);
    }
}
