package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Hero;

public class Weakness extends Effect {
    
    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes)");
    }

    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class){
            this.points -= 1;
            if (this.points > 0) {
                System.out.println(points + " turnos restantes de "+ name + " sobre " + owner.getName() + ".\r\n");}

            if (this.points == 0) {
                owner.removeEffect(this);
                return true;
            } 
        }
        return false;
    }

    public Weakness(String name, int turns) {
        this.name = name;
        this.points = turns;
        this.startPoints = turns;
    }

    public Weakness(Weakness effect) {
        this.name = effect.name;
        this.owner = effect.owner;
        this.points = effect.points;
        this.startPoints = effect.startPoints;
    }
}

