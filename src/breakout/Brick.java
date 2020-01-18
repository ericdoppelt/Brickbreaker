package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;

public class Brick {

    private Rectangle myRectangle;
    private int myHitsLeft;

    public Brick(int X, int Y, int rectWidth, int rectHeight, int hits) {
        myRectangle = new Rectangle(X, Y, rectWidth, rectHeight);
        myHitsLeft = hits;
        colorBrick();
    }

    public Rectangle getRectangle() {
        return myRectangle;
    }

    // consider a switch and an exception?
    public void colorBrick() {
        if (myHitsLeft == 0) Main.deleteBrick(this);
        else if (myHitsLeft == 1) myRectangle.setFill(Color.RED);
        else if (myHitsLeft == 2) myRectangle.setFill(Color.ORANGE);
        else if (myHitsLeft == 3) myRectangle.setFill(Color.YELLOW);
        else if (myHitsLeft == 4) myRectangle.setFill(Color.GREEN);
        else if (myHitsLeft == 5) myRectangle.setFill(Color.BLUE);
        else if (myHitsLeft == 6) myRectangle.setFill(Color.INDIGO);
        else if (myHitsLeft == 7) myRectangle.setFill(Color.VIOLET);
    }

    public double getX() {
        return myRectangle.getX();
    }

    public double getY() {
        return myRectangle.getY();
    }

    public double getWidth() {
        return myRectangle.getWidth();
    }

    public double getHeight() {
        return myRectangle.getHeight();
    }

    public boolean contains(double x, double y) {
        return getRectangle().contains(x, y);
    }

    public void handleHit() {
        myHitsLeft--;
        colorBrick();
    }

}
