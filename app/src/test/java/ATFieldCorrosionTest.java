import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.Battle;
import mc322_slay.EventEnum;
import mc322_slay.card.CardStack;
import mc322_slay.effect.ATFieldCorrosion;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Testes de {@link mc322_slay.effect.ATFieldCorrosion}: notificação e expiração ao fim do turno.
 */
public class ATFieldCorrosionTest {
    @Test
    public void beNotifiedNotFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 20, null);

        ATFieldCorrosion atFieldCorrosion = new ATFieldCorrosion("Corrosão", 3);
        atFieldCorrosion.setOwner(hero);

        Battle battle = new Battle(hero, new Enemy("Anjo", 200, 100, 20, 40, null), new CardStack());

        assertFalse(atFieldCorrosion.beNotified(EventEnum.playerEndOfTurn, battle));
    }

    @Test
    public void beNotifiedFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 20, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão", 1));

        Battle battle = new Battle(hero, new Enemy("Anjo", 200, 100, 20, 40, null), new CardStack());
    
        ATFieldCorrosion corrosion = (ATFieldCorrosion) hero.getLastEffect();
        assertTrue(corrosion.beNotified(EventEnum.playerEndOfTurn, battle));
        assertFalse(hero.hasEffect(ATFieldCorrosion.class));
    }

    @Test
    public void corrosionWhileActiveDamageBypassesShield() {
        Hero hero = new Hero("Herói", 50, 20, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão", 2));

        hero.takeDamage(10);
        assertEquals(40, hero.getHealth());
        assertEquals(20, hero.getShield());
    }

    @Test
    public void beNotifiedWrongEvent() {
        Hero hero = new Hero("Herói", 50, 20, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão", 2));
        ATFieldCorrosion corrosion = (ATFieldCorrosion) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertFalse(corrosion.beNotified(EventEnum.playerStartOfTurn, battle));
        assertEquals(2, corrosion.getPoints());
    }

    @Test
    public void beNotifiedTurnsDecreased() {
        Hero hero = new Hero("Herói", 50, 20, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão", 2));
        ATFieldCorrosion corrosion = (ATFieldCorrosion) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertFalse(corrosion.beNotified(EventEnum.playerEndOfTurn, battle));
        assertEquals(1, corrosion.getPoints());
    }

    @Test
    public void shieldReabsorbsAfterCorrosion() {
        Hero hero = new Hero("Herói", 50, 20, null);
        hero.applyEffect(new ATFieldCorrosion("Corrosão", 1));
        ATFieldCorrosion corrosion = (ATFieldCorrosion) hero.getLastEffect();
        Battle battle = battleFor(hero);

        hero.takeDamage(10);
        assertEquals(40, hero.getHealth());
        assertEquals(20, hero.getShield());

        assertTrue(corrosion.beNotified(EventEnum.playerEndOfTurn, battle));

        hero.takeDamage(10);
        assertEquals(40, hero.getHealth());
        assertEquals(10, hero.getShield());
    }

    private static Battle battleFor(Hero hero) {
        return new Battle(hero, new Enemy("Anjo", 200, 100, 20, 40, null), new CardStack());
    }
}
