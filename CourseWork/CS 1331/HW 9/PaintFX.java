import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * This class is basic frame of
 * this program and has buttons,
 * canvas, and vbox
 *
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class PaintFX extends Application {
    private Tool control = new Pencil();
    /**
     * This main method just launch the stage
     *
     * @param args args is parameter for main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * This method has hbox, vbox, buttons, and
     * canvas. Therefore, it will display a frame
     * for this program. Each button is a connection
     * between this class and other tool classes.
     *
     * @param stage it contains whole frame.
     */
    @Override
    public void start(Stage stage) {
        HBox root = new HBox();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(500, 500);
        canvas.setCursor(Cursor.CROSSHAIR);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        VBox vbox = new VBox(15);
        Text title = new Text("Tools");
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                gc.setFill(colorPicker.getValue());
                gc.setStroke(colorPicker.getValue());
            }
        });

        Button btn1 = new Button("Clear");
        btn1.setOnAction(event ->
            { gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); });
        Button btn2 = new Button("Pencil");
        btn2.setOnAction(event ->
            { control = new Pencil(); });
        Button btn3 = new Button("Rectangle");
        btn3.setOnAction(event ->
            { control = new Rectangle(); });
        Button btn4 = new Button("Oval");
        btn4.setOnAction(event ->
            { control = new Oval(); });

        canvas.setOnMousePressed(event -> control.onPress(event, gc));
        canvas.setOnMouseDragged(event -> control.onDrag(event, gc));
        canvas.setOnMouseReleased(event -> control.onRelease(event, gc));

        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(30));
        vbox.setStyle("-fx-background-color: blue;"
                      + "-fx-border-width: 1");

        vbox.getChildren().addAll(title, btn1, btn2, btn3, btn4, colorPicker);
        root.getChildren().addAll(vbox, canvas);
        stage.setTitle("PaintFX");
        stage.setScene(scene);
        stage.show();
    }
}