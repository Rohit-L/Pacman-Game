import java.util.Arrays;

public class GameState {

    private String[][] boardArray;
    private Board board;
    private String[] walls = new String[]{"1", "2", "3", "4", "5", "6", "*", "U", "X", "L"};


    public GameState() {
        String[][] boardArray = new String[35][15];

        this.boardArray = boardArray;
        this.board = new Board(this.boardArray);
        board.readLayout("standardLayout.txt");
        board.drawBoard();

    }

    public void dispay() {
        board.drawBoard();
        StdDraw.show(100);
    }

    public void keyPressed(Agent agent, String direction) {
        if (direction.equals("Right")) {
            int[] pacmanCurrentPosition = this.currentPacmanPosition();
            int[] nextPosition = new int[] {pacmanCurrentPosition[0] + 1, pacmanCurrentPosition[1]};
            if (!Arrays.asList(walls).contains(this.boardArray[nextPosition[0]][nextPosition[1]])) {
                agent.currentDirection = direction;
            }
        }
        System.out.println(direction + " Key Pressed");
    }

    public int[] currentPacmanPosition() {
        for (int x = 0; x < 35; x++) {
            for (int y = 0; y < 15; y++) {
                if (this.boardArray[x][y].equals("P")) {
                    return new int[] {x, y};
                }
            }
        }
        return null;
    }

    public void takeAction(Agent agent) {

        String agentAction = agent.chooseAction();

        if (agentAction.equals("Up")) {
            int[] pacmanCurrentPosition = this.currentPacmanPosition();
            int[] nextPosition = new int[] {pacmanCurrentPosition[0], pacmanCurrentPosition[1] + 1};
            if (!Arrays.asList(walls).contains(this.boardArray[nextPosition[0]][nextPosition[1]])) {
                boardArray[pacmanCurrentPosition[0]][pacmanCurrentPosition[1]] = "";
                boardArray[nextPosition[0]][nextPosition[1]] = "P";
            }
        }
        if (agentAction.equals("Right")) {
            int[] pacmanCurrentPosition = this.currentPacmanPosition();
            int[] nextPosition = new int[] {pacmanCurrentPosition[0] + 1, pacmanCurrentPosition[1]};
            if (!Arrays.asList(walls).contains(this.boardArray[nextPosition[0]][nextPosition[1]])) {
                boardArray[pacmanCurrentPosition[0]][pacmanCurrentPosition[1]] = "";
                boardArray[nextPosition[0]][nextPosition[1]] = "P";
            }
        }
    }

    public String[][] deepCopyBoard() {
        final String[][] result = new String[this.boardArray.length][];
        for (int i = 0; i < this.boardArray.length; i++) {
            result[i] = Arrays.copyOf(this.boardArray[i], this.boardArray[i].length);
        }
        return result;
    }
}
