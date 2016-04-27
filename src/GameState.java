import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

public class GameState {

    private String[][] configuration;
    private int numFood;
    private int numCapsules;
    private HashSet<Point> food = new HashSet<Point>();
    private HashSet<Point> capsules = new HashSet<Point>();

    private Board board;
    private String[] walls = new String[]{"1", "2", "3", "4", "5", "6", "*", "U", "X", "L"};


    public GameState() {

        StdDraw.setCanvasSize(1300, 500);

        this.configuration = new String[35][15];
        this.board = new Board(this.configuration);
        board.readLayout("standardLayout.txt");

        this.numFood = this.populateFood();
        this.numCapsules = this.populateCapsules();

        this.dispay();
    }

    public void dispay() {
        board.drawBoard();
        StdDraw.show(60);
    }

    public void keyPressed(Agent agent, String direction) {
        Point pacmanCurrentPosition = this.currentPacmanPosition();
        Point nextPosition = null;
        switch (direction) {
            case "Up":
                nextPosition = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y + 1);
                break;
            case "Right":
                nextPosition = new Point(pacmanCurrentPosition.x + 1, pacmanCurrentPosition.y);
                break;
            case "Down":
                nextPosition = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y - 1);
                break;
            case "Left":
                nextPosition = new Point(pacmanCurrentPosition.x - 1, pacmanCurrentPosition.y);
                break;
            default:
                nextPosition = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y);
                break;
        }
        if (!Arrays.asList(walls).contains(this.configuration[nextPosition.x][nextPosition.y])) {
            agent.currentDirection = direction;
        }

        System.out.println(direction + " Key Pressed");
    }

    public Point currentPacmanPosition() {
        for (int x = 0; x < 35; x++) {
            for (int y = 0; y < 15; y++) {
                if (this.configuration[x][y].equals("P")) {
                    return new Point(x,y);
                }
            }
        }
        return null;
    }

    public void takeAction(Agent agent) {

        String agentAction = agent.chooseAction();
        Point pacmanCurrentPosition = this.currentPacmanPosition();
        Point nextPosition = null;
        switch (agentAction) {
            case "Up":
                nextPosition = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y + 1);
                break;
            case "Right":
                nextPosition = new Point(pacmanCurrentPosition.x + 1, pacmanCurrentPosition.y);
                break;
            case "Down":
                nextPosition = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y - 1);
                break;
            case "Left":
                nextPosition = new Point(pacmanCurrentPosition.x - 1, pacmanCurrentPosition.y);
                break;
            default:
                nextPosition = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y);
                break;
        }


        if (!Arrays.asList(walls).contains(this.configuration[nextPosition.x][nextPosition.y])) {
            Point point = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y);
            if (this.food.contains(point)) {
                this.food.remove(point);
                this.numFood -= 1;
            }
            if (this.capsules.contains(point)) {
                this.capsules.remove(point);
                this.numCapsules -= 1;
            }
            configuration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = " ";
            configuration[nextPosition.x][nextPosition.y] = "P";
        }

    }

    public int populateFood() {
        int totalFood = 0;
        for (int i = 0; i < this.configuration.length; i++) {
            for (int j = 0; j < this.configuration[0].length; j++) {
                if (this.configuration[i][j].equals(".")) {
                    this.food.add(new Point(i, j));
                    totalFood += 1;
                }
            }
        }
        return totalFood;
    }

    public int populateCapsules() {
        int totalCapsules = 0;
        for (int i = 0; i < this.configuration.length; i++) {
            for (int j = 0; j < this.configuration[0].length; j++) {
                if (this.configuration[i][j].equals("C")) {
                    this.capsules.add(new Point(i, j));
                    totalCapsules += 1;
                }
            }
        }
        return totalCapsules;
    }

    public String[][] deepCopyBoard() {
        final String[][] result = new String[this.configuration.length][];
        for (int i = 0; i < this.configuration.length; i++) {
            result[i] = Arrays.copyOf(this.configuration[i], this.configuration[i].length);
        }
        return result;
    }

    public String toString() {
        for (int i = this.configuration[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < this.configuration.length; j++) {
                System.out.print(this.configuration[j][i] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("Num Food Left " + numFood);
        System.out.println("Num Capsules Left " + numCapsules);
        return "";
    }

    public int getNumFood() {
        return this.numFood;
    }

    public int getNumCapsules() {
        return this.numCapsules;
    }
}
