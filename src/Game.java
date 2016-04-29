import java.util.HashMap;
import java.util.NoSuchElementException;

public class Game {

    private static HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();
    private static String layout = "standardLayout.txt";

    public static void playGame(GameState gameState) {
        boolean pause = false;
        while(true) {
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'p') {
                    Game.clearKeyCache();
                    System.out.println("P is pressed");
                    if (gameState.getState() == null) {
                        pause = true;
                    } else if (gameState.getState().equals("Pause")) {
                        pause = false;
                    }
                }
            }
            if (pause) { gameState.setState("Pause"); }
            if (!pause) { gameState.setState(null); }

            /* End Game Conditions */
            if (gameState.getTimeLeft() == 0 || gameState.getScore() <= -50 || gameState.getLivesLeft() == 0) {
                gameState.setState("Lose");
            }
            if (gameState.getNumFood() == 0) {
                gameState.setState("Win");
            }

            if (gameState.getState() != null) {
                if (gameState.getState().equals("Pause")) {
                    gameState.pause();
                    continue;
                } else {
                    System.out.println("game over");
                    gameState.display();
                    Game.endGame();
                    return;
                }
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

        }


    }

    public static void endGame() {
        long start = System.currentTimeMillis();
        Game.clearKeyCache();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'y') {
                    Game.clearKeyCache();
                    GameState newGameState = new GameState(layout);
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
            try {
                key = StdDraw.nextKeyTyped();
                System.out.println(key);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        keyBindings.put("Left", 37);
        keyBindings.put("Right", 39);
        keyBindings.put("Up", 38);
        keyBindings.put("Down", 40);

        GameState gameState = new GameState(layout);
        Game.playGame(gameState);

        System.out.println("GAME IS ACTUALLY OVER");

    }
}