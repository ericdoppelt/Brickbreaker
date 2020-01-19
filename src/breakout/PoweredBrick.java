package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class PoweredBrick extends NormalBrick {

    private PowerUp myPowerUp;

    public PoweredBrick(int X, int Y, int rectWidth, int rectHeight, int hits, PowerUp power) {
        super(X, Y, rectWidth, rectHeight, hits);
        myPowerUp = power;
    }

    @Override
    public void handleHit() {
        super.handleHit();
        if (myHitsLeft == 0)  myPowerUp.dropPowerUp(Main.myRoot, this);
    }

    public PowerUp getPowerUP() {
        return myPowerUp;
    }
}
