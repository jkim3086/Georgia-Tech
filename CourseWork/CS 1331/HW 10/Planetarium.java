import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
/**
 * This class is basic frame of
 * this program and has scene
 * , enums for planets.
 *
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class Planetarium extends Application {
    /**
     * variables, which represents height
     * width of the frame.
     *
     */
    public static final  int HEIGHT = 800;
    public static final  int WIDTH = 800;
    /**
     * This method has scene, sun, enum planets,
     * objects for javaFX animation.
     * Therefore, it will display a frame
     * for this program.
     *
     * @param stage it contains whole frame.
     */
    @Override public void start(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);

        Circle sun = new Circle((scene.getWidth()) / 2,
                    (scene.getHeight()) / 2,
                    65, Color.YELLOW);
        root.getChildren().addAll(
                Planet.MERCURY.getPath(),
                Planet.VENUS.getPath(),
                Planet.MARS.getPath(),
                Planet.EARTH.getPath());

        root.getChildren().addAll(sun,
                    Planet.MERCURY.getplanet(),
                    Planet.VENUS.getplanet(),
                    Planet.MARS.getplanet(),
                    Planet.EARTH.getplanet());

        Planet.VENUS.getPT().play();
        Planet.MERCURY.getPT().play();
        Planet.MARS.getPT().play();
        Planet.EARTH.getPT().play();
        //root.getChildren().add(Planet.EARTH.path);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This main method just launch the stage
     *
     * @param args args is parameter for main method.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}