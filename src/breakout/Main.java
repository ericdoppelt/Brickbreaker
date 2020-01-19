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

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Main extends Application {

    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 600;
    public static final String TITLE = "Breakout!";
    public static final Paint BACKGROUND = Color.AZURE;

    public static int LIVES = 3;

    public static final Paint PADDLE_COLOR = Color.BLACK;
    public static final int PADDLE_WIDTH = 200;
    public static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_SPEED = 50;

    public static final int BOUNCER_RADIUS = 10;
    public static final int BOUNCER_SPEED_X = 0;
    public static final int BOUNCER_SPEED_Y = -300;
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

        for (Bouncer tempBouncer : allBouncers) {

            // this could be a method?
            if (tempBouncer.HitWall()) tempBouncer.reverseXDirection();
            else if (tempBouncer.HitCeiling()) tempBouncer.reverseYDirection();
            else if (tempBouncer.HitPaddle(myPaddle)) tempBouncer.handlePaddleHit(myPaddle);
            else if (tempBouncer.hitBottom(SIZE_Y)) handleFallOff(tempBouncer);

            for (Brick tempBrick : allBricks) {
                if (myBouncer.hitsBrick(tempBrick)) {
                    myBouncer.handleBrick(tempBrick);
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
        LIVES--;
        bouncer.placeCenter(SIZE_X, SIZE_Y, PADDLE_HEIGHT);
    }

    private Scene setupGame(int width, int height, Paint background) {
        myRoot = new Group();
        myBouncer = new Bouncer(width / 2, height - BOUNCER_RADIUS - PADDLE_HEIGHT, BOUNCER_RADIUS, BOUNCER_COLOR, BOUNCER_SPEED_X, BOUNCER_SPEED_Y);
        allBouncers.add(myBouncer);

        myPaddle = new Paddle((width - PADDLE_WIDTH) / 2, height - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);

        PoweredBrick myBrick = new PoweredBrick(width/2, height/2, BRICK_LENGTH, BRICK_HEIGHT, 7);
        Brick myBrick2 = new Brick(width/4, height/4, BRICK_LENGTH, BRICK_HEIGHT, 7);

        allBricks.add(myBrick);
        allBricks.add(myBrick2);

        myRoot.getChildren().add(myBouncer.getCircle());
        myRoot.getChildren().add(myPaddle.getRectangle());
        for (Brick tempBrick: allBricks) myRoot.getChildren().add(tempBrick.getRectangle());

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
            LIVES++;
        }
    }

    public static void deleteBrick(Brick brick) {
        myRoot.getChildren().remove(brick.getRectangle());
        allBricks.remove(brick);
    }

    public static Group getRoot() {
        return myRoot;
    }
}
