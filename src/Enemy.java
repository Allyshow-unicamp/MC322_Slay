import java.util.Random;

public class Enemy extends Entity{

    public Random random = new Random();

    public int attack(Hero hero) {
        int damage = random.nextInt(41);
        hero.takeDamage(damage);
        return damage;
    }
    public Enemy(String name, int health, int ATField) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
    }
}
