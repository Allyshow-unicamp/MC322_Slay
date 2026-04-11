package mc322_slay;

/**
 * Eventos do ciclo de turnos usados para notificar efeitos ativos ({@link mc322_slay.effect.Effect}).
 * Nem todos os valores são disparados pelo código atual; os existentes cobrem início/fim de turno
 * do jogador e do inimigo.
 */
public enum EventEnum {
    /** Início do turno do jogador (ex.: regeneração de vida no herói). */
    playerStartOfTurn(1),
    /** Reservado para eventos de ataque do jogador (não utilizado na lógica atual). */
    playerAttack(2),
    /** Fim do turno do jogador (ex.: dano psicológico, alta/baixa sincronização, corrosão). */
    playerEndOfTurn(3),
    /** Início do turno do inimigo (ex.: regeneração no anjo). */
    enemyStartOfTurn(4),
    /** Reservado para eventos de ataque do inimigo (não utilizado na lógica atual). */
    enemyAttack(5),
    /** Fim do turno do inimigo (ex.: dano psicológico com dono {@link mc322_slay.entity.Enemy}). */
    enemyEndOfTurn(6);

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
