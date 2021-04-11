package business.UI.commands;

import business.UI.Command;
import business.dataStruct.DirectAccess;
import java.util.ArrayList;
import utils.Parsing;
import utils.error.NoElementFoundException;
import utils.error.TooManyMatchingElementsException;

/**
 * Self explanatory
 * @author CLEMENT Aimeric
 */
public class CmdAppendToAlbum extends Command{

    public CmdAppendToAlbum(Command cmnd){
        super(cmnd);
    }
    
    @Override
    protected String doStuff(String args, DirectAccess dataAccess){
        String ret;
        
        ArrayList<String> argsSplit= Parsing.split(args);
        if(argsSplit.size()==2){
            String albumName= argsSplit.get(0);
            String songName= argsSplit.get(1);
            try{
                dataAccess.addSongToAlbum(albumName, songName);
                ret= "The song "+songName+" has successfully been added to the album "+albumName;
            }
            catch(NoElementFoundException e){
                ret= "Error: The element "+e.getMessage()+" canot be found in the database";
            }
            catch(TooManyMatchingElementsException e){
                ret= "Error: The title "+e.getMessage()+" appears more than once in the database\r\n"+
                        "please use its ID instead";
            }
        }
        else{
            ret= "Error: wrong number of parameters\r\n"+
                    "(usage: \"" + this.taskParam() + " <Album> <Song>\")";
        }
        
        return ret+"\r\n";
    }

    @Override
    protected String taskParam(){
        return "+";
    }

    @Override
    public String description(){
        return "add an existing song to an album (usage: \"" + this.taskParam() + " <Album> <Song>\")";
    }
}