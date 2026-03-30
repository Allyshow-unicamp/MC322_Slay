package mc322_slay;

public class App {
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
