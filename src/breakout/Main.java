package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {

    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 600;
    public static final String TITLE = "Breakout!";
    public static final Paint BACKGROUND = Color.AZURE;

    public static int LIVES = 3;
    private static int myLives;

    public static final Paint PADDLE_COLOR = Color.BLACK;
    public static final int PADDLE_WIDTH = 800;
    public static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_SPEED = 50;

    public static final int BOUNCER_RADIUS = 10;
    public static final int BOUNCER_SPEED_X = 250;
    public static final int BOUNCER_SPEED_Y = -500;
    public static final Paint BOUNCER_COLOR = Color.ROYALBLUE;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final int BRICK_LENGTH = 100;
    public static final int BRICK_HEIGHT = 200;

    private Scene myScene;
    private Scene mySplashScreen;
    public static Group myRoot;

    private Bouncer myBouncer;
    private ArrayList<Bouncer> allBouncers = new ArrayList<>();
    private static ArrayList<Brick> allBricks = new ArrayList<>();
    public static ArrayList<PowerUp> allPowerUps = new ArrayList(); // change

    private Paddle myPaddle;

    private static int myLevel;
    private static final int myNumberLevels = 4;

    /**
     * Start of the program.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE_X, SIZE_Y, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }


    private void step(double elapsedTime) {

        // if (gameOver()) endGame();
        if (bricksCleared()) {
            loadNextLevel();
        }

        for (Bouncer tempBouncer : allBouncers) {

            // this could be a method?
            if (tempBouncer.HitWall()) tempBouncer.reverseXDirection();
            else if (tempBouncer.HitCeiling()) tempBouncer.reverseYDirection();
            else if (tempBouncer.HitPaddle(myPaddle)) tempBouncer.handlePaddleHit(myPaddle);
            else if (tempBouncer.hitBottom(SIZE_Y)) handleFallOff(tempBouncer);

            for (int i = 0; i < allBricks.size(); i++) {
                if (myBouncer.hitsBrick(allBricks.get(i))) {
                    myBouncer.handleBrick(allBricks.get(i));
                    allBricks.get(i).handleHit();
                    if (!allBricks.get(i).hasHitsLeft()) {
                        myRoot.getChildren().remove(allBricks.get(i).getRectangle());
                        allBricks.remove(i);
                        i--;
                    }
                }
            }

            tempBouncer.setX(tempBouncer.getCircle().getCenterX() + tempBouncer.getXChange() * elapsedTime);
            tempBouncer.setY(tempBouncer.getCircle().getCenterY() + tempBouncer.getYChange() * elapsedTime);
        }

        for (PowerUp tempPowerUp : allPowerUps) {
            if (tempPowerUp.isCaught(myPaddle)) {
                tempPowerUp.handleCatch(myRoot);
                allPowerUps.remove(tempPowerUp);
            } else {
                tempPowerUp.setY(tempPowerUp.getY() + tempPowerUp.getDropSpeed() * elapsedTime);
            }
        }

    }

    private void handleFallOff(Bouncer bouncer) {
        myLives--;
        bouncer.placeCenter(SIZE_X, SIZE_Y, PADDLE_HEIGHT);
    }

    private Scene setupGame(int width, int height, Paint background) {

        myLives = LIVES;
        myLevel = 3;

        myRoot = new Group();
        myBouncer = new Bouncer(width / 2, height - BOUNCER_RADIUS - PADDLE_HEIGHT, BOUNCER_RADIUS, BOUNCER_COLOR, BOUNCER_SPEED_X, BOUNCER_SPEED_Y);
        allBouncers.add(myBouncer);

        myPaddle = new Paddle((width - PADDLE_WIDTH) / 2, height - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);

        LevelReader level1 = new LevelReader(myLevel, SIZE_X, SIZE_Y);
        ArrayList<Brick> levelBricks = level1.readLevel();

        for (Brick tempBrick : levelBricks) {
            allBricks.add(tempBrick);
            myRoot.getChildren().add(tempBrick.getRectangle());
        }

        myRoot.getChildren().add(myBouncer.getCircle());
        myRoot.getChildren().add(myPaddle.getRectangle());

        Scene scene = new Scene(myRoot, width, height, background);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        // scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
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
        }
    }

    public static void deleteBrick(Brick brick) {
        myRoot.getChildren().remove(brick.getRectangle());
        allBricks.remove(brick);
    }

    public static Group getRoot() {
        return myRoot;
    }

    private boolean bricksCleared() {
        return allBricks.size() == 0;
    }

    private boolean gameOver() {
        return myLives == 0;
    }

    private void endGame() {};

    private void loadNextLevel() {
        myLevel++;
        if (myLevel <= 2) {
            LevelReader nextLevel = new LevelReader(myLevel, SIZE_X, SIZE_Y);
            for (Brick tempBrick : nextLevel.readLevel()) {
                allBricks.add(tempBrick);
                myRoot.getChildren().add(tempBrick.getRectangle());
            }
        }
    }
}
