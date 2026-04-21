import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

/**
 * Testes do {@link mc322_slay.entity.Enemy}: dano do ataque e interação com efeitos de
 * sincronização no multiplicador final.
 */
public class EnemyTest {
    /**
     * Garante que o ataque aplica o dano base e reduz o escudo do herói alvo.
     */
    @Test
    public void damageInflictedOnAttack() {
        Enemy enemy = new Enemy("Anjo", 200, 100, 20, 40, null);
        enemy.nextAction();
        Hero hero = new Hero("Herói", 50, 50, null, null);
        assertEquals(enemy.getDamage(), enemy.attack(hero));
        assertEquals(50, hero.getHealth());
        assertEquals(50 - enemy.getDamage(), hero.getShield());
    }

    /**
     * Garante que efeitos de boost/deboost alteram o multiplicador de dano final.
     */
    @Test
    public void damageInflictedWithEffects() {
        Enemy enemy = new Enemy("Anjo", 200, 100, 20, 40, null);
        enemy.nextAction();
        enemy.applyEffect(new HighSyncRate("Força", 3, 1.5));
        enemy.applyEffect(new LowSyncRate("Fraqueza", 3, 0.75));
        double multiplier = enemy.getBoost() * enemy.getDeboost();
        assertEquals(1.125, multiplier);
        double attack = enemy.getDamage();

        Hero hero = new Hero("Herói", 50, 50, null, null);
        assertEquals((int) attack, enemy.attack(hero));
        assertEquals(50, hero.getHealth());
        assertEquals(50 - (int) attack, hero.getShield());
    }
}
