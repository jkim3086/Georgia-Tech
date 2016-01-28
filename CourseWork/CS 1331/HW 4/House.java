/**
 * The abstract House for the Game of Thrones Simulation
 *
 */
import javax.swing.ImageIcon;
import java.awt.Graphics;

public abstract class House {

    protected ImageIcon image;
    protected String imageFilename;

    //Put constructors here!

    /**
     * Should draw the House at its location.
     */
    protected void draw(Graphics g) {
        image.paintIcon(null, g, xPos, yPos);
    }
}
