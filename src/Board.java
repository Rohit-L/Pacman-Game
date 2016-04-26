import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Board {

    private int xScale;
    private int yScale;
    private String[][] boardArray;
    private static HashMap<String, String> layoutToMap = new HashMap<String, String>();

    public Board(String[][] boardArray) {
        this.boardArray = boardArray;
        this.xScale = 35;
        this.yScale = 15;
        layoutToMap.put("X", "horizontal");
        layoutToMap.put("L", "vertical");
        layoutToMap.put("1", "90to180");
        layoutToMap.put("2", "0to90");
        layoutToMap.put("3", "180to270");
        layoutToMap.put("4", "270to0");
    }

    public void readLayout(String layout) {
        System.out.println(layoutToMap.toString());
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
                if (boardItem.equals("X")) {
                    StdDraw.line(x, y + 0.5, x + 1, y + 0.5);
                }
                if (boardItem.equals("L")) {
                    StdDraw.line(x + 0.5, y, x + 0.5, y + 1);
                }
                if (boardItem.equals("1")) {
                    StdDraw.arc(x + 1, y, 0.5, 90, 180);
                }
                if (boardItem.equals("2")) {
                    StdDraw.arc(x, y, 0.5, 0, 90);
                }
                if (boardItem.equals("3")) {
                    StdDraw.arc(x + 1, y + 1, 0.5, 180, 270);
                }
                if (boardItem.equals("4")) {
                    StdDraw.arc(x, y + 1, 0.5, 270, 360);
                }
                if (boardItem.equals("^")) {
                    StdDraw.line(x, y + 0.5, x + 1, y + 0.5);
                    StdDraw.line(x + 0.5, y + 0.5, x + 0.5, y + 1);
                }
            }
        }

    }
}
