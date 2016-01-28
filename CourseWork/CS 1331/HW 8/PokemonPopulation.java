import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the entire Pokemon population in the Kanto region and generates
 * one at random (each with equal probability).
 *
 * @author  Justin Nieto, Joe Rossi
 * @version Generation 1 (red/blue/yellow)
 */
public class PokemonPopulation {
    private ArrayList<Pokemon> population;
    private Random rand;

    /**
     * Creates a PokemonPopulation, adding all 151 to an ArrayList
     */
    public PokemonPopulation() {
        population = new ArrayList<Pokemon>(151);
        populate1();
        populate2();
        populate3();
        rand = new Random();
    }

    /**
     * Gets a random Pokemon from this population, each with equal probability.
     * Yes this means you are equally likely to see Mew as you are a Pidgey...
     *
     * @return  a random Pokemon
     */
    public Pokemon get() {
        return population.get(rand.nextInt(population.size()));
    }

    /**
     * Add all the Pokemon...
     */
    private void populate1() {

        //#001-#051 Bulbasaur-Dugrtio
        population.add(new Pokemon(1, "Bulbasaur", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(2, "Ivysaur", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(3, "Venusaur", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(4, "Charmander", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(5, "Charmeleon", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(6, "Charizard", PokemonType.FIRE,
            PokemonType.FLYING));
        population.add(new Pokemon(7, "Squirtle", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(8, "Wartortle", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(9, "Blastoise", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(10, "Caterpie", PokemonType.BUG,
            PokemonType.NONE));
        population.add(new Pokemon(11, "Metapod", PokemonType.BUG,
            PokemonType.NONE));
        population.add(new Pokemon(12, "Butterfree", PokemonType.BUG,
            PokemonType.FLYING));
        population.add(new Pokemon(13, "Weedle", PokemonType.BUG,
            PokemonType.POISON));
        population.add(new Pokemon(14, "Kakuna", PokemonType.BUG,
            PokemonType.POISON));
        population.add(new Pokemon(15, "Beedrill", PokemonType.BUG,
            PokemonType.POISON));
        population.add(new Pokemon(16, "Pidgey", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(17, "Pidgeotto", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(18, "Pidgeot", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(19, "Rattata", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(20, "Raticate", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(21, "Spearow", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(22, "Fearow", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(23, "Ekans", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(24, "Arbok", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(25, "Pikachu", PokemonType.ELECTRIC,
            PokemonType.NONE));
        population.add(new Pokemon(26, "Raichu", PokemonType.ELECTRIC,
            PokemonType.NONE));
        population.add(new Pokemon(27, "Sandshrew", PokemonType.GROUND,
            PokemonType.NONE));
        population.add(new Pokemon(28, "Sandslash", PokemonType.GROUND,
            PokemonType.NONE));
        population.add(new Pokemon(29, "Nidoran (F)", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(30, "Nidorina", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(31, "Nidoqueen", PokemonType.POISON,
            PokemonType.GROUND));
        population.add(new Pokemon(32, "Nidoran (M)", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(33, "Nidorino", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(34, "Nidoking", PokemonType.POISON,
            PokemonType.GROUND));
        population.add(new Pokemon(35, "Clefairy", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(36, "Clefable", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(37, "Vulpix", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(38, "Jigglypuff", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(39, "Wigglytuff", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(40, "Zubat", PokemonType.POISON,
            PokemonType.FLYING));
        population.add(new Pokemon(41, "Golbat", PokemonType.POISON,
            PokemonType.FLYING));
        population.add(new Pokemon(42, "Oddish", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(43, "Gloom", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(44, "Vileplume", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(45, "Paras", PokemonType.BUG,
            PokemonType.GRASS));
        population.add(new Pokemon(46, "Parasect", PokemonType.BUG,
            PokemonType.GRASS));
        population.add(new Pokemon(47, "Venonat", PokemonType.BUG,
            PokemonType.POISON));
        population.add(new Pokemon(48, "Venomoth", PokemonType.BUG,
            PokemonType.POISON));
        population.add(new Pokemon(49, "Weedle", PokemonType.BUG,
            PokemonType.POISON));
        population.add(new Pokemon(50, "Diglett", PokemonType.GROUND,
            PokemonType.NONE));
        population.add(new Pokemon(51, "Dugtrio", PokemonType.GROUND,
            PokemonType.NONE));
    }
    private void populate2() {
        //#052-#101 Meowth-Electrode
        population.add(new Pokemon(52, "Meowth", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(53, "Persian", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(54, "Psyduck", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(55, "Golduck", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(56, "Mankey", PokemonType.FIGHTING,
            PokemonType.NONE));
        population.add(new Pokemon(57, "Primeape", PokemonType.FIGHTING,
            PokemonType.NONE));
        population.add(new Pokemon(58, "Growlithe", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(59, "Arcanine", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(60, "Poliwag", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(61, "Poliwhirl", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(62, "Poliwrath", PokemonType.WATER,
            PokemonType.FIGHTING));
        population.add(new Pokemon(63, "Abra", PokemonType.PSYCHIC,
            PokemonType.NONE));
        population.add(new Pokemon(64, "Kadabra", PokemonType.PSYCHIC,
            PokemonType.NONE));
        population.add(new Pokemon(65, "Alakazam", PokemonType.PSYCHIC,
            PokemonType.NONE));
        population.add(new Pokemon(66, "Machop", PokemonType.FIGHTING,
            PokemonType.NONE));
        population.add(new Pokemon(67, "Machoke", PokemonType.FIGHTING,
            PokemonType.NONE));
        population.add(new Pokemon(68, "Machamp", PokemonType.FIGHTING,
            PokemonType.NONE));
        population.add(new Pokemon(69, "Bellsprout", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(70, "Weepinbell", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(71, "Victreebel", PokemonType.GRASS,
            PokemonType.POISON));
        population.add(new Pokemon(72, "Tentacool", PokemonType.WATER,
            PokemonType.POISON));
        population.add(new Pokemon(73, "Tentacruel", PokemonType.WATER,
            PokemonType.POISON));
        population.add(new Pokemon(74, "Geodude", PokemonType.ROCK,
            PokemonType.GROUND));
        population.add(new Pokemon(75, "Graveler", PokemonType.ROCK,
            PokemonType.GROUND));
        population.add(new Pokemon(76, "Golem", PokemonType.ROCK,
            PokemonType.GROUND));
        population.add(new Pokemon(77, "Ponyta", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(78, "Rapidash", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(79, "Slowpoke", PokemonType.WATER,
            PokemonType.PSYCHIC));
        population.add(new Pokemon(80, "Slowbro", PokemonType.WATER,
            PokemonType.PSYCHIC));
        population.add(new Pokemon(81, "Magnemite", PokemonType.ELECTRIC,
            PokemonType.NONE));
        population.add(new Pokemon(82, "Magneton", PokemonType.ELECTRIC,
            PokemonType.NONE));
        population.add(new Pokemon(83, "Farfetch'd", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(84, "Doduo", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(85, "Dodrio", PokemonType.NORMAL,
            PokemonType.FLYING));
        population.add(new Pokemon(86, "Seel", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(87, "Dewgong", PokemonType.WATER,
            PokemonType.ICE));
        population.add(new Pokemon(88, "Grimer", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(89, "Muk", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(90, "Shellder", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(91, "Cloyster", PokemonType.WATER,
            PokemonType.ICE));
        population.add(new Pokemon(92, "Gastly", PokemonType.GHOST,
            PokemonType.POISON));
        population.add(new Pokemon(93, "Haunter", PokemonType.GHOST,
            PokemonType.POISON));
        population.add(new Pokemon(94, "Gengar", PokemonType.GHOST,
            PokemonType.POISON));
        population.add(new Pokemon(95, "Onix", PokemonType.ROCK,
            PokemonType.GROUND));
        population.add(new Pokemon(96, "Drowzee", PokemonType.PSYCHIC,
            PokemonType.NONE));
        population.add(new Pokemon(97, "Hypno", PokemonType.PSYCHIC,
            PokemonType.NONE));
        population.add(new Pokemon(98, "Krabby", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(99, "Kingler", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(100, "Voltorb", PokemonType.ELECTRIC,
            PokemonType.NONE));
        population.add(new Pokemon(101, "Electrode",
            PokemonType.ELECTRIC, PokemonType.NONE));
    }

    private void populate3() {
        //#102-#151 Exeggcute-Mew
        population.add(new Pokemon(102, "Exeggcute", PokemonType.GRASS,
            PokemonType.PSYCHIC));
        population.add(new Pokemon(103, "Exeggutor", PokemonType.GRASS,
            PokemonType.PSYCHIC));
        population.add(new Pokemon(104, "Cubone", PokemonType.GROUND,
            PokemonType.NONE));
        population.add(new Pokemon(105, "Marowak", PokemonType.GROUND,
            PokemonType.NONE));
        population.add(new Pokemon(106, "Hitmonlee",
            PokemonType.FIGHTING, PokemonType.NONE));
        population.add(new Pokemon(107, "Hitmonchan",
            PokemonType.FIGHTING, PokemonType.NONE));
        population.add(new Pokemon(108, "Lickitung", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(109, "Koffing", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(110, "Weezing", PokemonType.POISON,
            PokemonType.NONE));
        population.add(new Pokemon(111, "Ryhorn", PokemonType.GROUND,
            PokemonType.ROCK));
        population.add(new Pokemon(112, "Rydon", PokemonType.GROUND,
            PokemonType.ROCK));
        population.add(new Pokemon(113, "Chansey", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(114, "Tangela", PokemonType.GRASS,
            PokemonType.NONE));
        population.add(new Pokemon(115, "Kangaskhan", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(116, "Horsea", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(117, "Seadra", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(118, "Goldeen", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(119, "Seaking", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(120, "Staryu", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(121, "Starmie", PokemonType.WATER,
            PokemonType.PSYCHIC));
        population.add(new Pokemon(122, "Mr. Mime", PokemonType.PSYCHIC,
            PokemonType.NONE));
        population.add(new Pokemon(123, "Scyther", PokemonType.BUG,
            PokemonType.FLYING));
        population.add(new Pokemon(124, "Jynx", PokemonType.ICE,
            PokemonType.PSYCHIC));
        population.add(new Pokemon(125, "Electabuzz",
            PokemonType.ELECTRIC, PokemonType.NONE));
        population.add(new Pokemon(126, "Magmar", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(127, "Pinsir", PokemonType.BUG,
            PokemonType.NONE));
        population.add(new Pokemon(128, "Tauros", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(129, "Magikarp", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(130, "Gyarados", PokemonType.WATER,
            PokemonType.FLYING));
        population.add(new Pokemon(131, "Lapras", PokemonType.WATER,
            PokemonType.ICE));
        population.add(new Pokemon(132, "Ditto", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(133, "Eevee", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(134, "Vaporeon", PokemonType.WATER,
            PokemonType.NONE));
        population.add(new Pokemon(135, "Jolteon", PokemonType.ELECTRIC,
            PokemonType.NONE));
        population.add(new Pokemon(136, "Flareon", PokemonType.FIRE,
            PokemonType.NONE));
        population.add(new Pokemon(137, "Porygon", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(138, "Omanyte", PokemonType.ROCK,
            PokemonType.WATER));
        population.add(new Pokemon(139, "Omastar", PokemonType.ROCK,
            PokemonType.WATER));
        population.add(new Pokemon(140, "Kabuto", PokemonType.ROCK,
            PokemonType.WATER));
        population.add(new Pokemon(141, "Kabutops", PokemonType.ROCK,
            PokemonType.WATER));
        population.add(new Pokemon(142, "Aerodactyl", PokemonType.ROCK,
            PokemonType.FLYING));
        population.add(new Pokemon(143, "Snorlax", PokemonType.NORMAL,
            PokemonType.NONE));
        population.add(new Pokemon(144, "Articuno", PokemonType.ICE,
            PokemonType.FLYING));
        population.add(new Pokemon(145, "Zapdos", PokemonType.ELECTRIC,
            PokemonType.FLYING));
        population.add(new Pokemon(146, "Moltres", PokemonType.FIRE,
            PokemonType.FLYING));
        population.add(new Pokemon(147, "Dratini", PokemonType.DRAGON,
            PokemonType.NONE));
        population.add(new Pokemon(148, "Dragonair", PokemonType.DRAGON,
            PokemonType.NONE));
        population.add(new Pokemon(149, "Dragonite", PokemonType.DRAGON,
            PokemonType.FLYING));
        population.add(new Pokemon(150, "Mewtwo", PokemonType.PSYCHIC,
            PokemonType.NONE));
        population.add(new Pokemon(151, "Mew", PokemonType.PSYCHIC,
            PokemonType.NONE));
    }
}
