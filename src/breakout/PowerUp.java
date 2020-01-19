package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class PowerUp {
    private Rectangle myRectangle;
    private PowerType myType;

    private static final int myDropSpeed = 50;
    private static final int SIZE = 10;

    public PowerUp(PowerType type) {
        myType = type;
    }

    public void dropPowerUp(Group root, Brick brick) {
        myRectangle = new Rectangle(brick.getCenterX() - SIZE/2, brick.getBottomY(), SIZE, SIZE);
        root.getChildren().add(myRectangle);
        Main.allPowerUps.add(this);
    }

    public int getDropSpeed() {
        return myDropSpeed;
    }

    public Rectangle getRectangle() {
        return myRectangle;
    }

    public void handleCatch(Group root) {
        root.getChildren().remove(myRectangle);
        myType.addPowerUp();
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

