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
import mc322_slay.effect.PsychicEffect;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

public class GameManager {
	static private final int nCards = 4;
	static private final int playerHealth = 40;
	static private final int playerField = 40;
	static private final int enemyHealth = 200;
	static private final int enemyField = 200;
	static private final int timeSleep = 1000;
	static private final int initialSync = 10;

	private Hero hero;
	private ArrayList<Enemy> angels;
	private int syncRate; // works identical to energy attribute
	private Scanner scanner;
	private Random random;
	private PlayerHand hand;
	private CardStack buyPile;
	private CardStack discardPile;
	private int turn;
	private ArrayList<Effect> subscribers;

	public void subscribe(Effect effect) {
		subscribers.add(effect);
	}

	public void unsubscribe(Effect effect) {
		subscribers.remove(effect);
	}

	public void notifySubscribers(EventEnum event) {
		for (Effect subscriber : this.subscribers) {
			subscriber.beNotified(event, this);
		}
	}

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
		buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 1", 2,
				"Restaura a integridade do Campo AT entre 4 e 8 pontos"));
		buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 2", 4,
				"Restaura a integridade do Campo AT entre 8 e 16 pontos"));
		buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 3", 6,
				"Restaura a integridade do Campo AT entre 12 e 24 pontos"));
		buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 4", 8,
				"Restaura a integridade do Campo AT entre 16 e 32 pontos"));
		buyPile.add(new ShieldCard("Regeneração do Campo de Terror Absoluto 5", 10,
				"Restaura a integridade do Campo AT entre 20 e 40 pontos"));
		buyPile.add(new EffectCard("Dano psicológico 1", 3,
				"Use-a para dar 20 de dano por 3 turnos", new PsychicEffect(20, 3)));
		buyPile.add(new EffectCard("Dano psicológico 2", 6,
				"Use-a para dar 40 de dano por 3 turnos", new PsychicEffect(40, 3)));

		buyPile.shuffle();
	}

	public void start() {
		this.hero = new Hero("", playerHealth, playerField);
		this.angels = new ArrayList<Enemy>();
		this.angels.add(new Enemy("Sachiel", enemyHealth, enemyField));
		this.hand = new PlayerHand();
		this.buyPile = new CardStack();
		this.discardPile = new CardStack();
		this.syncRate = initialSync;
		this.scanner = new Scanner(System.in);
		this.random = new Random();
		this.turn = 0;
		this.subscribers = new ArrayList<>();

		this.populateDeck();
	}

	public Hero getHero() {
		return hero;
	}
	public ArrayList<Enemy> getAngels() {
		return angels;
	}

	void enemyPlanning() {
		int damage = this.angels.get(0).nextAction();

		System.out.println("\r\nO inimigo " + angels.get(0).getName() + " pretende dar " + damage + " de dano ao final do turno");
	}

	public void initialScreen() {
		System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
				+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
				+ //
				"⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠠⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀"
				+ "⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀⡀⠀⢀⠀⠀\r\n"
				+ //
				"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠄⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀"
				+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀\r\n"
				+ //
				"⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠀⠁⠀⠀⢀⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡁⠀⠀⠀⠁⠀⠀⠂⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐"
				+ "⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠐⠀⠈⠀⠀⠀\r\n"
				+ //
				"⠀⠀⠄⠀⠀⠀⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠁⠀⠀⢀⣀⣤⣢⣤⠄⠀⠀⠀⠀⠀⠀⡀⠀⣤⣀⣄⠂⠀⠀⠀⠀⠠⠐⠀⠀⠀⡀⠐⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀"
				+ "⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠀⠀⠀⠄⠂⠁\r\n"
				+ //
				"⠀⡀⠀⠀⡀⠁⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⣤⡖⡼⢻⠋⠉⠀⠀⠈⠀⠀⠀⠂⠀⠀⠂⠀⠉⠻⠿⡄⣆⠀⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠂⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀"
				+ "⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀\r\n"
				+ //
				"⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀⠠⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠀⠈⡟⢠⠃⠁⠀⠀⠀⠀⠀⠀⠀⣶⠀⠀⠀⠀⠀⠀⠀⠓⠤⢉⢿⠀⠀⠀⠂⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈"
				+ "⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠐⠈⠀⠀⠀⠀⠂\r\n"
				+ //
				"⠀⠀⠂⠁⠀⠀⠂⠀⠀⠠⠀⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⠂⠀⠀⠀⠘⠀⠀⠀⠀⠀⡄⢠⡄⠀⣼⣿⣇⠐⣦⠀⠀⠀⢀⠀⠀⠌⠎⠀⠀⠀⠀⡀⠐⠀⠀⠀⠀⠠⠀⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀"
				+ "⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠀\r\n"
				+ //
				"⠀⠁⠀⠀⠀⠄⠀⠀⠠⠀⠀⠀⠐⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠂⠀⠠⠀⠀⠀⠀⠀⡠⢀⣾⠄⣾⡆⢠⣿⣿⣿⣀⣿⣧⢈⣷⣼⡄⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠐⠀⠀⠀⢀⠀⠁⠀⠀⠀⠠⠀⠀⠀⠀⠠"
				+ "⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠠\r\n"
				+ //
				"⠀⠀⠀⠐⠀⠀⠀⠄⠀⠀⢀⠈⠀⠀⠀⠐⠀⠀⠀⠀⠄⠂⠀⠀⠀⠀⠂⠀⠀⢀⠀⠂⠀⠀⢀⠀⠈⠀⠀⢀⠀⠄⠀⢀⠠⡁⠖⡼⠻⢾⢿⣿⣼⣿⣿⣿⣷⡿⠿⠧⢻⣟⣿⣤⡃⢀⠀⠀⠂⠀⠀⠀⠂⠁⠀⠀⡀⢈⡤⡴⣤⠶⣤⣣⠴⣤⢆⣔⣠⠀"
				+ "⡀⠀⠀⠂⠀⠀⢀⠀⠂⠀⠀⢀⠀⠂⠀⠀⢀⠀⠂⠀\r\n"
				+ //
				"⡀⠈⠀⠀⢀⠀⠂⠀⠀⠠⠀⠀⠀⡀⠈⠀⠀⠀⠐⠀⠀⠀⠀⡀⠈⠀⠀⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⠠⠀⠀⠀⠀⠐⠨⠄⡹⢸⢠⣧⠀⢲⣤⣬⣿⣿⣿⣴⣲⣦⠐⢳⣮⣻⣿⠥⣋⠀⠀⠀⡀⠈⠀⠀⠀⣄⢢⡔⣯⢞⣵⣫⢟⡶⣭⢟⢮⡻⣜⢧⢿"
				+ "⣱⢦⣄⠀⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⠀\r\n"
				+ //
				"⠀⠀⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠀⠀⠀⡀⠁⠀⠀⢀⠀⠁⠀⠀⢀⠐⠀⡀⢀⠀⠐⠀⠀⠄⡐⠰⠦⠀⠀⠀⠂⠀⠉⢆⠡⣋⠞⣿⣷⣾⣿⣿⡟⣿⣿⣿⣿⣿⣷⣿⣿⣿⢯⠒⠃⠀⢀⠀⠀⠀⢀⠠⣝⢮⣳⠽⣎⡟⣶⠹⣮⣵⣾⣿⠶⣯⡝⣞⣧"
				+ "⣛⠾⣜⣻⢤⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀\r\n"
				+ //
				"⠀⠀⠂⠀⠀⠀⠀⠂⠀⠀⠀⠈⠀⠀⠀⠁⠀⠀⠀⠄⠀⢀⠠⢐⠨⠐⠌⢂⠑⠨⠘⡄⠣⢌⠠⠐⠀⠀⠐⠀⠠⠀⠄⡀⠈⠒⡈⠳⣌⠻⠿⣟⢣⢚⢸⣿⣿⣿⣿⣿⣿⣿⠟⠀⠃⠀⠀⠀⠀⠀⢀⠂⣼⡹⣎⠷⢋⡵⢋⣾⣿⣸⣿⠿⣍⡻⢶⢙⡞⢲"
				+ "⣭⢳⠹⣎⢯⢧⡈⠄⠀⠀⠀⠂⠁⠀⠀⢀⠀⠁⠀⠀\r\n"
				+ //
				"⠈⠀⠀⠀⠄⠈⠀⠀⢀⠀⠁⠀⠀⠈⠀⠀⢀⠀⢢⠠⣁⠂⡌⠰⢢⡝⣎⠆⣉⢂⠡⡀⠑⠂⢆⠡⠂⠄⠐⠀⠂⠔⣠⢀⠀⠀⠀⠱⢌⢣⠏⣔⡋⢖⣾⣿⣿⣿⣿⣿⣿⠏⠀⠀⠀⢀⠀⠁⠀⠈⠠⠐⠰⡛⠌⡁⠢⠐⣿⣿⠇⡻⢌⡷⣸⠝⡣⡌⢹⠂"
				+ "⠜⣯⢣⠘⡌⢷⡆⠈⠀⠠⠀⠀⢀⠠⠀⠀⠀⠀⠀⠂\r\n"
				+ //
				"⠀⠀⠐⠀⠀⠀⠀⠄⠀⠀⠀⠀⠂⠀⠀⠄⢀⡘⠄⡃⢄⠣⡈⢅⠣⠙⢮⡱⡄⢊⠔⡁⠣⠌⡀⠄⣈⠦⢁⠀⠈⢆⠠⢉⠄⠀⡀⠀⠀⠃⢞⡰⢍⡖⣿⣶⣿⣿⣿⠟⠁⠀⠀⠀⠈⠀⠀⠀⠀⠌⠠⠁⡐⠀⢂⠀⠡⠈⠿⢻⡓⢡⡞⢰⢏⡜⣱⣿⡀⠁"
				+ "⠄⡈⢷⡀⠄⡩⢞⠀⠀⠀⠀⡀⠀⠀⠀⠀⡀⠈⠀⠀\r\n"
				+ //
				"⠀⠁⠀⠀⠀⠐⠀⠀⠀⢀⠀⠁⠀⠀⠌⠒⠤⡘⢠⠑⡌⠰⣁⠊⡔⣩⢦⡳⡉⠔⢢⠐⠡⠐⠈⠐⡀⢠⠖⠈⠀⣌⠲⣌⠠⠁⠀⠀⠀⠠⠀⠙⠲⡜⣿⣿⡿⠏⢁⡀⠀⠀⠀⠐⠀⠀⢀⠀⠡⢈⠐⠠⢀⠡⠀⠌⠐⡀⠂⠅⡰⡏⢄⡩⣞⣬⣿⣟⡛⠄"
				+ "⢂⠀⡘⡄⠠⠘⡜⠀⠀⠀⠁⠀⠀⠀⠐⠀⠀⠀⠀⠠\r\n"
				+ //
				"⠀⠀⢀⠀⠁⠀⠀⠠⠀⠀⠀⠀⡀⢐⡬⡑⢂⡘⠄⣳⢬⠧⢄⢃⡘⠡⠓⠥⠑⢨⠀⡉⠐⠡⠂⢀⡐⠌⠃⢌⠀⢌⢿⡘⢇⠐⠀⠀⠂⢨⡑⢦⡐⣈⠉⢁⢤⣺⣧⣿⠀⠀⠁⠀⠀⡀⠀⠀⢀⠂⠌⠠⠀⡐⠈⡀⠂⠄⡐⢸⡳⣥⢎⠷⣿⣿⣷⣾⣿⢬"
				+ "⣦⡐⠠⠀⢀⠁⠀⠀⠀⠂⠀⠀⠐⠈⠀⠀⠀⠀⠂⠀\r\n"
				+ //
				"⡀⠈⠀⠀⠀⠀⠂⠀⠀⠀⠐⠀⠀⢎⡷⠁⡶⡜⠐⡈⢣⠐⠂⠄⢤⣁⡀⣀⣀⢀⡀⡐⠠⠀⠈⠀⣴⢊⠄⠈⠆⠈⢆⠱⣈⠂⢡⠀⡐⣶⣤⣀⠱⡆⣄⢮⣾⣋⣡⣻⣧⢀⡀⠄⠀⠀⠀⢀⠀⢈⠐⡀⢁⠀⡐⢀⠁⠲⣹⠂⢿⣜⠩⡞⣿⣿⣿⣿⣯⣿"
				+ "⣿⡏⠀⠀⠀⠀⠀⡀⠁⠀⠀⠐⠀⠀⠀⡀⠈⠀⠀⠀\r\n"
				+ //
				"⠀⠀⠀⡀⠈⠀⠀⠀⡀⠈⠀⠈⠀⠌⠠⢁⠣⠑⠀⠡⡀⠊⠔⢬⡀⠡⠜⣡⣦⠄⢈⣽⣦⡅⣆⠡⣌⡻⡇⠈⠰⠁⡌⠒⣠⣽⣶⢟⣼⣿⠾⣿⣷⣼⣾⣿⣿⣿⡿⣟⣷⠺⣿⣶⢀⡄⠀⠀⠀⠀⢂⠐⠠⢀⠠⠀⠌⡀⠣⢩⠘⣮⢇⡘⡼⣿⣿⣿⣿⣿"
				+ "⣿⣧⡀⠀⠀⠂⠁⠀⠀⠀⠄⠀⠀⠠⠀⠀⠀⠀⠐⠀\r\n"
				+ //
				"⠀⠠⠀⠀⠀⠀⠠⠀⠀⠀⠀⠄⠈⢀⠀⠠⠡⢈⠀⠄⠳⠌⡈⠂⢿⣿⣾⢿⣿⣆⣼⣿⣿⣿⣿⣾⣯⣵⢆⣨⣴⣷⣿⣫⣾⢏⣶⣿⣿⣿⣿⣷⣮⡝⡻⣍⣳⣾⣿⣿⣿⣿⣙⢿⣎⡻⣷⣦⣄⡀⠀⢈⠐⠠⠀⢂⠐⠀⡁⠄⠃⠨⠷⡄⢱⣿⣿⣿⣿⣿"
				+ "⣿⣿⠛⠀⠀⠀⠀⠀⠠⠀⠀⠀⠄⠀⠀⠀⡀⠈⠀⠀\r\n"
				+ //
				"⠂⠀⠀⠀⠀⠂⠀⠀⠀⠐⠀⠀⠈⢀⠀⢂⠡⠀⠠⢘⠆⣷⡅⢳⣬⣹⣿⣯⣿⣿⣿⣿⣿⣿⣿⣿⢇⣾⣿⣿⣿⢋⣶⣿⡇⣾⣿⣿⣿⣿⣿⣿⣿⣿⣶⣾⣿⣿⣿⣿⣿⣿⣿⡆⣻⣷⡙⣿⣿⣷⣦⠀⠀⡁⠂⠄⢂⠐⠀⡐⠈⠀⠌⠳⢎⣿⣿⣿⣿⣿"
				+ "⡿⠃⠀⠀⠀⠐⠀⠁⠀⠀⢀⠀⠀⠀⠠⠀⠀⠀⠀⡀\r\n"
				+ //
				"⠀⠀⡀⠈⠀⠀⢀⠀⠁⠀⠀⠀⠄⠂⠀⠀⠆⠐⠀⡀⠙⠾⣳⡼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢏⢞⣿⣿⣿⣿⠈⠟⠁⠀⠈⣿⣿⣿⣿⣿⣿⡷⣆⣿⡅⣿⣿⣿⣿⣿⣿⡟⠃⠘⢻⠏⣼⣿⣿⣿⣿⡀⠀⠈⢀⠀⠂⢡⠀⢂⢡⠙⣦⡙⢿⣿⣿⣿⡟"
				+ "⠁⠀⠀⠀⠈⠀⠀⠀⠠⠀⠀⠀⠀⠐⠀⠀⠀⡀⠁⠀\r\n"
				+ //
				"⢀⠀⠀⠀⠀⠄⠀⠀⠀⠀⠂⠀⠀⠀⠀⠈⡐⠀⠀⠀⠄⠐⢫⣗⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⡌⠀⠄⣸⣿⠟⠀⠀⠀⠀⢸⡿⣿⣯⣿⣿⣿⣶⣴⣿⣷⣿⣿⢿⣿⣿⣿⣿⠀⠀⠀⠀⢿⣿⣿⠋⠉⢻⠀⠀⠀⣴⣦⣤⣩⡈⠃⠛⠴⠋⠘⠛⠻⠋⠀"
				+ "⠀⠀⠀⠁⠀⠀⠀⠂⠀⠀⠀⠐⠈⠀⠀⠀⠄⠀⠀⠀\r\n"
				+ //
				"⠀⠀⠀⠀⠂⠀⠀⢀⠈⠀⠀⠀⠄⠐⠀⠀⠀⠀⢀⠈⢀⠂⠀⠙⠧⢟⡾⣿⣿⣿⣿⣿⣿⣿⢁⠆⠀⠠⣼⠃⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡀⠀⠀⠀⠀⢻⣯⠐⠠⢈⡇⠀⢠⣿⣿⣿⣿⣿⣿⣶⡀⡆⠀⠀⠀⠀⠀"
				+ "⢀⠈⠀⠀⡀⠈⠀⠀⠀⠄⠈⠀⠀⠀⠠⠀⠀⠀⠀⠂\r\n"
				+ //
				"⠀⢀⠈⠀⠀⠀⠠⠀⠀⠀⠀⠂⠀⠀⠀⠠⠐⠈⠀⠀⡀⢀⠈⠀⠀⡀⠉⠓⠻⢿⡿⠿⡛⠞⠠⣋⢆⢣⢻⡅⠀⠀⠀⠠⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⡄⢀⠂⠁⢰⣿⣿⣦⣾⡅⠠⢀⠉⢻⣿⣿⣧⣉⣿⣶⡏⠀⠀⠀⠐⠈"
				+ "⠀⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⢀⠀⠂⠀⠀⢀⠀⠁⠀\r\n"
				+ //
				"⠠⠀⠀⠀⠀⠂⠀⠀⠀⡀⠁⠀⠀⠀⠂⠀⠀⠀⠀⠐⠀⠀⠀⡀⠂⠀⠀⡀⠀⠀⠀⠀⠀⠄⡀⢇⠎⢦⡙⡦⠀⠀⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⠀⠀⣹⣿⣿⣿⣿⠅⣒⠦⢌⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠈⠀⠀"
				+ "⠀⠀⡀⠁⠀⠀⠁⠀⠀⠀⠄⠀⠀⠀⢀⠠⠀⠀⠀⠀\r\n"
				+ //
				"⠀⠀⠀⡀⠁⠀⠀⠠⠀⠀⠀⠀⠈⠀⠀⠀⠠⠐⠀⢁⠈⠀⠄⠀⠀⠄⠀⠀⠠⠀⠀⠄⡈⠄⠀⢎⡜⢢⡑⠇⠀⠠⢿⣿⣿⣿⣿⣿⣿⣿⢿⠿⠛⠉⠉⠉⠛⣿⡿⣽⣿⣿⣿⣿⣿⣿⣧⠀⠰⣻⣿⣿⣿⠠⣝⠸⣸⣿⣿⣿⣿⣿⣿⣿⣿⣟⠰⣰⣄⠀"
				+ "⠈⠀⠀⠀⠀⠐⠀⠀⠈⠀⠀⠀⠀⠄⠀⠀⠀⠀⠀⠁\r\n"
				+ //
				"⠀⠠⠀⠀⠀⠀⠐⠀⠀⠀⡀⠈⠀⠀⠀⠂⠀⠀⡀⠂⢀⠠⠀⡀⢂⠐⠠⢊⠄⠠⢈⠐⠠⢈⠐⠠⢎⡱⡘⠀⠀⡘⠈⠹⢿⣿⣿⣿⣿⡿⠃⠀⠀⠠⢄⠀⠀⠀⢿⣿⣿⣿⣿⣿⡿⠛⠳⠀⠀⢱⢻⣿⡇⠸⣌⡃⢸⣿⣿⣿⣿⣿⣿⣿⣿⢻⣿⣌⠿⣿"
				+ "⣤⠀⠀⠀⠂⠀⠀⠀⠂⠀⠀⠈⠀⠀⠀⠀⠄⠈⠀⠀\r\n"
				+ //
				"⠂⠀⠀⢀⠀⠈⠀⠀⠀⠄⠀⠀⠀⡀⠁⠀⠀⠠⡐⠁⠀⠀⡐⠠⠀⠌⠐⠀⢎⠐⠠⣈⠱⠈⠘⠠⢃⠖⡁⠀⠀⠀⠄⡄⠈⠹⠿⡟⢭⡘⠁⠀⠀⠁⠞⠀⠀⠀⠬⡙⢿⣿⠿⠋⢀⠄⠐⠀⠀⠀⢏⢿⣰⠀⢧⠱⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡖⢹"
				+ "⣿⣷⠀⠀⠀⡀⠈⠀⠀⠀⠐⠀⠀⢀⠈⠀⠀⠀⢀⠀\r\n"
				+ //
				"⠀⠀⠄⠀⠀⠀⠠⠐⠀⠀⠀⠠⠀⠀⠀⢀⠠⢡⠐⠀⠀⠀⠐⠀⠌⢀⠂⠈⠆⠉⢀⠀⠠⠐⡀⠄⢋⡒⠀⠀⠀⠐⡸⢌⠣⡐⢣⠜⡢⢁⠀⠀⠀⠀⠀⠀⠀⠀⠐⢩⠒⡌⠆⡰⣉⠒⡀⠀⠀⠀⠨⢆⠹⠃⠀⠁⠀⢸⣿⣿⣿⣿⣿⣿⣿⢯⡽⣿⣿⢸"
				+ "⣿⡏⠀⠀⠀⠀⠀⢀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀\r\n"
				+ //
				"⠀⠂⠀⠀⠀⠐⠀⠀⠀⠀⠐⠀⠀⠀⠠⢀⠢⢁⠀⠄⠀⠀⠂⠀⡐⠀⠂⠈⠀⠌⠠⢈⠄⠡⠀⡐⢨⠐⠀⠀⠀⠀⠑⢪⡑⢌⢣⠊⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠬⣁⢒⡡⠃⠀⠀⠀⠀⠐⠂⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣷⣽⣿⣿⣾"
				+ "⣿⣇⠀⠀⠈⠀⠀⠀⠀⠀⠠⠐⠈⠀⠀⠐⠀⠀⠀⢀\r\n"
				+ //
				"⠀⠀⠀⡀⠁⠀⠀⢀⠀⠁⠀⠀⠀⠂⢀⠆⢡⠂⠀⠀⠀⠀⢄⠰⠀⡐⣀⢦⡁⠜⣀⠃⡌⢂⡁⠠⠐⠀⠀⠀⠀⠀⣀⢡⡘⢬⠀⠀⠀⠀⠀⠀⠀⠀⠄⠀⠀⠀⠀⠀⠀⠠⣅⣲⣤⡁⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿"
				+ "⣿⣟⠀⠀⢀⠀⠂⠁⠀⢀⠀⠀⠀⠀⠠⠀⠀⠀⠂⠀\r\n"
				+ //
				"⠀⠄⠀⠀⠀⠀⠄⠀⠀⠀⢀⠀⠁⡀⠎⠨⠄⡀⠄⣡⢞⠨⡐⢂⠁⡐⢋⢋⠑⢢⠐⠌⡄⢃⡐⠠⠀⠀⠀⠀⠀⠐⠌⢷⣛⡖⣃⣀⡀⠀⠀⠀⠀⠀⠂⠀⠀⠀⢀⠀⡄⢶⡞⣟⡿⠗⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿"
				+ "⣿⣿⣦⡀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠂⠀⠀⠀⠄⠀⠀\r\n"
				+ //
				"⠂⠀⠀⠀⠐⠀⠀⠀⠀⠄⠀⠀⡐⡈⠌⡑⠠⠀⢀⠢⠌⢂⠅⠂⡐⠈⡔⡈⢌⠂⡜⠐⡐⢂⠄⠡⠀⠀⠀⠀⠀⠀⠀⠈⠳⢿⣿⣷⣿⣖⠀⠀⠀⠀⡁⠀⠀⢰⣮⣿⣿⣿⣾⠏⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿"
				+ "⣿⣿⣿⣷⠀⠀⢀⠈⠀⠀⠀⠈⠀⠀⠀⠀⠂⠀⠀⠀\r\n"
				+ //
				"⠀⠀⡀⠁⠀⠀⠀⠐⠀⠀⠀⢠⠐⡈⠔⡈⠀⠠⠀⢂⠘⠀⢂⠡⢀⠂⡐⠈⡔⠨⣀⠣⠘⠀⠌⠠⢁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⠀⠀⠀⠀⢀⠀⠀⠸⠟⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿"
				+ "⣿⣿⣿⡏⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⠁⠀⠀⡀⠄⠂\r\n");

		System.out.println("\r\nPressione qualquer tecla para iniciar.");

		scanner.nextLine();
	}

	public void selectCharacter() {
		System.out.print("===============================================\r\n"
				+ "\r\n1 - Shinji Ikari\r\n"
				+ "2 - Rei Ayanami\r\n"
				+ "3 - Asuka Langley Soryu\r\n"
				+ "\r\n================================================\r\n");
		int option;
		String name = "";
		while (true) {
			try {
				System.out.print("Selecione o piloto de EVA: ");
				option = Integer.parseInt(scanner.nextLine());
				if (0 < option && option < 4) {
					break;
				}
			} catch (Exception e) {
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

		clearScreen();

		sleep();

		System.out.println(hero.getName() + " selecionado.\r\n");
	}

	public boolean isRunning() {
		return hero.isAlive() && angels.get(0).isAlive();
	}

	public void resetTurn() {
		hero.resetShield();
		syncRate = initialSync;
		hand.discardCards(discardPile);
		System.out.println("Você discarta todas as suas cartas e passa o turno.\r\n");
		sleep();
		turn += 1;
	}

	public boolean endOfTurn() {
		boolean end = !isRunning() || syncRate == 0;
		if (end) {
			notifySubscribers(EventEnum.playerEndOfTurn);
		}
		return end;
	}

	public void buyCards() {
		for (int i = 0; i < nCards; i++) {
			hand.buyCard(buyPile, discardPile);
		}

		System.out.println("Você compra " + nCards + " cartas.\r\n");

		sleep();
	}

	public void clearScreen() {
		try {
			new ProcessBuilder("clear").inheritIO().start().waitFor();
		} catch (Exception e) {
		}
	}

	void sleep() {
		try {
			Thread.sleep(timeSleep);
		} catch (Exception e) {
		}
	}

	public int selectOption() {
		sleep();

		System.out.println("=============== Turno " + turn + " ===============");

		System.out.print("" + //
				"Herói: " + hero.getName() + " vs. Inimigo: " + angels.get(0).getName() + "\r\n" + //
				"(" + hero.getHealth() + "/" + playerHealth + " pontos de vida)   (" + angels.get(0).getHealth()
				+ "/" + enemyHealth + " pontos de vida)\r\n" + //
				"(" + hero.getShield() + " pontos de escudo)    (" + angels.get(0).getShield()
				+ " pontos de escudo)\r\n");

		this.enemyPlanning();

		hand.showHand();

		System.out.println("\r\n=========================================\r\n" + //
				syncRate + "/" + initialSync + " de Sincronização (Energia) disponível\r\n");

		int option = 0;
		while (true) {
			try {
				System.out.print("Qual carta deseja usar (-1 para passar o turno): ");
				option = Integer.parseInt(scanner.nextLine());
				if (-1 <= option && option < hand.nCards()) {
					break;
				}
			} catch (Exception e) {
			}
		}

		clearScreen();

		return option;
	}

	public void playerAction(int option) {
		sleep();

		if (option == -1)
			syncRate = 0; // end of turn
		else {
			int cardCost = hand.seeCardCost(option);

			if (syncRate - cardCost >= 0) {
				Card card = hand.useCard(option);
				if (card.getClass() == DamageCard.class) {

					int damage = card.getCost() * 10 + random.nextInt(card.getCost() * 10);
					card.useCard(angels.get(0), damage);
					syncRate -= card.getCost();

					System.out.println("\r\nVocê usa " + card.getName() + " contra " + angels.get(0).getName()
							+ ", dando " + damage + " de dano.\r\n");

				} else if (card.getClass() == ShieldCard.class) {

					int shield = card.getCost() * 2 + random.nextInt(card.getCost() * 2);
					card.useCard(hero, shield);
					syncRate -= card.getCost();

					System.out.println("\r\nVocê usa " + card.getName() + ", recebendo " + shield
							+ " de Campo AT (escudo).\r\n");

				} else if (card.getClass() == EffectCard.class) {

					card.useCard(angels.get(0), 0);

					EffectCard effectCard = (EffectCard) card;
					subscribe(effectCard.getEffect());

					syncRate -= card.getCost();
				}
				discardPile.add(card);
			} else {
				System.out.println(
						"\r\nVocê não pode usar esta carta, o custo de energia é muito alto!\r\n");

				sleep();
			}
		}
	}

	public void enemyAction() {
		if (angels.get(0).isAlive()) {
			sleep();

			int damage = angels.get(0).attack(hero);

			System.out.println(
					"O inimigo " + angels.get(0).getName() + " te atacou, dando " + damage + " de dano.\r\n");
		}

		notifySubscribers(EventEnum.enemyEndOfTurn);
	}

	public void results() {
		sleep();

		System.out.print("" + //
				"Herói: " + hero.getName() + " vs. Inimigo: " + angels.get(0).getName() + "\r\n" + //
				"(" + hero.getHealth() + "/" + playerHealth + " pontos de vida)   (" + angels.get(0).getHealth()
				+ "/" + enemyHealth + " pontos de vida)\r\n" + //
				"(" + hero.getShield() + " pontos de escudo)    (" + angels.get(0).getShield()
				+ " pontos de escudo)\r\n");

		if (hero.isAlive()) {
			System.out.println("""

					8b        d8                           I8,        8        ,8I  88
					 Y8,    ,8P                            `8b       d8b       d8'  ""
						Y8,  ,8P                              "8,     ,8"8,     ,8"
						 "8aa8"  ,adPPYba,   88       88       Y8     8P Y8     8P    88  8b,dPPYba,
							`88'  a8"     "8a  88       88       `8b   d8' `8b   d8'    88  88P'   `"8a
							 88   8b       d8  88       88        `8a a8'   `8a a8'     88  88       88
							 88   "8a,   ,a8"  "8a,   ,a88         `8a8'     `8a8'      88  88       88
							 88    `"YbbdP"'    `"YbbdP'Y8          `8'       `8'       88  88       88

											""");
		} else {
			System.out.print(
					"""

								,ad8888ba,                                                    ,ad8888ba,
							 d8"'    `"8b                                                  d8"'    `"8b
							d8'                                                           d8'        `8b
							88             ,adPPYYba,  88,dPYba,,adPYba,    ,adPPYba,     88          88  8b       d8   ,adPPYba,  8b,dPPYba,
							88      88888  ""     `Y8  88P'   "88"    "8a  a8P_____88     88          88  `8b     d8'  a8P_____88  88P'   "Y8
							Y8,        88  ,adPPPPP88  88      88      88  8PP\"\"\"\"\"\"\"     Y8,        ,8P   `8b   d8'   8PP\"\"\"\"\"\"\"  88
							 Y8a.    .a88  88,    ,88  88      88      88  "8b,   ,aa      Y8a.    .a8P     `8b,d8'    "8b,   ,aa  88
								`"Y88888P"   `"8bbdP"Y8  88      88      88   `"Ybbd8"'       `"Y8888Y"'        "8"       `"Ybbd8"'  88

													""");
		}
	}
}
