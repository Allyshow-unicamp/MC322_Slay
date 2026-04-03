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
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;
import mc322_slay.entity.EnemyActions;

/**
 * Orquestra o estado do jogo, incluindo turnos, baralhos, ações e efeitos.
 */
public class GameManager {
    static private final int nCards = 4;
    static private final int initialSync = 10;

    private Hero hero;
    private ArrayList<Enemy> angels;
    private int syncRate; // works identical to energy attribute
    private Scanner scanner;
    private Random random;
    private PlayerHand hand;
    private CardStack buyPile;
    private CardStack discardPile;
    private ArrayList<Effect> subscribers;

    /**
     * Registra um efeito para receber notificações de eventos do jogo.
     *
     * @param effect efeito a ser inscrito.
     */
    public void subscribe(Effect effect) {
        if (!subscribers.contains(effect))
            subscribers.add(effect);
    }

    /**
     * Remove um efeito da lista de inscritos.
     *
     * @param effect efeito a ser removido.
     */
    public void unsubscribe(Effect effect) {
        subscribers.remove(effect);
    }

    /**
     * Notifica todos os efeitos inscritos sobre um evento.
     *
     * @param event evento disparado no ciclo do jogo.
     */
    public void notifySubscribers(EventEnum event) {
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
     * Preenche o baralho de compra com cartas iniciais da partida.
     */
    void populateDeck() {
        buyPile.add(new DamageCard("Longinus Spear", 10, "Use-a para dar de 100 a 200 de dano"));
        buyPile.add(new DamageCard("Cassius Spear", 9, "Use-a para dar de 90 a 180 de dano"));
        buyPile.add(new DamageCard("Positron Sniper Rifle", 8, "Use-a para dar de 80 a 160 de dano"));
        buyPile.add(new DamageCard("Prog Knife", 7, "Use-a para dar de 70 a 140 de dano"));
        buyPile.add(new DamageCard("Magokoru Sword", 6, "Use-a para dar de 60 a 120 de dano"));
        buyPile.add(new DamageCard("Pallet Rifle", 5, "Use-a para dar de 50 a 100 de dano"));
        buyPile.add(new DamageCard("Azumaterasu", 4, "Use-a para dar de 40 a 80 de dano"));
        buyPile.add(new DamageCard("Smash Hawk", 3, "Use-a para dar de 30 a 60 de dano"));
        buyPile.add(new DamageCard("N2 Weapon II", 2, "Use-a para dar de 20 a 40 de dano"));
        buyPile.add(new DamageCard("N2 Weapon", 1, "Use-a para dar de 10 a 20 de dano"));
        buyPile.add(new DamageCard("Positron Sniper Rifle", 8, "Use-a para dar de 80 a 160 de dano"));
        buyPile.add(new DamageCard("Prog Knife", 7, "Use-a para dar de 70 a 140 de dano"));
        buyPile.add(new DamageCard("Magokoru Sword", 6, "Use-a para dar de 60 a 120 de dano"));
        buyPile.add(new DamageCard("Pallet Rifle", 5, "Use-a para dar de 50 a 100 de dano"));
        buyPile.add(new DamageCard("Azumaterasu", 4, "Use-a para dar de 40 a 80 de dano"));
        buyPile.add(new DamageCard("Smash Hawk", 3, "Use-a para dar de 30 a 60 de dano"));
        buyPile.add(new DamageCard("N2 Weapon II", 2, "Use-a para dar de 20 a 40 de dano"));
        buyPile.add(new DamageCard("N2 Weapon", 1, "Use-a para dar de 10 a 20 de dano"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 1", 1,
                "Restaura a integridade do Campo AT entre 2 e 4 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 2", 2,
                "Restaura a integridade do Campo AT entre 4 e 8 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 3", 3,
                "Restaura a integridade do Campo AT entre 6 e 12 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 4", 4,
                "Restaura a integridade do Campo AT entre 8 e 16 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 5", 5,
                "Restaura a integridade do Campo AT entre 10 e 20 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 1", 6,
                "Restaura a integridade do Campo AT entre 12 e 24 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 2", 7,
                "Restaura a integridade do Campo AT entre 14 e 28 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 3", 8,
                "Restaura a integridade do Campo AT entre 16 e 32 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 4", 9,
                "Restaura a integridade do Campo AT entre 18 e 36 pontos"));
        buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 5", 10,
                "Restaura a integridade do Campo AT entre 20 e 40 pontos"));
        Effect effect1 = new PsychicEffect("Dano psicológico", 30, 3);
        Effect effect2 = new PsychicEffect("Dano psicológico", 60, 3);
        Effect effect3 = new PsychicEffect("Dano psicológico", 90, 3);
        buyPile.add(new EffectCard("Dano psicológico 1", 3,
                "Use-a para dar 30 de dano por 3 turnos", effect1));
        buyPile.add(new EffectCard("Dano psicológico 1", 3,
                "Use-a para dar 30 de dano por 3 turnos", effect1));
        buyPile.add(new EffectCard("Dano psicológico 2", 6,
                "Use-a para dar 60 de dano por 3 turnos", effect2));
        buyPile.add(new EffectCard("Dano psicológico 2", 6,
                "Use-a para dar 60 de dano por 3 turnos", effect2));
        buyPile.add(new EffectCard("Dano psicológico 3", 9,
                "Use-a para dar 90 de dano por 3 turnos", effect3));
        buyPile.add(new EffectCard("Restauração Forçada do pulso vital 1", 3,
                "Use-a para restaurar 10 pontos de vida por 3 turnos",
                new HealthRegeneration("Restauração Forçada do pulso vital", 10, 3)));
        buyPile.add(new EffectCard("Restauração Forçada do pulso vital 2", 6,
                "Use-a para restaurar 20 pontos de vida por 3 turnos",
                new HealthRegeneration("Restauração Forçada do pulso vital", 20, 3)));
        buyPile.add(new EffectCard("Restauração Forçada do pulso vital 3", 9,
                "Use-a para restaurar 30 pontos de vida por 3 turnos",
                new HealthRegeneration("Restauração Forçada do pulso vital", 30, 3)));
        Effect effect4 = new HighSyncRate("Alta taxa de sincronização", 2, 1.5);
        buyPile.add(new EffectCard("Alta taxa de sincronização", 4,
                "Use-a para aumentar em 50% o dano causado pelo armas do jogador por 2 turnos", effect4));
        buyPile.add(new EffectCard("Alta taxa de sincronização", 4,
                "Use-a para aumentar em 50% o dano causado pelo armas do jogador por 2 turnos", effect4));
        buyPile.add(new EffectCard("Alta taxa de sincronização", 4,
                "Use-a para aumentar em 50% o dano causado pelo armas do jogador por 2 turnos", effect4));
        Effect effect5 = new LowSyncRate("Baixa taxa de sincronização", 2, 0.75);
        buyPile.add(new EffectCard("Baixa da taxa de sincronização", 4,
                "Use-a para reduzir em 25% o dano causado pelo ataque do inimigo por 2 turnos", effect5));
        buyPile.add(new EffectCard("Baixa taxa de sincronização", 4,
                "Use-a para reduzir em 25% o dano causado pelo ataque do inimigo por 2 turnos", effect5));
        buyPile.add(new EffectCard("Baixa taxa de sincronização", 4,
                "Use-a para reduzir em 25% o dano causado pelo ataque do inimigo por 2 turnos", effect5));
        buyPile.add(new EffectCard("Corrosão do campo AT 1", 6,
                "Use-a para anular o campo AT do inimigo por 2 turnos",
                new ATFieldCorrosion("Corrosão do campo AT", 2)));
        buyPile.add(new EffectCard("Corrosão do campo AT 2", 8,
                "Use-a para anular o campo AT do inimigo por 3 turnos",
                new ATFieldCorrosion("Corrosão do campo AT", 3)));

