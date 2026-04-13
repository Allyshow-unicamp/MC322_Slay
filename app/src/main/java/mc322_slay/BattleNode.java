package mc322_slay;

import mc322_slay.entity.Enemy;

public class BattleNode {
    private int id;
    private Enemy enemy;
    private boolean visited;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public BattleNode() {
        super();
    }

    public BattleNode(int id, Enemy enemy, boolean visited) {
        this.id = id;
        this.enemy = enemy;
        this.visited = visited;
    }
}
