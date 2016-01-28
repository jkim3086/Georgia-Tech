/**
 * This class represents a custom
 * exception, MatrixIndexOutOfBoundsException
 * which is extended IndexOutOfBoundsException
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class MatrixIndexOutOfBoundsException extends IndexOutOfBoundsException {

    /**
     * Send a fixed error message to parent class
     */
    public MatrixIndexOutOfBoundsException() {
        super("Your vector has matrixindexoutofboundsexception!");
    }

    /**
     * Send a given message to parent class
     * @param message custom error message
     */
    public MatrixIndexOutOfBoundsException(String message) {
        super(message);
    }
}