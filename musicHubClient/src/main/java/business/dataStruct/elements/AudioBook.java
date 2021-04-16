package business.dataStruct.elements;

import business.dataStruct.Element;
import business.dataStruct.MusicalElement;
import business.dataStruct.enums.Category;
import business.dataStruct.enums.Language;
import utils.TimeFormat;

/**
 * 
 * @author DESCHAMPS Aymeric
 */
public class AudioBook extends MusicalElement implements Comparable{
    
    private final String author;
    private final Category category;
    private final Language language;
    
    public AudioBook(int id, String title, int length, String content, String author, Category category, Language language){
        super (id, title, length, content);
        this.author = author;
        this.category = category;
        this.language = language;
    }
    
    public String getAuthor(){return this.author; }
    public Category getCategory(){return this.category; }
    public Language getLanguage(){return this.language; }
    
    @Override
    public int compareTo(Object otherAdBook){ return this.author.compareTo(((AudioBook)otherAdBook).author); }

    @Override
    public String toString(){
        return String.valueOf(this.id)+". "+
                "\""+this.title+"\" by "+this.author+", "+
                this.category.toString()+" lang:"+
                this.language.toString()+
                Element.alignmentFarShift(String.valueOf(this.id)+". "+
                        "\""+this.title+"\" by "+this.author+", "+
                        this.category.toString()+" lang:"+
                        this.language.toString(), true)+ 
                TimeFormat.secToString(this.length)+
                " - "+this.content;
    }
}
