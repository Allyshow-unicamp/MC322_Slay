package mc322_slay.effect;

import java.util.ArrayList;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Entity;

public class PsychicEffect extends Effect {

    public String getString() {
        return name + ": " + points + " de acúmulo";
    }
    @Override
    public String beNotified(EventEnum event, GameManager gameManager, Entity attacker, ArrayList<Entity> receivers) {
        if (event == EventEnum.playerEndOfTurn) {
            if (attacker.getClass() == Enemy.class) {
                gameManager.heroTakeDamage(points);
                return "\r\nVocê leva " + points + " de dano devido a " + name + ".";
            }
            else {
                for (Entity receiver : receivers) {
                    gameManager.enemyTakeDamage(points);
                    return "\r\n" + receiver.getName() + " leva " + points + "de dano devido a " + name + ".";
                }
            }
        }
        return "";
    }

    public PsychicEffect(int points) {
        this.incrementPoints(points);
    }
}