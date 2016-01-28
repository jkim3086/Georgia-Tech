/**
 * Customer Class
 *
 * @author Jeongsoo
 * @version 1.0
 */
public class Customer implements Comparable<Customer> {
    /**
     * variables, used in this class
     */
    private Order order;
    private int money;
    /**
     * Constructor of Customer class
     * generate object random amount money and
     * customer's order
     *
     * @param order customer's order
     */
    public Customer(Order order) {
        this.order = order;
        money = Driver.RANDOM.nextInt(30) + 5;
    }
    /**
     * Compare prices of orders between Customers
     *
     * @param comp an object of Customer
     * @return a number a positve or negtaive or zero
     */
    public int compareTo(Customer comp) {
        return comp.getOrder().getPrice() - this.getOrder().getPrice();
    }
    /**
     * Get order
     *
     * @return order order
     */
    public Order getOrder() {
        return this.order;
    }
    /**
     * Get money
     *
     * @return money money
     */
    public int getMoney() {
        return this.money;
    }
}


