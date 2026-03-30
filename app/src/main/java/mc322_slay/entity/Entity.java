package mc322_slay.entity;

import java.util.ArrayList;

import mc322_slay.effect.Effect;

public abstract class Entity {
    
    protected String name;
    protected int health;
    protected int ATField; // works identical to the shield attribute
    protected ArrayList<Effect> effects;

    public String getName(){
        return this.name;
    }
    public int getHealth() {
        return this.health;
    }
    public int getShield() {
        return this.ATField;
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
    public void applyEffect(Effect effect) {
        if (effects.contains(effect)) {
            int index = effects.indexOf(effect);
            Effect e = effects.get(index);
            e.incrementPoints(effect.getPoints());
            effects.set(index, e);
        }
        else {
            effects.add(effect);
        }
        effect.setOwner(this);

        System.out.println("\r\n" + this.name + " está sob efeito de " + effect.getString());
    }
    public String removeEffect(Effect effect) {
        boolean result = effects.remove(effect);
        if (result)
            System.out.println("\r\n" + this.name + " não está mais sob efeito de " + effect.getString());
        return "";
    }
}
