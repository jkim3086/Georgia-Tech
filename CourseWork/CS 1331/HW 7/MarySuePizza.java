import java.util.PriorityQueue;
import java.util.Set;
/**
 * MarySuePizza Class
 *
 * @author Jeongsoo
 * @version 1.0
 */
public class MarySuePizza extends AbstractPizzeria {
    /**
     * Constructor of MarySuePizza calss
     * to set the given menu as a menu of this store
     *
     * @param menu menu of this pizza store
     */
    public MarySuePizza(Set<Order> menu) {
        super(menu);
        orderQueue = new PriorityQueue<>(new MarySueComparator());
    }

    /**
     * Pizzeria name
     *
     * @return MarySuePizza as a string
     */
    public String getName() {
        return "MarySuePizza";
    }
}