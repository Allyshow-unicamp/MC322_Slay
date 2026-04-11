package mc322_slay;

/**
 * Códigos de cor ANSI para estilizar mensagens no terminal.
 */
public enum ColorEnum {
    /** Restaura a formatação padrão do terminal. */
    reset("\u001B[0m"),
    /** Texto vermelho. */
    red("\u001B[31m"),
    /** Texto verde. */
    green("\u001B[32m"),
    /** Texto amarelo. */
    yellow("\u001B[33m"),
    /** Texto azul. */
    blue("\u001B[34m"),
    /** Texto roxo/magenta. */
    purple("\u001B[35m");

    /** Sequência de escape ANSI correspondente à cor. */
    private final String color;

    /**
     * @param color código ANSI (ex.: {@code \u001B[31m} para vermelho).
     */
    ColorEnum(String color) {
        this.color = color;
    }

    /**
     * Retorna a sequência ANSI desta cor para concatenação com o texto.
     *
     * @return código de cor ANSI.
     */
    public String getColor() {
        return this.color;
    }
}
