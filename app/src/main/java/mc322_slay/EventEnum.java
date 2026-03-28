package mc322_slay;

public enum EventEnum {
    playerStartOfTurn(1), playerAttack(2), playerEndOfTurn(3), 
    enemyStartOfTurn(4), enemyAttack(5), enemyEndOfTurn(6);

    private final int value;
    
    EventEnum(int option) {
        value = option;
    }
    
    public int getValue() {
        return value;
    }
}
