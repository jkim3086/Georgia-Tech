/**
 * This class represents a custom
 * exception, VectorIndexOutOfBoundsException
 * which is extended IndexOutOfBoundsException
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class VectorIndexOutOfBoundsException extends IndexOutOfBoundsException {

    /**
     * Send a fixed error message to parent class
     */
    public VectorIndexOutOfBoundsException() {
        super("Your vector has indexoutofboundsexcetion!");
    }

    /**
     * Send a given message to parent class
     * @param message custom error message
     */
    public VectorIndexOutOfBoundsException(String message) {
        super(message);
    }
}