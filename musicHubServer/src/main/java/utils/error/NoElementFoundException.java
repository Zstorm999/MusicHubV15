package utils.error;

/**
 *
 * @author CLEMENT Aimeric
 */
public class NoElementFoundException extends Exception{

    /**
     * Constructs an instance of <code>NoElementFound</code>
     * @param info
     */
    public NoElementFoundException(String info){
        super(info);
    }
}
