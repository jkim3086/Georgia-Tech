import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.util.Random;
/**
 * This class represents a Baratheon class.
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class Baratheon extends SouthHouse {
    /**
     * This is a constructor of Baratheon class
     * @param xpoint The x coordinate of house's location
     * ypoint The Y coordinate of house's location,
     * and bound The game board
     */
    public Baratheon(int xpoint, int ypoint, Rectangle bound) {
        super(xpoint, ypoint, bound);
        life = 100;
        count = 0;
        chance = 0;
        age = 0;
        maxage = 80;
        image = new ImageIcon("stag.PNG");
    }
    /**
     * This method is used to check when two houses are met together,
     * whether they can reproduce or not.
     * @param opp The house to check possibility
     * @return It will return true, if the given house is
     * proper house
     */
    public boolean canReproduceWithHouse(House opp) {
        return (opp instanceof Lannister);
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
            return new Baratheon((this.xPos + opp.xPos) / 2,
                    (this.yPos + opp.yPos) / 3, rec);
        }
        return null;
    }
    /**
     * This method is used to check when two houses are met,
     * whether one can damage another or not.
     * @param opp
     * @return It will return a boolean variable
     */
    public boolean canHarmHouse(House opp) {
        return (opp instanceof Targaryan);
    }
    /**
     * This method is used for houses to damage other houses,
     * which they can attack
     * @param opp The house will be attacked and damaged
     */
    public void harmHouse(House opp) {
        Random num3 = new Random();
        int num5 = num3.nextInt(10) + 1;
        if (opp instanceof Targaryan) {
            opp.life -= 7;
        }
    }
}