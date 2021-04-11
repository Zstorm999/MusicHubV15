package utils.error;

/**
 *
 * @author CLEMENT Aimeric
 */
public class TooManyMatchingElementsException extends Exception{

    /**
     * Constructs an instance of <code>tooManyMatchingElements</code>
     * @param info
     */
    public TooManyMatchingElementsException(String info){
        super(info);
    }
}
