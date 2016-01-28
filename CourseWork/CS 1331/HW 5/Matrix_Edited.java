/**
 * Immutable abstraction of Matrix.
 *
 * @author Michael Maurer
 * @version 1.2
 */
public class Matrix {

    /*
     * Double type two dimensional array,
     * which is going to be used as a matrix
     * in this class
     */
    private final double[][] matrix;
    /*
     * Width, which is number of column of
     * the matrix
     */
    private final int width;
    /*
     * height, which is number of row of
     * the matrix
     */
    private final int height;

    /**
     * Initialize instance variables
     * @param matrix 2D array representation of Matrix
     */
    public Matrix(double[][] matrix) {
        this.height = matrix.length;
        this.width = matrix[0].length;
        this.matrix = new double[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    /**
     * Gets value located at specified row and column
     * @param i row
     * @param j column
     * @return double located at row i and column j in matrix
     */
    public double get(int i, int j) {

        if (getHeight() < i || getWidth() < j) {
            throw new MatrixIndexOutOfBoundsException();
        }
        return this.matrix[i][j];
    }

    /**
     * Get's the height of the matrix.
     * @return number of rows in matrix
     */
    public int getHeight() {
        return matrix.length;
    }

    /**
     * Get's the width of the matrix.
     * @return number of columns in matrix
     */
    public int getWidth() {
        return matrix[0].length;
    }

    /**
     * Gets String representation of matrix.
     * Columns separated by tabs, rows by new lines.
     * @return String representation of matrix.
     */
    public String toString() {
        String print = "";

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                print += this.matrix[i][j];
                print += "  ";
            }
            print += "\n";
        }

        return print;
    }
}