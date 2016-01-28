/**
 * This class does all math by using vector and matrix
 * @author Jeongsoo Kim
 * @version 1.0
 */

public class LinearAlgebra {
    /**
     * Do multiplication matrix and vector
     * @param m object of matrix
     * @param v object of v
     * @return new vector multiplication of matrix and vector
     * @throws IllegalOperandException if the number of
     *                                 column of matrix and number of
     *                                 row of vector are not matched.
     */

    public static Vector matrixVectorMultiply(Matrix m,
        Vector v) throws IllegalOperandException {

        if (m.getWidth() != v.getLength()) {
            throw new IllegalOperandException("# of column(s) "
                        + "of Matrix and # of row(s) "
                        + "of vector are not mateched!");
        }
        double[] result = new double[m.getHeight()];

        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++) {
                result[i] += m.get(i, j) * v.get(j);
            }
        }
        return new Vector(result);
    }

    /**
     * Do addiction between matrix and matrix
     * @param m1 object of matrix
     * @param m2 object of matrix
     * @return new matrix addiction of matrix and matrix
     * @throws IllegalOperandException if the number of
     *                                 column of the two matrixes
     *                                 are not matched.
     */
    public static Matrix matrixAdd(Matrix m1,
                Matrix m2) throws IllegalOperandException {

        if (m1.getWidth() != m2.getWidth()) {
            throw new IllegalOperandException("#s of column(s) "
                        + "of the two Matrixs are not mateched!");

        }
        double[][] result = new double[m1.getHeight()][m1.getWidth()];

        for (int i = 0; i < m1.getHeight(); i++) {
            for (int j = 0; j < m1.getWidth(); j++) {
                result[i][j] = m1.get(i, j) + m2.get(i, j);
            }
        }

        return new Matrix(result);
    }

    /**
     * Do dotproduct between vector and vector
     * @param v1 object of vector
     * @param v2 object of vector
     * @return result double number of dotproduct
     *                of vector and vector
     * @throws IllegalOperandException if the number of
     *                                 row of the two vectors are not matched.
     */
    public static double dotProduct(Vector v1,
                Vector v2) throws IllegalOperandException {

        if (v1.getLength() != v2.getLength()) {
            throw new IllegalOperandException("# of component(s) "
                        + "of the two vectors are not mateched!");
        }
        double result = 0;

        for (int i = 0; i < v1.getLength(); i++) {
            result += v1.get(i) * v2.get(i);
        }
        return result;
    }

    /**
     * Do addiction between vector and vector
     * @param v1 object of vector
     * @param v2 object of vector
     * @return new vector addiction of vector and vector
     * @throws IllegalOperandException if the number of
     *                                 row of the two vectors are not matched.
     */
    public static Vector vectorAdd(Vector v1,
                Vector v2) throws IllegalOperandException {

        if (v1.getLength() != v2.getLength()) {
            throw new IllegalOperandException("# of component(s)"
                        + " of the two vectors are not mateched!");
        }
        double[] result = new double[v1.getLength()];

        for (int i = 0; i < v1.getLength(); i++) {
            result[i] = v1.get(i) + v2.get(i);
        }
        return new Vector(result);
    }
}