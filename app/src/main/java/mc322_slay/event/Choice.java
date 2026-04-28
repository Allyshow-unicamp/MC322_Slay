package mc322_slay.event;

import java.util.Random;
import java.util.Scanner;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.Card;
import mc322_slay.card.CardStack;
import mc322_slay.entity.Hero;

/**
 * Evento de escolha com risco/recompensa para alterar deck ou vida do herói.
 */
public class Choice extends Event {
    /**
     * Executa o fluxo principal de escolhas.
     */
    @Override
    public boolean init(Hero hero, CardStack possibleNewCards) {
        scanner = new Scanner(System.in);

        Interface.clearScreen();

        Interface.printInline("\r\n== ESCOLHA ==\r\n", ColorEnum.purple);
        Interface.printFile("choice.txt", ColorEnum.reset);
        Interface.printMessage("Você se depara com uma decisão difícil em sua jornada. Escolha com sabedoria: ",
                ColorEnum.reset);

        Random random = new Random();
        double prob0 = random.nextDouble(0.7, 0.9);

        Interface.printInline("\r\n0) Seleção/deleção de carta: \r\n", ColorEnum.yellow);
        Interface.printInline((int) (prob0 * 100) + "%", ColorEnum.green);
        Interface.printInline(" de chance de selecionar uma carta nova para ser adicionada ao baralho. \r\n",
                ColorEnum.reset);
        Interface.printInline(100 - (int) (prob0 * 100) + "%", ColorEnum.red);
        Interface.printInline(" de chance de deletar uma carta aleatória do baralho.\r\n", ColorEnum.reset);

        double health = random.nextDouble(0.2, 0.4);
        double prob1 = random.nextDouble(0.7, 0.9);

        Interface.printInline("\r\n1) Ganho/perda de vida: \r\n", ColorEnum.yellow);
        Interface.printInline((int) (prob1 * 100) + "%", ColorEnum.green);
        Interface.printInline(" de chance de recuperar " + (int) (health * 100) + "% da sua vida máxima. \r\n",
                ColorEnum.reset);
        Interface.printInline(100 - (int) (prob1 * 100) + "%", ColorEnum.red);
        Interface.printInline(" de chance de perder " + (int) (health * 100) + "% da sua vida máxima.\r\n",
                ColorEnum.reset);

        // outras oções.

        Interface.printInline("=============\r\n", ColorEnum.purple);

        int option = 0;
        while (true) {
            try {
                System.out.print("\r\nSelecione sua escolha (D para ver deck e vida): ");
                char response = scanner.next().charAt(0);
                if (response == 'D') {
                    Interface.printHeroInfo(hero);
                } else {
                    option = Integer.parseInt(response + "");
                    if (0 <= option && option <= 1) {
                        boolean goodOption;

                        switch (option) {
                            case 0:
                                goodOption = rollTheDices(prob0);
                                selectCard(hero, possibleNewCards, goodOption);
                                break;
                            case 1:
                                goodOption = rollTheDices(prob1);
                                return gainHealth(hero, goodOption, health);
                        }
                        break;
                    } else {
                        Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                    }
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }

        return true;
    }

    /**
     * Realiza um sorteio Bernoulli com a probabilidade informada.
     *
     * @param prob probabilidade de sucesso.
     * @return {@code true} em caso de sucesso.
     */
    private boolean rollTheDices(double prob) {
        Random random = new Random();
        double thisProb = random.nextDouble();
        if (thisProb <= prob) {
            Interface.printMessage("\r\nA sorte está com você.", ColorEnum.green);
            return true;
        } else {
            Interface.printMessage("\r\nHoje não é seu dia de sorte.", ColorEnum.red);
            return false;
        }
    }

    /**
     * Resolve a opção de manipulação de deck: ganhar carta ou remover aleatória.
     *
     * @param hero herói atual.
     * @param possibleNewCards pilha com cartas candidatas.
     * @param goodOption resultado do sorteio de sorte.
     */
    private void selectCard(Hero hero, CardStack possibleNewCards, boolean goodOption) {
        if (goodOption) {
            possibleNewCards.shuffle();

            Interface.clearScreen();

            Interface.printInline("\r\n== SELEÇÃO DE CARTA ==\r\n", ColorEnum.yellow);
            for (int i = 0; i < 3; i++) {
                Interface.printCardInfo(possibleNewCards.getStack().get(i), i);
            }
            Interface.printInline("=======================\r\n", ColorEnum.yellow);

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
                        if (-1 <= option && option < 3) {
                            if (option == -1)
                                break;

                            CardStack deck = hero.getDeck();
                            Card newCard = possibleNewCards.getStack().remove(option);
                            deck.add(newCard);
                            hero.setDeck(deck);

                            Interface.clearScreen();

                            Interface.printMessage("Carta " + newCard.getName() + " adicionada ao baralho.",
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
        } else {
            CardStack deck = hero.getDeck();
            deck.shuffle();
            Card removedCard = deck.remove();
            hero.setDeck(deck);

            Interface.printMessage("Carta " + removedCard.getName() + " removida ao baralho.", ColorEnum.yellow);
        }
    }

    /**
     * Resolve ganho ou perda de vida percentual sobre vida máxima.
     *
     * @param hero herói alvo.
     * @param goodOption resultado do sorteio.
     * @param health percentual (0-1) aplicado sobre vida máxima.
     * @return {@code true} se o herói permanecer vivo.
     */
    public boolean gainHealth(Hero hero, boolean goodOption, double health) {
        if (goodOption) {
            int healthIncrement = (int) (hero.getMaxHealth() * health);
            hero.gainHealth(healthIncrement);
            Interface.printMessage("Você recupera " + healthIncrement + " de vida.", ColorEnum.blue);
        } else {
            int healthDecrement = (int) (hero.getMaxHealth() * health);
            hero.takeDamage(healthDecrement);
            return hero.isAlive();
        }
        return true;
    }

    /**
     * @return descrição curta do evento para o mapa.
     */
    @Override
    public String getDescription() {
        return "Escolha";
    }

    public void printChar() {
        Interface.printInline("c", ColorEnum.green);
    }
}
