package mc322_slay.event.command;

import java.util.Scanner;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.Card;
import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;

public class DeleteCommand extends Command {

    @Override
    public void execute(Hero hero) {
        scanner = new Scanner(System.in);

        CardStack deck = hero.getDeck();
        deck.shuffle();

        Interface.clearScreen();

        Interface.printInline("\r\n== DELEÇÃO DE CARTA ==\r\n", ColorEnum.red);
        for (int i = 0; i < 3; i++) {
            Interface.printCardInfo(deck.getStack().get(i), i);
        }
        Interface.printInline("======================\r\n", ColorEnum.red);

        int option = 0;
        while (true) {
            try {
                System.out.print(
                        "\r\nSelecione uma das 3 cartas para deletar do seu deck (D para ver deck e vida, -1 para pular): ");
                String response = scanner.next();
                char opt = response.charAt(0);
                if (opt == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response);
                    option = Integer.parseInt(response + "");
                    if (-1 <= option && option < 3) {
                        if (option == -1)
                            break;

                        Card card = deck.getStack().remove(option);
                        Interface.printMessage(card.getName() + " foi removida do baralho.", ColorEnum.yellow);

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
