import java.util.HashSet;
/**
 * Creat a set of ingredient as an order
 *
 * @author Jeongsoo
 * @version 1.0
 */
public class Order extends HashSet<Ingredient> {
    /**
     * Sum all prices of ingredients in a order
     *
     * @return Price a price of a order
     */
    public int getPrice() {
        int price = 0;
        for (Ingredient i : this) {
            price += i.getPrice();
        }
        return price;
    }
}