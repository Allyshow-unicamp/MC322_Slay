package mc322_slay.event;

import java.util.Scanner;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.Card;
import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;

public class Reward extends Event {

    Scanner scanner;

    @Override
    public boolean init(Hero hero, CardStack possibleNewCards) {
        scanner = new Scanner(System.in);

        Interface.printInline("\r\n== RECOMPENSA DE BATALHA ==\r\n", ColorEnum.purple);
        Interface.printInline("Recompensas disponívies: \r\n", ColorEnum.purple);

        Interface.printInline("0) ", ColorEnum.yellow);
        System.out.println("Selecione 1 entre 3 cartas para adicionar ao seu baralho.");
        // outras oções
        Interface.printInline("===========================\r\n", ColorEnum.purple);

        int option = 0;
        while (true) {
            try {
                System.out.print(
                        "\r\nSelecione a recompensa que deseja obter (D para ver deck e vida, -1 para pular recompensa): ");
                char response = scanner.next().charAt(0);
                if (response == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response + "");
                    if (-1 <= option && option <= 0) {
                        if (option == -1)
                            break;

                        switch (option) {
                            case 0:
                                selectCard(hero, possibleNewCards);
                                break;
                        }
                        break;
                    } else {
                        Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                    }
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida 2!", ColorEnum.yellow);
            }
        }

        return true;
    }

    private void selectCard(Hero hero, CardStack possibleNewCards) {
        possibleNewCards.shuffle();

        Interface.printInline("\r\n== SELEÇÃO DE CARTA ==\r\n", ColorEnum.yellow);
        for (int i = 0; i < 3; i++) {
            Interface.printCardInfo(possibleNewCards.getStack().get(i), i);
        }
        Interface.printInline("======================\r\n", ColorEnum.yellow);

        int option = 0;
        while (true) {
            try {
                System.out.print(
                        "\r\nSelecione uma carta para adicionar ao seu deck (D para ver deck e vida, -1 para pular): ");
                char response = scanner.next().charAt(0);
                if (response == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response + "");
                    if (-1 <= option && option < 3) {
                        if (option == -1)
                            break;

                        CardStack deck = hero.getDeck();
                        Card newCard = possibleNewCards.getStack().remove(option);
                        deck.add(newCard);
                        hero.setDeck(deck);

                        Interface.clearScreen();

                        Interface.printMessage("\r\nCarta " + newCard.getName() + " adicionada ao baralho.",
                                ColorEnum.blue);

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

    @Override
    public String getDescription() {
        return "Recompensa da batalha";
    }

}
