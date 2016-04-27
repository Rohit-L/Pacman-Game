import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GameState {

    String[][] configuration;
    private int agentTurn;
    private int numFood;
    private int numCapsules;
    private int timeLeft;
    private boolean capsuleActive;
    private HashSet<Point> food = new HashSet<Point>();
    private HashSet<Point> capsules = new HashSet<Point>();
    private static int START_TIME = 800;
    private int capsuleTimer;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    Agent[][] agentConfiguration;
    Directions dir;

    private Board board;
    private String[] walls = new String[]{"1", "2", "3", "4", "5", "6", "*", "U", "X", "L"};

    public GameState() {

        StdDraw.setCanvasSize(1300, 500);

        this.configuration = new String[35][15];
        this.board = new Board(this.configuration);
        board.readLayout("standardLayout.txt");

        this.agentTurn = 0;
        this.numFood = this.populateFood();
        this.numCapsules = this.populateCapsules();
        this.timeLeft = START_TIME;
        this.capsuleActive = false;
        this.capsuleTimer = 0;
        this.pacman = new Pacman();
        this.ghosts.add(new Ghost(0));
        this.ghosts.add(new Ghost(1));
        this.agentConfiguration = new Agent[35][15];
        this.setInitialPositions();
        this.dir = new Directions();
        this.display();
    }

    private void setInitialPositions() {
        for (int x = 0; x < 35; x++) {
            for (int y = 0; y < 15; y++) {
                if (this.configuration[x][y].equals("P")) {
                    this.agentConfiguration[x][y] = this.pacman;
                    this.pacman.setCurrentPosition(new Point(x,y));
                } else if (this.configuration[x][y].equals("G")) {
                    if (this.ghosts.get(0).getCurrentPosition() == null) {
                        this.agentConfiguration[x][y] = this.ghosts.get(0);
                        this.ghosts.get(0).setCurrentPosition(new Point(x, y));
                    } else {
                        this.agentConfiguration[x][y] = this.ghosts.get(1);
                        this.ghosts.get(1).setCurrentPosition(new Point(x, y));
                    }
                } else {
                    this.agentConfiguration[x][y] = null;
                }
            }
        }
    }

    public void display() {
        board.drawBoard();
        StdDraw.show(60);
    }

    public void keyPressed(String direction) {
        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();
        Point nextPosition = new Point(pacmanCurrentPosition);
        dir.next(nextPosition, direction);

        if (!Arrays.asList(walls).contains(this.configuration[nextPosition.x][nextPosition.y])) {
            this.pacman.setCurrentDirection(direction);
        }
    }

    public void takeAction() {
        if (agentTurn % 3 == 0) {
            executePacmanTurn();
        } else {
            executeGhostTurn(agentTurn);
        }
        agentTurn += 1;
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
        System.out.println("Time Left " + timeLeft);
        return "";
    }

    //GETTERS AND SETTERS

    public int getNumFood() {
        return this.numFood;
    }

    public int getNumCapsules() {
        return this.numCapsules;
    }

    public int getTimeLeft() {
        return this.timeLeft;
    }

    public Point getPacmanPosition() {
        return this.pacman.getCurrentPosition();
    }

    private void executePacmanTurn() {
        String agentAction = this.pacman.chooseAction(this);
        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();
        Point nextPosition = new Point(pacmanCurrentPosition);
        dir.next(nextPosition, agentAction);


        if (!Arrays.asList(walls).contains(this.configuration[nextPosition.x][nextPosition.y])) {
            Point point = new Point(pacmanCurrentPosition.x, pacmanCurrentPosition.y);
            boolean ghostDeath = false;
            boolean pacmanDeath = false;

            /* Check for eaten food */
            if (this.food.contains(point)) {
                this.food.remove(point);
                pacman.eatenFood += 1;
                this.numFood -= 1;
            }

            /* Check for eaten capsule */
            if (this.capsules.contains(point)) {
                if (!this.capsuleActive) {
                    this.capsuleActive = true;
                    this.ghosts.get(0).setScaredTimer(Ghost.SCARED_TIME);
                    this.ghosts.get(1).setScaredTimer(Ghost.SCARED_TIME);
                }
                this.capsules.remove(point);
                this.numCapsules -= 1;
            }

            for (Ghost ghost: this.ghosts) {
                if (ghost.getCurrentPosition().equals(nextPosition)) {
                    if (ghost.getScaredTimer() > 0) {

                        this.configuration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = "";
                        this.configuration[nextPosition.x][nextPosition.y] = "P";
                        this.configuration[1][1] = "G";
                        ghost.setCurrentPosition(new Point(1,1));
                        this.pacman.setCurrentPosition(nextPosition);
                        this.agentConfiguration[1][1] = ghost;
                        this.agentConfiguration[nextPosition.x][nextPosition.y] = this.pacman;
                        this.agentConfiguration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = null;
                        ghost.setScaredTimer(0);
                        ghostDeath = true;
                        break;
                    } else {

                        this.configuration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = "";
                        this.configuration[1][1] = "P";
                        this.pacman.setCurrentPosition(new Point(1,1));
                        this.agentConfiguration[1][1] = this.pacman;
                        this.agentConfiguration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = null;
                        pacmanDeath = true;
                        break;
                    }
                }
            }

            if (!pacmanDeath && !ghostDeath) {
                this.configuration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = "";
                this.configuration[nextPosition.x][nextPosition.y] = "P";
                this.pacman.setCurrentPosition(nextPosition);
                this.agentConfiguration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = null;
                this.agentConfiguration[nextPosition.x][nextPosition.y] = this.pacman;
            }

        }

        this.timeLeft -= 1;
    }

    private void executeGhostTurn(int agentTurn) {
        Ghost ghost = this.ghosts.get(agentTurn % 3 - 1);
        String ghostAction = ghost.chooseAction(this);
        Point ghostCurrentPosition = ghost.getCurrentPosition();
        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();

        Point nextPosition = new Point(ghostCurrentPosition);
        dir.next(nextPosition, ghostAction);

        if (!Arrays.asList(walls).contains(this.configuration[nextPosition.x][nextPosition.y])) {
            Point point = new Point(ghostCurrentPosition.x, ghostCurrentPosition.y);
            boolean ghostDeath = false;
            boolean pacmanDeath = false;

            if (pacmanCurrentPosition.equals(nextPosition)) {
                if (ghost.getScaredTimer() == 0) {

                    /* Move the ghost to its new location */
                    this.configuration[ghostCurrentPosition.x][ghostCurrentPosition.y] = "";
                    this.configuration[nextPosition.x][nextPosition.y] = "G";
                    this.agentConfiguration[ghostCurrentPosition.x][ghostCurrentPosition.y] = null;
                    this.agentConfiguration[nextPosition.x][nextPosition.y] = ghost;
                    ghost.setCurrentPosition(new Point(nextPosition.x, nextPosition.y));

                    /* Reset Pacman to the initial location */
                    this.configuration[1][1] = "P";
                    this.pacman.setCurrentPosition(new Point(1,1));
                    ghost.setCurrentPosition(nextPosition);
                    this.agentConfiguration[1][1] = this.pacman;
                    this.agentConfiguration[nextPosition.x][nextPosition.y] = ghost;
                    this.agentConfiguration[pacmanCurrentPosition.x][pacmanCurrentPosition.y] = null;
                    pacmanDeath = true;

                } else {

                    /* Ghost is scared and runs into pacman */
                    this.configuration[ghostCurrentPosition.x][ghostCurrentPosition.y] = "";
                    this.configuration[1][1] = "G";
                    ghost.setCurrentPosition(new Point(1,1));
                    this.agentConfiguration[1][1] = ghost;
                    this.agentConfiguration[ghostCurrentPosition.x][ghostCurrentPosition.y] = null;
                    ghost.setScaredTimer(0);
                    ghostDeath = true;
                }
            }


            if (!pacmanDeath && !ghostDeath) {
                this.configuration[ghostCurrentPosition.x][ghostCurrentPosition.y] = "";
                this.configuration[nextPosition.x][nextPosition.y] = "G";
                ghost.setCurrentPosition(nextPosition);
                this.agentConfiguration[ghostCurrentPosition.x][ghostCurrentPosition.y] = null;
                this.agentConfiguration[nextPosition.x][nextPosition.y] = ghost;
            }

        }

        if (ghosts.get(0).getScaredTimer() == 0 && ghosts.get(1).getScaredTimer() == 0) {
            this.capsuleActive = false;
        }

    }
}
