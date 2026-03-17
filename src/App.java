public class App {
    public static void main(String[] args) throws Exception {
        GameManager game = new GameManager();
        
        game.start();
        game.initialScreen();
        game.selectCharacter();

        while (game.isRunning()) {
            game.buyCards();
            while (!game.endOfTurn()) {
                game.selectOption();
            }
            game.enemyAction();
            game.resetTurn();
        }

        game.results();
    }
}
