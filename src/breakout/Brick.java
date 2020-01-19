package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.Group;

public abstract class Brick {

    protected Rectangle myRectangle;

    public Brick(double X, double Y, double rectWidth, double rectHeight) {
        myRectangle = new Rectangle(X, Y, rectWidth, rectHeight);
    }

    public Rectangle getRectangle() {
        return myRectangle;
    }

    // consider a switch and an exception?

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

    public double getCenterX() {
        return myRectangle.getX() + (myRectangle.getWidth() / 2);
    }

    public double getBottomY() {
        return myRectangle.getY() + myRectangle.getHeight();
    }

    public void handleHit() {
    }

    public boolean hasHitsLeft() {
        return true;
    }
}
