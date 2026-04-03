package mc322_slay;

public class App {
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
