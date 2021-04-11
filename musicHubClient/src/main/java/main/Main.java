package main;

import business.UI.CLI;
import business.dataStruct.DirectAccess;
import utils.XML.XMLTool;
import java.io.FileNotFoundException;
import utils.error.XMLReadException;

/**
 *
 * @author CLEMENT Aimeric
 * @author DESCHAMPS Aymeric
 */
public class Main{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        XMLTool xmlTool= XMLTool.getInstance();
        DirectAccess data= new DirectAccess();

        /*load from XML files: */
        try{
            xmlTool.getLoaderTool().load(data);
            System.out.print("loading successful\r\n");
        }
        catch(XMLReadException e){
            System.out.print("Error: failed to load the last saved data\r\n");
        }
        catch(FileNotFoundException e){
            System.out.print("Error: the file "+e.getMessage()+" is unreachable\r\n");
        }
        
        /*launch UI: */
        (new CLI(data)).run();
    }
}