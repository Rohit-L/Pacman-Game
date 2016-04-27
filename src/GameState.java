import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GameState {

    private static int START_TIME = 1000;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private HashSet<Point> food;
    private HashSet<Point> capsules;
    private HashSet<Point> walls;
    private int numFood;
    private Directions dir;
    private Board board;
    private int agentTurn;
    private int numCapsules;
    private int timeLeft;
    private boolean capsuleActive;
    private int score;
    private String state;

    public GameState() {

        StdDraw.setCanvasSize(1300, 500);
        this.board = new Board();
        HashMap<String, ArrayList<Point>> allElems = board.readLayout("standardLayout.txt");
        this.food = new HashSet<>(allElems.get("Food"));
        this.capsules = new HashSet<>(allElems.get("Capsules"));
        this.walls = new HashSet<>(allElems.get("Walls"));
        this.pacman = new Pacman(allElems.get("Pacman").get(0));
        for (int i = 0; i < allElems.get("Ghosts").size(); i++) {
            this.ghosts.add(new Ghost(i + 1, allElems.get("Ghosts").get(i)));
        }

        this.score = 0;
        this.agentTurn = 0;
        this.numFood = this.food.size();
        this.numCapsules = this.capsules.size();
        this.timeLeft = START_TIME;
        this.capsuleActive = false;
        this.state = null;


        this.dir = new Directions();
        this.display();
    }


    public void display() {
        board.drawBoard(this);
        StdDraw.show(60);
    }

    public void keyPressed(String direction) {
//        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();
//        Point nextPosition = new Point(pacmanCurrentPosition);
//        dir.next(nextPosition, direction);
//
//        if (!this.walls.contains(nextPosition)) {
//            this.pacman.setCurrentDirection(direction);
//        }
        this.pacman.setFutureDirection(direction);
    }

    public void takeAction() {
        if (agentTurn % 3 == 0) {
            executePacmanTurn();
        } else {
            executeGhostTurn(agentTurn);
        }
        agentTurn += 1;
    }


//    public String[][] deepCopyBoard() {
//        final String[][] result = new String[this.configuration.length][];
//        for (int i = 0; i < this.configuration.length; i++) {
//            result[i] = Arrays.copyOf(this.configuration[i], this.configuration[i].length);
//        }
//        return result;
//    }

    public String toString() {
        System.out.println("Num Food Left " + numFood);
        System.out.println("Num Capsules Left " + numCapsules);
        System.out.println("Time Left " + timeLeft);
        System.out.println("Pacman Position" + this.pacman.getCurrentPosition());
        System.out.println("Score " + this.score);
        return "";
    }


    private void executePacmanTurn() {
        ArrayList<String> agentActions = this.pacman.chooseAction(this);
        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();
        Point nextPosition = new Point(pacmanCurrentPosition);
        dir.next(nextPosition, agentActions.get(0));

        if (this.walls.contains(nextPosition)) {
            nextPosition = new Point(pacmanCurrentPosition);
            dir.next(nextPosition, agentActions.get(1));
        } else {
            this.pacman.setCurrentDirection(agentActions.get(0));
        }

        if (!this.walls.contains(nextPosition)) {
            boolean ghostDeath = false;
            boolean pacmanDeath = false;

            /* Check for eaten food */
            if (this.food.contains(nextPosition)) {
                this.food.remove(nextPosition);
                this.pacman.eatenFood += 1;
                this.score += 5;
                this.numFood -= 1;
            }

            /* Check for eaten capsule */
            if (this.capsules.contains(nextPosition)) {
                if (!this.capsuleActive) {
                    this.capsuleActive = true;
                    this.ghosts.get(0).setScaredTimer(Ghost.SCARED_TIME);
                    this.ghosts.get(1).setScaredTimer(Ghost.SCARED_TIME);
                }
                this.capsules.remove(nextPosition);
                this.numCapsules -= 1;
            }

            for (Ghost ghost: this.ghosts) {
                if (ghost.getCurrentPosition().equals(nextPosition)) {
                    if (ghost.getScaredTimer() > 0) { //ghost is scared, dies
                        this.pacman.setCurrentPosition(nextPosition);
                        ghost.setCurrentPosition(ghost.getInitialPosition());
                        ghost.setScaredTimer(0);
                        ghostDeath = true;
                        break;
                    } else { //pacman dies
                        this.pacman.setCurrentPosition(this.pacman.getInitialPosition());
                        pacmanDeath = true;
                        this.score -= 10;
                        break;
                    }
                }
            }

            if (!pacmanDeath && !ghostDeath) {
                this.pacman.setCurrentPosition(nextPosition);
            }


        }

        this.timeLeft -= 1;
        this.score -= 1;
    }

    private void executeGhostTurn(int agentTurn) {
        Ghost ghost = this.ghosts.get(agentTurn % 3 - 1);
        String ghostAction = ghost.chooseAction(this);
        Point ghostCurrentPosition = ghost.getCurrentPosition();
        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();

        Point nextPosition = new Point(ghostCurrentPosition);
        dir.next(nextPosition, ghostAction);

        if (!this.walls.contains(nextPosition)) {
            Point point = new Point(ghostCurrentPosition.x, ghostCurrentPosition.y);
            boolean ghostDeath = false;
            boolean pacmanDeath = false;

            if (pacmanCurrentPosition.equals(nextPosition)) {
                if (ghost.getScaredTimer() == 0) { // pacman dies

                    /* Move the ghost to its new location */
                    ghost.setCurrentPosition(nextPosition);

                    /* Reset Pacman to the initial location */
                    this.pacman.setCurrentPosition(this.pacman.getInitialPosition());
                    this.score -= 10;
                    pacmanDeath = true;

                } else {

                    /* Ghost is scared and runs into pacman */
                    ghost.setCurrentPosition(ghost.getInitialPosition());
                    ghost.setScaredTimer(0);
                    ghostDeath = true;
                }
            }


            if (!pacmanDeath && !ghostDeath) {
                ghost.setCurrentPosition(nextPosition);
            }

        }

        if (ghosts.get(0).getScaredTimer() == 0 && ghosts.get(1).getScaredTimer() == 0) {
            this.capsuleActive = false;
        }

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

    public Pacman getPacman() { return this.pacman; }

    public ArrayList<Ghost> getGhosts() { return this.ghosts; }

    public HashSet<Point> getFood() { return this.food; }

    public HashSet<Point> getCapsules() { return this.capsules; }

    public Point getPacmanPosition() {
        return this.pacman.getCurrentPosition();
    }

    public int getScore() { return this.score; }

    public String getState() { return this.state; }

    public void setState(String state) { this.state = state; }

    public ArrayList<String> getLegalActions(Point positiion) {
        String[] possibleActions = new String[]{"Up", "Down", "Left", "Right"};
        ArrayList<String> legalActions = new ArrayList<String>();
        for (String action: possibleActions) {
            Point nextPosition = new Point(positiion);
            dir.next(nextPosition, action);

            if (!this.walls.contains(nextPosition)) {
                legalActions.add(action);
            }
        }
        return legalActions;
    }

    public Point getNextPositionGivenAction(Point position, String action) {
        Point nextPosition = new Point(position);
        dir.next(nextPosition, action);
        return nextPosition;
    }
}
