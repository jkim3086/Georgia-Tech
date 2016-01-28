import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

/**
 * Simulation Driver.
 *
 * @author Gustavo Hidalgo
 * @version 1.2
 */
public class Driver {

    private static ArrayList<Customer> loyalCustomers = new ArrayList<>();
    private static Pizzeria pizzeria1;
    private static Pizzeria pizzeria2;
    private static Pizzeria pizzeria3;
    //only use this Random object so that everything is deterministic
    public static final Random RANDOM = new Random(10);


    //play with this parameter
    private static int probableMenuLength = 50;

    public static void main(String[] args) {
        int size = 1000; //bigger values give better results
        Set<Order> menu = generateMenu();
        pizzeria1 = new CascadePizza(menu);
        pizzeria2 = new MarySuePizza(menu);
        pizzeria3 = new PizzaShack(menu);
        generateCustomers(size);
        for (Customer c : loyalCustomers) {
            pizzeria1.placeOrder(c);
            pizzeria2.placeOrder(c);
            pizzeria3.placeOrder(c);
        }
        int end = RANDOM.nextInt(loyalCustomers.size());
        for (int i = 0; i < end; i++) {
            pizzeria1.processOrder();
            pizzeria2.processOrder();
            pizzeria3.processOrder();
        }
        System.out.println("Delivered to " + end + " customers when...");
        System.out.println("Midnight!!!");
        System.out.println(pizzeria1.getName() + "\n - " + pizzeria1.status());
        System.out.println(pizzeria2.getName() + "\n - " + pizzeria2.status());
        System.out.println(pizzeria3.getName() + "\n - " + pizzeria3.status());
    }

    /**
     * Generates n customers who will place the same order to all 3 pizzerias.
     * Pizzeria order deliveries do not interfere with each other because
     * customers are immutable.
     *
     * @param n number of customers to generate
     */
    private static void generateCustomers(int n) {
        for (int i = 0; i < n; i++) {
            Customer newCustomer = new Customer(generateOrder(pizzeria1));
            loyalCustomers.add(newCustomer);
        }
    }

    /**
     * Dynamically generates a menu for use with the pizzerias
     *
     * @return A set of menu items.
     */
    private static Set<Order> generateMenu() {
        Set<Order> menu = new HashSet<>();
        for (int i = 0; i < probableMenuLength; i++) {
            Order menuItem = new Order();
            for (int j = 0; j < Ingredient.values().length; j++) {
                if (RANDOM.nextBoolean()) {
                    menuItem.add(Ingredient.values()[j]);
                }
            }
            if (menu.isEmpty()) {
                menuItem.add(Ingredient.CHEESE); //gotta at least have cheese
            }
            menu.add(menuItem);
        }
        return menu;
    }


    /**
     * Generates a RANDOM order. There are some specific possibilities
     * 30% chance of cheapest menu item. broke college life.
     * 10% chance of most expensive menu item. living big.
     * 60% chance of RANDOM order from the Ingredients enum.
     *
     * @param p pizzeria used to generate orders. For our purposes,
     *          it doesn't matter which one is chosen all three
     *          share the same menu.
     * @return A RANDOM Order
     */
    public static Order generateOrder(Pizzeria p) {
        int choice = RANDOM.nextInt(10);
        if (choice < 2) {
            return p.getCheapestMenuItem();
        } else if (choice < 3) {
            return p.getMostExpensiveMenuItem();
        } else {
            Ingredient[] ingredients = Ingredient.values();
            Order ingredientList = new Order();
            for (int i = 0; i < ingredients.length; i++) {
                if (RANDOM.nextBoolean()) {
                    ingredientList.add(ingredients[i]);
                }
            }
            return ingredientList;
        }
    }
}
