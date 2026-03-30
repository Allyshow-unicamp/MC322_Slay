package mc322_slay.entity;

public enum EnemyActions {
    attack(0), gainShield(1), useEffect(2);

    private final int value;

    EnemyActions(int option) {
        value = option;
    }

    public int getValue() {
        return value;
    }
}
