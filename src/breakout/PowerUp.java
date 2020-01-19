package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class PowerUp {
    private Rectangle myRectangle;
    private PowerType myType;
    private int myDropSpeed;

    private static final int SIZE = 10;

    public PowerUp() {
    }

    public void dropPowerUp(Group root, Brick brick) {
        myRectangle = new Rectangle(brick.getCenterX() - SIZE/2, brick.getBottomY(), SIZE, SIZE);
        root.getChildren().add(myRectangle);
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
}

