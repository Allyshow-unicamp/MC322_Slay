public class DamageCard {
    String name = "Positron Rifle";
    int energyCost = 1;
    int damage = 80;

    public void useCard(Enemy angel, int damage) {
        angel.takeDamage(damage);
    }

    public DamageCard(String name, int energyCost, int damage) {
        this.name = name;
        this.damage = damage;
        this.energyCost = energyCost;
    }
}
