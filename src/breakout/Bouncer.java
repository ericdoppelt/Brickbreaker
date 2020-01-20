package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Bouncer {
    private Circle myCircle;
    private int myDirectionX;
    private int myDirectionY;
    private int mySpeedX;
    private int mySpeedY;
    private boolean myReset;


    public Bouncer(int centerX, int centerY, int radius, Paint fill) {
        myCircle = new Circle(centerX, centerY, radius);
        myCircle.setFill(fill);
        myDirectionX = 1;
        myDirectionY = 1;
        mySpeedX = 0;
        mySpeedY = 0;
        myReset = false;
    }

    public void setDirectionX(int speedX) {
        mySpeedX = speedX;
    }

    public void setDirectionY(int speedY) {
        mySpeedY = speedY;
    }

    public boolean getReset() {
        return myReset;
    }

    public void setReset(boolean reset) {
        myReset = reset;
    }

    public Bouncer(int centerX, int centerY, int radius, Paint fill, int speedX, int speedY) {
        this(centerX, centerY, radius, fill);
        mySpeedX = speedX;
        mySpeedY = speedY;
    }

    public Circle getCircle() {
        return myCircle;
    }

    public double getX() {
        return myCircle.getCenterX();
    }

    public double getY() {
        return myCircle.getCenterY();
    }


    public double getRadius() {
        return myCircle.getRadius();
    }

    public int getXChange() {
        return myDirectionX * mySpeedX;
    }

    public int getYChange() {
        return myDirectionY * mySpeedY;
    }

    public void setX(double x) {
        myCircle.setCenterX(x);
    }

    public void setY(double y) {
        myCircle.setCenterY(y);
    }

    public boolean HitWall() {
        if (myCircle.getCenterX() + myCircle.getRadius() >= Main.SIZE_X || myCircle.getCenterX() - myCircle.getRadius() <= 0)
            return true;
        return false;
    }

    public boolean HitCeiling() {
        if (myCircle.getCenterY() - myCircle.getRadius() <= 0) return true;
        return false;
    }

    public boolean HitPaddle(Paddle paddle) {
        if (myCircle.intersects(paddle.getRectangle().getBoundsInLocal())) return true;
        return false;
    }

    // boolean signifying the ball going off screen
    public boolean hitBottom(int height) {
        if (myCircle.getCenterY() > height + myCircle.getRadius()) return true;
        return false;
    }

    public void handleBottomHit(int width, int height, int rectHeight) {
        placeCenter(width, height, rectHeight);
    }

    public void handlePaddleHit(Paddle paddle) {
        myDirectionY *= -1;
        myCircle.setCenterY(Main.SIZE_Y - Main.BOUNCER_RADIUS - Main.PADDLE_HEIGHT);
    }

    public void reverseXDirection() {
        myDirectionX *= -1;
    }

    public void reverseYDirection() {
        myDirectionY *= -1;
    }

    public void placeCenter(int width, int height, int rectHeight) {
        myCircle.setCenterX(width / 2);
        myCircle.setCenterY(height - rectHeight - myCircle.getRadius());
        myDirectionX = 1;
        myDirectionY = -1;
        mySpeedX = 0;
        mySpeedY = 0;
    }


    public boolean hitsBrick(Brick brick) {
        return hitsBrickRight(brick) || hitsBrickBottom(brick) || hitsBrickLeft(brick) || hitsBrickTop(brick);
    }

    public void handleBrick(Brick brick) {
        if (hitsBrickRight(brick)) handleBrickRight(brick);
        if (hitsBrickLeft(brick)) handleBrickLeft(brick);
        if (hitsBrickTop(brick)) handleBrickTop(brick);
        if (hitsBrickBottom(brick)) handleBrickBottom(brick);
    }

    // this could be better
    public boolean hitsBrickRight(Brick brick) {
        return brick.contains(getX() - getRadius(), getY());
    }

    public boolean hitsBrickLeft(Brick brick) {
        return brick.contains(getX() + getRadius(), getY());
    }

    public boolean hitsBrickTop(Brick brick) {
        return brick.contains(getX(), getY() + getRadius());
    }

    public boolean hitsBrickBottom(Brick brick) {
        return brick.contains(getX(), getY() - getRadius());
    }

    public void handleBrickRight(Brick brick) {
        myDirectionX *= -1;
        setX(brick.getX() + brick.getWidth() + getRadius());
    }

    public void handleBrickLeft(Brick brick) {
        myDirectionX *= -1;
        setX(brick.getX() - getRadius());
    }

    public void handleBrickTop(Brick brick) {
        myDirectionY *= -1;
        setY(brick.getY() - getRadius());
    }

    public void handleBrickBottom(Brick brick) {
        myDirectionY *= -1;
        setY(brick.getY() + brick.getHeight() + getRadius());
    }
}

