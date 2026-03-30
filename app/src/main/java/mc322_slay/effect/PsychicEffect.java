package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.*;

public class PsychicEffect extends Effect {

    private int damage;

    @Override
    public String getString() {
        return name + " (" + points + " turnos restantes)";
    }

    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class ||
                event == EventEnum.enemyEndOfTurn && owner.getClass() == Enemy.class) {
            // damage inflicted to entity at the end of his turn, if it is under this effect
            this.owner.takeDamage(damage);
            System.out.println(owner.getName() + " recebe " + damage + " de dano psicológico.\r\n");

            this.points -= 1;
            if (this.points > 0) {
                System.out.println(points + " turnos restantes de " + name + " sobre " + owner.getName() + ".\r\n");
            }
            if (this.points == 0) { // effect is over
                owner.removeEffect(this);
                return true;
            }
        }
        return false;
    }

    public PsychicEffect(String name, int damage, int turns) {
        this.name = name;
        this.damage = damage;
        this.points = turns;
        this.startPoints = turns;
    }
}
