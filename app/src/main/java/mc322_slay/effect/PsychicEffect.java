package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.*;

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

    /**
     * Notifica o efeito sobre um evento do jogo.
     * Causa dano psicológico no final do turno do proprietário.
     * @param event O evento ocorrido.
     * @param gameManager O gerenciador do jogo.
     * @return True se o efeito terminou e deve ser removido, False caso contrário.
     */
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
}
