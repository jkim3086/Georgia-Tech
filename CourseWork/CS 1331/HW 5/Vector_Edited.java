/**
 * Immutable abstraction for Vector
 *
 * @author Michael Maurer
 * @version 1.2
 */
public class Vector {

    /*
    Create final instance variables
    */
    /**
     * double type of array, which
     * is used as a vector in this class
     */
    private final double[] vector;
    /**
     * number of row of the vector
     */
    private final int length;
    /**
     * Initialize instance variables
     * @param vector array representation of vector
     */
    public Vector(double[] vector) {
        this.length = vector.length;
        this.vector = new double[this.length];

        for (int i = 0; i < this.length; i++) {
            this.vector[i] = vector[i];
        }
    }

    /**
     * Gets value located at specified index
     * @param i index in vector
     * @return double located at index 'i' in vector
     */
    public double get(int i) {

        if (getLength() < i) {
            throw new VectorIndexOutOfBoundsException();
        }
        return vector[i];
    }

    /**
     * Get's the length of the Vector.
     * @return number of components in vector
     */
    public int getLength() {
        return this.vector.length;
    }

    /**
     * String representation of vector with components
     * separated by tabs
     * @return String representation of vector
     */
    public String toString() {
        String print = "";

        for (int i = 0; i < this.vector.length; i++) {
            print += this.vector[i];
            print += "  ";
        }
        return print;
    }
}