import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * This class represents Westeros
 *
 * @author Sundeep, Ethan Shernan
 * @version 1.0
 */
public class Westeros extends JPanel {
    public static final int WIDTH = 600, HEIGHT = 600;

    private ArrayList<House> houses = new ArrayList<House>();
    private ControlPanel cPanel;
    private Timer timer;
    private Rectangle bounds;

    /**
     * Constructor
     *
     * @param control A ControlPanel instance which is used
     * to determine the next House to create
     */
    public Westeros(ControlPanel control) {
        cPanel = control;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GREEN);
        bounds = new Rectangle(0, 0, WIDTH, HEIGHT);

        addMouseListener(new ClickListener());

        timer = new Timer(100, new TimerListener());
        timer.start();
    }

    /**
     * Move all the houses in Westeros
     */
    public void moveAll() {
        for (House a : houses) {
            a.move();
        }
    }

    /**
     * Check to see whether any of the houses can wound
     * the other houses they are around. Then wounds them.
     */
    public void checkFoodChain() {
        for (House a : houses) {
            for (House other : houses) {
                if (a.collidesWithHouse(other)) {
                    if (a != other
                        && a.canHarmHouse(other)) {
                        a.harmHouse(other);
                    }
                }
            }
        }
    }

    /**
     * Checks to see if members of a house need to reproduce
     */
    public void checkReproduce() {
        int currHousesSize = houses.size();
        for (int i = 0; i < currHousesSize; i++) {
            //don't want double reproduction
            for (int j = 0; j < currHousesSize; j++) {
                if (i != j) {
                    if (houses.get(i).
                        collidesWithHouse(houses.get(j))) {

                        if (houses.get(i).
                            canReproduceWithHouse(houses.get(j))) {

                            House baby = houses.get(i).
                                reproduceWithHouse(houses.get(j));

                            if (baby != null) {
                                //Prevent Heapsize crashes
                                if (houses.size() < 1000) {
                                    houses.add(baby);
                                }
                                i++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Circle of life. Dead houses go back to the soil and
     * Aka remove them from the list.
     *
     * Since Java Garbage collects, once the only reference to the
     * House is gone, it is deleted from memory.
     */
    public void checkDead() {
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).isDead()) {
                houses.remove(i);
                i--;
            }
        }
    }

    /**
     * This isn't the humane society. This is the Serengeti.
     * Old houses have to die.
     */
    public void euthanize() {
        for (House a : houses) {
            if (a.isOld()) {
                a.die();
            }
        }
    }

    /**
     * Draw all the houses
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        g.fillRect(0, 0, WIDTH, HEIGHT / 2);
        for (House a : houses) {
            a.draw(g);
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            moveAll();
            checkReproduce();
            checkFoodChain();
            euthanize();
            checkDead();
            repaint();
        }
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            //Determine type of House to create using ControlPanel
            //Based on the button that was last clicked
            String houseType = cPanel.getHouseType();
            Point p = e.getPoint();

            //Create the corresponding House
            if (houseType.equals("Stark")) {
                houses.add(new Stark(p.x, p.y, bounds));
            } else if (houseType.equals("Baratheon")) {
                houses.add(new Baratheon(p.x, p.y, bounds));
            } else if (houseType.equals("Lannister")) {
                houses.add(new Lannister(p.x, p.y, bounds));
            } else if (houseType.equals("Targaryan")) {
                houses.add(new Targaryan(p.x, p.y, bounds));
            } else if (houseType.equals("Tully")) {
                houses.add(new Tully(p.x, p.y, bounds));
            } else if (houseType.equals("CUSTOM_HOUSE")) {
                houses.add(new CUSTOM_HOUSE(p.x, p.y, bounds));
            } // TODO: CHANGE THESE LINES ^^

            repaint();
        }
    }
}
