import java.util.Queue;
import java.util.Set;
import java.text.NumberFormat;
/**
 * AbstractPizzeria Class
 *
 * @author Jeongsoo
 * @version 1.0
 */
public abstract class AbstractPizzeria implements Pizzeria {
    /**
     * two collections, menu and orderQueue
     * to store menu of each store and
     * customer's order
     */
    protected Set<Order> menu;
    protected Queue<Customer> orderQueue;
    /**
     * Simple int variables to count numbers
     * for purposes of each varible
     */
    protected int totalOrder;
    protected int attempted;
    protected int sDelivered;
    protected int profit;
    /**
     * Constructor of AbstractPizzeria calss
     * to set the given menu as a menu of this store
     *
     * @param menu menu of this pizza store
     */
    public AbstractPizzeria(Set<Order> menu) {
        this.menu = menu;
    }

    /**
     * Tells the pizzeria to place an order with a Customer
     *
     * @param customer Customer who wants a pizza
     */
    public void placeOrder(Customer customer) {
        orderQueue.add(customer);
        totalOrder++;
    }

    /**
     * Returns the cheapest menu item based on price
     *
     * @return cheapest menu item
     */
    public Order getCheapestMenuItem() {
        int price = 0;
        Order cheapMenu = new Order();

        for (Order i : this.menu) {
            if (price == 0) {
                price = i.getPrice();
                cheapMenu = i;
            } else if (price > i.getPrice()) {
                price = i.getPrice();
                cheapMenu = i;
            }
        }
        return cheapMenu;
    }

    /**
     * Returns the most expensive menu item based on price
     *
     * @return most expensive menu item
     */
    public Order getMostExpensiveMenuItem() {
        int price = 0;
        Order expenMenu = new Order();

        for (Order i : this.menu) {
            if (price == 0) {
                price = i.getPrice();
                expenMenu = i;
            } else if (price < i.getPrice()) {
                price = i.getPrice();
                expenMenu = i;
            }
        }
        return expenMenu;
    }


    /**
     * Status message of a Pizzeria's performance
     * Should say what percentage of orders have been delivered
     * and what percentage of attempted orders have been delivered
     * along with total revenues
     *
     * @return Message string
     */
    public String status() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String format = currencyFormat.format(profit);
        return "- We delivered "
                + (int) Math.rint((sDelivered * 100.0) / totalOrder)
                + " %"
                + " of our orders! We delivered "
                + (int) Math.rint((sDelivered * 100.0) / attempted) + " %"
                + " of our attempted orders and made "
                + format;
    }

    /**
     * Pizzeria name
     *
     * @return name
     */
    public abstract String getName();


    /**
     * Attempts to deliver an order to a customer.
     * If the customer placed an order that's not on the menu,
     * we don't deliver. If the customer placed an order that is
     * on the menu but he doesn't have enough money to pay,
     * we don't deliver. Otherwise, we deliver to customer
     * and collect our money.
     */
    public void processOrder() {

        Customer i = orderQueue.remove();
        attempted++;
        if (menu.contains(i.getOrder())) {
            if (i.getOrder().getPrice() <= i.getMoney()) {
                profit += i.getOrder().getPrice();
                sDelivered++;
            }
        }
    }
}