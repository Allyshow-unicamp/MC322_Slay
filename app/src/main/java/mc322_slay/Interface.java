package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Path;

import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

import java.io.IOException;

public class Interface {

    private static String root = "MC322_Slay";
    private static String folder = "assets";

    public void printFile(String text) {

        Path filePath = Path.of(root, folder, text);

        try {
            Files.lines(filePath).forEach(System.out::println);
        } catch (IOException e) {
        }
    }

    public void printTurnInfo(Hero hero, Enemy angel, int playerHealth, int enemyHealth) {
        System.out.print("" + //
                "Herói: " + hero.getName() + " vs. Inimigo: " + angel.getName() + "\r\n" + //
                "(" + hero.getHealth() + "/" + playerHealth + " pontos de vida)   (" + angel.getHealth()
                + "/" + enemyHealth + " pontos de vida)\r\n" + //
                "(" + hero.getShield() + " pontos de escudo)    (" + angel.getShield()
                + " pontos de escudo)\r\n");
    }
}
