package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * @author ericdoppelt
 * Purpose is to create a bouncer (which is a ball) that moves around the screen and interacts with Bricks
 * Occassionally interacts seemingly randomly with corners
 * Interacts with variables used in the Main method, specifically in regards to the following classes: Brick, Paddle
 * Implements the Circle class
 * How to Use it: Initialize a Bouncer and add it to the Main program's root
 */
public class Bouncer {
    private Circle myCircle;
    private int myDirectionX;
    private int myDirectionY;
    private int mySpeedX;
    private int mySpeedY;
    private boolean myReset;


    /**
     * Constructor for the Bouncer class, Ball initializes without any speed
     * Method assumes you are placing the ball on the screen (it does not check any bounds)
     * @param centerX the X-value for the center of the bouncer
     * @param centerY the Y-value for the center of the bouncer
     * @param radius the radius of the bouncer
     * @param fill the color of the bouncer
     */
    public Bouncer(int centerX, int centerY, int radius, Paint fill) {
        myCircle = new Circle(centerX, centerY, radius);
        myCircle.setFill(fill);
        myDirectionX = 1;
        myDirectionY = 1;
        mySpeedX = 0;
        mySpeedY = 0;
        myReset = false;
    }
    /**
     * Constructor for the Bouncer class that initializes a moving ball
     * Assumes you are creating it on the screen (it does not check if its off screen)
     * @param centerX the X-value for the center of the bouncer
     * @param centerY the Y-value for the center of the bouncer
     * @param radius the radius of the bouncer
     * @param fill the color of the bouncer
     * @param speedX the horizontal speed of the ball
     * @param speedY the vertical speed of the ball
     */
    public Bouncer(int centerX, int centerY, int radius, Paint fill, int speedX, int speedY) {
        this(centerX, centerY, radius, fill);
        mySpeedX = speedX;
        mySpeedY = speedY;
    }

    /**
     * Updates the instance variable mySpeedX
     * @param speedX the new horizontal speed of the ball
     */
    public void setDirectionX(int speedX) {
        mySpeedX = speedX;
    }

    /**
     * Updates the instance variable mySpeedY
     * @param speedY the new vertical speed of the ball
     */
    public void setDirectionY(int speedY) {
        mySpeedY = speedY;
    }

    /**
     * Tells whether or not a bouncer is reset to the starting position (above the reset paddle)
     * @return boolean indicating whether a bouncer is reset
     */
    public boolean getReset() {
        return myReset;
    }

    /**
     * Allows a user to update the instance variable myReset indicating if the ball is in the original location
     * @param reset boolean indicating whether a bouncer is reset
     */
    public void setReset(boolean reset) {
        myReset = reset;
    }

    /**
     * Gets the Circle object representing the bouncer in the scene
     * @return Circle object that is the visual bouncer
     */
    public Circle getCircle() {
        return myCircle;
    }

    /**
     * gets the X location for the center of the circle
     * @return horizontal location of circle
     */
    public double getX() {
        return myCircle.getCenterX();
    }

    /**
     * gets the Y location for the center of the circle
     * @return vertical location of circle
     */
    public double getY() {
        return myCircle.getCenterY();
    }

    /**
     * Increases the horizontal speed of the bouncer by a given amount
     * @param increase double to increase horizontal speed by
     */
    public void addXSpeed(double increase) {
        mySpeedX += increase;
    }

    /**
     * Increases the vertical speed of the bouncer by a given amount
     * @param increase double to increase vertical speed by
     */
    public void addYSpeed(double increase) {
        mySpeedY += increase;
    }

    /**
     * gets the radius of the circle
     * @return size of the radius of the circle
     */
    public double getRadius() {
        return myCircle.getRadius();
    }

    /**
     * gets the change in X of the bouncer for a frame
     * @return the horizontal change of a bouncer beteween frames
     */
    public int getXChange() {
        return myDirectionX * mySpeedX;
    }

    /**
     * gets the change in Y of the bouncer for a frame
     * @return the vertical change of a bouncer beteween frames
     */
    public int getYChange() {
        return myDirectionY * mySpeedY;
    }

    /**
     * sets the X value of the bouncer
     * @param x the new horizonntal position
     */
    public void setX(double x) {
        myCircle.setCenterX(x);
    }

    /**
     * sets the Y value of the bouncer
     * @param y the new vertical position
     */
    public void setY(double y) {
        myCircle.setCenterY(y);
    }

    /**
     * Determines whether a bouncer has collided with a wall
     * @param x the size of the frame, used to check if the ball hits the right wall
     * @return boolean telling if a bouncer collides with a wall
     */
    public boolean HitWall(double x) {
        if (myCircle.getCenterX() + myCircle.getRadius() >= x || myCircle.getCenterX() - myCircle.getRadius() <= 0)
            return true;
        return false;
    }

    /**
     * Determines whether a bouncer has hit the ceiling
     * @return boolean telling if a bouncer collies with the ceiling
     */
    public boolean HitCeiling() {
        if (myCircle.getCenterY() - myCircle.getRadius() <= 0) return true;
        return false;
    }

    /**
     * Determines whether a bouncer has hit the paddle
     * @param paddle Paddle object to check collision with
     * @return boolean indicating collision
     */
    public boolean HitPaddle(Paddle paddle) {
        if (myCircle.intersects(paddle.getRectangle().getBoundsInLocal())) return true;
        return false;
    }

