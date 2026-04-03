package mc322_slay.effect;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.Interface;
import mc322_slay.entity.Hero;

public class LowSyncRate extends Effect {

    private double deboost;

    public double getDeboost() {
        return this.deboost;
    }

    public void setDeboost(double deboost) {
        this.deboost = deboost;
    }

    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes) - Dano causado é multiplicado por " + deboost
                + " (desconsiderando danos oriundos de efeitos)");
    }

    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class) {
            this.points -= 1;
            if (this.points > 0) {
                // Interface.printMessage(points + " turnos restantes de " + name + " sobre " +
                // owner.getName() + ".",
                // ColorEnum.reset);
            }

            if (this.points == 0) {
                owner.removeEffect(this);
                return true;
            }
        }
        return false;
    }

    public LowSyncRate(String name, int turns, double deboost) {
        this.name = name;
        this.points = turns;
        this.startPoints = turns;
        this.deboost = deboost;
    }

    public LowSyncRate(LowSyncRate effect) {
        this.name = effect.name;
        this.owner = effect.owner;
        this.points = effect.points;
        this.deboost = effect.deboost;
        this.startPoints = effect.startPoints;
    }
}
