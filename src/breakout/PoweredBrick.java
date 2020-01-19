package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class PoweredBrick extends Brick {

    private PowerUp myPowerUp = new PowerUp(null);

    public PoweredBrick(int X, int Y, int rectWidth, int rectHeight, int hits) {
        super(X, Y, rectWidth, rectHeight, hits);
    }

    @Override
    public void handleHit() {
        super.handleHit();
        if (myHitsLeft == 0)  myPowerUp.dropPowerUp(Main.myRoot, this);
    }

    public PowerUp getPowerUp() {
        return myPowerUp;
    }
}
