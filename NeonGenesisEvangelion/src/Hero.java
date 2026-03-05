public class Hero {

    String name = "Shinji Ikari";
    int health = 40;
    int ATField = 0; // works identical to the shield attribute

    public void takeDamage(int damage) {
        this.health = (health - damage) >= 0 ? health - damage : 0;
    }
    public void gainSyncRate(int amount) {
        this.ATField = ATField + amount;
    }
    public boolean isAlive() {
        return this.health == 0;
    }

    public Hero(String name, int health, int ATField) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
    }
}
