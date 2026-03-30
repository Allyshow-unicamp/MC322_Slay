package mc322_slay.entity;

import java.util.ArrayList;
import java.util.Random;

import mc322_slay.effect.*;

public class Enemy extends Entity {

    public Random random = new Random();
    private int damage = 0;

    public int attack(Hero hero) {
        hero.takeDamage(damage);
        return damage;
    }
    public void useEffect(Hero hero) {
        int effect = random.nextInt(3);
        switch (effect) { // utilizarei outra branch para criar novos efeitos
            case 0: // caso sorteado efeito de dano psicológico
                PsychicEffect p = new PsychicEffect("Dano psicológico 1", 20, 3);
                hero.applyEffect(p);
                break;
            case 1:
                ATFieldCorrosion c = new ATFieldCorrosion("Corrosão de campo AT 1", 3);
                hero.applyEffect(c);
                break;
            case 2:
                HealthRegeneration h = new HealthRegeneration("Regeneração de vida 1", 20, 3);
                this.applyEffect(h);
                break;
        }
    }
    
    public ArrayList<Integer> nextAction() {
        ArrayList<Integer> nextAction = new ArrayList<>();
        this.damage = random.nextInt(41);
        nextAction.add(damage);
        int action = random.nextInt(3);
        nextAction.add(action);
        return nextAction;
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
