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
 * This is only partially complete, so powerUps do not do anything when caught.
 * Depends and calls on every class in the breakout package.
 * Use it by running the main method below.
 * Note that this is INCOMPLETE. The programmer ran out of time before the deadline, so PowerUps, splashScreen, and Boss Fight are left unmarked.
 * The root is accessed by a getterMethod to work with PoweredUpBricks, which is problematic.
 */
public class Main extends Application {

    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 600;
    public static final String TITLE = "Breakout!";
    public static final Paint BACKGROUND = Color.AZURE;

    public static final int LIVES = 3;
    private int myLives;

    public static final Paint PADDLE_COLOR = Color.BLACK;
    public static final int PADDLE_WIDTH = 150;
    public static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_SPEED = 50;

    public static final int BOUNCER_RADIUS = 10;
    public static final int BOUNCER_SPEED_X = 250;
    public static final int BOUNCER_SPEED_Y = -500;
    public static final Paint BOUNCER_COLOR = Color.ROYALBLUE;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Scene myScene;
    private static Group myRoot;

    private static ArrayList<Bouncer> allBouncers = new ArrayList<>();
    private static ArrayList<NormalBrick> allNormalBricks = new ArrayList<>();
    private static ArrayList<PoweredBrick> allPoweredBricks = new ArrayList<>();
    private static ArrayList<PermanentBrick> allPermanentBricks = new ArrayList<>();

    public static ArrayList<PowerUp> allPowerUps = new ArrayList(); // not used much, ran out of time

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
        // attach scene to the stage and display it
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

