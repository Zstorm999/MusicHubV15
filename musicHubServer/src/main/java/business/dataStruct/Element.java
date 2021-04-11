package business.dataStruct;

/**
 * This abstract class is the ancessor of everything in the package <code>business.dataStruct.elements</code>,
 * since every single elements are sharing 2 common atributes: an id and a title
 *
 * @author DESCHAMPS Aymeric
 */
public abstract class Element{
    
    protected final int id;
    protected String title;
    
    private static final int TABALIGMENTSHIFT= 56;
    private static final int TABALIGNMENTSHIFTLARGE= 75;

    public Element(int id, String title){
        this.id= id;
        this.title= title;
    }

    public int getId(){return this.id; }
    public String getTitle(){return this.title; }
    
    protected static String alignmentFarShift(String precedingText, Boolean large){
        String shift= "";
        for(int i= 0; i<((large)?Element.TABALIGNMENTSHIFTLARGE:Element.TABALIGMENTSHIFT)-precedingText.length(); i++)
            shift+= " ";
        for(int i= shift.length(); i<4; i++)
            shift+= " ";
        return shift;
    }
}
