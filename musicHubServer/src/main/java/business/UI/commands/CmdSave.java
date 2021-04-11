package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import utils.XML.XMLTool;
import utils.error.XMLWriteException;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdSave extends Command{

    private final XMLTool xmlTool;
    
    public CmdSave(Command cmnd){
        super(cmnd);
        this.xmlTool= XMLTool.getInstance();
    }

    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret= "Saving...\r\n";
        String shift= Command.shift();
        
        try{
            xmlTool.getSaverTool().saveElements(dataAccess);
            ret+= shift+String.valueOf(dataAccess.getSongs().size()+dataAccess.getAudioBooks().size())+
                    " musical elements successfully saved\r\n";
        } catch (XMLWriteException e) {
            ret+= shift+"Error: failed to save the elements\r\n";
        }
        try{
            xmlTool.getSaverTool().saveAlbums(dataAccess);
            ret+= shift+String.valueOf(dataAccess.getAlbums().size())+
                    " albums successfully saved\r\n";
        } catch (XMLWriteException e) {
            ret+= shift+"Error: failed to save the albums\r\n";
        }
        try{
            xmlTool.getSaverTool().savePlaylist(dataAccess);
            ret+= shift+String.valueOf(dataAccess.getPlaylists().size())+
                    " playlists successfully saved";
        } catch (XMLWriteException e) {
            ret+= shift+"Error: failed to save the playlists";
        }
        ret= "COMMAND UNSUPPORTED YET";
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "s";
    }

    @Override
    public String description(){
        return "save all";
    }
}