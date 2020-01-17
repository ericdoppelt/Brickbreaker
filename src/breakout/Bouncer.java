package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Bouncer {
    private Circle myCircle;
    private int myDirectionX;
    private int myDirectionY;
    private int mySpeedX;
    private int mySpeedY;


    public Bouncer(int centerX, int centerY, int radius, Paint fill,  int speedX, int speedY) {
        myCircle = new Circle(centerX, centerY, radius);
        myCircle.setFill(fill);
        myDirectionX = 1;
        myDirectionY = 1;
        mySpeedX = speedX;
        mySpeedY = speedY;
}

    public Circle getCircle() {
        return myCircle;
    }

    public int getXChange() {
        return myDirectionX * mySpeedX;
    }

    public int getYChange() {
        return myDirectionY * mySpeedY;
    }

    public void setX(double x) {
        myCircle.setCenterX(x);
    }

    public void setY(double y) {
        myCircle.setCenterY(y);
    }

    public boolean xOnScreen() {
        if (myCircle.getCenterX() + myCircle.getRadius() >= Main.SIZE_X || myCircle.getCenterX() - myCircle.getRadius() <= 0) return false;
        return true;
    }

    public boolean yOnScreen() {
        if (myCircle.getCenterY() + myCircle.getRadius() >= Main.SIZE_Y || myCircle.getCenterY() - myCircle.getRadius() <= 0) return false;
        return true;
    }

    public void reverseXDirection() {
        myDirectionX *= -1;
    }

    public void reverseYDirection() {
        myDirectionY *= -1;
    }
}
