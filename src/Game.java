import java.util.HashMap;

public class Game {

    private static HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();

    public static void playGame(GameState gameState) {
        while(true) {
            if (gameState.getState() != null) {
                System.out.println("game over");
                gameState.display();
                Game.endGame(gameState);
                return;
            }

            /* Mouse Click */
            if (StdDraw.isKeyPressed(keyBindings.get("Left"))) {
                gameState.keyPressed("Left");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Right"))) {
                gameState.keyPressed("Right");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Up"))) {
                gameState.keyPressed("Up");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Down"))) {
                gameState.keyPressed("Down");
            }

            gameState.takeAction();
            gameState.display();
            /* End Game Conditions */
            if (gameState.getTimeLeft() == 0 || gameState.getScore() <= -50) {
                gameState.setState("Lose");
            }
            if (gameState.getNumFood() == 0) {
                gameState.setState("Win");
            }
        }


    }

    public static void endGame(GameState gameState) {
        long start = System.currentTimeMillis();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'y') {
                    Game.clearKeyCache();
                    GameState newGameState = new GameState();
                    Game.playGame(newGameState);
                }
                return;
            }
            long now = System.currentTimeMillis();

            if (now - start >= 5000) {
                return;
            }

        }

    }

    public static void clearKeyCache() {
        char key = 'a';
        while (StdDraw.hasNextKeyTyped()) {
            key = StdDraw.nextKeyTyped();
            System.out.println(StdDraw.nextKeyTyped());
        }
    }

    public static void main(String[] args) {

        keyBindings.put("Left", 37);
        keyBindings.put("Right", 39);
        keyBindings.put("Up", 38);
        keyBindings.put("Down", 40);

        GameState gameState = new GameState();
        Game.playGame(gameState);

        System.out.println("GAME IS ACTUALLY OVER");

    }
}