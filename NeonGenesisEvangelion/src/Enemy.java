public class Enemy {

    private String name = "Kaworu Nagisa";
    private int health = 400;
    private int ATField = 0; // works identical to the shield attribute

    public void takeDamage(int damage) {
        if (this.ATField > 0) {
            if (damage > ATField) {
                this.health = (health - (damage - ATField)) >= 0 ? health - (damage - ATField) : 0;
            }
            this.ATField = (ATField - damage) >= 0 ? ATField - damage : 0;
        }
        else {
            this.health = (health - damage) >= 0 ? health - damage : 0;
        }
    }
    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }
    public boolean isAlive() {
        return this.health > 0;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getShield() {
        return this.ATField;
    }

    public Enemy(String name, int health, int ATField) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
    }
}
