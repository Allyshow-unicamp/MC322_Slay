package mc322_slay;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import mc322_slay.effect.Effect;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;
import mc322_slay.card.Card;

import java.io.IOException;

public class Interface {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static String folder = "assets";

    public void printFile(String text) {

        Path filePath = Path.of("..", folder, text);

        try {
            Files.lines(filePath, StandardCharsets.UTF_8).forEach(System.out::println);
        } catch (IOException e) {
        }
    }

    public void printImagesSideBySide(String img1, String img2) {
        Path pathImg1 = Path.of("..", folder, img1);
        Path pathImg2 = Path.of("..", folder, img2);

        try {
            List<String> lines1 = Files.readAllLines(Paths.get(pathImg1.toString()), StandardCharsets.UTF_8);
            List<String> lines2 = Files.readAllLines(Paths.get(pathImg2.toString()), StandardCharsets.UTF_8);

            for (int i = 0; i < Math.min(lines1.size(), lines2.size()); i++) {
                System.out.println(lines1.get(i) + lines2.get(i));
            }
        } catch (IOException e) {
        }
    }

    public void printTurnInfo(Hero hero, Enemy angel) {
        System.out.println(
                "Herói: " + ANSI_BLUE + hero.getName() + ANSI_RESET + " vs. Inimigo: " + ANSI_RED + angel.getName()
                        + ANSI_RESET + "\r\n");

        printImagesSideBySide(hero.getImage(), angel.getImage());

        System.out.println(
                "\r\n(" + ANSI_GREEN + hero.getHealth() + "/" + hero.getMaxHealth() + ANSI_RESET + " pontos de vida) "
                        + "(" + ANSI_YELLOW + angel.getHealth() + "/" + angel.getMaxHealth() + ANSI_RESET
                        + " pontos de vida)\r\n" + //
                        "(" + ANSI_GREEN + hero.getShield() + ANSI_RESET + " pontos de escudo) " +
                        " (" + ANSI_YELLOW + angel.getShield() + ANSI_RESET + " pontos de escudo)\r\n");

        String result = "";

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

    public void showHand(List<Card> hand, int syncRate, int initialSync) {
        System.out.println("=== Cartas disponíveis ===\r\n");

        // sotrs cards by cost, using a compareTo like function to do so
        // given that the sort function sorts in ascending order, -1 is placed at the
        // beginning so that is simulates a desc order
        hand.sort((card1, card2) -> {
            return -1 * (card1.getCost() > card2.getCost() ? 1 : card1.getCost() == card2.getCost() ? 0 : -1);
        });

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            System.out
                    .println(ANSI_YELLOW + i + ") " + ANSI_RESET + card.getName() +
                            " (Custo: " + ANSI_YELLOW + card.getCost() + ANSI_RESET + "): " + card.getDescription());
        }

        System.out.println("\r\n=========================================\r\n" + //
                ANSI_YELLOW + syncRate + "/" + initialSync + ANSI_RESET + " de Sincronização (Energia) disponível\r\n");
    }
}
