package breakout;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.util.ArrayList;
    import java.util.Scanner;

public class LevelReader {

    private File myFile;
    private Scanner myScanner;
    private int mySizeX;
    private int mySizeY;

    public LevelReader(int level, int sizeX, int sizeY) {

        try {
            String fileName = "./resources/level" + level + ".txt";
            myFile = new File(fileName);
            myScanner = new Scanner(myFile);
            mySizeX = sizeX;
            mySizeY = sizeY;

        } catch(FileNotFoundException e) {
        }
    }

    public ArrayList<Brick> readLevel() {
        ArrayList<Brick> levelBricks = new ArrayList<Brick>();
        int row = 0;
        while (myScanner.hasNextLine()) {
            String line = myScanner.nextLine();
            String[] fileBricks = line.split(" ");
            for (int i = 0; i < fileBricks.length; i++) {
                String block = fileBricks[i];
                char power = readBlockPower(block);
                int hits = readBlockNumber(block);

                if (hits > 0) {
                    Brick tempBrick = createBrick(hits, power, row, i);
                    levelBricks.add(tempBrick);
                }
            }
            row++;
        }
        return levelBricks;
    }

    private Brick createBrick(int hits, char power, int line, int column) {
        double x = calculateBrickX(column);
        double y = calculateBrickY(line);
        double brickLength = getUnitLengthX();
        double brickWidth = getUnitLengthY();

        if (power == 'B') return new NormalBrick(x, y, brickLength, brickWidth, hits);
        else if (power == 'P') return new PermanentBrick(x, y, brickLength, brickWidth);
        else return null;
    }

    private double calculateBrickX(int column) {
        return (column + 1) * getUnitLengthX();
    }

    private double calculateBrickY(int row) {
        return (row + 1) * getUnitLengthY();
    }

    private int readBlockNumber(String s) {
        return Integer.parseInt(s.substring(1));
    }

    private char readBlockPower(String s) {
        return s.charAt(0);
    }

    private double getUnitLengthX() {
        return mySizeX / 10;
    }

    private double getUnitLengthY() {
        return mySizeY / 10;
    }

}
