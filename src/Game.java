import java.util.HashMap;

public class Game {

    private static HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();

    public static void main(String[] args) {

        keyBindings.put("Left", 37);
        keyBindings.put("Right", 39);
        keyBindings.put("Up", 38);
        keyBindings.put("Down", 40);

        GameState gameState = new GameState();

        while(true) {

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
            System.out.println(gameState.getScore());
            /* End Game Conditions */
            if (gameState.getTimeLeft() == 0) {
                System.out.println(gameState);
                System.out.println("YOU LOSE LOSER");
                break;
            }
            if (gameState.getNumFood() == 0) {
                System.out.println(gameState);
                System.out.println("YOU WON");
                break;
            }
        }
    }
}