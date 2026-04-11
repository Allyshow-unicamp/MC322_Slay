import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mc322_slay.effect.HighSyncRate;
import mc322_slay.effect.LowSyncRate;
import mc322_slay.entity.Enemy;
import mc322_slay.entity.Hero;

public class EnemyTest {
    @Test
    public void damageInflictedOnAttack() {
        Enemy enemy = new Enemy("Anjo", 200, 100, null);
        enemy.nextAction();
        Hero hero = new Hero("Herói", 50,50, null);
        assertEquals(enemy.getDamage(), enemy.attack(hero));
        assertEquals(50, hero.getHealth());
        assertEquals(50 - enemy.getDamage(), hero.getShield());
    }

    @Test
    public void damageInflictedWithEffects() {
        Enemy enemy = new Enemy("Anjo", 200, 100, null);
        enemy.nextAction();
        enemy.applyEffect(new HighSyncRate("Força", 3, 1.5));
        enemy.applyEffect(new LowSyncRate("Fraqueza", 3, 0.75));
        assertEquals(1.125, enemy.getBoost() * enemy.getDeboost());
        double attack = enemy.getDamage() * enemy.getBoost() * enemy.getDeboost();
        assertEquals(enemy.getDamage() * 1.125, attack);

        Hero hero = new Hero("Herói", 50, 50, null);
        assertEquals((int) attack, enemy.attack(hero));
        assertEquals(50, hero.getHealth());
        assertEquals(50 - (int) attack, hero.getShield());
    }
}
