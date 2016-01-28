import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
/**
 * One of tool to draw
 *
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class Pencil implements Tool {

    /**
     * This method is used to point a initial
     * point to draw, when the mouse is pressed.
     *
     * @param e The mouseevent that fired this onPress.
     * @param g The current graphics context.
     */
    @Override
    public void onPress(MouseEvent e, GraphicsContext g) {
        g.beginPath();
        g.moveTo(e.getX(), e.getY());
        g.stroke();
    }
    /**
     * Tool method that is called when the mouse is dragged.
     * While this method is used, this method keep getting
     * new coordinates and drawing.
     *
     * @param e The mouseevent that fired this onDrag.
     * @param g The current graphics context.
     */
    @Override
    public void onDrag(MouseEvent e, GraphicsContext g) {
        g.lineTo(e.getX(), e.getY());
        g.stroke();
    }
    /**
     * Tool method that is called when the mouse is released.
     * When mouse is released, drawing should stop.
     * So, this method has an empty body.
     *
     * @param e The mouseevent that fired this onRelease.
     * @param g The current graphics context.
     */
    @Override
    public void onRelease(MouseEvent e, GraphicsContext g) {
    }
    /**
     * The name of this tool.
     *
     * @return This tool's name.
     */
    @Override
    public String getName() {
        return "Pencil";
    }

}