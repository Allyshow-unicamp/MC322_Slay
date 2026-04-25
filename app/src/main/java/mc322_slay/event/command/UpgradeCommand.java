package mc322_slay.event.command;

import java.util.Scanner;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.Card;
import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;
import mc322_slay.visitor.UpgradeVisitor;

/**
 * Comando que melhora uma carta escolhida do deck.
 */
public class UpgradeCommand extends Command {

    /**
     * Exibe opções e aplica melhoria via visitor na carta selecionada.
     *
     * @param hero herói dono do deck.
     */
    @Override
    public void execute(Hero hero) {
        scanner = new Scanner(System.in);

        CardStack deck = hero.getDeck();
        deck.shuffle();

        Interface.clearScreen();

        Interface.printInline("\r\n== MELHORIA DE CARTA ==\r\n", ColorEnum.yellow);
        for (int i = 0; i < 3; i++) {
            Interface.printCardInfo(deck.getStack().get(i), i);
        }
        Interface.printInline("======================\r\n", ColorEnum.yellow);

        int option = 0;
        while (true) {
            try {
                System.out.print(
                        "\r\nSelecione uma das 3 cartas para ser melhorada (D para ver deck e vida, -1 para pular): ");
                String response = scanner.next();
                char opt = response.charAt(0);
                if (opt == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response);
                    if (-1 <= option && option < 3) {
                        if (option == -1)
                            break;

                        UpgradeVisitor visitor = new UpgradeVisitor();
                        Card card = deck.getStack().get(option);
                        card.accept(visitor);

                        break;
                    } else {
                        Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                    }
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }
    }
    
}
