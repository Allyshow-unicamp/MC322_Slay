package mc322_slay.visitor;

import mc322_slay.ColorEnum;
import mc322_slay.Interface;
import mc322_slay.card.DamageCard;
import mc322_slay.card.EffectCard;
import mc322_slay.card.ShieldCard;
import mc322_slay.effect.Effect;

/**
 * Visitor que aplica melhorias em cartas.
 */
public class UpgradeVisitor implements Visitor {

    /**
     * Dobra multiplicadores de cartas de dano.
     *
     * @param card carta alvo.
     */
    @Override
    public void visit(DamageCard card) {
        card.setMultiplier(card.getMultiplier() * 2);
        Interface.printMessage(card.getName() + " teve seus danos mínimo e máximo dobrados.", ColorEnum.yellow);
    }

    /**
     * Dobra multiplicadores de cartas de escudo.
     *
     * @param card carta alvo.
     */
    @Override
    public void visit(ShieldCard card) {
        card.setMultiplier(card.getMultiplier() * 2);
        Interface.printMessage(card.getName() + " teve seus escudos mínimo e máximo dobrados.", ColorEnum.yellow);
    }

    /**
     * Encaminha melhoria para o efeito embutido da carta de efeito.
     *
     * @param card carta alvo.
     */
    @Override
    public void visit(EffectCard card) {
        Effect effect = card.getEffect();
        UpgradeEffectVisitor visitor = new UpgradeEffectVisitor();
        effect.accept(visitor);
    }
    
}
