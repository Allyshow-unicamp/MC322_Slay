package mc322_slay;

import java.nio.file.Files;
import java.nio.file.Path;

import mc322_slay.effect.Effect;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

import java.io.IOException;

public class Interface {
    private static String folder = "assets";

    public void printFile(String text) {

        Path filePath = Path.of("..", folder, text);

        try {
            Files.lines(filePath).forEach(System.out::println);
        } catch (IOException e) {
        }
    }

    public void printTurnInfo(int turn, Hero hero, Enemy angel, int playerHealth, int enemyHealth) {
        System.out.println("=============== Turno " + turn + " ===============");

        String result = "" + //
                "Herói: " + hero.getName() + " vs. Inimigo: " + angel.getName() + "\r\n" + //
                "(" + hero.getHealth() + "/" + playerHealth + " pontos de vida)   (" + angel.getHealth()
                + "/" + enemyHealth + " pontos de vida)\r\n" + //
                "(" + hero.getShield() + " pontos de escudo)    (" + angel.getShield()
                + " pontos de escudo)\r\n";

        if (!hero.getEffects().isEmpty()) {
            result += "\r\nEfeitos de " + hero.getName() + ": ";
            for (Effect effect : hero.getEffects())
                result += effect.getString() + "; ";
        }
        if (!angel.getEffects().isEmpty()) {
            result += "\r\nEfeitos de " + angel.getName() + ": ";
            for (Effect effect : angel.getEffects())
                result += effect.getString() + "; ";
        }
        result += "\r\n=======================================\r\n";

        System.out.print(result);
    }
}
