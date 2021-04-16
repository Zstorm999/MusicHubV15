package business.dataStruct.elements;

import business.dataStruct.Element;
import utils.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author DESCHAMPS Aymeric
 */
public class Album extends Element implements Comparable{

    public static final String DATEFORMAT= "dd/MM/yyyy";
    
    private final String artist;
    private final Date launchDate;
    private final ArrayList<Song> songs;//TODO:Type array list a revoir

    public Album(int id, String title, String artist, Date launchDate){
        super (id, title);
        this.artist= artist;
        this.launchDate= launchDate;
        songs= new ArrayList<>();
    }

    public String getArtist(){return this.artist; }
    public ArrayList<Song> getSongs(){return this.songs; }
    public Date getLaunchDate(){return this.launchDate; }
    public int getLength(){
        int length= 0;
        length = this.songs.stream().map(currSong -> currSong.getLength()).reduce(length, Integer::sum);
        return length;
    }
    
    /**
     * Will add a song to the album
     * @param song
     */
    public void addSong (Song song){
        songs.add(song);
    }

    /**
     * @param otherAlbum
     * @return the result of which one of the two is the oldest
     */
    @Override
    public int compareTo(Object otherAlbum){
        return this.launchDate.compareTo(((Album)otherAlbum).launchDate);
    }
    
    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(Album.DATEFORMAT);
        return  String.valueOf(this.id)+". "+
                "\""+this.title+"\" "+
                "by "+this.artist+
                Element.alignmentFarShift(
                        String.valueOf(this.id)+". "+"\""+this.title+"\" "+"by "+this.artist, false
                )+
                TimeFormat.secToString(this.getLength())+" -- launched the "+
                dateFormat.format(this.launchDate);
    }
}
