package mc322_slay.entity;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {

    public Random random = new Random();
    private int damage = 0;

    public int attack(Hero hero) {
        hero.takeDamage(damage);
        return damage;
    }
    public int nextAction() {
        this.damage = random.nextInt(41);
        return damage;
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
