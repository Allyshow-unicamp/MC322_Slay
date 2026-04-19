import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.BattleNode;
import mc322_slay.entity.Enemy;

/**
 * Testes unitários de {@link BattleNode} para validar construção e mutação de estado.
 */
public class BattleNodeTest {
    /**
     * Garante que o construtor parametrizado persiste id, inimigo e visitação.
     */
    @Test
    public void constructorSetsNodeData() {
        Enemy enemy = new Enemy("Sachiel", 180, 50, 20, 40, "sachiel.txt");
        BattleNode node = new BattleNode('7', enemy, true);

        assertEquals('7', node.getId());
        assertSame(enemy, node.getEnemy());
        assertTrue(node.isVisited());
    }

    /**
     * Garante que os setters atualizam corretamente os dados do nó.
     */
    @Test
    public void settersUpdateNodeState() {
        BattleNode node = new BattleNode();
        Enemy enemy = new Enemy("Ramiel", 200, 80, 20, 40, "ramiel.txt");

        node.setId('3');
        node.setEnemy(enemy);
        node.setVisited(false);

        assertEquals('3', node.getId());
        assertSame(enemy, node.getEnemy());
        assertFalse(node.isVisited());
    }
}
