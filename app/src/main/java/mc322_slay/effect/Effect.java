package mc322_slay.effect;

import java.util.ArrayList;

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

    public abstract String getString();

    public abstract String beNotified(EventEnum event, GameManager gameManager, Entity attacker, ArrayList<Entity> receivers);
}
