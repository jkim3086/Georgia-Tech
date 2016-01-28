import java.util.Random;
import java.util.Scanner;

/**
 * Your Pokemon journey begins here!
 *
 * @author Joe Rossi
 * @version 1.0
 */
public class KantoExpedition {

    private static Scanner in;
    private static PokemonPopulation popPop;
    private static Random rand;
    private static Pokedex dex;

    /**
     * Main method for KantoExpedition. Allows the user to find new
     * Pokemon using the Walk around option, check their Pokedex,
     * report to Professor Oak, start their journey over,
     * or quit the program.
     *
     * @param args command-line arguments for the program.
     */
    public static void main(String[] args) {
        in = new Scanner(System.in);
        popPop = new PokemonPopulation();
        rand = new Random();
        dex = new Pokedex();

        boolean running = true;
        System.out.println("Welcome to the world of Pokemon!\n");
        while (running) {
            System.out.println("What do you want to do?");
            System.out.println("1: Walk around");
            System.out.println("2: Check Pokedex");
            System.out.println("3: Report to Professor Oak");
            System.out.println("4: Start over");
            System.out.println("5: Give up...");
            int choice = 0;
            try {
                choice = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }
            if (choice == 1) {
                walkAround();
            } else if (choice == 2) {
                checkDex();
            } else if (choice == 3) {
                reportToProfessorOak(dex.countPokemon());
            } else if (choice == 4) {
                System.out.println("Clearing Pokedex...");
                dex.clear();
                System.out.println("Oak is not going to be happy about this\n");
            } else if (choice == 5) {
                running = false;
            } else {
                System.out.println("\nBill's PC does not "
                        + "recognize that input\n");
            }
        }
    }

    private static void walkAround() {
        boolean b = true;
        while (b) {
            System.out.println("You wander around for a bit");
            try {
                String dots = ".";
                System.out.println(dots);
                int ticks = 0;
                while (rand.nextInt(100) >= 10 && ticks < 20) {
                    Thread.sleep(0);
                    dots += ".";
                    System.out.println(dots);
                    ticks++;
                }
                Pokemon p = popPop.get();
                System.out.println("Suddenly, a wild "
                    + p.getName() + " appeared!");
                if (p.getNumber() == 138 || p.getNumber() == 139) {
                    System.out.println("Praise be Lord Helix!");
                } else if (p.getNumber() == 63) {
                    System.out.println(p.getName() + " used Teleport!");
                }
                if (dex.add(p)) {
                    System.out.println("A new Pokedex entry was made for "
                        + p.getName() + "!\n");
                } else {
                    System.out.println(
                        "But you've seen " + p.getName() + " before...\n");
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.print("Continue? (type n to stop walking):");
            if (in.nextLine().equals("n")) {
                b = false;
            }
        }
    }

    private static void checkDex() {
        System.out.println("What do you want to do?");
        System.out.println("1: List all (by number)");
        System.out.println("2: List all (alphabetically)");
        System.out.println("3: Group by Primary Type");
        System.out.println("4: List all of a single Type");
        System.out.println("5: List by Range of Numbers");
        int choice = 0;
        try {
            choice = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            choice = -1;
        }
        if (choice == 1) {
            System.out.println(dex);
        } else if (choice == 2) {
            System.out.println(dex.listAlphabetically());
        } else if (choice == 3) {
            System.out.println(dex.groupByPrimaryType());
        } else if (choice == 4) {
            listByType();
        } else if (choice == 5) {
            listByRange();
        } else {
            System.out.println("Bill's PC does not recognize that input");
        }
    }

    private static void listByType() {
        System.out.println("Enter a type "
                + "(or 'help' for a list of valid types)");
        String s = in.nextLine();
        System.out.println();
        PokemonType[] types = PokemonType.values();
        PokemonType type = null;
        if (s.equalsIgnoreCase("help")) {
            for (PokemonType pt : types) {
                System.out.println(pt);
            }
            System.out.println("\nEnter a type from the above list:");
            s = in.nextLine();
        }
        for (int i = 0; i < types.length && type == null; i++) {
            if (types[i].toString().equalsIgnoreCase(s)) {
                type = types[i];
            }
        }
        if (type == null) {
            System.out.println("Bill's PC does not recognize that type");
        } else {
            System.out.println(dex.listByType(type));
        }
    }

    private static void listByRange() {
        int start = -1;
        System.out.print("Enter the start number: ");
        while (start < 0) {
            try {
                start = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid positive integer");
            }
        }
        int end = -1;
        System.out.println("Enter the end number: ");
        while (end < 0) {
            try {
                end = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid positive integer");
            }
        }

        if (start > end) {
            System.out.println("That doesn't make any sense!");
        } else {
            System.out.println(dex.listRange(start, end));
        }
    }

    private static void reportToProfessorOak(int count) {
        System.out.println("PROF OAK: You've found " + count + " Pokemon!");
        if (count < 50) {
            System.out.println("Get back to work!");
        } else if (count < 100) {
            System.out.println("Not bad!");
        } else if (count == 151) {
            System.out.println("Wow! You've found 'em all! Now on to Johto!");
        }
        System.out.println();
    }
}
