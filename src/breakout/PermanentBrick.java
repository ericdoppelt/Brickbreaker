package breakout;

import javafx.scene.paint.Color;

public class PermanentBrick extends Brick {

    private static final Color permanantColor = Color.BLACK;

    public PermanentBrick(double X, double Y, double rectWidth, double rectHeight) {
        super(X, Y, rectWidth, rectHeight);
        myRectangle.setFill(permanantColor);
    }

    public void handleHit() {};

    public boolean hasHitsLeft() {
        return true;
    }

}
