package mc322_slay;

import java.util.Random;
import java.util.Scanner;

import mc322_slay.card.CardStack;
import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.Effect;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.PsychicEffect;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Orquestra o estado do jogo, incluindo turnos, baralhos, ações e efeitos.
 */
public class GameManager {
    private Hero hero;
    private Scanner scanner;
    private CardStack deck;
	private Random random;

    /**
     * Preenche o baralho de compra com cartas iniciais da partida.
     */
    void populateDeck() {
        deck.add(new DamageCard("Longinus Spear", 10, "Use-a para dar de 100 a 200 de dano"));
        deck.add(new DamageCard("Cassius Spear", 9, "Use-a para dar de 90 a 180 de dano"));
        deck.add(new DamageCard("Positron Sniper Rifle", 8, "Use-a para dar de 80 a 160 de dano"));
        deck.add(new DamageCard("Prog Knife", 7, "Use-a para dar de 70 a 140 de dano"));
        deck.add(new DamageCard("Magokoru Sword", 6, "Use-a para dar de 60 a 120 de dano"));
        deck.add(new DamageCard("Pallet Rifle", 5, "Use-a para dar de 50 a 100 de dano"));
        deck.add(new DamageCard("Azumaterasu", 4, "Use-a para dar de 40 a 80 de dano"));
        deck.add(new DamageCard("Smash Hawk", 3, "Use-a para dar de 30 a 60 de dano"));
        deck.add(new DamageCard("N2 Weapon II", 2, "Use-a para dar de 20 a 40 de dano"));
        deck.add(new DamageCard("N2 Weapon", 1, "Use-a para dar de 10 a 20 de dano"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 1", 1,
                "Restaura a integridade do Campo AT entre 2 e 4 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 2", 2,
                "Restaura a integridade do Campo AT entre 4 e 8 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 3", 3,
                "Restaura a integridade do Campo AT entre 6 e 12 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 4", 4,
                "Restaura a integridade do Campo AT entre 8 e 16 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 5", 5,
                "Restaura a integridade do Campo AT entre 10 e 20 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 6", 6,
                "Restaura a integridade do Campo AT entre 12 e 24 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 7", 7,
                "Restaura a integridade do Campo AT entre 14 e 28 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 8", 8,
                "Restaura a integridade do Campo AT entre 16 e 32 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 9", 9,
                "Restaura a integridade do Campo AT entre 18 e 36 pontos"));
        deck.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 10", 10,
                "Restaura a integridade do Campo AT entre 20 e 40 pontos"));
        Effect effect1 = new PsychicEffect("Dano psicológico", 30, 3);
        Effect effect2 = new PsychicEffect("Dano psicológico", 60, 3);
        Effect effect3 = new PsychicEffect("Dano psicológico", 90, 3);
        deck.add(new EffectCard("Dano psicológico 1", 3,
                "Use-a para dar 30 de dano por 3 turnos", effect1));
        deck.add(new EffectCard("Dano psicológico 2", 6,
                "Use-a para dar 60 de dano por 3 turnos", effect2));
        deck.add(new EffectCard("Dano psicológico 3", 9,
                "Use-a para dar 90 de dano por 3 turnos", effect3));
        deck.add(new EffectCard("Restauração Forçada do pulso vital 1", 3,
                "Use-a para restaurar 10 pontos de vida por 3 turnos",
                new HealthRegeneration("Restauração Forçada do pulso vital", 10, 3)));
        deck.add(new EffectCard("Restauração Forçada do pulso vital 2", 6,
                "Use-a para restaurar 20 pontos de vida por 3 turnos",
                new HealthRegeneration("Restauração Forçada do pulso vital", 20, 3)));
        deck.add(new EffectCard("Restauração Forçada do pulso vital 3", 9,
                "Use-a para restaurar 30 pontos de vida por 3 turnos",
                new HealthRegeneration("Restauração Forçada do pulso vital", 30, 3)));
        Effect effect4 = new HighSyncRate("Alta taxa de sincronização", 2, 1.5);
        deck.add(new EffectCard("Alta taxa de sincronização", 4,
                "Use-a para aumentar em 50% o dano causado pelas armas do jogador por 2 turnos", effect4));
        deck.add(new EffectCard("Alta taxa de sincronização", 4,
                "Use-a para aumentar em 50% o dano causado pelas armas do jogador por 2 turnos", effect4));
        Effect effect5 = new LowSyncRate("Baixa taxa de sincronização", 2, 0.75);
        deck.add(new EffectCard("Baixa taxa de sincronização", 4,
                "Use-a para reduzir em 25% o dano causado pelo ataque do inimigo por 2 turnos", effect5));
        deck.add(new EffectCard("Baixa taxa de sincronização", 4,
                "Use-a para reduzir em 25% o dano causado pelo ataque do inimigo por 2 turnos", effect5));
        deck.add(new EffectCard("Corrosão do campo AT 1", 4,
                "Use-a para anular o campo AT do inimigo por 2 turnos",
                new ATFieldCorrosion("Corrosão do campo AT", 2)));
        deck.add(new EffectCard("Corrosão do campo AT 2", 6,
                "Use-a para anular o campo AT do inimigo por 3 turnos",
                new ATFieldCorrosion("Corrosão do campo AT", 3)));

        deck.shuffle();
        Interface.printMessage("O baralho foi embaralhado!", ColorEnum.blue);
    }

    /**
     * Inicializa os objetos principais e variáveis de estado da partida.
     */
    public void start() {
        this.hero = new Hero("", 40, 0, "eva.txt");
        this.deck = new CardStack();
		this.random = new Random();
		this.scanner = new Scanner(System.in);
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

	private void resetBattle() {
		this.hero.resetEffects();
	}

    public void performNBattles(int n) {
		boolean won = true;
        for (int i = 0; i < n; i++) {
            Battle battle = new Battle(hero, new Enemy("Angel", 300 + random.nextInt(150), 200 + random.nextInt(100), "sachiel.txt"), deck);
            if (!battle.performFight()) {
				won = false;
				break;
			}
			resetBattle();
        }

		if (won) 
			Interface.printFile("victory.txt");
    }
}
