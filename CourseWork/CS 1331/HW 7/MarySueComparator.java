import java.util.Comparator;
/**
 * MarySueComparator Class
 * to sort the customer by an order of
 * high price of orders
 *
 * @author Jeongsoo
 * @version 1.0
 */
public class MarySueComparator implements Comparator<Customer> {
    /**
     * To compare the two orders' prices of two customers
     *
     * @param comp1 First customer obeject to compare
     *              with another customer object
     *        comp2 Second customer obeject to compare
     *              with another customer object
     * @return int number differece between the two prices of order
     */
    public int compare(Customer comp1, Customer comp2) {
        return comp1.compareTo(comp2);
    }
}