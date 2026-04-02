package mc322_slay.effect;

import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.entity.*;

/**
 * Efeito que restaura vida do alvo no início do turno.
 */
public class HealthRegeneration extends Effect {

    private int health;

    /**
     * Retorna descrição textual do efeito.
     *
     * @return texto com nome e turnos restantes.
     */
    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes)");
    }

    /**
     * Processa a regeneração quando o turno do dono começa.
     *
     * @param event evento do jogo.
     * @param gameManager gerenciador da partida.
     * @return {@code true} quando o efeito termina.
     */
    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerStartOfTurn && owner.getClass() == Hero.class ||
                event == EventEnum.enemyStartOfTurn && owner.getClass() == Enemy.class) {
            this.points -= 1;
            owner.gainHealth(health);
            System.out.println(owner.getName() + " recupera " + health + " de vida, devido a " + name + ".\r\n");

            if (this.points > 0) {
                System.out.println(points + " turnos restantes de " + name + " sobre " + owner.getName() + ".\r\n");
            }

            if (this.points == 0) {
                owner.removeEffect(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Cria um efeito de regeneração de vida.
     *
     * @param name nome do efeito.
     * @param amount vida recuperada por ativação.
     * @param points duração em turnos.
     */
    public HealthRegeneration(String name, int amount, int points) {
        this.name = name;
        this.health = amount;
        this.points = points;
    }
}