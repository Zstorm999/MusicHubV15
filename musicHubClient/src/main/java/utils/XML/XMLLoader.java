package utils.XML;

import business.dataStruct.DirectAccess;
import business.dataStruct.elements.Album;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.error.NoElementFoundException;
import utils.error.TooManyMatchingElementsException;
import utils.error.XMLReadException;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author CLEMENT Aimeric
 */
public class XMLLoader extends XMLSubtool{

    XMLLoader(XMLTool generalTool){
        super(generalTool);
    }
    
    public void load(DirectAccess dataAccess) throws XMLReadException, FileNotFoundException{
        try{
            this.loadElements(dataAccess);
            this.loadAlbums(dataAccess);
            this.loadPlaylists(dataAccess);
        }
        catch(ParseException|NoElementFoundException|TooManyMatchingElementsException|NullPointerException e){
            throw new XMLReadException("file unreachable");
        }
    }
    
    private void loadElements(DirectAccess dataAccess) throws XMLReadException, FileNotFoundException{
        NodeList nodes= this.generalTool.parseXMLFile(XMLTool.ELEMENTSFILEPATH);
        if (nodes==null)
            throw new XMLReadException("Failed to parse the file "+XMLTool.ELEMENTSFILEPATH);
        
        for(int i= 0; i<nodes.getLength(); i++){
            if(nodes.item(i).getNodeType()==Node.ELEMENT_NODE){
                Element currElem= (Element)nodes.item(i);
                if(currElem.getNodeName().equals("songs")){
                    NodeList xmlSongs= currElem.getElementsByTagName("song");
                    for(int j= 0; j<xmlSongs.getLength(); j++){
                        if(xmlSongs.item(j).getNodeType()== Node.ELEMENT_NODE){
                            Element currXMLSong= (Element)xmlSongs.item(j);
                            
                            int id= Integer.parseInt(currXMLSong.getElementsByTagName("id").item(0).getTextContent());
                            String title= currXMLSong.getElementsByTagName("title").item(0).getTextContent();
                            int length= Integer.parseInt(currXMLSong.getElementsByTagName("length").item(0).getTextContent());
                            String content= currXMLSong.getElementsByTagName("content").item(0).getTextContent();
                            String artist= currXMLSong.getElementsByTagName("artist").item(0).getTextContent();
                            String genre= currXMLSong.getElementsByTagName("genre").item(0).getTextContent();
                            
                            dataAccess.addSong(id, title, length, content, artist, genre);
                        }
                    }
                }
                else if(currElem.getNodeName().equals("audioBooks")){
                    NodeList xmlAdBooks= currElem.getElementsByTagName("audioBook");
                    for(int j= 0; j<xmlAdBooks.getLength(); j++){
                        if(xmlAdBooks.item(j).getNodeType()==Node.ELEMENT_NODE){
                            Element currXMLAdBook= (Element)xmlAdBooks.item(j);
                            
                            int id= Integer.parseInt(currXMLAdBook.getElementsByTagName("id").item(0).getTextContent());
                            String title= currXMLAdBook.getElementsByTagName("title").item(0).getTextContent();
                            int length= Integer.parseInt(currXMLAdBook.getElementsByTagName("length").item(0).getTextContent());
                            String content= currXMLAdBook.getElementsByTagName("content").item(0).getTextContent();
                            String author= currXMLAdBook.getElementsByTagName("author").item(0).getTextContent();
                            String category= currXMLAdBook.getElementsByTagName("category").item(0).getTextContent();
                            String language= currXMLAdBook.getElementsByTagName("language").item(0).getTextContent();
                            
                            dataAccess.addAudioBook(id, title, length, content, author, category, language);
                        }
                    }
                }
            }
        }
    }
    
    private void loadAlbums(DirectAccess dataAccess) throws XMLReadException, FileNotFoundException, ParseException, NoElementFoundException, TooManyMatchingElementsException{
        NodeList nodes= this.generalTool.parseXMLFile(XMLTool.ALBUMSFILEPATH);
        if (nodes==null)
            throw new XMLReadException("Failed to parse the file "+XMLTool.ALBUMSFILEPATH);
        
        for(int i= 0; i<nodes.getLength(); i++){
            if(nodes.item(i).getNodeType()==Node.ELEMENT_NODE){
                Element currElem= (Element)nodes.item(i);
                if(currElem.getNodeName().equals("album")){
                    int id= Integer.parseInt(currElem.getElementsByTagName("id").item(0).getTextContent());
                    String title= currElem.getElementsByTagName("title").item(0).getTextContent();
                    String artist= currElem.getElementsByTagName("artist").item(0).getTextContent();
                    Date launchDate= (new SimpleDateFormat(Album.DATEFORMAT)).parse(
                            currElem.getElementsByTagName("launchDate").item(0).getTextContent()
                    );
                    dataAccess.addAlbum(id, title, artist, launchDate);

                    Element xmlSongsDiv= (Element)currElem.getElementsByTagName("songs").item(0);
                    NodeList xmlSongs= xmlSongsDiv.getElementsByTagName("songId");
                    for(int j= 0; j<xmlSongs.getLength(); j++){
                        if(xmlSongs.item(j).getNodeType()==Node.ELEMENT_NODE){
                            Element currXMLSong= (Element)xmlSongs.item(j);
                            String songId= currXMLSong.getTextContent();

                            dataAccess.addSongToAlbum(String.valueOf(id), songId);
                        }
                    }
                }
            }
        }
    }
    
    private void loadPlaylists(DirectAccess dataAccess) throws XMLReadException, FileNotFoundException, ParseException, NoElementFoundException, TooManyMatchingElementsException{
        NodeList nodes= this.generalTool.parseXMLFile(XMLTool.PLAYLISTSFILEPATH);
        if (nodes==null)
            throw new XMLReadException("Failed to parse the file "+XMLTool.PLAYLISTSFILEPATH);
        
        for(int i= 0; i<nodes.getLength(); i++){
            if(nodes.item(i).getNodeType()==Node.ELEMENT_NODE){
                Element currElem= (Element)nodes.item(i);
                if(currElem.getNodeName().equals("playlist")){
                    int id= Integer.parseInt(currElem.getElementsByTagName("id").item(0).getTextContent());
                    String title= currElem.getElementsByTagName("title").item(0).getTextContent();

                    ArrayList<String> itemIDs= new ArrayList<>();
                    Element xmlElemsDiv= (Element)currElem.getElementsByTagName("musicalElements").item(0);
                    NodeList xmlElems= xmlElemsDiv.getElementsByTagName("itemId");
                    for(int j= 0; j<xmlElems.getLength(); j++){
                        Element currXMLElem= (Element)xmlElems.item(j);
                        String itemId= currXMLElem.getTextContent();

                        itemIDs.add(itemId);
                    }
                    dataAccess.addPlaylist(id, title, itemIDs);
                }
            }
        }
    }
}
