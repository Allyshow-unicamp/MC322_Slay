package mc322_slay.entity;

import java.util.ArrayList;

import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.Effect;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.PsychicEffect;

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
        Effect thisEffect = effect;
        boolean contains = false;
        int index = 0;
        for (Effect effectX : effects) {
            if (effectX.getClass() == effect.getClass()) {
                contains = true;
                thisEffect = effectX; 
                break;
            }
            index++;
        }
        if (contains) {
            Effect e = effects.get(index);
            e.incrementPoints(points);
            if (e instanceof HealthRegeneration healthRegeneration) {
                HealthRegeneration hE = healthRegeneration;
                HealthRegeneration tE = (HealthRegeneration) thisEffect;
                hE.setHealth(Math.max(hE.getHealth(), tE.getHealth()));
            }
            else if (e instanceof PsychicEffect psychicEffect) {
                PsychicEffect pE = psychicEffect;
                PsychicEffect tE = (PsychicEffect) thisEffect;
                pE.setDamage(Math.max(pE.getDamage(), tE.getDamage()));
            }
            effects.set(index, e);
        }
        else {
            Effect newEffect;
            if (effect instanceof ATFieldCorrosion aTFieldCorrosion) {
                newEffect = new ATFieldCorrosion(aTFieldCorrosion);
            }
            else if (effect instanceof HealthRegeneration healthRegeneration) {
                newEffect = new HealthRegeneration(healthRegeneration);
            }
            else if (effect instanceof HighSyncRate highSyncRate) {
                newEffect = new HighSyncRate(highSyncRate);
            }
            else {
                newEffect = new PsychicEffect((PsychicEffect) effect);
            }
            newEffect.setOwner(this);
            effects.add(newEffect);
            
        }

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
