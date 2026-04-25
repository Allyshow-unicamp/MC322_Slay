package mc322_slay.event.command;

import mc322_slay.entity.Hero;

public class HealCommand extends Command {

    @Override
    public void execute(Hero hero) {
        hero.gainHealth((int) (hero.getMaxHealth() * 0.3));
    }
    
}
