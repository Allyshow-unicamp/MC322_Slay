package mc322_slay.effect;

import mc322_slay.ColorEnum;
import mc322_slay.EventEnum;
import mc322_slay.GameManager;
import mc322_slay.Interface;

/**
 * Efeito que mantém o alvo vulnerável ao ignorar AT Field por alguns turnos.
 */
public class ATFieldCorrosion extends Effect {

    /**
     * Retorna descrição textual do efeito.
     *
     * @return texto com nome e turnos restantes.
     */
    @Override
    public String getString() {
        return (name + " (" + points + " turnos restantes) - Escudo é desconsiderado (dano é dado diretamente na entidade)");
    }

    /**
     * Atualiza a duração do efeito no fim do turno do jogador.
     *
     * @param event       evento do jogo.
     * @param gameManager gerenciador da partida.
     * @return {@code true} quando o efeito termina.
     */
    @Override
    public boolean beNotified(EventEnum event, GameManager gameManager) {
        if (event == EventEnum.playerEndOfTurn) {
            // at the beginning of the turn the entity has their shield temporarely reduced
            // to 0
            this.points -= 1;
            if (this.points > 0) {
                // Interface.printMessage(points + " turnos restantes de " + name + " sobre " +
                // owner.getName() + ".",
                // ColorEnum.reset);
            }

            if (this.points == 0) {
                owner.removeEffect(this);
                return true;
            }
        }
        return false;
    }

    /**
     * Cria um efeito de corrosão do AT Field.
     *
     * @param name  nome do efeito.
     * @param turns duração em turnos.
     */
    public ATFieldCorrosion(String name, int turns) {
        this.name = name;
        this.points = turns;
        this.startPoints = turns;
    }

    /**
     * Cópia usada ao empilhar o mesmo tipo de efeito na entidade.
     *
     * @param effect protótipo copiado.
     */
    public ATFieldCorrosion(ATFieldCorrosion effect) {
        this.name = effect.name;
        this.owner = effect.owner;
        this.points = effect.points;
        this.startPoints = effect.startPoints;
    }
}
