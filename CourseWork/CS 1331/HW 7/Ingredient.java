/**
 * Enumeration of Ingredients
 * Price based on their position in the enum
 *
 * @author Gustavo
 * @version 1.1
 */
public enum Ingredient {

    CHEESE, ONIONS, PINEAPPLE, PEPPERONI, CHICKEN, BEEF, SAUSAGE;

    private int price;

    Ingredient() {
        price = 1 + ordinal();
    }

    public int getPrice() {
        return price;
    }
}
