public class Hero {

    private String name = "Shinji Ikari";
    private int health = 40;
    private int ATField = 20; // works identical to the shield attribute

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
    public void gainATField(int amount) {
        this.ATField = ATField + amount;
    }
    public boolean isAlive() {
        return this.health == 0;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public void resetShield(){
        this.ATField = 0;
    }
    public String getName(){
        return this.name;
    }
    public int getHealth() {
        return this.health;
    }
    public int getShield() {
        return this.ATField;
    }

    public Hero(String name, int health, int ATField) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
    }

}
