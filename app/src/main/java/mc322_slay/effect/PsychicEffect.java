package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

public class PsychicEffect extends Effect {

    private int damage;

    @Override
    public String getString() {
        return name + " (" + points + " turnos restantes)";
    }
    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn || event == EventEnum.enemyEndOfTurn) {
            if (event == EventEnum.playerEndOfTurn) {
                // damage inflicted to player at the end of his turn, if it is under this effect

                if (this.owner.getClass() == Hero.class) {
                    this.owner.takeDamage(damage);
                    System.out.println("Você leva " + damage + " de dano devido a " + name + ".\r\n");

                    this.points -= 1;
                    if (this.points == 0) { // effect is over
                        owner.removeEffect(this);
                        return true;
                    }
                }
            }
            else {
                // damage inflicted to first enemy at the end of their turn

                if (owner.getClass() == Enemy.class) {
                    owner.takeDamage(damage);
                    System.out.println(owner.getName() + " leva " + damage + " de dano devido a " + name + ".\r\n");

                    this.points -= 1;
                    if (this.points == 0) { // effect is over
                        owner.removeEffect(this);
                        return true;
                    }
                }
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
