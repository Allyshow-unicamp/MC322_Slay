package mc322_slay;
public abstract class Entity {
    
    protected String name;
    protected int health;
    protected int ATField; // works identical to the shield attribute

    public String getName(){
        return this.name;
    }
    public int getHealth() {
        return this.health;
    }
    public int getShield() {
        return this.ATField;
    }
    public boolean isAlive() {
        return this.health > 0;
    }
    public void gainATField(int amount) {
        this.ATField = ATField + amount;
    }
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
}
