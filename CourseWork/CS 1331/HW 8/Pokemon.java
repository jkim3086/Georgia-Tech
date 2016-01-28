/**
 * Represents a Pokemon object. Each has a number, a name, and two elemental
 * types, chosen from the PokemonType enumeration.
 *
 * @author  Joe Rossi
 * @version 1.0
 */
public class Pokemon implements Comparable<Pokemon> {

    // ------Instance data here------
    private final int uniNum;
    private final String name;
    private final PokemonType pType1;
    private final PokemonType pType2;
    /**
     * Constructs a Pokemon object
     *
     * @param num   this Pokemon's unique number
     * @param name  this Pokemon's name
     * @param p this Pokemon's primary type
     * @param s this Pokemon's secondary type
     */
    public Pokemon(int num, String name, PokemonType p, PokemonType s) {
        uniNum = num;
        this.name = name;
        pType1 = p;
        pType2 = s;
    }
    /**
     * Compare pokemon's unique number
     *
     * @param o an object of pokemon
     * @return difference of the tweo pokemons' unique number
     */
    @Override
    public int compareTo(Pokemon o) {
        return this.uniNum - o.uniNum;
    }
    /**
     * Checking two unique numbers are equal or not
     *
     * @param o an object of pokemon
     * @return true if the two unique numbers are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Pokemon) {
            return compareTo((Pokemon) o) == 0;
        }
        return false;
    }
    /**
     * Get pokemon's hashcode
     *
     * @return pokemon's unique number as a hashcode
     */
    @Override
    public int hashCode() {
        return this.uniNum;
    }
    /**
     * Make a string with pokemon's information
     *
     * @return string a string include pokemon's information
     */
    @Override
    public String toString() {
        String temp = "";
        temp = String.format("# %3d: %10s\tPrimary"
            + " Type: %s\tSecondary Type: %s",
            uniNum, this.name, pType1, pType2);
        return temp;
    }

    /**
     * @return  the name of this Pokemon
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return  the unique number of this Pokemon
     */
    public int getNumber() {
        return this.uniNum;
    }

    /**
     * @return  the primary type of this Pokemon
     */
    public PokemonType getPrimaryType() {
        // TODO
        return this.pType1;
    }

    /**
     * @return  the secondary type of this Pokemon
     */
    public PokemonType getSecondaryType() {
        // TODO
        return this.pType2;
    }
}
