package mc322_slay.entity;

/**
 * Ações possíveis do inimigo durante a partida.
 */
public enum EnemyActions {
    attack(0), gainShield(1), useEffect(2);

    private final int value;

    /**
     * Cria uma ação de inimigo com valor numérico associado.
     *
     * @param option valor da ação.
     */
    EnemyActions(int option) {
        value = option;
    }

    /**
     * Retorna o valor numérico da ação.
     *
     * @return identificador inteiro da ação.
     */
    public int getValue() {
        return value;
    }
}
