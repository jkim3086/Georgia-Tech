import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

/**
 * This is whats called a Delegate class.
 *
 * Scanner is final so we can't extend it.
 * (If we tried to override nextLine, we would
 * probably mess it up)
 * Instead, we create a scanner instance variable
 * and use it to ask for console input.
 *
 * @author Michael Maurer
 * @version 1.2
 */
public class LinearAlgebraScanner {

    private final Scanner scanner;

    /**
     * Creates delegate Scanner
     */
    public LinearAlgebraScanner() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * @return Scanner's next double.
     * @see Scanner#nextDouble()
     */
    public double nextDouble() {
        return scanner.nextDouble();
    }

    /**
     * @return Scanner's next int.
     * @see Scanner#nextInt()
     */
    public int nextInt() {
        return scanner.nextInt();
    }

    /**
     * @return Scanner's next String.
     * @see Scanner#nextLine()
     */
    public String nextLine() {
        return scanner.nextLine();
    }

    /**
     * Reads a vector from the console.
     * @return Vector created from user input.
     * @throws InputMismatchException if the vector is
     * not composed of valid doubles.
     */
    public Vector readVector() {
        String stringVector = scanner.nextLine();
        double[] vector = parseRow(stringVector);
        return new Vector(vector);
    }

    /**
     * Reads a matrix from the console. Must be a standard
     * m x n matrix. Terminate the matrix with an empty new
     * line.
     * @return Matrix created from user input
     * @throws InputMismatchException if the matrix is ragged
     * or not composed of valid doubles.
     */
    public Matrix readMatrix() {
        List<double[]> rowList = new ArrayList<double[]>();
        int width = -1;
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break; // We're done
            }
            double[] row = parseRow(line);
            if (width != row.length && width != -1) {
                throw new InputMismatchException("Row with " + row.length
                    + " elements cannot be applied to matrix with width "
                    + "of " + width + ".");
            }
            width = row.length;
            rowList.add(row);
        }
        return new Matrix(rowList.toArray(new double[rowList.size()][]));
    }

    /**
     * Parses a line as a row in a matrix or vector
     * @param row String version of row to be parsed.
     * @return Parsed version of row.
     * @throws InputMismatchException if row is not properly formatted.
     */
    private double[] parseRow(String row) {
        String[] asArray = row.split(" ");
        double[] result = new double[asArray.length];
        for (int i = 0; i < asArray.length; i++) {
            try {
                result[i] = Double.parseDouble(asArray[i]);
            } catch (NumberFormatException e) {
                throw new InputMismatchException("Could not parse \""
                    + asArray[i] + "\" as a double.");
            }
        }
        return result;
    }
}