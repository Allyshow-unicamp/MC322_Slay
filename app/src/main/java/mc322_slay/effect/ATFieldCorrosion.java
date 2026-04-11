package mc322_slay.effect;

import mc322_slay.Battle;
import mc322_slay.EventEnum;

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
        return (name + " (" + points
                + " turnos restantes) - Escudo é desconsiderado (dano é dado diretamente na entidade)");
    }

    /**
     * Decrementa a duração ao fim do turno do jogador ({@link mc322_slay.EventEnum#playerEndOfTurn}).
     * Enquanto ativo, o dono ignora o campo AT ao receber dano (vide {@link mc322_slay.entity.Entity#takeDamage(int)}).
     *
     * @param event  evento do jogo.
     * @param battle batalha atual.
     * @return {@code true} quando o efeito termina e deve ser removido dos inscritos.
     */
    @Override
    public boolean beNotified(EventEnum event, Battle battle) {
        if (event == EventEnum.playerEndOfTurn) {
            this.points -= 1;

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

    @Override
    public ATFieldCorrosion cloneEffect() {
        return new ATFieldCorrosion(this);
    }

    @Override 
    public void merge(Effect effect) {}
}
