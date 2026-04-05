package mc322_slay;

/**
 * Ponto de entrada da aplicação.
 * Executa o loop principal da partida.
 */
public class App {
    /**
     * Inicializa o jogo e controla o ciclo de turnos até o fim da partida.
     *
     * @param args argumentos de linha de comando (não utilizados).
     * @throws Exception caso ocorra erro inesperado durante a execução.
     */
    public static void main(String[] args) throws Exception {
        try {
            // to ensure ascii braille art and latin characters will be printed
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        GameManager game = new GameManager();

        game.start();
        game.initialScreen();
        game.selectCharacter();
        game.populateDeck();

        game.performNBattles(5);
    }
}
