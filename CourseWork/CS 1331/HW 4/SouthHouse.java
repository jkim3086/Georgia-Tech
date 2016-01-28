import java.awt.Rectangle;
import java.util.Random;
/**
 * This class represents a SouthHouse class.
 * @author Jeongsoo Kim
 * @version 1.0
 */
public abstract class SouthHouse extends House {
    /**
     * This is a constructor of SouthHouse class
     * @param xpoint The x coordinate of house's location
     * ypoint The Y coordinate of house's location,
     * and bound The game board
     */
    public SouthHouse(int xpoint, int ypoint, Rectangle bound) {
        super(xpoint, ypoint, bound);
    }
    /**
     * This method is used to move houses in Southland
     */
    public void move() {
        Random num1 = new Random();
        int num = num1.nextInt(20) + 1;
        int pheight = yPos;
        int iheight = image.getIconHeight() / 2;

        if ((pheight + iheight) > (rec.height / 2)) {

            if (xPos <= rec.width || yPos <= rec.height) {

                if (num <= 5) {
                    xPos += 10;
                    yPos += 10;
                } else if (num > 5 && num < 10) {
                    xPos += 10;
                    yPos -= 10;
                } else if (num >= 10 && num < 16) {
                    xPos -= 10;
                    yPos += 10;
                } else if (num >= 16 && num <= 20) {
                    xPos -= 10;
                    yPos -= 10;
                }
                this.life -= 5;
                this.age += 3;
            }
        } else if ((pheight + iheight) <= (rec.height / 2)) {
            if (xPos <= rec.width || yPos <= rec.height) {

                if (num <= 5) {
                    xPos += 5;
                    yPos += 5;
                } else if (num > 5 && num < 10) {
                    xPos += 5;
                    yPos -= 5;
                } else if (num >= 10 && num < 16) {
                    xPos -= 5;
                    yPos += 5;
                } else if (num >= 16 && num <= 20) {
                    xPos -= 5;
                    yPos -= 5;
                }
                this.life -= 5;
                this.age += 3;
            }
        }
        num = 0;
        if (xPos > rec.width - image.getIconWidth()) {
            xPos = rec.width - image.getIconWidth();
        } else if (xPos < 0) {
            xPos = 0;
        }

        if (yPos > rec.height - image.getIconWidth()) {
            yPos = rec.height - image.getIconWidth();
        } else if (yPos < 0) {
            yPos = 0;
        }
    }
}