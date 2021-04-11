package business.dataStruct.elements;

import business.dataStruct.Element;
import business.dataStruct.MusicalElement;

import java.util.ArrayList;

/**
 * 
 * @author DESCHAMPS Aymeric
 */
public class Playlist extends Element{

    private final ArrayList<MusicalElement> musicalElements;//TODO:Type array list a revoir

    public Playlist(int id, String name){
    super(id, name);
        musicalElements = new ArrayList<>();
    }

    public ArrayList<MusicalElement> getElements(){return musicalElements; }

    public void addElement(MusicalElement musicalElement){
        musicalElements.add(musicalElement);
    }

    @Override
    public String toString(){
        return String.valueOf(this.id)+". "+
                this.title+
                Element.alignmentFarShift(
                    String.valueOf(this.id)+". "+this.title, false
                )+
                "containing "+String.valueOf(this.musicalElements.size())+ " elements";
    }
}
