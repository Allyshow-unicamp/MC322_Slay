package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Entity;

public abstract class Effect {
    protected String name;
    protected Entity owner;
    protected int points;
    protected int startPoints;

    public int getPoints() {
        return this.points;    
    }
    public int getStartPoints() {
        return this.startPoints;
    }
    public void incrementPoints(int points) {
        this.points += points;
    } 
    public String getName() {
        return this.name;
    }
    public Entity getOwner() {
        return owner;
    }
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public abstract String getString();

    public abstract boolean beNotified(EventEnum event, GameManager gameManager);
}
