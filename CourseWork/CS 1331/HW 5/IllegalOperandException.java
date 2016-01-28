/**
 * This class represents a custom
 * exception, Illegaloperandexception
 * which is extended Exception
 * @author Jeongsoo Kim
 * @version 1.0
 */
public class IllegalOperandException extends Exception {

    /**
     * Send a fixed error message to parent class
     */
    public IllegalOperandException() {
        super("Illegal Operand!!!");
    }

    /**
     * Send a given message to parent class
     * @param message custom error message
     */
    public IllegalOperandException(String message) {
        super(message);
    }
}