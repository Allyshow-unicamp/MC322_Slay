package mc322_slay;

import mc322_slay.entity.Enemy;

/**
 * Nó lógico de um caminho do mapa.
 * Cada nó representa uma batalha contra um inimigo e mantém o estado de visita.
 */
public class BattleNode {
    private char id;
    private Enemy enemy;
    private boolean visited;

    /**
     * @return identificador único do nó no mapa.
     */
    public char getId() {
        return id;
    }

    /**
     * @param id novo identificador único do nó.
     */
    public void setId(char id) {
        this.id = id;
    }

    /**
     * @return inimigo associado à batalha deste nó.
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * @param enemy novo inimigo associado à batalha.
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * @return {@code true} quando o nó já foi visitado pelo jogador.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited marca se o nó já foi visitado.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Constrói um nó vazio para desserialização.
     */
    public BattleNode() {
        super();
    }

    /**
     * Cria um nó de batalha completo.
     *
     * @param id identificador do nó.
     * @param enemy inimigo da batalha.
     * @param visited estado inicial de visita.
     */
    public BattleNode(char id, Enemy enemy, boolean visited) {
        this.id = id;
        this.enemy = enemy;
        this.visited = visited;
    }
}
