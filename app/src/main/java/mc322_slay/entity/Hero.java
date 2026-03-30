package mc322_slay.entity;

import java.util.ArrayList;

public class Hero extends Entity {

    public void setName(String newName) {
        this.name = newName;
    }
    public void resetShield(){
        this.ATField = 0;
    }
    public Hero(String name, int health, int ATField, String imageAsset) {
        this.name = name;
        this.health = health;
        this.ATField = ATField;
        this.effects = new ArrayList<>();
        this.imageAsset = imageAsset;
        this.maxHealth = health;
    }
}
