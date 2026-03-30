package mc322_slay.entity;

import java.util.ArrayList;
import java.util.Random;

import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.PsychicEffect;

public class Enemy extends Entity {

    public Random random = new Random();
    private int damage;
    private PsychicEffect p = new PsychicEffect("Dano psicológico 1", 20, 3);
    private ATFieldCorrosion c = new ATFieldCorrosion("Corrosão de campo AT 1", 3);
    private HealthRegeneration h = new HealthRegeneration("Regeneração de vida 1", 20, 3);

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
    
    public int nextAction() {
        this.damage = (random.nextInt(40) +1);
        int action = random.nextInt(3);
        return action;
    }
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
