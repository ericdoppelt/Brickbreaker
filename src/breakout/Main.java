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
    public static final Paint PADDLE_COLOR = Color.BLACK;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BOUNCER_RADIUS = 10;
    public static final int BOUNCER_SPEED_X = 500;
    public static final int BOUNCER_SPEED_Y = 600;
    public static final Paint BOUNCER_COLOR = Color.ROYALBLUE;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final int MOVER_SPEED = 20;

    private Scene myScene;
    private Scene mySplashScreen;

    private Bouncer myBouncer;
    private ArrayList<Bouncer> allBouncers = new ArrayList<>();
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
            if (!tempBouncer.xOnScreen()) tempBouncer.reverseXDirection();
            tempBouncer.setX(tempBouncer.getCircle().getCenterX() + tempBouncer.getXChange() * elapsedTime);
            System.out.println((tempBouncer.getXChange()));
            if (!tempBouncer.yOnScreen()) tempBouncer.reverseYDirection();
            tempBouncer.setY(tempBouncer.getCircle().getCenterY() + tempBouncer.getYChange() * elapsedTime);

        }
    }

    private Scene setupGame(int width, int height, Paint background) {
        Group root = new Group();
        myBouncer = new Bouncer(width / 2, height - BOUNCER_RADIUS - PADDLE_HEIGHT, BOUNCER_RADIUS, BOUNCER_COLOR, BOUNCER_SPEED_X, BOUNCER_SPEED_Y);
        System.out.println("okay");
        allBouncers.add(myBouncer);
        myPaddle = new Paddle((width - PADDLE_WIDTH) / 2, height - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);

        root.getChildren().add(myBouncer.getCircle());
        root.getChildren().add(myPaddle.getPaddle());

        Scene scene = new Scene(root, width, height, background);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        // scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }


    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.getPaddle().setX(myPaddle.getPaddle().getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.getPaddle().setX(myPaddle.getPaddle().getX() - MOVER_SPEED);
        }
    }
}
