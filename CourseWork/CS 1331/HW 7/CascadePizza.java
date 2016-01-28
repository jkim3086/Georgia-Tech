import java.util.ArrayDeque;
import java.util.Set;
/**
 * CascadePizza Class
 *
 * @author Jeongsoo
 * @version 1.0
 */
public class CascadePizza extends AbstractPizzeria {
    /**
     * Constructor of CascadePizza calss
     * to set the given menu as a menu of this store
     *
     * @param menu menu of this pizza store
     */
    public CascadePizza(Set<Order> menu) {
        super(menu);
        orderQueue = new ArrayDeque<>();
    }

    /**
     * Pizzeria name
     *
     * @return CascadePizza as a string
     */
    public String getName() {
        return "CascadePizza";
    }
}