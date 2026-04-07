package mc322_slay;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import mc322_slay.card.Card;
import mc322_slay.card.CardStack;
import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.PlayerHand;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.Effect;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.EnemyActions;
import mc322_slay.entity.Hero;

public class Battle {
    private Hero hero;
    private Enemy angel;
    private int syncRate; // works identical to energy attribute
    static private final int nCards = 4;
    static private final int initialSync = 10;
    private Random random;
    private PlayerHand hand;
    private CardStack buyPile;
    private CardStack discardPile;
    private ArrayList<Effect> subscribers;
    private Scanner scanner;

    public Battle(Hero hero, Enemy angel, CardStack deck) {
        this.hero = hero;
        this.angel = angel;
        this.hand = new PlayerHand();
        this.buyPile = new CardStack(deck);
        this.discardPile = new CardStack();
        this.syncRate = initialSync;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.subscribers = new ArrayList<>();
    }

    public boolean performFight() {
        Interface.printMessage("\r\n=== BATALHA ÉPICA ===", ColorEnum.purple);
        Interface.printMessage(this.hero.getName() + " x " + this.angel.getName() + "\r\n", ColorEnum.purple);

        while (isRunning()) {
            notifySubscribers(EventEnum.playerStartOfTurn);
            Interface.printMessage("\r\n=== TURNO DO JOGADOR ===\r\n", ColorEnum.reset);

            buyCards();
            resetTurn();
            int enemyOption = enemyPlanning();
            while (!endOfTurn()) {
                int option = selectOption();
                playerAction(option);
            }
            notifySubscribers(EventEnum.playerEndOfTurn);

            if (isRunning()) {
                discardCards();

                notifySubscribers(EventEnum.enemyStartOfTurn);
                Interface.printMessage("\r\n=== TURNO DO INIMIGO ===\r\n", ColorEnum.reset);
                enemyAction(enemyOption);
                notifySubscribers(EventEnum.enemyEndOfTurn);
            }
        }

        return results();
    }

    /**
     * Registra um efeito para receber notificações de eventos do jogo.
     *
     * @param effect efeito a ser inscrito.
     */
    private void subscribe(Effect effect) {
        if (!subscribers.contains(effect))
            subscribers.add(effect);
    }

    /**
     * Remove um efeito da lista de inscritos.
     *
     * @param effect efeito a ser removido.
     */
    private void unsubscribe(Effect effect) {
        subscribers.remove(effect);
    }

    /**
     * Notifica todos os efeitos inscritos sobre um evento.
     *
     * @param event evento disparado no ciclo do jogo.
     */
    private void notifySubscribers(EventEnum event) {
        if (isRunning()) {
            ArrayList<Effect> effectsToBeRemoved = new ArrayList<>();
            for (Effect subscriber : this.subscribers) {
                // if effect has to be removed
                if (subscriber.beNotified(event, this))
                    effectsToBeRemoved.add(subscriber);
            }
            // remove effects that are no longer active
            for (Effect e : effectsToBeRemoved) {
                unsubscribe(e);
            }
        }
    }

    /**
     * Define e exibe a ação planejada do inimigo para o turno.
     *
     * @return valor numérico da ação escolhida.
     */
    int enemyPlanning() {
        int actionValue = angel.nextAction();
        EnemyActions action = EnemyActions.values()[actionValue];

        String message = "\r\n";
        switch (action) {
            case attack:
                message = "O inimigo " + angel.getName() + " pretende causar dano ao final do turno.\r\n";
                break;

            case gainShield:
                message = "O inimigo " + angel.getName()
                        + " pretende recuperar campo AT ao final do turno.\r\n";
                break;

            case useEffect:
                message = "O inimigo " + angel.getName()
                        + " pretende utilizar um efeito aleatório ao final do turno.\r\n";
                break;
        }
        Interface.printMessage(message, ColorEnum.red);
        return action.getValue();
    }

    /**
     * Verifica se a partida deve continuar.
     *
     * @return {@code true} se herói e inimigo estão vivos.
     */
    private boolean isRunning() {
        return hero.isAlive() && angel.isAlive();
    }

    /**
     * Move todas as cartas da mão para a pilha de descarte ao fim do turno do jogador.
     */
    private void discardCards() {
        hand.discardCards(discardPile);
    }

    /**
     * Restaura energia base e reseta o escudo.
     */
    private void resetTurn() {
        hero.resetShield();
        Interface.printMessage("Seu campo AT (escudo) foi zerado.", ColorEnum.blue);
        syncRate = initialSync;
        Interface.printMessage("Sua taxa de sincronização (energia) foi recuperada.", ColorEnum.blue);
    }

    /**
     * Indica se o turno atual chegou ao fim.
     *
     * @return {@code true} quando não há energia ou o combate terminou.
     */
    private boolean endOfTurn() {
        boolean end = !isRunning() || syncRate == 0;
        return end;
    }

