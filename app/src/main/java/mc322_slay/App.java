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
        while (game.isRunning()) {
            game.notifySubscribers(EventEnum.playerStartOfTurn);
            Interface.printMessage("\r\n=== TURNO DO JOGADOR ===\r\n", ColorEnum.reset);

            game.buyCards();
            game.resetTurn();
            int enemyOption = game.enemyPlanning();
            while (!game.endOfTurn()) {
                int option = game.selectOption();
                game.playerAction(option);
            }
            game.notifySubscribers(EventEnum.playerEndOfTurn);

            if (game.isRunning()) {
                game.discardCards();

                game.notifySubscribers(EventEnum.enemyStartOfTurn);
                Interface.printMessage("\r\n=== TURNO DO INIMIGO ===\r\n", ColorEnum.reset);
                game.enemyAction(enemyOption);
                game.notifySubscribers(EventEnum.enemyEndOfTurn);
            }
        }

        game.results();
    }
}
