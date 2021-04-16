package business.dataStruct;

import business.dataStruct.elements.Album;
import business.dataStruct.elements.AudioBook;
import business.dataStruct.elements.Playlist;
import business.dataStruct.elements.Song;
import business.dataStruct.enums.Category;
import business.dataStruct.enums.Genre;
import business.dataStruct.enums.Language;
import utils.error.NoElementFoundException;
import utils.error.TooManyMatchingElementsException;

import java.util.ArrayList;
import java.util.Date;


/**
 * Is both the database and the interface of this database
 * @author CLEMENT Aimeric
 * @author DESCHAMPS Aymeric
 */
public class DirectAccess{
    
    
    /*################### Elements: ###################*/
    
    private final ArrayList<Album> albums;
    private final ArrayList<Song> songs;
    private final ArrayList<Playlist> playlists;
    private final ArrayList<AudioBook> audioBooks;
    
    public DirectAccess() {
        this.albums= new ArrayList<>();
        this.songs= new ArrayList<>();
        this.playlists= new ArrayList<>();
        this.audioBooks= new ArrayList<>();
    }

    
    /*################### Accessors: ##################*/
    
    public ArrayList<Album> getAlbums(){return this.albums; }
    public ArrayList<Song> getSongs(){return this.songs; }
    public Song getSong(String title){
        //Looking for the particular title matching with the parameters
        for (Song song:this.songs) {
            if (song.getTitle().equals(title)){
                return song;
            }
        }
        return null;
    }
    public ArrayList<Playlist> getPlaylists(){return this.playlists; }
    public ArrayList<AudioBook> getAudioBooks(){return this.audioBooks; }
    
    public Album getAlbum(String identifier) throws TooManyMatchingElementsException, NoElementFoundException{
        return (Album) this.lookForIn(identifier, this.albums);
    }
    
    public ArrayList<Album> getMatchingAlbum(String title){
        ArrayList<Album> ret= new ArrayList<>();
        ArrayList<Element> found= this.lookForTitleIn(title, this.albums);
        
        found.forEach(currToBeCasted -> {
            ret.add((Album) currToBeCasted);
        });
        
        return ret;
    }
    
    
    /*################### Inserters: ##################*/
    
    public int addAlbum(String title, String artist, Date launchDate){
        int id= this.generateId();
        this.addAlbum(id, title, artist, launchDate);
        return id;
    }
    public void addAlbum(int id, String title, String artist, Date launchDate){
        this.albums.add(new Album(id, title, artist, launchDate));
    }
    
    public int addSong(String title, int length, String content, String artist, String genre) throws IllegalArgumentException{
        int id= this.generateId();
        this.addSong(id, title, length, content, artist, genre);
        return id;
    }
    public void addSong(int id, String title, int length, String content, String artist, String genre) throws IllegalArgumentException{
        this.songs.add(new Song(id, title, length, content, artist, Genre.valueOf(genre)));
    }
    
    public int addAudioBook(String title, int length, String content, String author, String category, String language){
        int id= this.generateId();
        this.addAudioBook(id, title, length, content, author, category, language);
        return id;
    }
    public void addAudioBook(int id, String title, int length, String content, String author, String category, String language){
        this.audioBooks.add(new AudioBook(id, title, length, content, author, Category.valueOf(category), Language.valueOf(language)));
    }
    
    public void addSongToAlbum(String albumIdentifier, String songIdentifier) throws NoElementFoundException, TooManyMatchingElementsException{
        ((Album) this.lookForIn(albumIdentifier, this.albums)).addSong((Song) this.lookForIn(songIdentifier, this.songs));
    }
    
