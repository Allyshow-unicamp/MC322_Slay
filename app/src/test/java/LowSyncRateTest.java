import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.EventEnum;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;
import mc322_slay.event.Battle;

/**
 * Testes de {@link mc322_slay.effect.LowSyncRate}: multiplicador de debuff do dono; duração decresce no
 * fim do turno correspondente (jogador ou anjo).
 */
public class LowSyncRateTest {
    @Test
    public void beNotifiedNotFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 20, null, null);

        LowSyncRate lowSyncRate = new LowSyncRate("Fraqueza", 3, 0.5);
        lowSyncRate.setOwner(hero);

        Battle battle = battleFor(hero);

        assertFalse(lowSyncRate.beNotified(EventEnum.playerEndOfTurn, battle));
    }

    @Test
    public void beNotifiedFinishedEffect() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new LowSyncRate("Fraqueza", 1, 0.5));
        LowSyncRate low = (LowSyncRate) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertTrue(low.beNotified(EventEnum.playerEndOfTurn, battle));
        assertFalse(hero.hasEffect(LowSyncRate.class));
    }

    @Test
    public void deboostWhileEffectActive() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new LowSyncRate("Fraqueza", 2, 0.5));

        assertEquals(0.5, hero.getDeboost(), 1e-9);
    }

    @Test
    public void beNotifiedWrongEvent() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new LowSyncRate("Fraqueza", 2, 0.5));
        LowSyncRate low = (LowSyncRate) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertEquals(2, low.getPoints());

        assertFalse(low.beNotified(EventEnum.playerStartOfTurn, battle));
        assertEquals(2, low.getPoints());
    }

    @Test
    public void beNotifiedTurnsDecreased() {
        Hero hero = new Hero("Herói", 50, 0, null, null);
        hero.applyEffect(new LowSyncRate("Fraqueza", 2, 0.5));
        LowSyncRate low = (LowSyncRate) hero.getLastEffect();
        Battle battle = battleFor(hero);

        assertFalse(low.beNotified(EventEnum.playerEndOfTurn, battle));
        assertEquals(1, low.getPoints());
        assertEquals(0.5, hero.getDeboost(), 1e-9);
        assertTrue(hero.hasEffect(LowSyncRate.class));
    }

    @Test
    public void beNotifiedEnemyEndOfTurn() {
        Enemy angel = new Enemy("Anjo", 200, 100, 20, 40, null);
        angel.applyEffect(new LowSyncRate("Fraqueza", 2, 0.5));
        LowSyncRate low = (LowSyncRate) angel.getLastEffect();
        Battle battle = new Battle(angel);

        assertEquals(0.5, angel.getDeboost(), 1e-9);

        assertFalse(low.beNotified(EventEnum.enemyEndOfTurn, battle));
        assertEquals(1, low.getPoints());
        assertTrue(angel.hasEffect(LowSyncRate.class));
    }

    private static Battle battleFor(Hero hero) {
        return new Battle(new Enemy("Anjo", 200, 100, 20, 40, null));
    }
}