        buyPile.shuffle();
        Interface.printMessage("A pilha de compra foi embaralhada!", ColorEnum.blue);
    }

    /**
     * Inicializa os objetos principais e variáveis de estado da partida.
     */
    public void start() {
        this.hero = new Hero("", 40, 0, "eva.txt");
        this.angels = new ArrayList<Enemy>();
        this.angels.add(new Enemy("Sachiel", 300, 200, "sachiel.txt"));
        this.hand = new PlayerHand();
        this.buyPile = new CardStack();
        this.discardPile = new CardStack();
        this.syncRate = initialSync;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.subscribers = new ArrayList<>();
    }

    /**
     * Define e exibe a ação planejada do inimigo para o turno.
     *
     * @return valor numérico da ação escolhida.
     */
    int enemyPlanning() {
        int actionValue = angels.get(0).nextAction();
        EnemyActions action = EnemyActions.values()[actionValue];

        String message = "\r\n";
        switch (action) {
            case attack:
                message = "O inimigo " + angels.get(0).getName() + " pretende causar dano ao final do turno.\r\n";
                break;

            case gainShield:
                message = "O inimigo " + angels.get(0).getName()
                        + " pretende recuperar campo AT ao final do turno.\r\n";
                break;

            case useEffect:
                message = "O inimigo " + angels.get(0).getName()
                        + " pretende utilizar um efeito aleatório ao final do turno.\r\n";
                break;
        }
        Interface.printMessage(message, ColorEnum.red);
        return action.getValue();
    }

    /**
     * Exibe a tela inicial e aguarda confirmação para começar.
     */
    public void initialScreen() {
        Interface.clearScreen();
        Interface.printFile("initialScreenArt.txt");
        System.out.println("Pressione qualquer tecla para iniciar.");
        scanner.nextLine();
    }

    /**
     * Permite ao jogador escolher o personagem controlado.
     */
    public void selectCharacter() {
        Interface.printFile("selectCharacter.txt");
        int option;
        String name = "";
        while (true) {
            try {
                System.out.print("Selecione o piloto de EVA: ");
                option = Integer.parseInt(scanner.nextLine());
                if (0 < option && option < 4) {
                    break;
                } else {
                    Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
                }
            } catch (Exception e) {
                Interface.printMessage("Digite uma opção válida!", ColorEnum.yellow);
            }
        }
        switch (option) {
            case 1:
                name = "Shinji Ikari";
                break;
            case 2:
                name = "Rei Ayanami";
                break;
            case 3:
                name = "Asuka Langley Soryu";
                break;
        }
        hero.setName(name);

        Interface.clearScreen();

        Interface.printMessage("\r\n" + hero.getName() + " selecionad*.\r\n", ColorEnum.reset);
    }

    /**
     * Verifica se a partida deve continuar.
     *
     * @return {@code true} se herói e inimigo estão vivos.
     */
    public boolean isRunning() {
        return hero.isAlive() && angels.get(0).isAlive();
    }

    public void discardCards() {
        hand.discardCards(discardPile);
    }

    /**
     * Restaura energia base e reseta o escudo.
     */
    public void resetTurn() {
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
    public boolean endOfTurn() {
        boolean end = !isRunning() || syncRate == 0;
        return end;
    }

    /**
     * Compra o número padrão de cartas para a mão do jogador.
     */
    public void buyCards() {
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
    public int selectOption() {
        Interface.printTurnInfo(hero, angels.get(0));

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
    public void playerAction(int option) {
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
                        hero.getName() + " usa " + card.getName() + " contra " + angels.get(0).getName() + ".",
                        ColorEnum.green);

                card.useCard(angels.get(0), damage);

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
                            hero.getName() + " usa " + card.getName() + " contra " + angels.get(0).getName() + ".",
                            ColorEnum.green);

                    card.useCard(angels.get(0), effectX.getStartPoints());
                    subscribe(angels.get(0).getLastEffect());
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
    public void enemyAction(int enemyOption) {
        if (isRunning()) {

            EnemyActions action = EnemyActions.values()[enemyOption];

            switch (action) {
                case attack:
                    angels.get(0).attack(hero);
                    break;

                case gainShield:
                    int amount = random.nextInt(100) + 1;
                    Interface.printMessage(angels.get(0).getName() + " fortalece seu escudo.", ColorEnum.red);
                    angels.get(0).gainATField(amount);
                    break;

                case useEffect:
                    boolean selfInflicted = angels.get(0).useEffect(hero);
                    Effect lastEffect;
                    if (!selfInflicted) {
                        lastEffect = hero.getLastEffect();
                    } else {
                        lastEffect = angels.get(0).getLastEffect();
                    }
                    subscribe(lastEffect);
                    break;
            }
        }
    }

    /**
     * Exibe o resultado final da partida.
     */
    public void results() {
        Interface.sleep();

        Interface.printTurnInfo(hero, angels.get(0));

        if (hero.isAlive()) {
            Interface.printFile("youWin.txt");
        } else {
            Interface.printFile("gameOver.txt");
        }
    }
}
