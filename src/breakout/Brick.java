package breakout;

import javafx.scene.shape.Rectangle;

/**
 * @author ericdoppelt
 * Purpose is to create a brick (which is a rectangle) that interacts with the Bouncer
 * Called upon in the Main class often and uses the Rectangle class
 * An example of how to use it is to add a brick to the Main program and allow that to update its instance variables in the game
 */
public abstract class Brick {

    protected Rectangle myRectangle;

    /**
     * Constructor that creates a Brick to add to the game
     * @param X the horizontal location of the top left corner of the brick
     * @param Y the vertical location of the top left corner of the brick
     * @param rectWidth the width of the rectangle representing the brick
     * @param rectHeight the height of the rectangle representing the brick
     */
    public Brick(double X, double Y, double rectWidth, double rectHeight) {
        myRectangle = new Rectangle(X, Y, rectWidth, rectHeight);
    }

    /**
     * Gets the Rectangle object representing the Brick
     * @return Rectangle used to display brick in JavaFX
     */
    public Rectangle getRectangle() {
        return myRectangle;
    }

    /**
     * Gets the x location of the Brick
     * @return horizontal location of the Brick
     */
    public double getX() {
        return myRectangle.getX();
    }

    /**
     * Gets the y location of the Brick
     * @return vertical location of the Brick
     */
    public double getY() {
        return myRectangle.getY();
    }

    /**
     * Gets the width of the Brick
     * @return width of the Rectangle representing Brick
     */
    public double getWidth() {
        return myRectangle.getWidth();
    }

    /**
     * Gets the height of the Brick
     * @return height of the Rectangle representing Brick
     */
    public double getHeight() {
        return myRectangle.getHeight();
    }

    /**
     * determines whether a Rectangle contains the point whose location is given in x,y coordinates
     * @param x horizontal location of point
     * @param y vertical location of point
     * @return boolean if the point is in the Rectangle
     */
    public boolean contains(double x, double y) {
        return getRectangle().contains(x, y);
    }

    /**
     * Gets the horizontal center of the Brick
     * @return the X coordinate of the center of the Brick
     */
    public double getCenterX() {
        return myRectangle.getX() + (myRectangle.getWidth() / 2);
    }

    /**
     * Gets the vertical center of the Brick
     * @return the Y coordinate of the center of the Brick
     */
    public double getBottomY() {
        return myRectangle.getY() + myRectangle.getHeight();
    }

    /**
     * Abstract method that is implemented in all sublasses of the Brick
     * This is called when a bouncer collides with the Brick
     * If applicable, this lowers the number of hits left and can drop a power up if the brick breaks
     */
    public abstract void handleHit();

    /**
     * Abstract method that lets the user know if the Brick should be removed or not
     * Permanent Brick does nothing when this method is called because collisions do not affect them
     * @return boolean representing whether the Brick has hits left in it
     */
    public abstract boolean hasHitsLeft();
}
