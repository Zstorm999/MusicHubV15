package business.dataStruct.elements;

import business.dataStruct.Element;
import business.dataStruct.MusicalElement;
import business.dataStruct.enums.Genre;
import utils.TimeFormat;

/**
 * 
 * @author DESCHAMPS Aymeric
 */
public class Song extends MusicalElement implements Comparable{
    
    private final String artist;
    private final Genre genre;
    
    public Song(int id, String title, int length, String content, String artist, Genre genre){
        super(id, title, length, content);
        this.artist = artist;
        this.genre = genre;
    }
    
    public String getArtist(){return this.artist; }
    public Genre getGenre(){return this.genre; }
    
    @Override
    public int compareTo(Object otherSong){
        return this.genre.compareTo(((Song)otherSong).genre);
    }
    
    @Override
    public String toString(){
        return String.valueOf(this.id)+". "+
                "\""+this.title+
                "\" by "+this.artist+
                ", style "+this.genre.toString()+
                Element.alignmentFarShift(
                        String.valueOf(this.id)+". "+"\""+this.title+"\" by "+this.artist+", style "+this.genre.toString(), true)+
                TimeFormat.secToString(this.length)+
                " - "+this.content;
    }
}
