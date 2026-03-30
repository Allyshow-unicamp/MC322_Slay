package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;

public class ATFieldCorrosion extends Effect {    
    
    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes)");
    }

    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn) {
            // at the beginning of the turn the entity has their shield temporarely reduced to 0
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

    public ATFieldCorrosion(String name, int turns) {
        this.name = name;
        this.points = turns;
    }
}
