package mc322_slay.effect;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.Interface;
import mc322_slay.entity.Hero;

/**
 * Efeito que aumenta temporariamente o dano causado pelo herói.
 */
public class HighSyncRate extends Effect {

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
     * Atualiza a duração no fim do turno do herói.
     *
     * @param event       evento do jogo.
     * @param gameManager gerenciador da partida.
     * @return {@code true} quando o efeito termina.
     */
    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn && owner.getClass() == Hero.class) {
            this.points -= 1;
            if (this.points > 0) {
                // Interface.printMessage(points + " turnos restantes de " + name + " sobre " + owner.getName() + ".",
                //         ColorEnum.reset);
            }

            if (this.points == 0) {
                owner.removeEffect(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Cria um efeito de alta sincronização.
     *
     * @param name  nome do efeito.
     * @param turns duração em turnos.
     */
    public HighSyncRate(String name, int turns) {
        this.name = name;
        this.points = turns;
        this.startPoints = turns;
    }

    /**
     * Cria uma cópia de outro efeito de alta sincronização (nome, dono e duração).
     *
     * @param effect instância a copiar.
     */
    public HighSyncRate(HighSyncRate effect) {
        this.name = effect.name;
        this.owner = effect.owner;
        this.points = effect.points;
        this.startPoints = effect.startPoints;
    }
}
