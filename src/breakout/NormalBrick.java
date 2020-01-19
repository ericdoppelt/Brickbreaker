package breakout;

import javafx.scene.paint.Color;

public class NormalBrick extends Brick {

    protected int myHitsLeft;

    public NormalBrick(double X, double Y, double rectWidth, double rectHeight, int hits) {
        super(X, Y, rectWidth, rectHeight);
        myHitsLeft = hits;
        colorBrick();
    }

    public boolean hasHitsLeft() {
        return myHitsLeft  > 0;
    }

    public void colorBrick() {
        if (myHitsLeft == 1) myRectangle.setFill(Color.RED);
        else if (myHitsLeft == 2) myRectangle.setFill(Color.ORANGE);
        else if (myHitsLeft == 3) myRectangle.setFill(Color.YELLOW);
        else if (myHitsLeft == 4) myRectangle.setFill(Color.GREEN);
        else if (myHitsLeft == 5) myRectangle.setFill(Color.BLUE);
        else if (myHitsLeft == 6) myRectangle.setFill(Color.INDIGO);
        else if (myHitsLeft == 7) myRectangle.setFill(Color.VIOLET);
    }

    public void handleHit() {
        myHitsLeft--;
        colorBrick();
    }

}
