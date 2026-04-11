package mc322_slay.effect;

import mc322_slay.Battle;
import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.Interface;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Efeito que restaura vida do alvo no início do turno.
 */
public class HealthRegeneration extends Effect {

    /** Vida recuperada a cada ativação do efeito. */
    private int health;

    /**
     * Define quantidade de vida restaurada por ativação.
     *
     * @param health valor recuperado por turno.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Retorna a vida restaurada a cada ativação.
     *
     * @return valor recuperado por turno.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Retorna descrição textual do efeito.
     *
     * @return texto com nome e turnos restantes.
     */
    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes) - Recupera " + health + " de saúde por turno");
    }

    /**
     * Processa a cura no início do turno do dono (herói ou anjo, conforme a classe do {@link #owner}).
     *
     * @param event  evento do jogo.
     * @param battle batalha atual.
     * @return {@code true} quando o efeito termina após esgotar os turnos.
     */
    @Override
    public boolean beNotified(EventEnum event, Battle battle) {
        if (event == EventEnum.playerStartOfTurn && owner.getClass() == Hero.class ||
                event == EventEnum.enemyStartOfTurn && owner.getClass() == Enemy.class) {
            this.points -= 1;
            Interface.printMessage(owner.getName() + " recupera " + health + " de vida, devido a " + name + ".",
                    ColorEnum.yellow);
            owner.gainHealth(health);

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
     * @param name   nome do efeito.
     * @param amount vida recuperada por ativação.
     * @param points duração em turnos.
     */
    public HealthRegeneration(String name, int amount, int points) {
        this.name = name;
        this.health = amount;
        this.points = points;
        this.startPoints = points;
    }

    /**
     * Cria uma cópia de outro efeito de regeneração (nome, cura, dono e duração).
     *
     * @param effect instância a copiar.
     */
    public HealthRegeneration(HealthRegeneration effect) {
        this.name = effect.name;
        this.health = effect.health;
        this.owner = effect.owner;
        this.points = effect.points;
        this.startPoints = effect.startPoints;
    }

    @Override
    public HealthRegeneration cloneEffect() {
        return new HealthRegeneration(this);
    }

    @Override
    public void merge(Effect effect) {
        HealthRegeneration hE = (HealthRegeneration) effect;
        this.setHealth(Math.max(hE.getHealth(), this.getHealth()));
    }
}