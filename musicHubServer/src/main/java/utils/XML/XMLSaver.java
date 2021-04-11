package utils.XML;

import business.dataStruct.DirectAccess;
import business.dataStruct.elements.Album;
import business.dataStruct.elements.AudioBook;
import business.dataStruct.elements.Playlist;
import business.dataStruct.elements.Song;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.error.XMLWriteException;

/**
 * @author CLEMENT Aimeric
 */
public class XMLSaver extends XMLSubtool{

    XMLSaver(XMLTool generalTool){
        super(generalTool);
    }
    
    public void saveAlbums(DirectAccess dataAccess) throws XMLWriteException{
        Document document= this.generalTool.createXMLDocument();
        if (document==null)
            throw new XMLWriteException("failed to create a new xml document");
        ArrayList<Album> albums= dataAccess.getAlbums();
        
        Element root= document.createElement("albums");
        
        albums.stream().map(currAlbum ->{
            
            /*base element: */
            Element elemAlbum= document.createElement("album");
            
            /*atributes: */
            Element id= document.createElement("id");
            Element title= document.createElement("title");
            Element length= document.createElement("length");
            Element artist= document.createElement("artist");
            Element launchDate= document.createElement("launchDate");
            Element songs= document.createElement("songs");
            
            id.appendChild(document.createTextNode(String.valueOf(currAlbum.getId())));
            title.appendChild(document.createTextNode(currAlbum.getTitle()));
            length.appendChild(document.createTextNode(String.valueOf(currAlbum.getLength())));
            artist.appendChild(document.createTextNode(currAlbum.getArtist()));
            launchDate.appendChild(document.createTextNode((new SimpleDateFormat(Album.DATEFORMAT)).format(currAlbum.getLaunchDate())));
            currAlbum.getSongs().forEach(currSongFromCurrAlbum ->{
                Element songId= document.createElement("songId");
                songId.appendChild(document.createTextNode(String.valueOf(currSongFromCurrAlbum.getId())));
                songs.appendChild(songId);
            });
            
            /*add the atributes to the album node: */
            elemAlbum.appendChild(id);
            elemAlbum.appendChild(title);
            elemAlbum.appendChild(length);
            elemAlbum.appendChild(artist);
            elemAlbum.appendChild(launchDate);
            elemAlbum.appendChild(songs);
            return elemAlbum;                                                   /*soft return*/               
        }).forEach(elemAlbum ->{
            
            /*add each album to the root: */
            root.appendChild(elemAlbum);                                        /*canot be done inside the functional loop*/
        });
        
        document.appendChild(root);
        this.generalTool.createXMLFile(document, XMLTool.ALBUMSFILEPATH);
    }
    
    public void savePlaylist(DirectAccess dataAccess) throws XMLWriteException{
        Document document= this.generalTool.createXMLDocument();
        if (document==null)
            throw new XMLWriteException("failed to create a new xml document");
        ArrayList<Playlist> playlists= dataAccess.getPlaylists();
        
        Element root= document.createElement("playlists");

        playlists.stream().map(currPlaylist ->{
            
            /*base element: */
            Element elemPlaylist= document.createElement("playlist");
            
            /*atributes: */
            Element id= document.createElement("id");
            Element title= document.createElement("title");
            Element musicalElements= document.createElement("musicalElements");
            
            id.appendChild(document.createTextNode(String.valueOf(currPlaylist.getId())));
            title.appendChild(document.createTextNode(currPlaylist.getTitle()));
            currPlaylist.getElements().forEach(currElementFromCurrPlaylist -> {
                Element itemId= document.createElement("itemId");
                itemId.appendChild(document.createTextNode(String.valueOf(currElementFromCurrPlaylist.getId())));
                musicalElements.appendChild(itemId);
            });
            
            /*add the atributes to the album node: */
            elemPlaylist.appendChild(id);
            elemPlaylist.appendChild(title);
            elemPlaylist.appendChild(musicalElements);

            return elemPlaylist;                                                /*soft return*/
        }).forEach(elemPlaylist ->{

            /*add each playlist to the root: */
            root.appendChild(elemPlaylist);                                     /*cannot be done inside the functional loop*/
        });
        
        document.appendChild(root);
        this.generalTool.createXMLFile(document, XMLTool.PLAYLISTSFILEPATH);
    }
    