    /**
     * Compra o número padrão de cartas para a mão do jogador.
     */
    private void buyCards() {
        for (int i = 0; i < nCards; i++) {
            hand.buyCard(buyPile, discardPile);
        }

        Interface.printMessage("Você compra " + nCards + " cartas da pilha de compra.", ColorEnum.blue);
    }

    /**
     * Lê e valida a opção de carta escolhida pelo jogador.
     *
     * @return índice da carta selecionada ou {@code -1} para encerrar turno.
     */
    private int selectOption() {
        Interface.printTurnInfo(hero, angel);

        Interface.showHand(hand.getHand(), syncRate, initialSync);

        int option = 0;
        while (true) {
            try {
                System.out.println("Qual carta deseja usar (-1 para passar o turno): ");
                option = Integer.parseInt(scanner.nextLine());
                if (-1 <= option && option < hand.nCards()) {
                    if (option == -1)
                        break;
                    int cardCost = hand.seeCardCost(option);
                    if (syncRate - cardCost >= 0) {
                        break;
                    } else {
                        Interface.printMessage("Você não tem energia o suficiente para usar essa carta!",
                                ColorEnum.yellow);
                    }
                } else {
                    Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }

        Interface.clearScreen();

        return option;
    }

    /**
     * Executa a ação do jogador com base na carta escolhida.
     *
     * @param option índice da carta na mão ou {@code -1} para passar.
     */
    private void playerAction(int option) {
        Interface.printMessage("\r\n", ColorEnum.reset);

        if (option == -1) {
            syncRate = 0; // end of turn
            Interface.printMessage("Você passa o turno.", ColorEnum.blue);
        } else {
            Card card = hand.useCard(option);
            if (card.getClass() == DamageCard.class) {

                int damage = card.getCost() * 10 + random.nextInt(card.getCost() * 10);
                if (hero.hasEffect(HighSyncRate.class)) {
                    damage = (int) (hero.getBoost() * damage);
                }
                if (hero.hasEffect(LowSyncRate.class)) {
                    damage = (int) (hero.getDeboost() * damage);
                }
                Interface.printMessage(
                        hero.getName() + " usa " + card.getName() + " contra " + angel.getName() + ".",
                        ColorEnum.green);

                card.useCard(angel, damage);

            } else if (card.getClass() == ShieldCard.class) {

                int shield = card.getCost() * 2 + random.nextInt(card.getCost() * 2);

                Interface.printMessage(hero.getName() + " usa " + card.getName() + " em si mesm*.", ColorEnum.green);

                card.useCard(hero, shield);

            } else if (card.getClass() == EffectCard.class) {

                EffectCard effectCard = (EffectCard) card;
                Effect effectX = effectCard.getEffect();

                if (effectX instanceof HealthRegeneration || effectX instanceof HighSyncRate) {
                    Interface.printMessage(hero.getName() + " usa " + card.getName() + " em si mesm*.",
                            ColorEnum.green);

                    card.useCard(hero, effectX.getStartPoints());
                    subscribe(hero.getLastEffect());
                } else {
                    Interface.printMessage(
                            hero.getName() + " usa " + card.getName() + " contra " + angel.getName() + ".",
                            ColorEnum.green);

                    card.useCard(angel, effectX.getStartPoints());
                    subscribe(angel.getLastEffect());
                }
            }
            syncRate -= card.getCost();
            discardPile.add(card);

            if (syncRate == 0) {
                Interface.printMessage("Sua energia acabou.", ColorEnum.yellow);
            }
        }
    }

    /**
     * Executa a ação do inimigo planejada para o fim do turno.
     *
     * @param enemyOption valor correspondente à ação do inimigo.
     */
    private void enemyAction(int enemyOption) {
        if (isRunning()) {

            EnemyActions action = EnemyActions.values()[enemyOption];

            switch (action) {
                case attack:
                    angel.attack(hero);
                    break;

                case gainShield:
                    int amount = random.nextInt(100) + 1;
                    Interface.printMessage(angel.getName() + " fortalece seu escudo.", ColorEnum.red);
                    angel.gainATField(amount);
                    break;

                case useEffect:
                    boolean selfInflicted = angel.useEffect(hero);
                    Effect lastEffect;
                    if (!selfInflicted) {
                        lastEffect = hero.getLastEffect();
                    } else {
                        lastEffect = angel.getLastEffect();
                    }
                    subscribe(lastEffect);
                    break;
            }
        }
    }

    /**
     * Exibe o resultado final da partida.
     * @return true se jogador venceu, false se perdeu
     */
    private boolean results() {
        Interface.sleep();

        Interface.printTurnInfo(hero, angel);

        if (hero.isAlive()) {
            Interface.printFile("youWin.txt");
            return true;
        } else {
            Interface.printFile("gameOver.txt");
            return false;
        }
    }
}
