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
            while (!game.endOfTurn()) {
                int option = game.selectOption();
                game.playerAction(option);
            }
            game.enemyAction(enemyOption);
            game.resetTurn();
        }

        game.results();
    }
}
