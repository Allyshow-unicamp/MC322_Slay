package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Representa um efeito de dano psicológico aplicado a uma entidade.
 * Este efeito causa dano ao dono no final de seu turno.
 */
public class PsychicEffect extends Effect {

    /** Quantidade de dano psicológico causado por turno. */
    private int damage;

    @Override
    public String getString() {
        return name + " (" + points + " turnos restantes)";
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getDamage() {
        return damage;
    }

    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn && this.owner.getClass() == Hero.class ||
                event == EventEnum.enemyEndOfTurn && this.owner.getClass() == Enemy.class) {
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

    /**
     * Construtor da classe PsychicEffect.
     * @param name Nome do efeito.
     * @param damage Dano causado por turno.
     * @param turns Duração em turnos.
     */
    public PsychicEffect(String name, int damage, int turns) {
        this.name = name;
        this.damage = damage;
        this.points = turns;
        this.startPoints = turns;
    }

    public PsychicEffect(PsychicEffect effect) {
        this.name = effect.name;
        this.damage = effect.damage;
        this.points = effect.points;
        this.startPoints = effect.startPoints;
    }
}
