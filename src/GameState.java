import java.awt.*;
import java.util.*;

public class GameState {

    private static int START_TIME = 800;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private HashSet<Point> food;
    private HashSet<Point> capsules;
    private HashSet<Point> walls;
    private int numFood;
    Directions dir;
    private Board board;
    private int agentTurn;
    private int numCapsules;
    private int timeLeft;
    private boolean capsuleActive;




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


        this.agentTurn = 0;
        this.numFood = this.food.size();
        this.numCapsules = this.capsules.size();
        this.timeLeft = START_TIME;
        this.capsuleActive = false;


        this.dir = new Directions();
        this.display();
    }


    public void display() {
        board.drawBoard(this.pacman, this.ghosts, this.food, this.capsules);
        StdDraw.show(60);
    }

    public void keyPressed(String direction) {
        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();
        Point nextPosition = new Point(pacmanCurrentPosition);
        dir.next(nextPosition, direction);

        if (!this.walls.contains(nextPosition)) {
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


//    public String[][] deepCopyBoard() {
//        final String[][] result = new String[this.configuration.length][];
//        for (int i = 0; i < this.configuration.length; i++) {
//            result[i] = Arrays.copyOf(this.configuration[i], this.configuration[i].length);
//        }
//        return result;
//    }

//    public String toString() {
//        for (int i = this.configuration[0].length - 1; i >= 0; i--) {
//            for (int j = 0; j < this.configuration.length; j++) {
//                System.out.print(this.configuration[j][i] + " ");
//            }
//            System.out.print("\n");
//        }
//        System.out.println("Num Food Left " + numFood);
//        System.out.println("Num Capsules Left " + numCapsules);
//        System.out.println("Time Left " + timeLeft);
//        return "";
//    }


    private void executePacmanTurn() {
        String agentAction = this.pacman.chooseAction(this);
        Point pacmanCurrentPosition = this.pacman.getCurrentPosition();
        Point nextPosition = new Point(pacmanCurrentPosition);
        dir.next(nextPosition, agentAction);


        if (!this.walls.contains(nextPosition)) {
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
                    if (ghost.getScaredTimer() > 0) { //ghost is scared, dies
                        this.pacman.setCurrentPosition(nextPosition);
                        ghost.setCurrentPosition(ghost.getInitialPosition());
                        ghost.setScaredTimer(0);
                        ghostDeath = true;
                        break;
                    } else { //pacman dies

                        this.pacman.setCurrentPosition(this.pacman.getInitialPosition());
                        pacmanDeath = true;
                        break;
                    }
                }
            }

            if (!pacmanDeath && !ghostDeath) {
                this.pacman.setCurrentPosition(nextPosition);
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

    public Point getPacmanPosition() {
        return this.pacman.getCurrentPosition();
    }
}
