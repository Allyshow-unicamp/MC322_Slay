public class App {
    public static void main(String[] args) throws Exception {
        GameManager game = new GameManager();
        
        game.start();
        game.initialScreen();
        game.selectCharacter();

        while (game.isRunning()) {
            game.buyCards();
            while (!game.endOfTurn()) {
                int option = game.selectOption();
                game.playerAction(option);
            }
            game.enemyAction();
            game.resetTurn();
        }

        game.results();
    }
}