    /**
     * Create a playlist containing references to any audio-element.
     * It prevent creating a playlist with objects that do not exists, but do not
     * prevent the use to add more than once a element in it.
     * @param playlistTitle
     * @param elements is the list of every elements, defined by their name or their id
     * @return the id of the newly created playlist
     * @throws NoElementFoundException when an element of the
     * playlist do not exist, since it is throw in a for each, only the first
     * invalid element is submited back trough the raised exception.
     * @throws TooManyMatchingElementsException when there is a
     * conflict with elements sharing the same name, since it is throw in a
     * for each, only the first invalid element is submited
     * back trough the raised exception.
     */
    public int addPlaylist(String playlistTitle, ArrayList<String> elements) throws NoElementFoundException, TooManyMatchingElementsException{
        int id= generateId();
        this.addPlaylist(id, playlistTitle, elements);
        return id;
    }
    public void addPlaylist(int id, String playlistTitle, ArrayList<String> elements) throws NoElementFoundException, TooManyMatchingElementsException{
        Playlist newPlaylist= new Playlist(id, playlistTitle);
        
        /*build an array containing both songs and audioBooks*/
        ArrayList<MusicalElement> fullSet;
        ArrayList<MusicalElement> songsConverted= new ArrayList<>();
        ArrayList<MusicalElement> adBooksConverted= new ArrayList<>();
        
        this.songs.forEach(currSong -> {songsConverted.add(currSong); });
        this.audioBooks.forEach(currAdBook -> {adBooksConverted.add(currAdBook); });
        fullSet= songsConverted;
        fullSet.addAll(adBooksConverted);
        
        /*insert musical elements into the playlist: */
        for(String currIdentifier: elements){
            newPlaylist.addElement((MusicalElement) this.lookForIn(currIdentifier, fullSet));
        }
        
        this.playlists.add(newPlaylist);
    }

    
    /*################### Purgators: ##################*/
    
    public void dellPlaylist(String identifier) throws NoElementFoundException, TooManyMatchingElementsException{
        this.playlists.remove((Playlist)this.lookForIn(identifier, this.playlists));
    }
    
    
    /*################### Tools: ######################*/
    
    /**
     * Look for an element using its ID or its name
     * @param identifier a name or an ID (thus, a name canot be a number)
     * @param elements the set of elements it must look into
     * @return an unique element
     * @throws NoElementFoundException
     * @throws TooManyMatchingElementsException 
     */
    private Element lookForIn(String identifier, ArrayList<? extends Element> elements) throws NoElementFoundException, TooManyMatchingElementsException{
        Element selected= null;
        try{                                                                /*if element identifier is an ID*/
            int elemId= Integer.parseInt(identifier);
            selected= this.lookForIdIn(elemId, elements);
            if (selected==null)
                throw new NoElementFoundException(identifier);
        }
        catch(NumberFormatException e){                                     /*else (if element identifier is a title)*/
            ArrayList<Element> results;
            results= this.lookForTitleIn(identifier, elements);
            if(results.size()>1){
                throw new TooManyMatchingElementsException(identifier);
            }
            else if(results.isEmpty()){
                throw new NoElementFoundException(identifier);
            }
            else{
                selected= results.get(0);
            }
        }
        
        return selected;
    }
    
    private ArrayList<Element> lookForTitleIn(String title, ArrayList<? extends Element> elements){
        ArrayList<Element> found= new ArrayList<>();
        found.clear();
        elements.stream().filter(currElem -> (
            currElem.getTitle().equals(title))).forEachOrdered(currElem -> {
                found.add(currElem);
            }
        );
        return found;
    }
    
    private Element lookForIdIn(int id, ArrayList<? extends Element> elements){
        Element found= null;
        for(Element currElem: elements){
            if(currElem.getId()==id)
                found= currElem;
        }
        return found;
    }
    
    /**
     * Aproximate the maximum existing id (+1) by taking the number of elements
     * as a starting point, and if this aproximated id is already taken (which 
     * means that some elements has been deleted), it browse the list of used
     * ip down, looking for any hole it can fill. In the worse case (that I
     * do not think possible), it will generate an unique signed ID.
     * @return generated id
     */
    private int generateId(){
        int newId;
        
        ArrayList<Element> everyElements= new ArrayList<>();
        everyElements.addAll(this.albums);
        everyElements.addAll(this.audioBooks);
        everyElements.addAll(this.playlists);
        everyElements.addAll(this.songs);
        
        newId= everyElements.size();
        int currsorId= newId+1;
        Boolean idAlreadyExist;
        do{
            currsorId--;
            idAlreadyExist= false;
            for(Element currElem: everyElements){
                if(currElem.id==currsorId){
                    idAlreadyExist= true;
                }
            }
        }
        while(idAlreadyExist);
        newId= currsorId;

        return newId;
    }
}
