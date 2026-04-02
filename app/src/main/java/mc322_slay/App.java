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
    GameManager game = new GameManager();
    
    game.start();
    game.initialScreen();
    game.selectCharacter();

    while (game.isRunning()) {
        game.buyCards();
        int enemyOption = game.enemyPlanning();
        game.notifySubscribers(EventEnum.playerStartOfTurn);
        while (!game.endOfTurn()) {
            int option = game.selectOption();
            game.playerAction(option);
        }
        game.notifySubscribers(EventEnum.playerEndOfTurn);
        game.notifySubscribers(EventEnum.enemyStartOfTurn);
        game.enemyAction(enemyOption);
        game.notifySubscribers(EventEnum.enemyEndOfTurn);
        game.resetTurn();
    }

    game.results();
    }
}
