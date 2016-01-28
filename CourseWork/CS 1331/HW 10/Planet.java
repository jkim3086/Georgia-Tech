import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
/**
 * This class is enum class.
 * It has all planets, which are,
 * on the program screen.
 *
 * @author Jeongsoo Kim
 * @version 1.0
 */
public enum Planet {

    /**
     * All planets enum constructors
     * with its color, size, speed,
     * and distance from the sun.
     */
    EARTH(Color.SPRINGGREEN, 1, 1, 1),
    MERCURY(Color.SILVER, 0.1915, 0.387, 0.24),
    VENUS(Color.GREEN, 0.4745, 0.723, 0.62),
    MARS(Color.RED, 0.266, 1.52, 1.88);

    /**
     * Basic information of planet Earth
     * Those are used for calculating other
     * planets' information.
     */
    /** DO NOT MODIFY IT'S FOR YOUR OWN GOOD**/
    private final int earthRadius = 35;
    private final int earthDistance = 265;
    private final int earthPeriod = 5;
    /** OK YOU'RE GOOD GO AHEAD AND DO WORK NOW **/

    /**
     * Instances, which are used
     * in this class.
     */
    private Circle planet;
    private PathTransition pt;
    private Path path;

    /**
     * Constructor of this enum class
     * So it accepts ratio to calculate
     * actual values for each planet.
     * Then specifies an actual planet.
     *
     * @param col color of a planet
     *        radius ratio of radius of a planet
     *        distance ratio distance from the sum
     *        period ratio speed of a planet
     */
    Planet(Color col, double radius, double distance, double period) {
        double widthp = Planetarium.WIDTH / 2;
        double heightp = Planetarium.HEIGHT / 2;
        double pr = earthPeriod * period;
        planet = new Circle(widthp, heightp
                    + (distance * earthDistance),
                    radius * earthRadius, col);
        pt = new PathTransition();
        path = new Path();
        circular((distance * earthDistance),
                pr, heightp, widthp);
    }

    /**
     * This method is created for performing
     * javaFX. So it specified path, arcoto,
     * and pathtransition
     *
     * @param dist actual distance from the sum
     * @param pd actual speed of a planet
     * @param hp Sun's Y_coordinate
     * @param wp Sun's X_coordinate
     */
    public void circular(double dist, double pd, double hp, double wp) {
        path.setStroke(Color.WHITE);
        path.getElements().add(new MoveTo(wp - dist, hp));

        ArcTo arcTo1 = new ArcTo();
        arcTo1.setX(wp + dist);
        arcTo1.setY(hp);
        arcTo1.setRadiusX(dist);
        arcTo1.setRadiusY(dist);

        ArcTo arcTo2 = new ArcTo();
        arcTo2.setX(wp - dist);
        arcTo2.setY(hp);
        arcTo2.setRadiusX(dist);
        arcTo2.setRadiusY(dist);

        path.getElements().addAll(arcTo1, arcTo2);

        pt.setDuration(Duration.seconds(pd));
        pt.setNode(this.planet);
        pt.setPath(path);
        pt.setInterpolator(Interpolator.LINEAR);
        pt.setCycleCount(Transition.INDEFINITE);
    }

    /**
     * Get planet
     *
     * @return a cirlce a circle object as a planet
     */
    public Circle getplanet() {
        return this.planet;
    }

    /**
     * Get pathtransition
     *
     * @return a pathtransition a pathtranstion
     *                          of a planet
     */
    public PathTransition getPT() {
        return this.pt;
    }

    /**
     * Get path
     *
     * @return a path a path of a planet
     */
    public Path  getPath() {
        return this.path;
    }
}