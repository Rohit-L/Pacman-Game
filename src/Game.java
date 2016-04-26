import java.util.HashMap;

public class Game {

    private static HashMap<String, Integer> keyBindings = new HashMap<String, Integer>();
    public String[][] boardArray;
    public Agent agent;

    public static void main(String[] args) {

        keyBindings.put("Left", 37);
        keyBindings.put("Right", 39);
        keyBindings.put("Up", 38);
        keyBindings.put("Down", 40);

        GameState gameState = new GameState();
        Agent agent = new Agent();

        while(true) {

            /* Mouse Click */
            if (StdDraw.isKeyPressed(keyBindings.get("Left"))) {
                gameState.keyPressed(agent, "Left");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Right"))) {
                gameState.keyPressed(agent, "Right");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Up"))) {
                gameState.keyPressed(agent, "Up");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Down"))) {
                gameState.keyPressed(agent, "Down");
            }

            gameState.takeAction(agent);
            gameState.dispay();
        }
    }
}