import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.util.Random;
/**
 * This class represents a Targaryan class.
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class Targaryan extends House {
    /**
     * This is a constructor of Targaryan class
     * @param xpoint The x coordinate of house's location
     * ypoint The Y coordinate of house's location,
     * and bound The game board
     */
    public Targaryan(int xpoint, int ypoint, Rectangle bound) {
        super(xpoint, ypoint, bound);
        life = 100;
        count = 0;
        chance = 0;
        image = new ImageIcon("dragon.PNG");
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

            if (xPos <= rec.width || yPos  <= rec.height) {

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
            }
        } else if ((pheight + iheight) < (rec.height / 2)) {
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
    /**
     * This method is used to check when two houses are met together,
     * whether they can reproduce or not.
     * @param opp The house to check possibility
     * @return It will return true, if the given house is
     * proper house
     */
    public boolean canReproduceWithHouse(House opp) {
        return (opp instanceof Targaryan);
    }
    /**
     * This method is used to reproduce a child of two houses
     * @param opp The house to reproduce with.
     * @return It will return a new house object or nothing
     */
    public House reproduceWithHouse(House opp) {
        Random num3 = new Random();
        int num5 = num3.nextInt(100) + 1;
        if (num5 <= 5) {
            return new Targaryan((this.xPos + opp.xPos) / 4,
                    (this.yPos + opp.yPos) / 4, rec);
        }
        return null;
    }
    /**
     * This method is used to check when two houses are met,
     * whether one can damage another or not.
     * @param opp
     * @return true if the other house is a Baratheon, false otherwise
     */
    public boolean canHarmHouse(House opp) {
        return !(opp instanceof Baratheon);
    }
    /**
     * This method is used for houses to damage other houses,
     * which they can attack
     * @param opp The house will be attacked and damaged
     */
    public void harmHouse(House opp) {
        Random num3 = new Random();
        int num5 = num3.nextInt(10) + 1;
        if (!(opp instanceof Baratheon)) {
            opp.life -= 7;
        }
    }
}