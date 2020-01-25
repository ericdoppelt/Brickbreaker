package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * @author ericdoppelt
 * Main class runs the game by adding Bouncers, Bricks, and Paddle to the screen and updating their locations.
 * Depends and calls on every class in the breakout package.
 * Use it by running the main method below.
 * Note that this is INCOMPLETE. The programmer ran out of time before the deadline, so PowerUps, splashScreen, and Boss Fight are left incomplete.
 */

public class Main extends Application {

    private static final int SIZE_X = 1000;
    private static final int SIZE_Y = 800;
    private static final String TITLE = "Breakout!";
    private static final Paint BACKGROUND = Color.AZURE;

    private static final int LIVES = 3;
    private int myLives;

    private static final Paint PADDLE_COLOR = Color.BLACK;
    private static final int PADDLE_WIDTH = 200;
    private static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_SPEED = 50;
    private static final int PADDLE_GROWTH = 30;

    private static final int BOUNCER_RADIUS = 10;
    private static final int BOUNCER_SPEED_X = 250;
    private static final int BOUNCER_SPEED_Y = -500;
    private static final Paint BOUNCER_COLOR = Color.ROYALBLUE;
    private static final int BOUNCER_SPEED_INCREASE = 200;

    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Scene myScene;
    private Group myRoot;

    private ArrayList<Bouncer> allBouncers = new ArrayList<>();
    private ArrayList<Brick> allBricks = new ArrayList<>();
    private static ArrayList<PowerUp> allPowerUps = new ArrayList<>(); // not used much, ran out of time, also why is this italic?
    private Paddle myPaddle;

    private int myLevel;
    private final int myNumberLevels = 3;
    private final int myStartLevel = 1;


    /**
     * Launches the program by calling the start method using JavaFx.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the program by creating the initial Scene and beginning the program's Timeline.
     * Calls the setupGame() method to construct the initial Scene.
     * @param stage the Stage for the JavaFX program to run on.
     */
    public void start(Stage stage) {
        myScene = setupGame();
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private Scene setupGame() {
        // TA Question: thoughts on the white space in this method? does it look too empty?
        setLives();
        setAllBouncers();
        setPaddle();

        myRoot = new Group();

        loadLevel(myStartLevel);

        for (Bouncer tempBouncer : allBouncers) myRoot.getChildren().add(tempBouncer.getCircle());
        myRoot.getChildren().add(myPaddle.getRectangle());

        Scene scene = new Scene(myRoot, SIZE_X, SIZE_Y, BACKGROUND);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }


    private void step(double elapsedTime) {

        if (bricksCleared()) {
            ++myLevel;
            if (myLevel > myNumberLevels) win();
            loadLevel(++myLevel);
        }

        handleBouncerSurroundings();
        handleBouncerBricks(); // is there a better way to handle updating bricks and bouncers? maybe do each individually as opposed to together?
        handlePowerUps(elapsedTime);
        updateBouncers(elapsedTime);
    }

    private void setLives() {
        myLives = LIVES;
    }

    private void setAllBouncers() {
        // is this okay? or would it be better to store the bouncer in a temporary variable and then add it?
        allBouncers.add(new Bouncer(SIZE_X / 2, SIZE_Y - BOUNCER_RADIUS - PADDLE_HEIGHT, BOUNCER_RADIUS, BOUNCER_COLOR, BOUNCER_SPEED_X, BOUNCER_SPEED_Y));
        allBouncers.add(new Bouncer(SIZE_X / 2, SIZE_Y / 2, BOUNCER_RADIUS, BOUNCER_COLOR, BOUNCER_SPEED_X, BOUNCER_SPEED_Y));
    }

    private void setPaddle() {
        myPaddle = new Paddle((SIZE_X - PADDLE_WIDTH) / 2, SIZE_Y - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);
    }

    private void loadLevel(int i) {
        clearLevel();
        myLevel = i;
        if (myLevel <= myNumberLevels) {
            LevelReader nextLevel = new LevelReader(myLevel, SIZE_X, SIZE_Y);

            for (Brick addBrick : nextLevel.readLevel()) {
                allBricks.add(addBrick);
                myRoot.getChildren().add(addBrick.getRectangle());
            }
        }
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.getRectangle().setX(myPaddle.getRectangle().getX() + PADDLE_SPEED);
            if (myPaddle.offRight(SIZE_X)) myPaddle.handleRight(SIZE_X);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.getRectangle().setX(myPaddle.getRectangle().getX() - PADDLE_SPEED);
            if (myPaddle.offLeft()) myPaddle.handleLeft(SIZE_X);
        }
        else if (code == KeyCode.W) {
            myPaddle.toggleWrapAround();
        } else if (code == KeyCode.R) {
            myPaddle.placeCenter(SIZE_X);
        } else if (code == KeyCode.L) {
            myLives++;
        } else if (code == KeyCode.SPACE) {
            for (Bouncer tempBouncer : allBouncers) {
                if (tempBouncer.getReset()) {
                    tempBouncer.setDirectionX(BOUNCER_SPEED_X);
                    tempBouncer.setDirectionY(BOUNCER_SPEED_Y);
                }
            }
        } if (code == KeyCode.DIGIT1) {
            loadLevel(1);
        } else if (code == KeyCode.DIGIT2) {
            loadLevel(2);
        } else if (codeIsHighestLevel(code)) {
            loadLevel(myNumberLevels);
        }
    }