    /**
     * Determines whether the bouncer has fallen off the bottom of the screen
     * @param height the height of the frame, used to check if the ball fell off the screen
     * @return boolean indicating whether or not the ball fell off
     */
    public boolean hitBottom(int height) {
        if (myCircle.getCenterY() > height + myCircle.getRadius()) return true;
        return false;
    }

    /**
     * Handles a collision between a paddle and bouncer by reversing bouncer's Y direction and resetting it on top of paddle
     * Resetting avoids bugs that occur with the ball getting "stuck" in the paddle and intersecting every frame
     * @param paddle the paddle to place the ball on top of
     */
    public void handlePaddleHit(Paddle paddle, double sizeY, double radius, double height) {
        myDirectionY *= -1;
        myCircle.setCenterY(sizeY - radius - height);
    }

    /**
     * Reverses the horizontal direction of the bouncer
     */
    public void reverseXDirection() {
        myDirectionX *= -1;
    }

    /**
     * Reverses the vertical direction of the bouncer
     */
    public void reverseYDirection() {
        myDirectionY *= -1;
    }

    /**
     * Resets the ball to its original location (on top of a centered paddle), used when the ball falls off screen
     * @param width the width of the frame
     * @param height the height of the frame
     * @param rectHeight the height of the Paddle in the frame
     */
    public void placeCenter(int width, int height, int rectHeight) {
        myCircle.setCenterX(width / 2);
        myCircle.setCenterY(height - rectHeight - myCircle.getRadius());
        myDirectionX = 1;
        myDirectionY = 1;
        mySpeedX = 0;
        mySpeedY = 0;
    }

    /**
     * Determines whether a bouncer has collided with a given brick
     * @param brick Brick to check collision with
     * @return boolean indicating whether the bouncer hit the brick or not
     */
    public boolean hitsBrick(Brick brick) {
        return hitsBrickRight(brick) || hitsBrickBottom(brick) || hitsBrickLeft(brick) || hitsBrickTop(brick);
    }

    /**
     * Method called after a bouncer hits a brick, determines the side of the collision
     * Uses nested methods to direct the bouncer: handleBrickRight(), handleBrickLeft, handleBrickTop, handleBrickBottom
     * @param brick the brick that the bouncer collides with
     */
    public void handleBrick(Brick brick) {
        if (hitsBrickRight(brick)) handleBrickRight(brick);
        if (hitsBrickLeft(brick)) handleBrickLeft(brick);
        if (hitsBrickTop(brick)) handleBrickTop(brick);
        if (hitsBrickBottom(brick)) handleBrickBottom(brick);
    }

    /**
     * checks whether the bouncer collided with the given brick from the right
     * @param brick Brick to check collision for
     * @return boolean indicating if the bouncer his the brick from the right
     */
    public boolean hitsBrickRight(Brick brick) {
        return brick.contains(getX() - getRadius(), getY());
    }

    /**
     * checks whether the bouncer collided with the given brick from the left
     * @param brick Brick to check collision for
     * @return boolean indicating if the bouncer his the brick from the left
     */
    public boolean hitsBrickLeft(Brick brick) {
        return brick.contains(getX() + getRadius(), getY());
    }

    /**
     * checks whether the bouncer collided with the given brick from the top
     * @param brick Brick to check collision for
     * @return boolean indicating if the bouncer his the brick from the top
     */
    public boolean hitsBrickTop(Brick brick) {
        return brick.contains(getX(), getY() + getRadius());
    }

    /**
     * checks whether the bouncer collided with the given brick from the bottom
     * @param brick Brick to check collision for
     * @return boolean indicating if the bouncer his the brick from the bottom
     */
    public boolean hitsBrickBottom(Brick brick) {
        return brick.contains(getX(), getY() - getRadius());
    }

    /**
     * Called when a bouncer hits a brick from the right
     * Reverses the horizontal direction of the bouncer and sets its location directly to the right of the brick to avoid bugs
     * @param brick Brick object that the bouncer collides with
     */
    public void handleBrickRight(Brick brick) {
        myDirectionX *= -1;
        setX(brick.getX() + brick.getWidth() + getRadius());
    }

    /**
     * Called when a bouncer hits a brick from the left
     * Reverses the horizontal direction of the bouncer and sets its location directly to the left of the brick to avoid bugs
     * @param brick Brick object that the bouncer collides with
     */
    public void handleBrickLeft(Brick brick) {
        myDirectionX *= -1;
        setX(brick.getX() - getRadius());
    }

    /**
     * Called when a bouncer hits a brick from the top
     * Reverses the vertical direction of the bouncer and sets its location directly on top of the brick to avoid bugs
     * @param brick Brick object that the bouncer collides with
     */
    public void handleBrickTop(Brick brick) {
        myDirectionY *= -1;
        setY(brick.getY() - getRadius());
    }

    /**
     * Called when a bouncer hits a brick from the bottom
     * Reverses the vertical direction of the bouncer and sets its location directly to the bottom of the brick to avoid bugs
     * @param brick Brick object that the bouncer collides with
     */
    public void handleBrickBottom(Brick brick) {
        myDirectionY *= -1;
        setY(brick.getY() + brick.getHeight() + getRadius());
    }
}

