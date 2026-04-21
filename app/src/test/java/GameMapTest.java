import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.jupiter.api.Test;

import mc322_slay.GameMap;
import mc322_slay.event.EventNode;

/**
 * Testes unitários de {@link GameMap} para validação de criação e movimentação no mapa.
 */
public class GameMapTest {
    /**
     * Verifica se o mapa é construído com a raiz e nó inicial do jogador.
     */
    @Test
    public void buildMapInitializesTreeAndPlayerOnRootNode() {
        GameMap map = new GameMap();

        map.buildMap("map.json", "events.json");

        DefaultMutableTreeNode playerNode = map.getPlayerNode();
        assertNotNull(playerNode);

        assertEquals(2, playerNode.getChildCount());
    }

    /**
     * Verifica se a referência de posição do jogador muda ao avançar para um filho.
     */
    @Test
    public void setPlayerNodeUpdatesCurrentNodeReference() {
        GameMap map = new GameMap();
        map.buildMap("map.json", "events.json");

        DefaultMutableTreeNode child = (DefaultMutableTreeNode) map.getPlayerNode().getChildAt(0);
        map.setPlayerNode(child);

        EventNode battleNode = (EventNode) map.getPlayerNode().getUserObject();
        assertEquals('1', battleNode.getId());
    }
}
