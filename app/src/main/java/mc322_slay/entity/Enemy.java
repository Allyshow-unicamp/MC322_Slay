package mc322_slay.entity;

import java.util.ArrayList;
import java.util.Random;

import mc322_slay.effect.Effect;
import mc322_slay.effect.PsychicEffect;

public class Enemy extends Entity {

    public Random random = new Random();
    int damage = 0;

    public int attack(Hero hero) {
        hero.takeDamage(damage);
        return damage;
    }
    public void useEffect(Hero hero) {
        PsychicEffect e = new PsychicEffect("Dano psicológico 1", 20, 3);
        int effect = random.nextInt(3);
        switch (effect) { // utilizarei outra branch para criar novos efeitos
            case 0:
                
                break;
            case 1:

                break;

            case 2:
                
                break;
        }

        hero.applyEffect(e);
    }
    
    public ArrayList<Integer> nextAction() {
        ArrayList<Integer> nextAction = new ArrayList<>();
        this.damage = random.nextInt(41);
        nextAction.add(damage);
        int action = random.nextInt(3);
        nextAction.add(action);
        return nextAction;
    }
    public Enemy(String name, int health, int ATField) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
        this.damage = 0;
        this.effects = new ArrayList<>();
    }
}
