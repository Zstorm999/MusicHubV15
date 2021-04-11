package utils.XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import utils.error.XMLReadException;
import utils.error.XMLWriteException;

/**
 * XMLTool is a singleton, constraining it to be instanciated no more than once.
 * this way, by using XMLTool for anything related to writing or reading, we
 * can ensure that there will be no conflict while editing a file.
 * (Honestly, such design patern does not make that much sense there since the
 * program is not multi-threaded. But in fact, I just wanted to try to do
 * a working singleton)
 * @author CLEMENT Aimeric
 */
public class XMLTool{
    private static final XMLTool singleton= new XMLTool();
    
    private static Transformer transformer;
    private static DocumentBuilder docBuilder;
    
    private static XMLLoader loader;
    private static XMLSaver saver;
    
    public static final String ALBUMSFILEPATH= "./musicHubClient/files/albums.xml";
    public static final String ELEMENTSFILEPATH= "./musicHubClient/files/elements.xml";
    public static final String PLAYLISTSFILEPATH= "./musicHubClient/files/playlists.xml";
    
    private XMLTool(){
        try{
            XMLTool.transformer = TransformerFactory.newInstance().newTransformer();
            XMLTool.docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch(TransformerException | ParserConfigurationException e){
            System.out.print("Fatal Error: "+e.getCause().toString());
        }
        XMLTool.loader= new XMLLoader(this);
        XMLTool.saver= new XMLSaver(this);
    }
    
    public static XMLTool getInstance(){return XMLTool.singleton; }
    
    public XMLLoader getLoaderTool(){return XMLTool.loader; }
    public XMLSaver getSaverTool(){return XMLTool.saver; }
    
    /*package private*/ NodeList parseXMLFile (String filePath) throws XMLReadException, FileNotFoundException{
        NodeList elementNodes;
        try {
            org.w3c.dom.Element root= XMLTool.docBuilder.parse(new File(filePath)).getDocumentElement();
            elementNodes= root.getChildNodes();
        }
        catch (org.xml.sax.SAXException e){
            throw new XMLReadException(filePath);
        }
        catch (IOException e){
            throw new FileNotFoundException(filePath);
        }
        
        return elementNodes;
    }
    
    /*package private*/ Document createXMLDocument(){
        return XMLTool.docBuilder.newDocument();
    }
    
    /*package private*/ void createXMLFile(Document document, String filePath) throws XMLWriteException{
        try{
            XMLTool.transformer.transform(new DOMSource(document), new StreamResult(new File(filePath)));
        }
        catch (TransformerException e){
            throw new XMLWriteException("file error for "+filePath);
        }
    }
}