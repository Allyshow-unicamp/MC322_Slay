package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Entity;

public abstract class Effect {
    protected String name;
    protected Entity owner;
    protected int points;

    public int getPoints() {
        return this.points;    
    }
    public void incrementPoints(int points) {
        this.points += points;
    } 
    public String getName() {
        return this.name;
    }
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public abstract String getString();

    public abstract void beNotified(EventEnum event, GameManager gameManager);
}
