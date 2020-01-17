package breakout;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Main extends Application {

    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 600;
    public static final String TITLE = "Breakout!";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint PADDLE_COLOR = Color.BLACK;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final int BOUNCER_RADIUS = 10;
    public static final Paint BOUNCER_COLOR = Color.ROYALBLUE;

    private static final int MOVER_SPEED = 20;

    private Scene myScene;
    private Bouncer mainBouncer;
    private Paddle mainPaddle;
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

    }
    
    private Scene setupGame(int width, int height, Paint background) {
        Group root = new Group();
        mainBouncer = new Bouncer(width / 2, height - BOUNCER_RADIUS - PADDLE_HEIGHT, BOUNCER_RADIUS, BOUNCER_COLOR);
        mainPaddle = new Paddle((width - PADDLE_WIDTH) / 2, height - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);

        root.getChildren().add(mainBouncer.getBouncer());
        root.getChildren().add(mainPaddle.getPaddle());

        Scene scene = new Scene(root, width, height, background);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        // scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            mainPaddle.getPaddle().setX(mainPaddle.getPaddle().getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            mainPaddle.getPaddle().setX(mainPaddle.getPaddle().getX() - MOVER_SPEED);
        }
    }
}
