package mc322_slay.visitor;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.effect.HealthRegeneration;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.effect.PsychicEffect;

public class UpgradeEffectVisitor implements EffectVisitor {

    @Override
    public void visit(ATFieldCorrosion effect) {
        effect.setStartPoints(effect.getStartPoints() * 2);
        effect.setPoints(effect.getPoints() * 2);
        Interface.printMessage(effect.getName() + " teve sua duração máxima dobrada.", ColorEnum.yellow);
    }

    @Override
    public void visit(HealthRegeneration effect) {
        effect.setHealth(effect.getHealth() * 2);
        Interface.printMessage(effect.getName() + " teve sua vida recuperada por turno dobrada.", ColorEnum.yellow);
    }

    @Override
    public void visit(HighSyncRate effect) {
        effect.setBoost(effect.getBoost() * 2);
        Interface.printMessage(effect.getName() + " teve seu multiplicador dobrado.", ColorEnum.yellow);
    }

    @Override
    public void visit(LowSyncRate effect) {
        effect.setDeboost(effect.getDeboost() / 2);
        Interface.printMessage(effect.getName() + " teve seu multiplicador reduzido pela metade.", ColorEnum.yellow);
    }

    @Override
    public void visit(PsychicEffect effect) {
        effect.setDamage(effect.getDamage() * 2);
        Interface.printMessage(effect.getName() + " teve seu dano dado por turno dobrado.", ColorEnum.yellow);
    }
}