    /**
     * Returns the root of the program
     * Used in the PoweredBrick class once to drop a PowerUp
     * @return the root for the game.
     */
    public static Group getRoot() {return myRoot;}
    private Scene setupGame() {

        setLives();
        setBouncer();
        setPaddle();

        myRoot = new Group();

        createStartLevel();

        for (Bouncer tempBouncer : allBouncers) myRoot.getChildren().add(tempBouncer.getCircle());
        myRoot.getChildren().add(myPaddle.getRectangle());

        Scene scene = new Scene(myRoot, SIZE_X, SIZE_Y, BACKGROUND);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private void createStartLevel() {
        LevelReader startLevel = new LevelReader(myStartLevel, SIZE_X, SIZE_Y);
        for (Brick tempBrick : startLevel.readLevel()) {
            myRoot.getChildren().add(tempBrick.getRectangle());
            if ((tempBrick instanceof NormalBrick)) allNormalBricks.add((NormalBrick)tempBrick);
            else if ((tempBrick instanceof PoweredBrick)) allPoweredBricks.add((PoweredBrick)tempBrick);
            else if ((tempBrick instanceof PermanentBrick)) allPermanentBricks.add((PermanentBrick)tempBrick);
        }
    }

    private void step(double elapsedTime) {

        if (bricksCleared()) {
            ++myLevel;
            if (myLevel > myNumberLevels) win();
            loadLevel(++myLevel);
        }

        handleBouncersBricks(elapsedTime);
        handlePowerUps(elapsedTime);
        updateBouncers(elapsedTime);
    }

    private void setLives() {
        myLives = LIVES;
    }

    private void setBouncer() {
        Bouncer tempBouncer = new Bouncer(SIZE_X / 2, SIZE_Y - BOUNCER_RADIUS - PADDLE_HEIGHT, BOUNCER_RADIUS, BOUNCER_COLOR, BOUNCER_SPEED_X, BOUNCER_SPEED_Y);
        allBouncers.add(tempBouncer);
    }

    private void setPaddle() {
        myPaddle = new Paddle((SIZE_X - PADDLE_WIDTH) / 2, SIZE_Y - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);
    }

    private void handleBouncerSurroundings(Bouncer tempBouncer) {
        if (tempBouncer.HitWall()) tempBouncer.reverseXDirection();
        else if (tempBouncer.HitCeiling()) tempBouncer.reverseYDirection();
        else if (tempBouncer.HitPaddle(myPaddle)) tempBouncer.handlePaddleHit(myPaddle);
        else if (tempBouncer.hitBottom(SIZE_Y)) handleFallOff(tempBouncer);
    }

    private void handleBouncerNormals(Bouncer tempBouncer) {
        for (int i = 0; i < allNormalBricks.size(); i++) {
            NormalBrick tempBrick = allNormalBricks.get(i);
            handleBrick(tempBouncer, tempBrick);
            if (tryRemoveBrick(tempBrick)) {
                allNormalBricks.remove(i);
            }
        }
    }

    private void handleBouncerPowereds(Bouncer tempBouncer) {
        for (int i = 0; i < allPoweredBricks.size(); i++) {
            PoweredBrick tempBrick = allPoweredBricks.get(i);
            handleBrick(tempBouncer, tempBrick);
            if (tryRemoveBrick(tempBrick)) {
                allPoweredBricks.remove(i);
                i--;
            }
        }
    }

    private void handleBouncerPermanents(Bouncer tempBouncer) {
        for (PermanentBrick tempBrick : allPermanentBricks) {
            if (tempBouncer.hitsBrick(tempBrick)) tempBouncer.handleBrick(tempBrick);
        }
    }

    private boolean tryRemoveBrick(Brick tempBrick) {
        if (!tempBrick.hasHitsLeft()) {
            myRoot.getChildren().remove(tempBrick.getRectangle());
            return true;
        } else {
            return false;
        }
    }

    private void handleBrick(Bouncer tempBouncer, Brick tempBrick) {
        if (tempBouncer.hitsBrick(tempBrick)) {
            tempBouncer.handleBrick(tempBrick);
            tempBrick.handleHit();
        }
    }

    private void updateBouncers(double elapsedTime) {
        for (Bouncer tempBouncer: allBouncers) {
            tempBouncer.setX(tempBouncer.getCircle().getCenterX() + tempBouncer.getXChange() * elapsedTime);
            tempBouncer.setY(tempBouncer.getCircle().getCenterY() + tempBouncer.getYChange() * elapsedTime);
        }
    }


    private void handleBouncersBricks(double elapsedTime) {
        for (Bouncer tempBouncer : allBouncers) {
            handleBouncerSurroundings(tempBouncer);
            handleBouncerNormals(tempBouncer);
            handleBouncerPowereds(tempBouncer);
            handleBouncerPermanents(tempBouncer);
        }
    }


    private void handlePowerUps(double elapsedTime) {
        for (int i = 0; i < allPowerUps.size(); i++) {
            PowerUp tempPowerUp = allPowerUps.get(i);
            if (tempPowerUp.isCaught(myPaddle)) {
                tempPowerUp.handleCatch(myRoot);
                allPowerUps.remove(tempPowerUp);
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
        } else if (codeHighestLevel(code)) {
            loadLevel(myNumberLevels);
        }
    }

    private boolean codeHighestLevel(KeyCode code) {
        return code.getCode() - 48 >= myNumberLevels && code.getCode() <= 57;
    }

    private boolean bricksCleared() {
        return allPoweredBricks.size() == 0 && allNormalBricks.size() == 0;
    }

    private void loadLevel(int i) {
        clearLevel();

        myLevel = i;
        if (myLevel <= myNumberLevels) {
            LevelReader nextLevel = new LevelReader(myLevel, SIZE_X, SIZE_Y);

            for (Brick tempBrick : nextLevel.readLevel()) {
                if (tempBrick instanceof NormalBrick) allNormalBricks.add((NormalBrick)tempBrick);
                if (tempBrick instanceof PoweredBrick) allPoweredBricks.add((PoweredBrick)tempBrick);
                if (tempBrick instanceof PermanentBrick) allPermanentBricks.add((PermanentBrick)tempBrick);

                myRoot.getChildren().add(tempBrick.getRectangle());
            }
        }
    }

    private void clearLevel() {
        clearBricks(allNormalBricks);
        clearBricks(allPoweredBricks);
        clearBricks(allPermanentBricks);
    }

    private void clearBricks(ArrayList<? extends Brick> listBrick) {
        for (Brick tempBrick : listBrick)  {
            myRoot.getChildren().remove(tempBrick.getRectangle());
        }
        listBrick.clear();
    }
}




