package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.*;

public class HighSyncRate extends Effect {
    
    @Override
    public String getString() {
        return name + ": Aumenta o dano causado por " + owner.getName() + "em 50% por " + points + " turnos.";
    }

    @Override
    public void beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class){
            this.points -= 1;
            if (this.points <= 0) {
                gameManager.unsubscribe(this);
                owner.removeEffect(this);
            } else {
                System.out.println(points + " turnos restantes de "+ name + " sobre " + owner.getName() + ".");
            }
        }
    }

    public HighSyncRate(String name, int turns) {
        this.name = name;
        this.points = turns;
    }
}

