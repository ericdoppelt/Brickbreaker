package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;

public class Paddle {
    private Rectangle myRectangle;
    private boolean wrapAround = false;

    public Paddle(int X, int Y, int width, int height, Paint fill) {
        myRectangle = new Rectangle(X, Y, width, height);
        myRectangle.setFill(fill);
    }

    public Rectangle getRectangle() {
        return myRectangle;
    }

    public void toggleWrapAround() {
        wrapAround = !wrapAround;
    }

    public boolean offRight(int width) {
        if (myRectangle.getX() > width - myRectangle.getWidth()) return true;
        return false;
    }

    public void placeRight(int width) {
        myRectangle.setX(width - myRectangle.getWidth());
    }

    public void placeLeft() {
        myRectangle.setX(0);
        System.out.println("Left");
    }

    public void placeCenter(int width) {
        myRectangle.setX((width - myRectangle.getWidth()) / 2);
    }

    public void handleRight(int width) {
        if (wrapAround) {
            if (myRectangle.getX() > width) placeLeft();
        } else {
            placeRight(width);
        }
    }

    public void handleLeft(int width) {
        if (wrapAround) {
            if (myRectangle.getX() < 0 - myRectangle.getWidth()) placeRight(width);
        } else {
            placeLeft();
        }
    }

    public boolean offLeft() {
        if (myRectangle.getX() < 0) return true;
        return false;
    }

    public boolean catchPowerUp(PowerUp powerUp) {
        if (myRectangle.intersects(powerUp.getRectangle().getBoundsInLocal())) return true;
        return false;
    }

}

