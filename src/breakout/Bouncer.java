package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Bouncer {
    private Circle myBouncer;

    public Bouncer(int centerX, int centerY, int radius, Paint fill) {
        myBouncer = new Circle(centerX, centerY, radius);
        myBouncer.setFill(fill);
    }

    public Circle getBouncer() {
        return myBouncer;
    }
}
