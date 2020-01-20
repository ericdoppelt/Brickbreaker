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


public class Main extends Application {

    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 600;
    public static final String TITLE = "Breakout!";
    public static final Paint BACKGROUND = Color.AZURE;

    public static final int LIVES = 3;
    private int myLives;

    public static final Paint PADDLE_COLOR = Color.BLACK;
    public static final int PADDLE_WIDTH = 80;
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
    public static Group myRoot;

    private static ArrayList<Bouncer> allBouncers = new ArrayList<>();
    private static ArrayList<Brick> allPlayableBricks = new ArrayList<>();
    private static ArrayList<PermanentBrick> allPermanentBricks = new ArrayList<>();

    public static ArrayList<PowerUp> allPowerUps = new ArrayList(); // not used much, ran out of time

    private Paddle myPaddle;

    private int myLevel;
    private final int myNumberLevels = 3;
    private final int myStartLevel = 1;

    /**
     * Start of the program.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
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
            if ((tempBrick instanceof PermanentBrick)) allPermanentBricks.add((PermanentBrick)tempBrick); // should be fixed
            else allPlayableBricks.add(tempBrick);
        }
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

    private void handleBouncerSurroundings(Bouncer tempBouncer, double elapsedTime) {
        if (tempBouncer.HitWall()) tempBouncer.reverseXDirection();
        else if (tempBouncer.HitCeiling()) tempBouncer.reverseYDirection();
        else if (tempBouncer.HitPaddle(myPaddle)) tempBouncer.handlePaddleHit(myPaddle);
        else if (tempBouncer.hitBottom(SIZE_Y)) handleFallOff(tempBouncer);
    }

    private void handleBouncerPlayables(Bouncer tempBouncer, double elapsedTime) {
        for (int i = 0; i < allPlayableBricks.size(); i++) {
            if (tempBouncer.hitsBrick(allPlayableBricks.get(i))) {
                tempBouncer.handleBrick(allPlayableBricks.get(i));
                allPlayableBricks.get(i).handleHit();
                if (!allPlayableBricks.get(i).hasHitsLeft()) {
                    myRoot.getChildren().remove(allPlayableBricks.get(i).getRectangle());
                    allPlayableBricks.remove(i);
                    i--;
                }
            }
        }
    }

    private void handleBouncerPermanents(Bouncer tempBouncer, double elapsedTime) {
        for (PermanentBrick tempBrick : allPermanentBricks) {
            if (tempBouncer.hitsBrick(tempBrick)) tempBouncer.handleBrick(tempBrick);
        }
    }

    private void updateBouncers(double elapsedTime) {
        for (Bouncer tempBouncer: allBouncers) {
            tempBouncer.setX(tempBouncer.getCircle().getCenterX() + tempBouncer.getXChange() * elapsedTime);
            tempBouncer.setY(tempBouncer.getCircle().getCenterY() + tempBouncer.getYChange() * elapsedTime);
        }
    }


    private void handleBouncers(double elapsedTime) {
        for (Bouncer tempBouncer : allBouncers) {
            handleBouncerSurroundings(tempBouncer, elapsedTime);
            handleBouncerPlayables(tempBouncer, elapsedTime);
            handleBouncerPermanents(tempBouncer, elapsedTime);
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

    private void step(double elapsedTime) {

        if (bricksCleared()) {
            ++myLevel;
            if (myLevel > myNumberLevels) win();
            loadLevel(++myLevel);
        }

        handleBouncers(elapsedTime);
        handlePowerUps(elapsedTime);
        updateBouncers(elapsedTime);
    }

    private void handleFallOff(Bouncer bouncer) {
        --myLives;
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

    public static void deleteBrick(Brick brick) {
        myRoot.getChildren().remove(brick.getRectangle());
        allPlayableBricks.remove(brick);
    }

    public static Group getRoot() {
        return myRoot;
    }

    private boolean bricksCleared() {
        return allPlayableBricks.size() == 0;
    }

    private boolean gameOver() {
        return myLives == 0;
    }

    private void endGame() {};

    private void loadLevel(int i) {
        clearLevel();

        myLevel = i;
        if (myLevel <= myNumberLevels) {
            LevelReader nextLevel = new LevelReader(myLevel, SIZE_X, SIZE_Y);

            for (Brick tempBrick : nextLevel.readLevel()) {
                if (tempBrick instanceof PermanentBrick) allPermanentBricks.add((PermanentBrick)tempBrick);
                else allPlayableBricks.add(tempBrick);

                myRoot.getChildren().add(tempBrick.getRectangle());
            }
        }
    }

    private void clearLevel() {

        for (int i = 0; i < allPlayableBricks.size(); i++) {
            Brick tempBrick = allPlayableBricks.get(i);
            allPlayableBricks.remove(tempBrick);
            myRoot.getChildren().remove(tempBrick.getRectangle());
            i--;
        }
        System.out.println(allPlayableBricks.size());

        for (int i = 0; i < allPermanentBricks.size(); i++) {
            Brick tempBrick = allPermanentBricks.get(i);
            allPermanentBricks.remove(tempBrick);
            myRoot.getChildren().remove(tempBrick.getRectangle());
            i--;
        }
    }
}



