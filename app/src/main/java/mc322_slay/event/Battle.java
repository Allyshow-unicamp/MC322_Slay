package mc322_slay.event;

import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.card.Card;
import mc322_slay.card.CardStack;
import mc322_slay.card.EffectCard;
import mc322_slay.card.PlayerHand;
import mc322_slay.effect.Effect;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.EnemyActions;
import mc322_slay.entity.Hero;
import mc322_slay.serializer.BattleSerializer;

/**
 * Representa um combate por turnos entre o herói e um anjo.
 * Gerencia compra de cartas, gasto de sincronização (energia), ações do jogador,
 * notificação de efeitos e turno do inimigo.
 */
@JsonSerialize(using = BattleSerializer.class)
public class Battle extends Event {
    /** Inimigo (anjo) enfrentado nesta batalha. */
    @JsonProperty("angel")
    private Enemy angel;

    /**
     * @return inimigo associado a esta batalha.
     */
    public Enemy getAngel() {
        return angel;
    }

    /**
     * Taxa de sincronização restante no turno atual; equivale à energia disponível
     * para jogar cartas.
     */
    private int syncRate;
    /** Quantidade de cartas compradas no início de cada turno do jogador. */
    static private final int nCards = 4;
    /** Valor inicial e máximo de sincronização recuperado a cada turno do jogador. */
    static private final int initialSync = 10;
    /** Mão de cartas do jogador. */
    private PlayerHand hand;
    /** Pilha de onde as cartas são compradas. */
    private CardStack buyPile;
    /** Pilha de descarte; quando a compra esgota, é reembaralhada para formar nova compra. */
    private CardStack discardPile;
    /** Efeitos ativos que recebem eventos do ciclo de batalha. */
    private ArrayList<Effect> subscribers;
    /** Leitor de entrada de ações do jogador no terminal. */
    private Scanner scanner;
    /** Mensagem textual com a intenção do inimigo no turno atual (exibida ao jogador). */
    private String enemyPlanning;

    /**
     * Cria uma batalha com o inimigo especificado.
     *
     * @param angel inimigo desta luta.
     */
    public Battle(Enemy angel) {
        this.angel = angel;
    }

    /**
     * Construtor vazio para desserialização.
     */
    public Battle() {
        super();
    }

    /**
     * Executa o loop da batalha até vitória ou derrota.
     *
     * @return {@code true} se o herói vencer; {@code false} se for derrotado.
     */
    @Override
    public boolean init(Hero hero, CardStack possibleNewCards) {
        this.hand = new PlayerHand();
        this.syncRate = initialSync;
        this.scanner = new Scanner(System.in);
        this.subscribers = new ArrayList<>();
        hero.getDeck().shuffle();
        this.buyPile = new CardStack(hero.getDeck());
        this.discardPile = new CardStack();

        Interface.clearScreen();

        Interface.printMessage("\r\n=== BATALHA ÉPICA ===", ColorEnum.purple);
        Interface.printMessage(hero.getName() + " x " + this.angel.getName(), ColorEnum.purple);

        while (isRunning(hero)) {
            notifySubscribers(EventEnum.playerStartOfTurn, hero);
            Interface.printMessage("\r\n=== TURNO DO JOGADOR ===\r\n", ColorEnum.reset);

            buyCards();
            resetTurn(hero);
            int enemyOption = enemyPlanning();
            while (!endOfTurn(hero)) {
                Interface.printMessage(enemyPlanning, ColorEnum.red);
                int option = selectOption(hero);
                playerAction(option, hero);
            }
            notifySubscribers(EventEnum.playerEndOfTurn, hero);

            if (isRunning(hero)) {
                discardCards();

                notifySubscribers(EventEnum.enemyStartOfTurn, hero);
                Interface.printMessage("\r\n=== TURNO DO INIMIGO ===\r\n", ColorEnum.reset);
                enemyAction(enemyOption, hero);
                notifySubscribers(EventEnum.enemyEndOfTurn, hero);
            }
        }

        boolean won = results(hero);

        if (hero.isAlive()) {
            Reward reward = new Reward();
            reward.init(hero, possibleNewCards);
        }

        return won;
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
    private void notifySubscribers(EventEnum event, Hero hero) {
        if (isRunning(hero)) {
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
     * Sorteia o dano base do próximo ataque do anjo, define a ação do turno e monta
     * a mensagem exibida ao jogador.
     *
     * @return índice numérico compatível com {@code EnemyActions.values()[índice]}.
     */
    int enemyPlanning() {
        int actionValue = angel.nextAction();
        EnemyActions action = EnemyActions.values()[actionValue];

        enemyPlanning = "\r\n";
        switch (action) {
            case attack:
                enemyPlanning = "O inimigo " + angel.getName() + " pretende causar dano ao final do turno.\r\n";
                break;

            case gainShield:
                enemyPlanning = "O inimigo " + angel.getName()
                        + " pretende recuperar campo AT ao final do turno.\r\n";
                break;

            case useEffect:
                enemyPlanning = "O inimigo " + angel.getName()
                        + " pretende utilizar um efeito aleatório ao final do turno.\r\n";
                break;
        }
        return action.getValue();
    }

    /**
     * Verifica se a partida deve continuar.
     *
     * @return {@code true} se herói e inimigo estão vivos.
     */
    private boolean isRunning(Hero hero) {
        return hero.isAlive() && angel.isAlive();
    }

    /**
     * Move todas as cartas da mão para a pilha de descarte ao fim do turno do
     * jogador.
     */
    private void discardCards() {
        hand.discardCards(discardPile);
    }

    /**
     * Restaura energia base e reseta o escudo.
     */
    private void resetTurn(Hero hero) {
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
    private boolean endOfTurn(Hero hero) {
        boolean end = !isRunning(hero) || syncRate == 0;
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
    private int selectOption(Hero hero) {
        Interface.printTurnInfo(hero, angel);

        Interface.showHand(hand.getHand(), syncRate, initialSync);

        int option = 0;
        while (true) {
            try {
                System.out.print("Qual carta deseja usar (-1 para passar o turno): ");
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
    private void playerAction(int option, Hero hero) {
        Interface.printMessage("", ColorEnum.reset);

        if (option == -1) {
            syncRate = 0; // end of turn
            Interface.printMessage("Você passa o turno.", ColorEnum.blue);
        } else {
            Card card = hand.useCard(option);
            card.useCard(hero, angel);
            if (card instanceof EffectCard) {
                Effect effect = ((EffectCard)card).getEffect();
                if (effect instanceof HealthRegeneration || effect instanceof HighSyncRate)
                    subscribe(hero.getLastEffect());
                else
                    subscribe(angel.getLastEffect());
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
    private void enemyAction(int enemyOption, Hero hero) {
        if (isRunning(hero)) {

            EnemyActions action = EnemyActions.values()[enemyOption];

            switch (action) {
                case attack:
                    angel.attack(hero);
                    break;

                case gainShield:
                    angel.gainATField();;
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
     * Exibe o resultado final da batalha (vitória ou game over).
     *
     * @return {@code true} se o jogador venceu; {@code false} se perdeu.
     */
    private boolean results(Hero hero) {
        Interface.printTurnInfo(hero, angel);

        Interface.clearScreen();

        System.out.print("\r\n\r\n");

        if (hero.isAlive()) {
            Interface.printFile("victory.txt", ColorEnum.green);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Batalha";
    }
}
