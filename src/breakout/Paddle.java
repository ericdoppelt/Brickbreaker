package breakout;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;

public class Paddle {
    private Rectangle myPaddle;

    public Paddle(int centerX, int centerY, int width, int height, Paint fill) {
        myPaddle = new Rectangle(centerX, centerY, width, height);
        myPaddle.setFill(fill);
    }

    public Rectangle getRectangle() {
        return myPaddle;
    }


}

