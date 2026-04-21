import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mc322_slay.entity.Enemy;
import mc322_slay.event.Battle;
import mc322_slay.event.EventNode;

/**
 * Testes unitários de {@link EventNode} para validar construção e mutação de estado.
 */
public class EventNodeTest {
    /**
     * Garante que o construtor parametrizado persiste id, inimigo e visitação.
     */
    @Test
    public void constructorSetsNodeData() {
        Battle battle = new Battle(new Enemy("Sachiel", 180, 50, 20, 40, "sachiel.txt"));
        EventNode node = new EventNode('7', battle, true);

        assertEquals('7', node.getId());
        assertSame(battle, node.getEvent());
        assertTrue(node.isVisited());
    }

    /**
     * Garante que os setters atualizam corretamente os dados do nó.
     */
    @Test
    public void settersUpdateNodeState() {
        EventNode node = new EventNode();
        Battle battle = new Battle(new Enemy("Ramiel", 200, 80, 20, 40, "ramiel.txt"));

        node.setId('3');
        node.setEvent(battle);
        node.setVisited(false);

        assertEquals('3', node.getId());
        assertSame(battle, node.getEvent());
        assertFalse(node.isVisited());
    }
}
