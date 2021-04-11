package business.dataStruct;

/**
 * 
 * @author DESCHAMPS Aymeric
 */
public abstract class MusicalElement extends Element{

    protected int length;
    protected String content;

    public MusicalElement(int id, String title, int length, String content){
        super(id, title);
        this.length = length;
        this.content = content;
    }

    public int getLength(){return this.length; }
    public String getContent(){return this.content; }
}
