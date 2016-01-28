import java.util.Comparator;
/**
 * PizzaShackComparator Class
 * to sort the customer by an order of
 * high price of orders. Then customer's
 * ability
 *
 * @author Jeongsoo
 * @version 1.0
 */
public class PizzaShackComparator implements Comparator<Customer> {
    /**
     * To compare the two orders' prices of two customers
     * and their abilities to pay after checking the prices
     *
     * @param comp1 First customer obeject to compare
     *              with another customer object
     *        comp2 Second customer obeject to compare
     *              with another customer object
     * @return int number differece between the two prices of order
     *                    or the abilities of the two customers
     */
    public int compare(Customer comp1, Customer comp2) {
        if (comp1.getMoney() >= comp1.getOrder().getPrice()
            && comp2.getMoney() >= comp2.getOrder().getPrice()) {
            return comp1.compareTo(comp2);
        } else if (comp1.getMoney() >= comp1.getOrder().getPrice()
            || comp2.getMoney() < comp2.getOrder().getPrice()) {
            return -1;
        } else if (comp1.getMoney() < comp1.getOrder().getPrice()
            || comp2.getMoney() >= comp2.getOrder().getPrice()) {
            return 1;
        } else {
            return comp2.getMoney() - comp1.getMoney();
        }
    }
}
