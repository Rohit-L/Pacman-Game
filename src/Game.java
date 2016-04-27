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
        Pacman pacman = new Pacman();
        System.out.println(gameState);

        while(true) {

            /* Mouse Click */
            if (StdDraw.isKeyPressed(keyBindings.get("Left"))) {
                gameState.keyPressed(pacman, "Left");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Right"))) {
                gameState.keyPressed(pacman, "Right");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Up"))) {
                gameState.keyPressed(pacman, "Up");
            }
            if (StdDraw.isKeyPressed(keyBindings.get("Down"))) {
                gameState.keyPressed(pacman, "Down");
            }

            gameState.takeAction(pacman);
            gameState.display();

            if (gameState.getNumFood() == 0) {
                System.out.println(gameState);
                break;
            }
        }
    }
}