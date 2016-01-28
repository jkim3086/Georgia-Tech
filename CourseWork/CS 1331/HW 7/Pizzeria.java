/**
 * Pizzeria interface
 *
 * @author Gustavo
 * @version 1.0
 */
public interface Pizzeria {


    /**
     * Tells the pizzeria to place an order with a Customer
     *
     * @param customer Customer who wants a pizza
     */
    void placeOrder(Customer customer);

    /**
     * Returns the cheapest menu item based on price
     *
     * @return cheapest menu item
     */
    Order getCheapestMenuItem();

    /**
     * Returns the most expensive menu item based on price
     *
     * @return most expensive menu item
     */
    Order getMostExpensiveMenuItem();


    /**
     * Status message of a Pizzeria's performance
     * Should say what percentage of orders have been delivered
     * and what percentage of attempted orders have been delivered
     * along with total revenues
     *
     * @return Message string
     */
    String status();

    /**
     * Pizzeria name
     *
     * @return name
     */
    String getName();


    /**
     * Attempts to deliver an order to a customer.
     * If the customer placed an order that's not on the menu,
     * we don't deliver. If the customer placed an order that is
     * on the menu but he doesn't have enough money to pay,
     * we don't deliver. Otherwise, we deliver to customer
     * and collect our money.
     */
    void processOrder();
}
