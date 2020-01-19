package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class PoweredBrick extends Brick {

    PowerUp myPowerUp = new PowerUp();

    public PoweredBrick(int X, int Y, int rectWidth, int rectHeight, int hits) {
        super(X, Y, rectWidth, rectHeight, hits);
    }

    @Override
    public void handleHit() {
        super.handleHit();
        myPowerUp.dropPowerUp(Main.myRoot, this);
    }
}
