public class Hero extends Entity{

    public void setName(String newName) {
        this.name = newName;
    }
    public void resetShield(){
        this.ATField = 0;
    }
    public Hero(String name, int health, int ATField) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
    }

}
