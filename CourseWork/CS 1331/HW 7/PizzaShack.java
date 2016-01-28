import java.util.PriorityQueue;
import java.util.Set;
/**
 * PizzaShack Class
 *
 * @author Jeongsoo
 * @version 1.0
 */
public class PizzaShack extends AbstractPizzeria {
    /**
     * Constructor of PizzaShack calss
     * to set the given menu as a menu of this store
     *
     * @param menu menu of this pizza store
     */
    public PizzaShack(Set<Order> menu) {
        super(menu);
        orderQueue = new PriorityQueue<>(new PizzaShackComparator());
    }

    /**
     * Pizzeria name
     *
     * @return PizzaShack as a string
     */
    public String getName() {
        return "PizzaShack";
    }
}