    private boolean bricksCleared() {
        for (Brick tempBrick : allBricks) {
            if (!(tempBrick instanceof PermanentBrick)) return false;
        }
        return true;
    }

    private void handleBouncerSurroundings() {
        for (Bouncer tempBouncer: allBouncers) {
            if (tempBouncer.HitWall(SIZE_X)) tempBouncer.reverseXDirection();
            else if (tempBouncer.HitCeiling()) tempBouncer.reverseYDirection();
            else if (tempBouncer.HitPaddle(myPaddle))
                tempBouncer.handlePaddleHit(myPaddle, SIZE_Y, BOUNCER_RADIUS, PADDLE_HEIGHT);
            else if (tempBouncer.hitBottom(SIZE_Y)) handleFallOff(tempBouncer);
        }
    }

    private void handleBouncerBricks() {
        for (Bouncer tempBouncer : allBouncers) {
            for (int i = 0; i < allBricks.size(); i++) {
                Brick testBrick = allBricks.get(i);

                if (tempBouncer.hitsBrick(testBrick)) {
                    tempBouncer.handleBrick(testBrick);
                    testBrick.handleHit();
                }

                // Question for TA: a PoweredBrick returns true here also right? ... it has to?
                // Other Question: how can I shift the code below into each Brick class?
                if (testBrick instanceof NormalBrick) {
                    if (!testBrick.hasHitsLeft()) {
                        myRoot.getChildren().remove(testBrick.getRectangle());
                        allBricks.remove(i);
                    }
                }

                // TA Comment: feel like there is a better way to do this but cannot think one...
                if (testBrick instanceof PoweredBrick) {
                    PoweredBrick testPoweredBrick = (PoweredBrick) testBrick;
                    if (testPoweredBrick.getPowerUp().wasDropped()) {
                        myRoot.getChildren().add(testPoweredBrick.getPowerUp().getRectangle());
                        allPowerUps.add(testPoweredBrick.getPowerUp());
                    }
                }
            }
        }
    }

    private void updateBouncers(double elapsedTime) {
        for (Bouncer tempBouncer: allBouncers) {
            tempBouncer.setX(tempBouncer.getCircle().getCenterX() + tempBouncer.getXChange() * elapsedTime);
            tempBouncer.setY(tempBouncer.getCircle().getCenterY() + tempBouncer.getYChange() * elapsedTime);
        }
    }


    private void handlePowerUps(double elapsedTime) {
        for (int i = 0; i < allPowerUps.size(); i++) {
            PowerUp tempPowerUp = allPowerUps.get(i);
            if (tempPowerUp.isCaught(myPaddle)) {
                myRoot.getChildren().remove(tempPowerUp.getRectangle());
                allPowerUps.remove(tempPowerUp);
                addPowerUp(tempPowerUp.getType());
                i--;
            } else {
                tempPowerUp.setY(tempPowerUp.getY() + tempPowerUp.getDropSpeed() * elapsedTime);
            }
        }
    }

    private void handleFallOff(Bouncer bouncer) {
        myLives--;
        if (myLives == 0) lose();
        else {
            bouncer.placeCenter(SIZE_X, SIZE_Y, PADDLE_HEIGHT);
            bouncer.setReset(true);
        }
    }

    private void win() {}

    private void lose() {}

    private boolean codeIsHighestLevel(KeyCode code) {
        return code.getCode() - 48 >= myNumberLevels && code.getCode() <= 57;
    }

    private void clearLevel() {
        for (Brick removeBrick : allBricks)  {
            myRoot.getChildren().remove(removeBrick.getRectangle());
        }
        allBricks.clear();
    }

    private void addPowerUp(String type) {
        if (type.equals("G")) growPaddle();
        else if (type.equals("S")) shrinkPaddle();
        else if (type.equals("I")) speedUpBall();
    }

    private void growPaddle() {
        myRoot.getChildren().remove(myPaddle.getRectangle());
        myPaddle.getRectangle().setWidth(myPaddle.getWidth() + PADDLE_GROWTH);
        myRoot.getChildren().add(myPaddle.getRectangle());
    }

    private void shrinkPaddle() {
        myRoot.getChildren().remove(myPaddle.getRectangle());
        myPaddle.getRectangle().setWidth(myPaddle.getWidth() - PADDLE_GROWTH);
        myRoot.getChildren().add(myPaddle.getRectangle());
    }

    private void speedUpBall() {
        for (Bouncer tempBouncer : allBouncers) {
            tempBouncer.addXSpeed(BOUNCER_SPEED_INCREASE);
            tempBouncer.addYSpeed(BOUNCER_SPEED_INCREASE);
        }
    }
}