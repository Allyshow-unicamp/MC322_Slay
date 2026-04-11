import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.Battle;
import mc322_slay.EventEnum;
import mc322_slay.card.CardStack;
import mc322_slay.effect.HighSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Testes de {@link mc322_slay.effect.HighSyncRate}: multiplicador de dano do dono; duração decresce no
 * fim do turno correspondente (jogador ou anjo).
 */
public class HighSyncRateTest {
    @Test
    public void beNotifiedNotFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 20, null);

        HighSyncRate highSyncRate = new HighSyncRate("Força", 3, 1.5);
        highSyncRate.setOwner(hero);

        Battle battle = battleFor(hero);

        assertFalse(highSyncRate.beNotified(EventEnum.playerEndOfTurn, battle));
    }

    @Test
    public void beNotifiedFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 0, null);
        hero.applyEffect(new HighSyncRate("Força", 1, 1.5));
        HighSyncRate high = (HighSyncRate) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertTrue(high.beNotified(EventEnum.playerEndOfTurn, battle));
        assertFalse(hero.hasEffect(HighSyncRate.class));
    }

    @Test
    public void boostWhileEffectActive() {
        Hero hero = new Hero("Herói", 50, 0, null);
        hero.applyEffect(new HighSyncRate("Força", 2, 1.5));

        assertEquals(1.5, hero.getBoost(), 1e-9);
    }

    @Test
    public void beNotifiedWrongEvent() {
        Hero hero = new Hero("Herói", 50, 0, null);
        hero.applyEffect(new HighSyncRate("Força", 2, 1.5));
        HighSyncRate high = (HighSyncRate) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertEquals(2, high.getPoints());

        assertFalse(high.beNotified(EventEnum.playerStartOfTurn, battle));
        assertEquals(2, high.getPoints());
    }

    @Test
    public void beNotifiedTurnsDecreased() {
        Hero hero = new Hero("Herói", 50, 0, null);
        hero.applyEffect(new HighSyncRate("Força", 2, 1.5));
        HighSyncRate high = (HighSyncRate) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertFalse(high.beNotified(EventEnum.playerEndOfTurn, battle));
        assertEquals(1, high.getPoints());
        assertEquals(1.5, hero.getBoost(), 1e-9);
        assertTrue(hero.hasEffect(HighSyncRate.class));
    }

    @Test
    public void beNotifiedEnemyEndOfTurn() {
        Hero hero = new Hero("Herói", 50, 0, null);
        Enemy angel = new Enemy("Anjo", 200, 100, null);
        angel.applyEffect(new HighSyncRate("Força", 2, 1.5));
        HighSyncRate high = (HighSyncRate) angel.getLastEffect();
        Battle battle = new Battle(hero, angel, new CardStack());

        assertEquals(1.5, angel.getBoost(), 1e-9);

        assertFalse(high.beNotified(EventEnum.enemyEndOfTurn, battle));
        assertEquals(1, high.getPoints());
        assertTrue(angel.hasEffect(HighSyncRate.class));
    }

    private static Battle battleFor(Hero hero) {
        return new Battle(hero, new Enemy("Anjo", 200, 100, null), new CardStack());
    }
}
