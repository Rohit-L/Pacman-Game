import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Board {

    private int xScale;
    private int yScale;
    private String[][] boardArray;

    public Board(String[][] boardArray) {
        this.boardArray = boardArray;
        this.xScale = 35;
        this.yScale = 15;

        StdDraw.setCanvasSize(1300, 500);
    }

    public void readLayout(String layout) {
        String line = null;
        try {
            FileReader fileReader = new FileReader("layouts/" + layout);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int index = 0;
            while((line = bufferedReader.readLine()) != null) {
                String[] lineArr = line.split("(?!^)");
                for (int i = 0; i < this.xScale; i++) {
                    this.boardArray[i][this.yScale - index - 1] = lineArr[i];
                }

                index += 1;
            }

            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + layout + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + layout + "'");
        }
    }

    public void drawBoard() {

        /* Set Board Size */
        StdDraw.setXscale(0, xScale);
        StdDraw.setYscale(0, yScale);

        /* Display blank board */
        for (int x = 0; x < this.xScale; x++) {
            for (int y = 0; y < this.yScale; y++) {

                if ((x + y) % 2 == 0) StdDraw.setPenColor(StdDraw.BLACK);
                else                  StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(x + .5, y + .5, .5);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.01);
                String boardItem = this.boardArray[x][y];
                if (boardItem.equals("X")) { // Horizontal Wall
                    StdDraw.line(x, y + 0.5, x + 1, y + 0.5);
                }
                if (boardItem.equals("L")) { // Vertical Wall
                    StdDraw.line(x + 0.5, y, x + 0.5, y + 1);
                }
                if (boardItem.equals("1")) { // TopLeft Curve Wall
                    StdDraw.arc(x + 1, y, 0.5, 90, 180);
                }
                if (boardItem.equals("2")) { // TopRight Curve Wall
                    StdDraw.arc(x, y, 0.5, 0, 90);
                }
                if (boardItem.equals("3")) { // BottomLeft Curve Wall
                    StdDraw.arc(x + 1, y + 1, 0.5, 180, 270);
                }
                if (boardItem.equals("4")) { // BottomRight Curve Wall
                    StdDraw.arc(x, y + 1, 0.5, 270, 360);
                }
                if (boardItem.equals("U")) { // Inverted T Wall
                    StdDraw.line(x, y + 0.5, x + 1, y + 0.5);
                    StdDraw.line(x + 0.5, y + 0.5, x + 0.5, y + 1);
                }
                if (boardItem.equals(("*"))) { // Circle Wall
                    StdDraw.circle(x + 0.5, y + 0.5, .3);
                }
                if (boardItem.equals(("5"))) { // TopLeft Curve - WHITE Wall
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.arc(x + 1, y, 0.5, 90, 180);
                }
                if (boardItem.equals(("6"))) { // TopRight Curve - WHITE Wall
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.arc(x, y, 0.5, 0, 90);
                }
                if (boardItem.equals("G")) { // Ghost
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.picture(x + 0.5, y + 0.5, "img/cop.jpg", 1.2, 1.2);
                }
                if (boardItem.equals("P")) { // Pacman
                    StdDraw.setPenColor(StdDraw.YELLOW);
                    StdDraw.filledCircle(x + 0.5, y + 0.5, 0.49);
                }
                if (boardItem.equals(".")) { // Food Pellet
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledCircle(x + 0.5, y + 0.5, .12);
                }
                if (boardItem.equals("C")) { // Capsule
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledCircle(x + 0.5, y + 0.5, .32);
                }
            }
        }

    }
}
