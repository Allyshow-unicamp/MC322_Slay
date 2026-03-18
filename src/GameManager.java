import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    public static final int nCards = 4;
    public static final int playerHealth = 40;
    public static final int playerField = 20;
    public static final int enemyHealth = 200;
    public static final int enemyField = 200;
    public static final int timeSleep = 1000;
    public static final int initialSync = 4;
    
    Hero hero;
    Enemy angel;
    int syncRate; // works identical to energy attribute
    Scanner scanner;
    Random random;
    PlayerHand hand;
    CardStack buyPile;
    CardStack discardPile;
    int turn;

    public void populateDeck() {
        buyPile.add(new DamageCard("Lança de Longinus", 10, "Use-a para dar de 100 a 200 de dano"));
        buyPile.add(new DamageCard("Lança de Cassius", 8, "Use-a para dar de 80 a 160 de dano"));
        buyPile.add(new DamageCard("Rifle Positron", 6, "Use-a para dar de 60 a 120 de dano"));
        buyPile.add(new DamageCard("Espada Progressiva", 4, "Use-a para dar de 40 a 80 de dano"));
        buyPile.add(new DamageCard("Faca Progressiva", 2, "Use-a para dar de 20 a 40 de dano"));
    }

    public void start() {
        this.hero = new Hero("", playerHealth, playerField);
        this.angel = new Enemy("Sachiel", enemyHealth, enemyField);
        this.hand = new PlayerHand();
        this.buyPile = new CardStack();
        this.discardPile = new CardStack();
        this.syncRate = initialSync;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.turn = 0;

        this.populateDeck();
    }

    public void initialScreen() {
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n" + //
                        "⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠠⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀\r\n" + //
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠄⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀\r\n" + //
                        "⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠀⠁⠀⠀⢀⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡁⠀⠀⠀⠁⠀⠀⠂⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠀\r\n" + //
                        "⠀⠀⠄⠀⠀⠀⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠁⠀⠀⢀⣀⣤⣢⣤⠄⠀⠀⠀⠀⠀⠀⡀⠀⣤⣀⣄⠂⠀⠀⠀⠀⠠⠐⠀⠀⠀⡀⠐⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠁\r\n" + //
                        "⠀⡀⠀⠀⡀⠁⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⣤⡖⡼⢻⠋⠉⠀⠀⠈⠀⠀⠀⠂⠀⠀⠂⠀⠉⠻⠿⡄⣆⠀⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠂⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀\r\n" + //
                        "⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀⠠⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠀⠈⡟⢠⠃⠁⠀⠀⠀⠀⠀⠀⠀⣶⠀⠀⠀⠀⠀⠀⠀⠓⠤⢉⢿⠀⠀⠀⠂⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠀⠂\r\n" + //
                        "⠀⠀⠂⠁⠀⠀⠂⠀⠀⠠⠀⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⠂⠀⠀⠀⠘⠀⠀⠀⠀⠀⡄⢠⡄⠀⣼⣿⣇⠐⣦⠀⠀⠀⢀⠀⠀⠌⠎⠀⠀⠀⠀⡀⠐⠀⠀⠀⠀⠠⠀⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀\r\n" + //
                        "⠀⠁⠀⠀⠀⠄⠀⠀⠠⠀⠀⠀⠐⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠂⠀⠠⠀⠀⠀⠀⠀⡠⢀⣾⠄⣾⡆⢠⣿⣿⣿⣀⣿⣧⢈⣷⣼⡄⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠐⠀⠀⠀⢀⠀⠁⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠\r\n" + //
                        "⠀⠀⠀⠐⠀⠀⠀⠄⠀⠀⢀⠈⠀⠀⠀⠐⠀⠀⠀⠀⠄⠂⠀⠀⠀⠀⠂⠀⠀⢀⠀⠂⠀⠀⢀⠀⠈⠀⠀⢀⠀⠄⠀⢀⠠⡁⠖⡼⠻⢾⢿⣿⣼⣿⣿⣿⣷⡿⠿⠧⢻⣟⣿⣤⡃⢀⠀⠀⠂⠀⠀⠀⠂⠁⠀⠀⡀⢈⡤⡴⣤⠶⣤⣣⠴⣤⢆⣔⣠⠀⡀⠀⠀⠂⠀⠀⢀⠀⠂⠀⠀⢀⠀⠂⠀⠀⢀⠀⠂⠀\r\n" + //
                        "⡀⠈⠀⠀⢀⠀⠂⠀⠀⠠⠀⠀⠀⡀⠈⠀⠀⠀⠐⠀⠀⠀⠀⡀⠈⠀⠀⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⠠⠀⠀⠀⠀⠐⠨⠄⡹⢸⢠⣧⠀⢲⣤⣬⣿⣿⣿⣴⣲⣦⠐⢳⣮⣻⣿⠥⣋⠀⠀⠀⡀⠈⠀⠀⠀⣄⢢⡔⣯⢞⣵⣫⢟⡶⣭⢟⢮⡻⣜⢧⢿⣱⢦⣄⠀⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⠀\r\n" + //
                        "⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠀⠀⡀⠁⠀⠀⢀⠀⠁⠀⠀⢀⠐⠀⡀⢀⠀⠐⠀⠀⠄⡐⠰⠦⠀⠀⠀⠂⠀⠉⢆⠡⣋⠞⣿⣷⣾⣿⣿⡟⣿⣿⣿⣿⣿⣷⣿⣿⣿⢯⠒⠃⠀⢀⠀⠀⠀⢀⠠⣝⢮⣳⠽⣎⡟⣶⠹⣮⣵⣾⣿⠶⣯⡝⣞⣧⣛⠾⣜⣻⢤⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀\r\n" + //
                        "⠀⠀⠂⠀⠀⠀⠀⠂⠀⠀⠀⠈⠀⠀⠀⠁⠀⠀⠀⠄⠀⢀⠠⢐⠨⠐⠌⢂⠑⠨⠘⡄⠣⢌⠠⠐⠀⠀⠐⠀⠠⠀⠄⡀⠈⠒⡈⠳⣌⠻⠿⣟⢣⢚⢸⣿⣿⣿⣿⣿⣿⣿⠟⠀⠃⠀⠀⠀⠀⠀⢀⠂⣼⡹⣎⠷⢋⡵⢋⣾⣿⣸⣿⠿⣍⡻⢶⢙⡞⢲⣭⢳⠹⣎⢯⢧⡈⠄⠀⠀⠀⠂⠁⠀⠀⢀⠀⠁⠀⠀\r\n" + //
                        "⠈⠀⠀⠀⠄⠈⠀⠀⢀⠀⠁⠀⠀⠈⠀⠀⢀⠀⢢⠠⣁⠂⡌⠰⢢⡝⣎⠆⣉⢂⠡⡀⠑⠂⢆⠡⠂⠄⠐⠀⠂⠔⣠⢀⠀⠀⠀⠱⢌⢣⠏⣔⡋⢖⣾⣿⣿⣿⣿⣿⣿⠏⠀⠀⠀⢀⠀⠁⠀⠈⠠⠐⠰⡛⠌⡁⠢⠐⣿⣿⠇⡻⢌⡷⣸⠝⡣⡌⢹⠂⠜⣯⢣⠘⡌⢷⡆⠈⠀⠠⠀⠀⢀⠠⠀⠀⠀⠀⠀⠂\r\n" + //
                        "⠀⠀⠐⠀⠀⠀⠀⠄⠀⠀⠀⠀⠂⠀⠀⠄⢀⡘⠄⡃⢄⠣⡈⢅⠣⠙⢮⡱⡄⢊⠔⡁⠣⠌⡀⠄⣈⠦⢁⠀⠈⢆⠠⢉⠄⠀⡀⠀⠀⠃⢞⡰⢍⡖⣿⣶⣿⣿⣿⠟⠁⠀⠀⠀⠈⠀⠀⠀⠀⠌⠠⠁⡐⠀⢂⠀⠡⠈⠿⢻⡓⢡⡞⢰⢏⡜⣱⣿⡀⠁⠄⡈⢷⡀⠄⡩⢞⠀⠀⠀⠀⡀⠀⠀⠀⠀⡀⠈⠀⠀\r\n" + //
                        "⠀⠁⠀⠀⠀⠐⠀⠀⠀⢀⠀⠁⠀⠀⠌⠒⠤⡘⢠⠑⡌⠰⣁⠊⡔⣩⢦⡳⡉⠔⢢⠐⠡⠐⠈⠐⡀⢠⠖⠈⠀⣌⠲⣌⠠⠁⠀⠀⠀⠠⠀⠙⠲⡜⣿⣿⡿⠏⢁⡀⠀⠀⠀⠐⠀⠀⢀⠀⠡⢈⠐⠠⢀⠡⠀⠌⠐⡀⠂⠅⡰⡏⢄⡩⣞⣬⣿⣟⡛⠄⢂⠀⡘⡄⠠⠘⡜⠀⠀⠀⠁⠀⠀⠀⠐⠀⠀⠀⠀⠠\r\n" + //
                        "⠀⠀⢀⠀⠁⠀⠀⠠⠀⠀⠀⠀⡀⢐⡬⡑⢂⡘⠄⣳⢬⠧⢄⢃⡘⠡⠓⠥⠑⢨⠀⡉⠐⠡⠂⢀⡐⠌⠃⢌⠀⢌⢿⡘⢇⠐⠀⠀⠂⢨⡑⢦⡐⣈⠉⢁⢤⣺⣧⣿⠀⠀⠁⠀⠀⡀⠀⠀⢀⠂⠌⠠⠀⡐⠈⡀⠂⠄⡐⢸⡳⣥⢎⠷⣿⣿⣷⣾⣿⢬⣦⡐⠠⠀⢀⠁⠀⠀⠀⠂⠀⠀⠐⠈⠀⠀⠀⠀⠂⠀\r\n" + //
                        "⡀⠈⠀⠀⠀⠀⠂⠀⠀⠀⠐⠀⠀⢎⡷⠁⡶⡜⠐⡈⢣⠐⠂⠄⢤⣁⡀⣀⣀⢀⡀⡐⠠⠀⠈⠀⣴⢊⠄⠈⠆⠈⢆⠱⣈⠂⢡⠀⡐⣶⣤⣀⠱⡆⣄⢮⣾⣋⣡⣻⣧⢀⡀⠄⠀⠀⠀⢀⠀⢈⠐⡀⢁⠀⡐⢀⠁⠲⣹⠂⢿⣜⠩⡞⣿⣿⣿⣿⣯⣿⣿⡏⠀⠀⠀⠀⠀⡀⠁⠀⠀⠐⠀⠀⠀⡀⠈⠀⠀⠀\r\n" + //
                        "⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠈⠀⠌⠠⢁⠣⠑⠀⠡⡀⠊⠔⢬⡀⠡⠜⣡⣦⠄⢈⣽⣦⡅⣆⠡⣌⡻⡇⠈⠰⠁⡌⠒⣠⣽⣶⢟⣼⣿⠾⣿⣷⣼⣾⣿⣿⣿⡿⣟⣷⠺⣿⣶⢀⡄⠀⠀⠀⠀⢂⠐⠠⢀⠠⠀⠌⡀⠣⢩⠘⣮⢇⡘⡼⣿⣿⣿⣿⣿⣿⣧⡀⠀⠀⠂⠁⠀⠀⠀⠄⠀⠀⠠⠀⠀⠀⠀⠐⠀\r\n" + //
                        "⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠄⠈⢀⠀⠠⠡⢈⠀⠄⠳⠌⡈⠂⢿⣿⣾⢿⣿⣆⣼⣿⣿⣿⣿⣾⣯⣵⢆⣨⣴⣷⣿⣫⣾⢏⣶⣿⣿⣿⣿⣷⣮⡝⡻⣍⣳⣾⣿⣿⣿⣿⣙⢿⣎⡻⣷⣦⣄⡀⠀⢈⠐⠠⠀⢂⠐⠀⡁⠄⠃⠨⠷⡄⢱⣿⣿⣿⣿⣿⣿⣿⠛⠀⠀⠀⠀⠀⠠⠀⠀⠀⠄⠀⠀⠀⡀⠈⠀⠀\r\n" + //
                        "⠂⠀⠀⠀⠀⠂⠀⠀⠀⠐⠀⠀⠈⢀⠀⢂⠡⠀⠠⢘⠆⣷⡅⢳⣬⣹⣿⣯⣿⣿⣿⣿⣿⣿⣿⣿⢇⣾⣿⣿⣿⢋⣶⣿⡇⣾⣿⣿⣿⣿⣿⣿⣿⣿⣶⣾⣿⣿⣿⣿⣿⣿⣿⡆⣻⣷⡙⣿⣿⣷⣦⠀⠀⡁⠂⠄⢂⠐⠀⡐⠈⠀⠌⠳⢎⣿⣿⣿⣿⣿⡿⠃⠀⠀⠀⠐⠀⠁⠀⠀⢀⠀⠀⠀⠠⠀⠀⠀⠀⡀\r\n" + //
                        "⠀⠀⡀⠈⠀⠀⢀⠀⠁⠀⠀⠀⠄⠂⠀⠀⠆⠐⠀⡀⠙⠾⣳⡼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢏⢞⣿⣿⣿⣿⠈⠟⠁⠀⠈⣿⣿⣿⣿⣿⣿⡷⣆⣿⡅⣿⣿⣿⣿⣿⣿⡟⠃⠘⢻⠏⣼⣿⣿⣿⣿⡀⠀⠈⢀⠀⠂⢡⠀⢂⢡⠙⣦⡙⢿⣿⣿⣿⡟⠁⠀⠀⠀⠈⠀⠀⠀⠠⠀⠀⠀⠀⠐⠀⠀⠀⡀⠁⠀\r\n" + //
                        "⢀⠀⠀⠀⠀⠄⠀⠀⠀⠀⠂⠀⠀⠀⠀⠈⡐⠀⠀⠀⠄⠐⢫⣗⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⡌⠀⠄⣸⣿⠟⠀⠀⠀⠀⢸⡿⣿⣯⣿⣿⣿⣶⣴⣿⣷⣿⣿⢿⣿⣿⣿⣿⠀⠀⠀⠀⢿⣿⣿⠋⠉⢻⠀⠀⠀⣴⣦⣤⣩⡈⠃⠛⠴⠋⠘⠛⠻⠋⠀⠀⠀⠀⠁⠀⠀⠀⠂⠀⠀⠀⠐⠈⠀⠀⠀⠄⠀⠀⠀\r\n" + //
                        "⠀⠀⠀⠀⠂⠀⠀⢀⠈⠀⠀⠀⠄⠐⠀⠀⠀⠀⢀⠈⢀⠂⠀⠙⠧⢟⡾⣿⣿⣿⣿⣿⣿⣿⢁⠆⠀⠠⣼⠃⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡀⠀⠀⠀⠀⢻⣯⠐⠠⢈⡇⠀⢠⣿⣿⣿⣿⣿⣿⣶⡀⡆⠀⠀⠀⠀⠀⢀⠈⠀⠀⡀⠈⠀⠀⠀⠄⠈⠀⠀⠀⠠⠀⠀⠀⠀⠂\r\n" + //
                        "⠀⢀⠈⠀⠀⠀⠠⠀⠀⠀⠀⠂⠀⠀⠀⠠⠐⠈⠀⠀⡀⢀⠈⠀⠀⡀⠉⠓⠻⢿⡿⠿⡛⠞⠠⣋⢆⢣⢻⡅⠀⠀⠀⠠⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⡄⢀⠂⠁⢰⣿⣿⣦⣾⡅⠠⢀⠉⢻⣿⣿⣧⣉⣿⣶⡏⠀⠀⠀⠐⠈⠀⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⢀⠀⠂⠀⠀⢀⠀⠁⠀\r\n" + //
                        "⠠⠀⠀⠀⠀⠂⠀⠀⠀⡀⠁⠀⠀⠀⠂⠀⠀⠀⠀⠐⠀⠀⠀⡀⠂⠀⠀⡀⠀⠀⠀⠀⠀⠄⡀⢇⠎⢦⡙⡦⠀⠀⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⠀⠀⣹⣿⣿⣿⣿⠅⣒⠦⢌⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠈⠀⠀⠀⠀⡀⠁⠀⠀⠁⠀⠀⠀⠄⠀⠀⠀⢀⠠⠀⠀⠀⠀\r\n" + //
                        "⠀⠀⠀⡀⠁⠀⠀⠠⠀⠀⠀⠀⠈⠀⠀⠀⠠⠐⠀⢁⠈⠀⠄⠀⠀⠄⠀⠀⠠⠀⠀⠄⡈⠄⠀⢎⡜⢢⡑⠇⠀⠠⢿⣿⣿⣿⣿⣿⣿⣿⢿⠿⠛⠉⠉⠉⠛⣿⡿⣽⣿⣿⣿⣿⣿⣿⣧⠀⠰⣻⣿⣿⣿⠠⣝⠸⣸⣿⣿⣿⣿⣿⣿⣿⣿⣟⠰⣰⣄⠀⠈⠀⠀⠀⠀⠐⠀⠀⠈⠀⠀⠀⠀⠄⠀⠀⠀⠀⠀⠁\r\n" + //
                        "⠀⠠⠀⠀⠀⠀⠐⠀⠀⠀⡀⠈⠀⠀⠀⠂⠀⠀⡀⠂⢀⠠⠀⡀⢂⠐⠠⢊⠄⠠⢈⠐⠠⢈⠐⠠⢎⡱⡘⠀⠀⡘⠈⠹⢿⣿⣿⣿⣿⡿⠃⠀⠀⠠⢄⠀⠀⠀⢿⣿⣿⣿⣿⣿⡿⠛⠳⠀⠀⢱⢻⣿⡇⠸⣌⡃⢸⣿⣿⣿⣿⣿⣿⣿⣿⢻⣿⣌⠿⣿⣤⠀⠀⠀⠂⠀⠀⠀⠂⠀⠀⠈⠀⠀⠀⠀⠄⠈⠀⠀\r\n" + //
                        "⠂⠀⠀⢀⠀⠈⠀⠀⠀⠄⠀⠀⠀⡀⠁⠀⠀⠠⡐⠁⠀⠀⡐⠠⠀⠌⠐⠀⢎⠐⠠⣈⠱⠈⠘⠠⢃⠖⡁⠀⠀⠀⠄⡄⠈⠹⠿⡟⢭⡘⠁⠀⠀⠁⠞⠀⠀⠀⠬⡙⢿⣿⠿⠋⢀⠄⠐⠀⠀⠀⢏⢿⣰⠀⢧⠱⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡖⢹⣿⣷⠀⠀⠀⡀⠈⠀⠀⠀⠐⠀⠀⢀⠈⠀⠀⠀⢀⠀\r\n" + //
                        "⠀⠀⠄⠀⠀⠀⠠⠐⠀⠀⠀⠠⠀⠀⠀⢀⠠⢡⠐⠀⠀⠀⠐⠀⠌⢀⠂⠈⠆⠉⢀⠀⠠⠐⡀⠄⢋⡒⠀⠀⠀⠐⡸⢌⠣⡐⢣⠜⡢⢁⠀⠀⠀⠀⠀⠀⠀⠀⠐⢩⠒⡌⠆⡰⣉⠒⡀⠀⠀⠀⠨⢆⠹⠃⠀⠁⠀⢸⣿⣿⣿⣿⣿⣿⣿⢯⡽⣿⣿⢸⣿⡏⠀⠀⠀⠀⠀⢀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀\r\n" + //
                        "⠀⠂⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠠⢀⠢⢁⠀⠄⠀⠀⠂⠀⡐⠀⠂⠈⠀⠌⠠⢈⠄⠡⠀⡐⢨⠐⠀⠀⠀⠀⠑⢪⡑⢌⢣⠊⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠬⣁⢒⡡⠃⠀⠀⠀⠀⠐⠂⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣷⣽⣿⣿⣾⣿⣇⠀⠀⠈⠀⠀⠀⠀⠀⠠⠐⠈⠀⠀⠐⠀⠀⠀⢀\r\n" + //
                        "⠀⠀⠀⡀⠁⠀⠀⢀⠀⠁⠀⠀⠀⠂⢀⠆⢡⠂⠀⠀⠀⠀⢄⠰⠀⡐⣀⢦⡁⠜⣀⠃⡌⢂⡁⠠⠐⠀⠀⠀⠀⠀⣀⢡⡘⢬⠀⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀⠀⠀⠀⠀⠠⣅⣲⣤⡁⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣟⠀⠀⢀⠀⠂⠁⠀⢀⠀⠀⠀⠀⠠⠀⠀⠀⠂⠀\r\n" + //
                        "⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⢀⠀⠁⡀⠎⠨⠄⡀⠄⣡⢞⠨⡐⢂⠁⡐⢋⢋⠑⢢⠐⠌⡄⢃⡐⠠⠀⠀⠀⠀⠀⠐⠌⢷⣛⡖⣃⣀⡀⠀⠀⠀⠀⠀⠂⠀⠀⠀⢀⠀⡄⢶⡞⣟⡿⠗⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠂⠀⠀⠀⠄⠀⠀\r\n" + //
                        "⠂⠀⠀⠀⠐⠀⠀⠀⠀⠄⠀⠀⡐⡈⠌⡑⠠⠀⢀⠢⠌⢂⠅⠂⡐⠈⡔⡈⢌⠂⡜⠐⡐⢂⠄⠡⠀⠀⠀⠀⠀⠀⠀⠈⠳⢿⣿⣷⣿⣖⠀⠀⠀⠀⡁⠀⠀⢰⣮⣿⣿⣿⣾⠏⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠀⠀⢀⠈⠀⠀⠀⠈⠀⠀⠀⠀⠂⠀⠀⠀\r\n" + //
                        "⠀⠀⡀⠁⠀⠀⠀⠐⠀⠀⠀⢠⠐⡈⠔⡈⠀⠠⠀⢂⠘⠀⢂⠡⢀⠂⡐⠈⡔⠨⣀⠣⠘⠀⠌⠠⢁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⠀⠀⠀⠀⢀⠀⠀⠸⠟⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⠁⠀⠀⡀⠄⠂\r\n");

        System.out.println("\r\nPressione qualquer tecla para iniciar.");

        scanner.nextLine();
    }

    public void selectCharacter() {
        System.out.print("===============================================\r\n"+
                        "\r\n1 - Shinji Ikari\r\n" +
                        "2 - Rei Ayanami\r\n" +
                        "3 - Asuka Langley Soryu\r\n" +
                        "\r\n================================================\r\n");
        int option;
        String name = "";
        while (true) { 
            try {
                System.out.print("Selecione o piloto de EVA: ");
                option = Integer.parseInt(scanner.nextLine());
                if (0 < option && option < 4) {
                    break;
                }
            }
            catch (Exception e) {}
        }
        switch (option) {
            case 1:
                name = "Shinji Ikari";
                break;
            case 2:
                name = "Rei Ayanami";
                break;
            case 3:
                name = "Asuka Langley Soryu";
                break;
        }
        hero.setName(name);

        clearScreen();

        try {
            Thread.sleep(timeSleep);
        } 
        catch (Exception e) {}

        System.out.println(hero.getName() + " selecionado.\r\n");
    }

    public boolean isRunning() {
        return hero.isAlive() && angel.isAlive();
    }

    public void resetTurn(){
        hero.resetShield();
        syncRate = initialSync;
        turn += 1;
    }

    public boolean endOfTurn(){
        return !isRunning() || syncRate == 0;
    }

    public void buyCards() {
        for (int i = 0; i < nCards; i++) {
            hand.buyCard(buyPile);
        }

        System.out.println("Você compra " + nCards + " cartas.");

        try {
            Thread.sleep(timeSleep);
        }
        catch (Exception e) {}
    }

    public void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {}
    }

    public void selectOption() {
        try {
            Thread.sleep(timeSleep);
        } 
        catch (Exception e) {}

        System.out.println("========== Turno " + turn + " ==========");

        System.out.print("\r\n=========================================\r\n" + //
                        "Herói: " + hero.getName() + " vs. Inimigo: " + angel.getName() + "\r\n" + //
                        "("+ hero.getHealth() +"/" + playerHealth + " pontos de vida)   (" + angel.getHealth()+ "/" + enemyHealth + " pontos de vida)\r\n" + //
                        "("+ hero.getShield() +" pontos de escudo)    (" + angel.getShield()+ " pontos de escudo)\r\n" + //
                        "=========================================\r\n\r\n");

        hand.showHand();

        System.out.println("=========================================\r\n\r\n" + //
                        syncRate + "/" + syncRate + " de Sincronização (Energia) disponível\r\n \r\n");
    
        int option = 0;
        while (true) { 
            try {
                System.out.print("Qual carta deseja usar (-1 para passar o turno): ");
                option = Integer.parseInt(scanner.nextLine());
                if (0 < option && option < hand.nCards()) {
                    break;
                }
            }
            catch (Exception e) {}
        }
        
        clearScreen();

        if (option == -1)
            syncRate = 0; // end of turn
        else {
            Card card = hand.useCard(option);

            if (syncRate - card.getCost() >= 0) {
                if (card.getClass() == DamageCard.class) {
                    int damage = card.getCost() * 10 + random.nextInt(card.getCost() * 10);
                    card.useCard(angel, damage);
                    syncRate -= card.getCost();
                    
                    System.out.println("\r\nVocê usa " + card.getName() + " contra " + angel.getName() + ", dando " + damage + " de dano.\r\n");
                }
                else if (card.getClass() == ShieldCard.class) {
                    int shield = card.getCost() * 10 + random.nextInt(card.getCost() * 10);
                    card.useCard(hero, shield);
                    syncRate -= card.getCost();

                    System.out.println("\r\nVocê usa " + card.getName() + ", recebendo " + shield + " de Campo AT (escudo).\r\n");
                }
            }

            discardPile.add(card);
        }
    }

    public void enemyAction() {
        if (angel.isAlive()) {
            try {
                Thread.sleep(timeSleep);
            } 
            catch (Exception e) {}

            int damage = angel.attack(hero);
            
            System.out.println("\r\nO inimigo " + angel.getName() + " te atacou, dando " + damage + " de dano.\r\n");
        }
    }

    public void results() {
        try {
            Thread.sleep(timeSleep);
        } 
        catch (Exception e) {}

        if (hero.isAlive()) {
            System.out.println("""
                                                                                 
8b        d8                           I8,        8        ,8I  88               
 Y8,    ,8P                            `8b       d8b       d8'  ""               
  Y8,  ,8P                              "8,     ,8"8,     ,8"                    
   "8aa8"  ,adPPYba,   88       88       Y8     8P Y8     8P    88  8b,dPPYba,   
    `88'  a8"     "8a  88       88       `8b   d8' `8b   d8'    88  88P'   `"8a  
     88   8b       d8  88       88        `8a a8'   `8a a8'     88  88       88  
     88   "8a,   ,a8"  "8a,   ,a88         `8a8'     `8a8'      88  88       88  
     88    `"YbbdP"'    `"YbbdP'Y8          `8'       `8'       88  88       88  
                                                                                                                                                                     
            """);
        }
        else {
            System.out.print("""
                                                                                                                              
  ,ad8888ba,                                                    ,ad8888ba,                                         
 d8"'    `"8b                                                  d8"'    `"8b                                        
d8'                                                           d8'        `8b                                       
88             ,adPPYYba,  88,dPYba,,adPYba,    ,adPPYba,     88          88  8b       d8   ,adPPYba,  8b,dPPYba,  
88      88888  ""     `Y8  88P'   "88"    "8a  a8P_____88     88          88  `8b     d8'  a8P_____88  88P'   "Y8  
Y8,        88  ,adPPPPP88  88      88      88  8PP\"\"\"\"\"\"\"     Y8,        ,8P   `8b   d8'   8PP\"\"\"\"\"\"\"  88          
 Y8a.    .a88  88,    ,88  88      88      88  "8b,   ,aa      Y8a.    .a8P     `8b,d8'    "8b,   ,aa  88          
  `"Y88888P"   `"8bbdP"Y8  88      88      88   `"Ybbd8"'       `"Y8888Y"'        "8"       `"Ybbd8"'  88          
                                                                                                                   
            """);                                                                                                          
        }
    }
}
