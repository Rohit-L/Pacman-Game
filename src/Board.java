import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Board {

    private int xScale;
    private int yScale;
    private String[][] wallArray;
    private String[] wallArr = new String[]{"1", "2", "3", "4", "5", "6", "*", "U", "X", "L"};

    public Board() {
        this.wallArray = new String[35][15];
        this.xScale = 35;
        this.yScale = 15;

        StdDraw.setCanvasSize(1300, 500);
    }

    public HashMap<String, ArrayList<Point>> readLayout(String layout) {
        String line = null;
        HashMap<String, ArrayList<Point>> allElems = new HashMap<>();
        try {
            ArrayList<Point> pacman = new ArrayList<>();
            ArrayList<Point> ghosts = new ArrayList<>();
            ArrayList<Point> food = new ArrayList<>();
            ArrayList<Point> capsules = new ArrayList<>();
            ArrayList<Point> walls = new ArrayList<>();
            FileReader fileReader = new FileReader("layouts/" + layout);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int index = 0;
            while((line = bufferedReader.readLine()) != null) {
                String[] lineArr = line.split("(?!^)");
                for (int i = 0; i < this.xScale; i++) {
                    String boardItem = lineArr[i];
                    if (Arrays.asList(wallArr).contains(boardItem)) {
                        this.wallArray[i][this.yScale - index - 1] = boardItem;
                        walls.add(new Point(i, this.yScale - index - 1));
                    }
                    if (boardItem.equals("G")) { // Ghost
                        ghosts.add(new Point(i, this.yScale - index - 1));
                    }
                    if (boardItem.equals("P")) { // Pacman
                        pacman.add(new Point(i, this.yScale - index - 1));
                    }
                    if (boardItem.equals(".")) { // Food Pellet
                        food.add(new Point(i, this.yScale - index - 1));
                    }
                    if (boardItem.equals("C")) { // Capsule
                        capsules.add(new Point(i, this.yScale - index - 1));
                    }
                }

                index += 1;
            }
            allElems.put("Walls", walls);
            allElems.put("Pacman", pacman);
            allElems.put("Ghosts", ghosts);
            allElems.put("Food", food);
            allElems.put("Capsules", capsules);

            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + layout + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + layout + "'");
        }
        return allElems;
    }

    public String[][] getWallArray() {
        return this.wallArray;
    }

    public void drawBoard(GameState gameState) {

        /* Set Board Size */
        StdDraw.setXscale(0, xScale + 5);
        StdDraw.setYscale(0, yScale);

        /* Display blank board */
        for (int x = 0; x < this.xScale; x++) {
            for (int y = 0; y < this.yScale; y++) {

                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(x + .5, y + .5, .5);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.01);
                String boardItem = this.wallArray[x][y];
                if (boardItem != null) {
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
                }
            }
        }

        // Food
        for (Point f: gameState.getFood()) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(f.x + 0.5, f.y + 0.5, .12);
        }

        // Capsules
        for (Point c: gameState.getCapsules()) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(c.x + 0.5, c.y + 0.5, .32);
        }

        // Pacman
        Pacman p = gameState.getPacman();
//        StdDraw.setPenColor(StdDraw.YELLOW);
//        StdDraw.filledCircle(p.getCurrentPosition().x + 0.5, p.getCurrentPosition().y + 0.5, 0.49);
        StdDraw.picture(p.getCurrentPosition().x + 0.5, p.getCurrentPosition().y + 0.5, "img/pacman.jpg", 1.2, 1.2);

        // Ghosts
        for (Ghost g: gameState.getGhosts()) {
            if (g.getScaredTimer() == 0) {
                if (g.getId() == 1) {
                    StdDraw.picture(g.getCurrentPosition().x + 0.5, g.getCurrentPosition().y + 0.5, "img/pinkGhost.jpg", 1.2, 1.2);
                } else {
                    StdDraw.picture(g.getCurrentPosition().x + 0.5, g.getCurrentPosition().y + 0.5, "img/redGhost.jpg", 1.2, 1.2);
                }
            } else {
                StdDraw.picture(g.getCurrentPosition().x + 0.5, g.getCurrentPosition().y + 0.5, "img/scared.jpg", 1.2, 1.2);
            }
        }

        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        for (int x = xScale; x < xScale + 5; x++) {
            for (int y = 0; y < this.yScale; y++) {
                StdDraw.filledSquare(x + .5, y + .5, .5);

            }
        }
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.1);
        StdDraw.text(xScale + 2.5, (yScale * 4)/9, "Pellets Left: " + gameState.getNumFood());
        StdDraw.text(xScale + 2.5, (yScale * 5)/9, "Score: " + gameState.getScore());
        StdDraw.text(xScale + 2.5, (yScale * 6)/9, "Time Left: " + gameState.getTimeLeft());

        if (gameState.getState() != null) {
            if (gameState.getState().equals("Win")) {
                StdDraw.text(xScale + 2.5, (yScale * 8)/9, "You Win!");
            } else if (gameState.getState().equals("Lose")) {
                StdDraw.text(xScale + 2.5, (yScale * 8)/9, "You Lose!");
            }
            StdDraw.text(xScale + 2.5, (yScale * 2)/9, "Press Y to Restart.");
        }

    }
}
