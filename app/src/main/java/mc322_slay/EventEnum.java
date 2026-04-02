package mc322_slay;

/**
 * Eventos do ciclo de turnos usados para notificar efeitos ativos.
 */
public enum EventEnum {
    playerStartOfTurn(1), playerAttack(2), playerEndOfTurn(3), 
    enemyStartOfTurn(4), enemyAttack(5), enemyEndOfTurn(6);

    private final int value;
    
    /**
     * Cria um evento com identificador numérico.
     *
     * @param option valor associado ao evento.
     */
    EventEnum(int option) {
        value = option;
    }
    
    /**
     * Retorna o valor numérico do evento.
     *
     * @return identificador inteiro do evento.
     */
    public int getValue() {
        return value;
    }
}
