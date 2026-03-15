import java.util.Random;
import java.util.Scanner;

public class GameManager {
    
    Hero hero;
    Enemy angel;
    int syncRate; // works identical to energy attribute
    Scanner scanner;
    Random random;
    ShieldCard ATFieldCard;
    DamageCard weapon;
    int turn;

    public void start() {
        this.hero = new Hero("", 40, 20);
        this.angel = new Enemy("Sachiel", 200, 200);
        this.ATFieldCard = new ShieldCard("Conexão Neural", 2, "");
        this.weapon = new DamageCard("Positron Rifle", 2, "");
        this.syncRate = 4;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.turn = 0;
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
            Thread.sleep(1000);
        } 
        catch (Exception e) {}

        System.out.println(hero.getName() + " selecionado.\r\n");
    }

    public boolean isRunning() {
        return hero.isAlive() && angel.isAlive();
    }

    public void resetTurn(){
        hero.resetShield();
        syncRate = 4;
        turn += 1;
    }

    public boolean endOfTurn(){
        return !isRunning() || syncRate == 0;
    }

    public void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {}
    }

    public void selectOption() {
        try {
            Thread.sleep(1000);
        } 
        catch (Exception e) {}

        System.out.println("========== Turno " + turn + " ==========");

        System.out.print("\r\n=========================================\r\n" + //
                        "Herói: " + hero.getName() + " vs. Inimigo: " + angel.getName() + "\r\n" + //
                        "("+ hero.getHealth() +"/40 pontos de vida)   (" + angel.getHealth()+ "/200 pontos de vida)\r\n" + //
                        "("+ hero.getShield() +" pontos de escudo)    (" + angel.getShield()+ " pontos de escudo)\r\n" + //
                        "=========================================\r\n\r\n" + //
                        syncRate + "/4 de Sincronização (Energia) disponível\r\n \r\n" + //
                        "1 - Usar Carta de Dano (Custo: " + this.weapon.getCost() + " de energia)\r\n" + //
                        "2 - Usar Carta de Escudo (Custo: " + this.ATFieldCard.getCost() + " de energia)\r\n" + //
                        "3 - Encerrar turno\r\n" + //
                        "\r\n=========================================\r\n");
    
        int option = 0;
        while (true) { 
            try {
                System.out.print("Escolha: ");
                option = Integer.parseInt(scanner.nextLine());
                if (0 < option && option < 4) {
                    break;
                }
            }
            catch (Exception e) {}
        }
        
        clearScreen();

        switch (option) { 
            case 1:
                if (syncRate - weapon.getCost() >= 0) {
                    int damage = random.nextInt(81);
                    weapon.useCard(angel, damage);
                    syncRate -= weapon.getCost();

                    System.out.println("\r\nVocê usa " + weapon.getName() + " contra " + angel.getName() + ", dando " + damage + " de dano.\r\n");
                } 
                break;
            case 2:
                if (syncRate - ATFieldCard.getCost() >= 0) {
                    int shield = random.nextInt(81);
                    ATFieldCard.useCard(hero, shield);
                    syncRate -= ATFieldCard.getCost();

                    System.out.println("\r\nVocê usa " + ATFieldCard.getName() + ", recebendo " + shield + " de Campo AT (escudo).\r\n");
                }
                break;
            case 3:
                syncRate = 0;
                break;
            default:
            }
    }

    public void enemyAction() {
        if (angel.isAlive()) {
            try {
                Thread.sleep(1000);
            } 
            catch (Exception e) {}

            int damage = angel.attack(hero);
            
            System.out.println("\r\nO inimigo " + angel.getName() + " te atacou, dando " + damage + " de dano.\r\n");
        }
    }

    public void results() {
        try {
            Thread.sleep(1000);
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
