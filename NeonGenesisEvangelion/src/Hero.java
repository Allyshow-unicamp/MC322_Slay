public class Hero {

    String name = "Shinji Ikari";
    int health = 40;
    int syncRate = 0; // works identical to the shield attribute

    public void takeDamage(int damage) {
        this.health = (health - damage) >= 0 ? health - damage : 0;
    }
    public void gainSyncRate(int amount) {
        this.syncRate = syncRate + amount;
    }
    public boolean isAlive() {
        return this.health == 0;
    }

    public Hero(String name, int health, int syncRate) {
        this.name = name;
        this.health = health;
        this.syncRate = syncRate;
    }
}
