import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
/**
 * One of Tool to draw a rectangle
 *
 * @author Jeongsoo kim
 * @version 1.0
 */
public class Rectangle implements Tool {
    private double orinx = 0;
    private double oriny = 0;
    /**
     * This method is used to point initial
     * coordinates of a rectangle, when mouse is
     * pressed on the canvase
     *
     * @param e The mouseevent that fired this onPress.
     * @param g The current graphics context.
     */
    @Override
    public void onPress(MouseEvent e, GraphicsContext g) {
        orinx = e.getX();
        oriny = e.getY();
    }
    /**
     * This tool is originally used, when
     * mouse is dragged. But there is no
     * body of this method for a purpose
     * of its usage
     *
     * @param e The mouseevent that fired this onDrag.
     * @param g The current graphics context.
     */
    @Override
    public void onDrag(MouseEvent e, GraphicsContext g) {
    }
    /**
     * This method is used, when mouse is released
     * as the mouse is released, drawing a
     * rectangle is also finished.
     *
     * @param e The mouseevent that fired this onRelease.
     * @param g The current graphics context.
     */
    @Override
    public void onRelease(MouseEvent e, GraphicsContext g) {
        g.fillRect(Math.min(orinx, e.getX()),
            Math.min(oriny, e.getY()),
            Math.abs(e.getX() - orinx),
            Math.abs(e.getY() - oriny));
        g.stroke();
    }
    /**
     * The name of this tool.
     *
     * @return This tool's name.
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