    /**
     * Generate and write in an XML file the audio elements with the folowing structure:
     * 
     * {@code
     *  <elements>
     *      <songs>
     *          <song>
     *              <id>id</id>
     *              <title>title</title>
     *              <length>length</length>
     *              <content>content</content>
     *              <artist>artist</artist>
     *              <genre>genre</genre>
     *          </song>
     *      </songs>
     *      <audioBooks>
     *          <audioBook>
     *              <id>id</id>
     *              <title>title</title>
     *              <length>length</length>
     *              <content>content</content>
     *              <author>author</author>
     *              <category>category</category>
     *              <language>language</language>
     *          </audioBook>
     *      </audioBooks>
     *  </elements>
     * }
     * @param dataAccess
     * @throws XMLWriteException 
     */
    public void saveElements(DirectAccess dataAccess) throws XMLWriteException{
        Document document= this.generalTool.createXMLDocument();
        if (document==null)
            throw new XMLWriteException("failed to create a new xml document");
        ArrayList<Song> songs= dataAccess.getSongs();
        ArrayList<AudioBook> adBooks= dataAccess.getAudioBooks();
        
        Element root= document.createElement("elements");
        
        /*songs: */
        Element elemSongs= document.createElement("songs");
        songs.stream().map(currSong ->{
            
            /*base element: */
            Element elemSong= document.createElement("song");
            
            /*atributes: */
            Element id= document.createElement("id");
            Element title= document.createElement("title");
            Element length= document.createElement("length");
            Element content= document.createElement("content");
            Element artist= document.createElement("artist");
            Element genre= document.createElement("genre");
            
            id.appendChild(document.createTextNode(String.valueOf(currSong.getId())));
            title.appendChild(document.createTextNode(currSong.getTitle()));
            length.appendChild(document.createTextNode(String.valueOf(currSong.getLength())));
            content.appendChild(document.createTextNode(currSong.getContent()));
            artist.appendChild(document.createTextNode(currSong.getArtist()));
            genre.appendChild(document.createTextNode(currSong.getGenre().toString()));
            
            /*add the atributes to the album node: */
            elemSong.appendChild(id);
            elemSong.appendChild(title);
            elemSong.appendChild(length);
            elemSong.appendChild(content);
            elemSong.appendChild(artist);
            elemSong.appendChild(genre);
            
            return elemSong;                                                    /*soft return*/            
        }).forEach(elemSong ->{
            /*add each song to the root: */
            elemSongs.appendChild(elemSong);                                    /*canot be done inside the functional loop*/
        });
        root.appendChild(elemSongs);
        
        /*audio books: */
        Element elemAdBooks= document.createElement("audioBooks");
        adBooks.stream().map(currAdBook ->{
            
            /*base element: */
            Element elemAdBook= document.createElement("audioBook");
            
            /*atributes: */
            Element id= document.createElement("id");
            Element title= document.createElement("title");
            Element length= document.createElement("length");
            Element content= document.createElement("content");
            Element author= document.createElement("author");
            Element category= document.createElement("category");
            Element language= document.createElement("language");
            
            id.appendChild(document.createTextNode(String.valueOf(currAdBook.getId())));
            title.appendChild(document.createTextNode(currAdBook.getTitle()));
            length.appendChild(document.createTextNode(String.valueOf(currAdBook.getLength())));
            content.appendChild(document.createTextNode(currAdBook.getContent()));
            author.appendChild(document.createTextNode(currAdBook.getAuthor()));
            category.appendChild(document.createTextNode(currAdBook.getCategory().toString()));
            language.appendChild(document.createTextNode(currAdBook.getLanguage().toString()));
            
            /*add the atributes to the album node: */
            elemAdBook.appendChild(id);
            elemAdBook.appendChild(title);
            elemAdBook.appendChild(length);
            elemAdBook.appendChild(content);
            elemAdBook.appendChild(author);
            elemAdBook.appendChild(category);
            elemAdBook.appendChild(language);
            
            return elemAdBook;                                                  /*soft return*/            
        }).forEach(elemAdBook ->{
            /*add each audio-book to the root: */
            elemAdBooks.appendChild(elemAdBook);                                /*canot be done inside the functional loop*/
        });
        root.appendChild(elemAdBooks);
        
        document.appendChild(root);
        this.generalTool.createXMLFile(document, XMLTool.ELEMENTSFILEPATH);
    }
}
