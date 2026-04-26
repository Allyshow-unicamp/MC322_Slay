package mc322_slay.event;

import java.util.Scanner;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.Card;
import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;

/**
 * Evento de recompensa pós-batalha para adicionar cartas ao deck do herói.
 */
public class Reward extends Event {
    /**
     * Exibe e processa as opções de recompensa disponíveis.
     */
    @Override
    public boolean init(Hero hero, CardStack possibleNewCards) {
        scanner = new Scanner(System.in);

        Interface.printInline("\r\n== RECOMPENSA DE BATALHA ==\r\n\r\n", ColorEnum.purple);
        Interface.printInline("Você travou uma luta difícil e merece uma recompensa. Recompensas disponíveis: \r\n", ColorEnum.purple);

        Interface.printInline("0) ", ColorEnum.yellow);
        System.out.println("Selecione 1 entre 3 cartas para adicionar ao seu baralho.");
        // outras oções
        Interface.printInline("===========================\r\n", ColorEnum.purple);

        int option = 0;
        while (true) {
            try {
                System.out.print(
                        "\r\nSelecione a recompensa que deseja obter (D para ver deck e vida, -1 para pular recompensa): ");
                String response = scanner.next();
                char opt = response.charAt(0);
                if (opt == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response);
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

    /**
     * Mostra três cartas aleatórias e permite selecionar uma para o deck.
     *
     * @param hero herói que receberá a carta.
     * @param possibleNewCards pilha de cartas candidatas.
     */
    private void selectCard(Hero hero, CardStack possibleNewCards) {
        possibleNewCards.shuffle();

        Interface.clearScreen();

        Interface.printInline("\r\n== SELEÇÃO DE CARTA ==\r\n", ColorEnum.yellow);
        for (int i = 0; i < 3; i++) {
            Interface.printCardInfo(possibleNewCards.getStack().get(i), i);
        }
        Interface.printInline("======================\r\n", ColorEnum.yellow);

        int option = 0;
        while (true) {
            try {
                System.out.print(
                        "\r\nSelecione uma das 3 cartas para adicionar ao seu deck (D para ver deck e vida, -1 para pular): ");
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

    /**
     * @return descrição curta do evento para o mapa.
     */
    @Override
    public String getDescription() {
        return "Recompensa da batalha";
    }

    public void printChar() {
        Interface.printInline("r", ColorEnum.red);
    }
}
