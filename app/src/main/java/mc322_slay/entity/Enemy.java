package mc322_slay.entity;

import java.util.ArrayList;
import java.util.Random;

import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.PsychicEffect;

/**
 * Representa um inimigo (Anjo) no jogo.
 * Estende a classe Entity e define comportamentos específicos de ataque e uso de efeitos.
 */
public class Enemy extends Entity {

    /** Gerador de números aleatórios para as ações do inimigo. */
    public Random random = new Random();
    
    /** Quantidade de dano que o inimigo causará em seu próximo ataque. */
    private int damage;
    
    /** Efeito de dano psicológico predefinido. */
    private PsychicEffect p = new PsychicEffect("Dano psicológico 1", 20, 3);
    
    /** Efeito de corrosão de campo AT predefinido. */
    private ATFieldCorrosion c = new ATFieldCorrosion("Corrosão de campo AT 1", 3);
    
    /** Efeito de regeneração de vida predefinido. */
    private HealthRegeneration h = new HealthRegeneration("Regeneração de vida 1", 20, 3);

    /**
     * Realiza um ataque contra o herói.
     * @param hero O herói que receberá o dano.
     * @return A quantidade de dano causada.
     */
    public int attack(Hero hero) {
        hero.takeDamage(this.damage);
        return this.damage;
    }
    public boolean useEffect(Hero hero) {

        int effect = random.nextInt(3);
        boolean selfInflicted = false;
        switch (effect) { // utilizarei outra branch para criar novos efeitos
            case 0: // caso sorteado efeito de dano psicológico
                hero.applyEffect(p, p.getPoints());
                break;
            case 1:
                hero.applyEffect(c, c.getPoints());
                break;
            case 2:
                this.applyEffect(h, h.getPoints());
                selfInflicted = true;
                break;
        }
        return selfInflicted;
    }
    
    /**
     * Define a próxima ação do inimigo aleatoriamente.
     * @return O valor correspondente à ação sorteada.
     */
    public int nextAction() {
        this.damage = (random.nextInt(40) +1);
        int action = random.nextInt(3);
        return action;
    }

    /**
     * Construtor da classe Enemy.
     * @param name Nome do inimigo.
     * @param health Vida inicial.
     * @param ATField Valor inicial do escudo (AT Field).
     * @param imageAsset Caminho do recurso de imagem.
     */
    public Enemy(String name, int health, int ATField, String imageAsset) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
        this.damage = 0;
        this.effects = new ArrayList<>();
        this.imageAsset = imageAsset;
        this.maxHealth = health;
    }
}
