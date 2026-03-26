package mc322_slay.effect;

import java.util.ArrayList;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Entity;

public class PsychicEffect extends Effect {

    private int damage;

    public String getString() {
        return name + ": " + points + " turnos restantes";
    }
    @Override
    public String beNotified(EventEnum event, GameManager gameManager, Entity attacker, ArrayList<Entity> receivers) {
        if (event == EventEnum.playerEndOfTurn) {
            if (attacker.getClass() == Enemy.class) {
                receivers.get(0).takeDamage(damage);
                return "\r\nVocê leva " + damage + " de dano devido a " + name + ".";
            }
            else {
                for (Entity receiver : receivers) {
                    receiver.takeDamage(damage);
                    return "\r\n" + receiver.getName() + " leva " + damage + "de dano devido a " + name + ".";
                }
            }
        }
        return "";
    }

    public PsychicEffect(int damage, int turns) {
        this.damage = damage;
        this.incrementPoints(turns);
    }
}