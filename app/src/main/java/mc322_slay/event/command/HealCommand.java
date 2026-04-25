package mc322_slay.event.command;

import mc322_slay.entity.Hero;

/**
 * Comando que recupera parte da vida máxima do herói.
 */
public class HealCommand extends Command {

    /**
     * Cura 30% da vida máxima do herói.
     *
     * @param hero herói alvo da cura.
     */
    @Override
    public void execute(Hero hero) {
        hero.gainHealth((int) (hero.getMaxHealth() * 0.3));
    }
    
}
