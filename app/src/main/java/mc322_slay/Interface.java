package mc322_slay;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import mc322_slay.card.Card;
import mc322_slay.card.DamageCard;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.Effect;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Camada de apresentação textual do jogo no terminal.
 */
public class Interface {
    private static String folder = "assets";
    static private final int timeSleep = 350;

    /**
     * Limpa a tela do terminal.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pausa breve utilizada para dar ritmo à interface textual.
     */
    static void sleep() {
        try {
            Thread.sleep(timeSleep);
        } catch (Exception e) {
        }
    }

    /**
     * Exibe uma linha no terminal com a sequência ANSI da cor e uma pausa curta.
     *
     * @param message texto a imprimir.
     * @param color   cor do prefixo ANSI aplicado à mensagem.
     */
    public static void printMessage(String message, ColorEnum color) {
        System.out.println(color.getColor() + message + ColorEnum.reset.getColor());
        sleep();
    }

    /**
     * Imprime o conteúdo de um arquivo de texto da pasta de assets.
     *
     * @param text nome do arquivo a ser exibido.
     */
    public static void printFile(String text) {

        Path filePath = Path.of("..", folder, text);

        try {
            Files.lines(filePath, StandardCharsets.UTF_8).forEach(System.out::println);
        } catch (IOException e) {
        }
    }

    /**
     * Imprime duas artes ASCII lado a lado.
     *
     * @param img1 arquivo da primeira imagem.
     * @param img2 arquivo da segunda imagem.
     */
    public static void printImagesSideBySide(String img1, String img2) {
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

    /**
     * Exibe informações de turno, status de entidades e efeitos ativos.
     *
     * @param hero  herói controlado pelo jogador.
     * @param angel inimigo atual.
     */
    public static void printTurnInfo(Hero hero, Enemy angel) {
        System.out.println(
                "\r\nHerói: " + ColorEnum.blue.getColor() + hero.getName() + ColorEnum.reset.getColor()
                        + " vs. Inimigo: "
                        + ColorEnum.red.getColor() + angel.getName() + ColorEnum.reset.getColor() + "\r\n");

        printImagesSideBySide(hero.getImage(), angel.getImage());

        System.out.println(
                "\r\n(" + ColorEnum.green.getColor() + hero.getHealth() + "/" + hero.getMaxHealth()
                        + ColorEnum.reset.getColor() + " pontos de vida) "
                        + "    (" + ColorEnum.yellow.getColor() + angel.getHealth() + "/" + angel.getMaxHealth()
                        + ColorEnum.reset.getColor() + " pontos de vida)\r\n" + //
                        "(" + ColorEnum.green.getColor() + hero.getShield() + ColorEnum.reset.getColor()
                        + " pontos de escudo) " +
                        "     (" + ColorEnum.yellow.getColor() + angel.getShield() + ColorEnum.reset.getColor()
                        + " pontos de escudo)");

        String result = "\r\n";

        if (!hero.getEffects().isEmpty()) {
            result += ColorEnum.yellow.getColor() + "Efeitos de " + ColorEnum.blue.getColor() + hero.getName()
                    + ColorEnum.reset.getColor() + ": ";
            for (Effect effect : hero.getEffects())
                result += "\r\n   " + effect.getString();
            result += "\r\n";
        }
        if (!angel.getEffects().isEmpty()) {
            result += ColorEnum.yellow.getColor() + "Efeitos de " + ColorEnum.red.getColor() + angel.getName()
                    + ColorEnum.reset.getColor() + ": ";
            for (Effect effect : angel.getEffects())
                result += "\r\n   " + effect.getString();
            result += "\r\n";
        }

        System.out.print(result);
    }

    /**
     * Exibe as cartas da mão e a energia disponível do jogador.
     *
     * @param hand        cartas atualmente na mão.
     * @param syncRate    energia disponível no turno.
     * @param initialSync energia máxima padrão.
     */
    public static void showHand(List<Card> hand, int syncRate, int initialSync) {
        System.out.println("\r\n===== Sua Mão =====\r\n");

        // sotrs cards by cost, using a compareTo like function to do so
        // given that the sort function sorts in ascending order, -1 is placed at the
        // beginning so that is simulates a desc order
        hand.sort((card1, card2) -> {
            return -1 * (card1.getCost() > card2.getCost() ? 1 : card1.getCost() == card2.getCost() ? 0 : -1);
        });

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            String cardType = "";
            if (card instanceof DamageCard)
                cardType = "Carta de Dano  ";
            else if (card instanceof ShieldCard)
                cardType = "Carta de Escudo";
            else
                cardType = "Carta de Efeito";

            System.out
                    .println(ColorEnum.yellow.getColor() + "" + i + ") " + ColorEnum.blue.getColor() + cardType
                            + ColorEnum.reset.getColor() + ": " + card.getName() +
                            ColorEnum.yellow.getColor() + " (Custo: " + card.getCost() + ")"
                            + ColorEnum.reset.getColor() + ": " + card.getDescription());
        }

        System.out.println("\r\n===================\r\n" + //
                ColorEnum.yellow.getColor() + syncRate + "/" + initialSync + ColorEnum.reset.getColor()
                + " de Sincronização (Energia) disponível\r\n");
    }
}
