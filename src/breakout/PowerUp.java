package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class PowerUp {
    private Rectangle myRectangle;
    private String myType;
    private boolean myDrop;

    private static final int myDropSpeed = 50;
    private static final int SIZE = 10;

    public PowerUp(String type) {
        myType = type;
        myDrop = false;
    }

    public void dropPowerUp(Brick brick) {
        myRectangle = new Rectangle(brick.getCenterX() - SIZE/2, brick.getBottomY(), SIZE, SIZE);
        myDrop = true;
    }

    public boolean wasDropped() {
        return myDrop;
    }

    public int getDropSpeed() {
        return myDropSpeed;
    }

    public Rectangle getRectangle() {
        return myRectangle;
    }

    public void handleCatch(Group root) {
        root.getChildren().remove(myRectangle);
    }

    public double getY() {
        return myRectangle.getY();
    }

    public void setX(double x) {
        myRectangle.setX(x);
    }

    public void setY(double y) {
        myRectangle.setY(y);
    }

    public boolean isCaught(Paddle paddle) {
        return (myRectangle.intersects(paddle.getRectangle().getBoundsInLocal()));
    }
}

