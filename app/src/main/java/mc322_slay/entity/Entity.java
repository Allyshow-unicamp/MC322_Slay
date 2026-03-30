package mc322_slay.entity;

import java.util.ArrayList;

import mc322_slay.effect.*;

public abstract class Entity {
    
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int ATField; // works identical to the shield attribute
    protected int maxATField;
    protected ArrayList<Effect> effects;
    protected String imageAsset;

    public String getName(){
        return this.name;
    }
    public int getHealth() {
        return this.health;
    }
    public void gainHealth(int amount) {
        this.health = (health + amount > maxHealth ? maxHealth : health + amount);
    }
    public int getMaxHealth() {
        return this.maxHealth;
    }
    public int getShield() {
        return this.ATField;
    }
    public int getMaxShield() {
        return this.maxATField;
    }
    public String getImage() {
        return this.imageAsset;
    }
    public ArrayList<Effect> getEffects() {
        return this.effects;
    }
    public Effect getLastEffect() {
        return this.effects.getLast();
    }
    public boolean isAlive() {
        return this.health > 0;
    }
    public void gainATField(int amount) {
        this.ATField = ATField + amount;
    }
    public void takeDamage(int damage) {
        if (this.ATField > 0 && !hasEffect(ATFieldCorrosion.class)) {
            if (damage > ATField) {
                this.health = (health - (damage - ATField)) >= 0 ? health - (damage - ATField) : 0;
            }
            this.ATField = (ATField - damage) >= 0 ? ATField - damage : 0;
        }
        else {
            this.health = (health - damage) >= 0 ? health - damage : 0;
        }
    }
    public void applyEffect(Effect effect, int points) {
        if (effects.contains(effect)) {
            int index = effects.indexOf(effect);
            Effect e = effects.get(index);
            e.incrementPoints(points);
            effects.set(index, e);
        }
        else {
            effects.add(effect);
        }
        effect.setOwner(this);

        System.out.println("\r\n" + this.name + " está sob efeito de " + effect.getString() + "\r\n");
    }
    public String removeEffect(Effect effect) {
        boolean result = effects.remove(effect);
        effect.setOwner(null);
        if (result)
            System.out.println(this.name + " não está mais sob efeito de " + effect.getString() + "\r\n");
        return "";
    }
    public boolean hasEffect(Class<? extends Effect> effectX) {
        for (Effect effect : this.effects) {
            if (effectX.isInstance(effect)) {
                return true;
            }
        }
        return false;
    }
}
