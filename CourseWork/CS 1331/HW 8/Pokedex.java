/**
 * Represents a Pokedex - basically a Pokemon encyclopedia that adds new entries
 * when you encounter a Pokemon for the first time.
 * It also provides methods for organizing its information in useful ways.
 *
 * @author Joe Rossi
 * @version 1.0
 */
public class Pokedex {

    // ------ Instance data here ------
    private MySortedSet<Pokemon> pSet;

    /**
     * Constructs a Pokedex object by setting up the sorted set of Pokemon
     */
    public Pokedex() {
        pSet = new MySortedSet<Pokemon>();
    }

    @Override
    public String toString() {
        String temp = "";
        for (Pokemon p : pSet) {
            temp += p.toString();
            temp += "\n";
        }
        return temp;
    }

    /**
     * Adds a Pokemon to the sorted set
     *
     * @param p the Pokemon to be added
     * @return true if the pokemon was not already in the set, false otherwise
     */
    public boolean add(Pokemon p) {
        return pSet.add(p);
    }

    /**
     * Returns the number of Pokemon in the Pokedex
     *
     * @return  the number of Pokemon in the Pokedex
     */
    public int countPokemon() {
        return pSet.size();
    }

    /**
     * Clear the Pokedex and start over
     */
    public void clear() {
        pSet.clear();
    }

    /**
     * Returns a set of alphabetized Pokemon, using a lambda expression
     *
     * @return  the alphabetized set
     */
    public MySortedSet<Pokemon> listAlphabetically() {
        return pSet.sort((Pokemon p1, Pokemon p2)
            -> p1.getName().compareTo(p2.getName()));
    }
    /**
     * Returns a set of Pokemon grouped by type, using a lambda expression
     *
     * @return  the grouped by primary type set
     */
    public MySortedSet<Pokemon> groupByPrimaryType() {
        return pSet.sort((Pokemon p1, Pokemon p2)
            -> p1.getPrimaryType().compareTo(p2.getPrimaryType()));

    }

    /**
     * Returns a set of all Pokemon of type t
     *
     * @param t the type we want listed
     * @return  the set of all Pokemon in the Pokedex of type t
     */
    public MySortedSet<Pokemon> listByType(PokemonType t) {
        return pSet.filter((Pokemon p1)
            -> (t.equals(p1.getPrimaryType())
            || t.equals(p1.getSecondaryType())));

    }

    /**
     * Returns a set of Pokemon with numbers in the range [start, end]
     *
     * @param start the first number in the new set
     * @param end   the last number in the new set
     * @return  the set containing all Pokemon in the Pokedex from [start, end]
     */
    public MySortedSet<Pokemon> listRange(int start, int end) {
        return pSet.filter((Pokemon p1)
            -> (start <= p1.getNumber()
            && p1.getNumber() <= end));
    }
}
