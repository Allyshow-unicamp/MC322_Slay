public class Enemy {

    String name = "Kaworu Nagisa";
    int health = 400;
    int ATField = 0; // works identical to the shield attribute

    public void takeDamage(int damage) {
        this.health = (health - damage) >= 0 ? health - damage : 0;
    }
    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }
    public boolean isAlive() {
        return this.health == 0;
    }

    public Enemy(String name, int health, int ATField) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
    }
}
