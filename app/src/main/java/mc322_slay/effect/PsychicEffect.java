package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

public class PsychicEffect extends Effect {

    private int damage;

    public String getString() {
        return name + ": " + points + " turnos restantes";
    }
    @Override
    public void beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn || event == EventEnum.enemyEndOfTurn) {
            if (event == EventEnum.playerEndOfTurn && gameManager.getHero().getEffects().contains(this)) {
                // damage inflicted to player at the end of his turn, if it is under this effect

                Hero hero = gameManager.getHero();

                hero.takeDamage(damage);
                System.out.println("\r\nVocê leva " + damage + " de dano devido a " + name + ".");

                this.points -= 1;
                if (this.points == 0) { // effect is over
                    gameManager.unsubscribe(this);
                    hero.removeEffect(this);
                }
            }
            else {
                // damage inflicted to first enemy at the end of their turn

                Enemy enemy = gameManager.getAngels().get(0);
                if (enemy.getEffects().contains(this)) {
                    enemy.takeDamage(damage);
                    System.out.println("\r\n" + enemy.getName() + " leva " + damage + "de dano devido a " + name + ".");
                }

                this.points -= 1;
                if (this.points == 0) { // effect is over
                    gameManager.unsubscribe(this);
                    enemy.removeEffect(this);
                }
            }
        }
    }

    public PsychicEffect(int damage, int turns) {
        this.damage = damage;
        this.incrementPoints(turns);
    }
}
