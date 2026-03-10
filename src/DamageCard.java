public class DamageCard {
    
    private String name = "Positron Rifle";
    private int energyCost = 1;

    public void useCard(Enemy angel, int damage) {
        angel.takeDamage(damage);
    }
    public String getName() {
        return this.name;
    }
    public int getCost() {
        return this.energyCost;
    }

    public DamageCard(String name, int energyCost) {
        this.name = name;
        this.energyCost = energyCost;
    }
}
